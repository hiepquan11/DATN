package com.huynhduc.backend.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
@Getter
public class VNPayConfig {

    @Value("${vnpay.pay-url}")
    private String vnp_PayUrl;

    @Value("${vnpay.return-url}")
    private String vnp_ReturnUrl;

    @Value("${vnpay.tmn-code}")
    private String vnp_TmnCode;

    @Value("${vnpay.secret-key}")
    private String secretKey;

    @Value("${vnpay.api-url}")
    private String vnp_ApiUrl;

    @Value("${vnpay.version}")
    private String vnp_Version;

    @Value("${vnpay.command}")
    private String vnp_Command;

    @Value("${vnpay.order-type}")
    private String orderType;

    public String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-FORWARDED-FOR");
        return (ip != null && !ip.isEmpty()) ? ip : request.getRemoteAddr();
    }

    public String getRandomNumber(int length) {
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(length);
        Random rnd = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public String buildQueryString(Map<String, String> params) {
        return params.entrySet().stream()
                .map(e -> URLEncoder.encode(e.getKey(), StandardCharsets.UTF_8) + "=" +
                        URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));
    }

    public String hmacSHA512(String key, String data) {
        try {
            Mac hmac512 = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            hmac512.init(secretKeySpec);
            byte[] hashBytes = hmac512.doFinal(data.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (Exception ex) {
            return "";
        }
    }

    public String hashAllFields(Map<String, String> fields) {
        List<String> fieldNames = new ArrayList<>(fields.keySet());
        Collections.sort(fieldNames);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fieldNames.size(); i++) {
            String key = fieldNames.get(i);
            String value = fields.get(key);
            if (value != null && !value.isEmpty()) {
                sb.append(key).append("=").append(value);
                if (i < fieldNames.size() - 1) {
                    sb.append("&");
                }
            }
        }
        return hmacSHA512(secretKey, sb.toString());
    }

    public String buildVNPayUrl(
            long amountVND,
            String orderInfo,
            String txnRef,
            String clientIp,
            @Nullable String customReturnUrl
    ) {
        try {
            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", vnp_Version);
            vnp_Params.put("vnp_Command", vnp_Command);
            vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
            vnp_Params.put("vnp_Amount", String.valueOf(amountVND * 100));
            vnp_Params.put("vnp_CurrCode", "VND");
            vnp_Params.put("vnp_TxnRef", txnRef);
            vnp_Params.put("vnp_OrderInfo", orderInfo);
            vnp_Params.put("vnp_OrderType", orderType);
            vnp_Params.put("vnp_Locale", "vn");
            vnp_Params.put("vnp_ReturnUrl", (customReturnUrl != null) ? customReturnUrl : vnp_ReturnUrl);
            vnp_Params.put("vnp_IpAddr", clientIp);

            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String createDate = formatter.format(calendar.getTime());
            vnp_Params.put("vnp_CreateDate", createDate);

            calendar.add(Calendar.MINUTE, 15);
            String expireDate = formatter.format(calendar.getTime());
            vnp_Params.put("vnp_ExpireDate", expireDate);

            // Sort fields
            List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
            Collections.sort(fieldNames);

            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();

            for (int i = 0; i < fieldNames.size(); i++) {
                String key = fieldNames.get(i);
                String value = vnp_Params.get(key);

                String encodedKey = URLEncoder.encode(key, StandardCharsets.US_ASCII.toString());
                String encodedValue = URLEncoder.encode(value, StandardCharsets.US_ASCII.toString());

                hashData.append(encodedKey).append("=").append(encodedValue);
                query.append(encodedKey).append("=").append(encodedValue);

                if (i < fieldNames.size() - 1) {
                    hashData.append("&");
                    query.append("&");
                }
            }

            String secureHash = hmacSHA512(secretKey, hashData.toString());
            query.append("&vnp_SecureHash=").append(secureHash);

            return vnp_PayUrl + "?" + query.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to build VNPay URL", e);
        }
    }

    public boolean validateSignature(Map<String, String> params, String vnpSecureHash) {
        System.out.println("🔍 Vào validateSignature");

        if (secretKey == null || secretKey.isEmpty()) {
            System.err.println("❌ SECRET_KEY is null or empty! Không thể xác thực chữ ký.");
            return false;
        }

        // 1. Lọc các tham số cần ký
        Map<String, String> filteredParams = new HashMap<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key.startsWith("vnp_") &&
                    !key.equals("vnp_SecureHash") &&
                    !key.equals("vnp_SecureHashType")) {
                filteredParams.put(key, value);
            }
        }

        // 2. Sắp xếp các key theo thứ tự alphabet
        List<String> fieldNames = new ArrayList<>(filteredParams.keySet());
        Collections.sort(fieldNames);

        // 3. Ghép các key=value thành chuỗi, có URLEncoder.encode như VNPay yêu cầu
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fieldNames.size(); i++) {
            String key = fieldNames.get(i);
            String value = filteredParams.get(key);

            try {
                sb.append(URLEncoder.encode(key, StandardCharsets.US_ASCII.toString()))
                        .append("=")
                        .append(URLEncoder.encode(value, StandardCharsets.US_ASCII.toString()));
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            if (i < fieldNames.size() - 1) {
                sb.append("&");
            }
        }

        String signData = sb.toString();
        String calculatedHash = hmacSHA512(secretKey, signData);

        // Debug output
        System.out.println("======= VNPay Signature Debug =======");
        System.out.println("SECRET_KEY     : " + secretKey);
        System.out.println("SIGN DATA      : " + signData);
        System.out.println("RECEIVED HASH  : " + vnpSecureHash);
        System.out.println("CALCULATED HASH: " + calculatedHash);
        System.out.println("======================================");

        return calculatedHash.equalsIgnoreCase(vnpSecureHash);
    }
}
