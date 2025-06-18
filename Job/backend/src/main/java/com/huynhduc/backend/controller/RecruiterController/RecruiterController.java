package com.huynhduc.backend.controller.RecruiterController;

import com.huynhduc.backend.config.VNPayConfig;
import com.huynhduc.backend.entity.JobportalsCompany;
import com.huynhduc.backend.entity.JobportalsJobpost;

import com.huynhduc.backend.entity.JobportalsPayment;
import com.huynhduc.backend.entity.JobportalsUser;
import com.huynhduc.backend.repository.PaymentRepo;
import com.huynhduc.backend.service.JobportalsCompany.JobportalsCompanyService;
import com.huynhduc.backend.service.JobportalsJobPost.JobportalsJobpostService;
import com.huynhduc.backend.service.JobportalsUser.JobportalsUserService;
import com.huynhduc.backend.utils.JWT.JWTService;
import com.huynhduc.backend.utils.Response.ErrorResponse;
import com.huynhduc.backend.utils.Response.SuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RestController
@RequestMapping("/users")
public class RecruiterController {

    @Autowired
    private JobportalsJobpostService jobPostService;

    @Autowired
    private JobportalsUserService userService;

    @Autowired
    private JobportalsCompanyService companyService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private VNPayConfig vnpayConfig;

    @PostMapping(value = "/{userId}/job-post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createJobPost(
            @PathVariable("userId") int userId,
            @ModelAttribute JobportalsJobpost jobPost,
            HttpServletRequest request) {

        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponse(401, "Thiếu token xác thực", null));
            }

            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);
            if (!jwtService.isTokenValid(token, username)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponse(401, "Token không hợp lệ", null));
            }

            JobportalsUser authenticatedUser = userService.getUserByUsername(username);
            if (authenticatedUser == null || authenticatedUser.getId() != userId) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ErrorResponse(403, "User ID không khớp với token", null));
            }

            Date deadline = jobPost.getDeadline();
            if (deadline == null) {
                return ResponseEntity.badRequest().body(
                        new ErrorResponse(400, "Deadline không được để trống", null));
            }

            jobPost.setRecruiter(authenticatedUser);
            jobPost.setIs_active(false);

            JobportalsJobpost createdPost = jobPostService.createJobPost(jobPost);

            Date createdDate = createdPost.getCreated_date();

            LocalDate startDate = createdDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            LocalDate endDate = deadline.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            long days = ChronoUnit.DAYS.between(startDate, endDate);
            if (days <= 0) days = 1;

            BigDecimal rate = new BigDecimal("20000");
            BigDecimal amount = rate.multiply(BigDecimal.valueOf(days));

            JobportalsPayment payment = new JobportalsPayment();
            payment.setUser(authenticatedUser);
            payment.setJobPost(createdPost);
            payment.setDays((int) days);
            payment.setRate(rate);
            payment.setAmount(amount);
            payment.setStatus("PENDING");
            payment.setPaymentMethod("VNPAY");

            payment = paymentRepo.save(payment);

            String paymentUrl = vnpayConfig.buildVNPayUrl(
                    amount.longValue(),
                    "Thanh toán bài đăng #" + createdPost.getId(),
                    String.valueOf(payment.getId()),
                    vnpayConfig.getIpAddress(request),
                    null
            );

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("jobPost", createdPost);
            responseData.put("paymentId", payment.getId());
            responseData.put("paymentUrl", paymentUrl);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new SuccessResponse<>(201, "Tạo bài đăng thành công - vui lòng thanh toán", responseData));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Lỗi khi tạo bài đăng", e.getMessage()));
        }
    }

    @GetMapping("/vnpay/payment-return")
    public ResponseEntity<?> handleVNPayReturn(@RequestParam Map<String, String> params) {
        try {
            String vnpTxnRef = params.get("vnp_TxnRef");
            String vnpResponseCode = params.get("vnp_ResponseCode");
            String vnpSecureHash = params.get("vnp_SecureHash");
            boolean isValid = vnpayConfig.validateSignature(params, vnpSecureHash);
            if (!isValid) {
                return ResponseEntity.badRequest().body(
                        new ErrorResponse(400, "Xác thực chữ ký không hợp lệ", null)
                );
            }

            if (!"00".equals(vnpResponseCode)) {
                return ResponseEntity.badRequest().body(
                        new ErrorResponse(400, "Thanh toán không thành công từ VNPay", null)
                );
            }

            Optional<JobportalsPayment> optionalPayment = paymentRepo.findById(Integer.parseInt(vnpTxnRef));
            if (optionalPayment.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ErrorResponse(404, "Không tìm thấy bản ghi thanh toán", null)
                );
            }

            JobportalsPayment payment = optionalPayment.get();

            if (!"SUCCESS".equalsIgnoreCase(payment.getStatus())) {
                payment.setStatus("SUCCESS");
                paymentRepo.save(payment);

                JobportalsJobpost jobPost = payment.getJobPost();
                jobPost.setIs_active(true);
                jobPostService.updateJobPost(jobPost.getId(),jobPost);
            }

            return ResponseEntity.ok(new SuccessResponse<>(
                    200,
                    "Thanh toán thành công - bài đăng đã được kích hoạt",
                    Map.of(
                            "paymentId", payment.getId(),
                            "jobPostId", payment.getJobPost().getId()
                    )
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Lỗi xử lý callback VNPay", e.getMessage()));
        }
    }

    @PatchMapping(value = "/job-posts/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateJobPost(
            @PathVariable int id,
            @ModelAttribute JobportalsJobpost jobPost) {

        try {
            JobportalsJobpost updated = jobPostService.updateJobPost(id, jobPost);
            return ResponseEntity.ok(new SuccessResponse<>(200, "Cập nhật thành công", updated));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorResponse(400, "Không thể cập nhật", e.getMessage())
            );
        }
    }

    @GetMapping("/{id}/job-post")
    public ResponseEntity<?> getJobPostDetail (@PathVariable int id) {
        List<JobportalsJobpost> listJob = jobPostService.getJobPostsByRecruiterId(id);
        if (listJob == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Not found",null)
            );
        }
        return ResponseEntity.ok(new SuccessResponse<>(200, "Lấy dữ liệu thành công", listJob));
    }

    @GetMapping("/{id}/company")
    public ResponseEntity<?> getByRecruiterId(@PathVariable int id) {
        try {
            JobportalsCompany company = companyService.getByRecruiterId(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new SuccessResponse<>(200,"Success", company)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500,"Cannot get company by recruiter id", e.getMessage()));
        }
    }
}
