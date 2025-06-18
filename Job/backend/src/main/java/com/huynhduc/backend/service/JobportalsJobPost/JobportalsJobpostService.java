package com.huynhduc.backend.service.JobportalsJobPost;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huynhduc.backend.entity.JobportalsJobpost;
import com.huynhduc.backend.entity.JobportalsUser;
import com.huynhduc.backend.repository.JobportalsJobPostRepo;
import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JobportalsJobpostService implements JobportalsJobPostInterface {

    @Autowired
    private JobportalsJobPostRepo repository;

    @Override
    public List<JobportalsJobpost> getAllJobPosts() {
        return repository.findAll();
    }

    @Override
    public JobportalsJobpost getJobPostById(int id) {
       JobportalsJobpost optional = repository.findById(id);
        return optional;
    }

    @Override
    public JobportalsJobpost createJobPost(JobportalsJobpost jobPost) {

        jobPost.setCreated_date(new Date());
        jobPost.setUpdated_date(new Date());

        return repository.save(jobPost);
    }

    @Override
    public JobportalsJobpost updateJobPost(int id, JobportalsJobpost jobPost) {
        if (repository.existsById(id)) {
            jobPost.setId(id);
            return repository.save(jobPost);
        }
        return null;
    }

    public List<JobportalsJobpost> getJobPostsByRecruiterId(int id) {
        return repository.findByRecruiterIdOrderByCreatedDate(id);
    }

    @Override
    public Page<JobportalsJobpost> getSemanticFilteredJobPosts(String keyword, Pageable pageable) {
        String flaskUrl = "https://7716-35-247-32-139.ngrok-free.app/search?keyword=" + keyword + "&top_n=100";
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<List> response = restTemplate.getForEntity(flaskUrl, List.class);
            List<Map<String, Object>> aiResults = response.getBody();

            if (aiResults == null || aiResults.isEmpty()) {
                return Page.empty();
            }

            List<Integer> ids = aiResults.stream()
                    .map(item -> ((Number) item.get("JobID")).intValue())
                    .collect(Collectors.toList());

            return repository.findByIdIn(ids, pageable);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi gọi semantic search: " + e.getMessage());
        }
    }

    @Override
    public void deleteJobPost(int id) {
        repository.deleteById(id);
    }

//    @Override
//    public Page<JobportalsJobpost> getJobPostsWithFilters(Map<String, String> filters, Pageable pageable) {
//        final List<Integer>[] semanticMatchedIds = new List[]{null};
//
//        String semanticKeyword = filters.get("keyword");
//        if (semanticKeyword != null && !semanticKeyword.trim().isEmpty()) {
//            try {
//                String flaskUrl = "https://7716-35-247-32-139.ngrok-free.app/search?keyword=" + semanticKeyword + "&top_n=100";
//                RestTemplate restTemplate = new RestTemplate();
//                ResponseEntity<List> response = restTemplate.getForEntity(flaskUrl, List.class);
//                List<Map<String, Object>> aiResults = response.getBody();
//
//                if (aiResults != null && !aiResults.isEmpty()) {
//                    semanticMatchedIds[0] = aiResults.stream()
//                            .map(item -> ((Number) item.get("JobID")).intValue())
//                            .collect(Collectors.toList());
//                } else {
//                    return Page.empty();
//                }
//            } catch (Exception e) {
//                throw new RuntimeException("Lỗi gọi semantic search: " + e.getMessage(), e);
//            }
//        }
//
//        Specification<JobportalsJobpost> spec = (root, query, cb) -> {
//            List<Predicate> predicates = new ArrayList<>();
//
//            // sử dụng semanticMatchedIds[0] thay vì biến thường
//            if (semanticMatchedIds[0] != null && !semanticMatchedIds[0].isEmpty()) {
//                predicates.add(root.get("id").in(semanticMatchedIds[0]));
//            }
//
//            String keyword = filters.get("keyword");
//            if (keyword != null && !keyword.trim().isEmpty()) {
//                predicates.add(cb.like(cb.lower(root.get("job_name")), "%" + keyword.trim().toLowerCase() + "%"));
//            }
//
//            String city = filters.get("city");
//            if (isNumeric(city)) {
//                predicates.add(cb.equal(root.get("city").get("id"), Integer.parseInt(city)));
//            }
//
//            String experience = filters.get("experience");
//            if (isNumeric(experience)) {
//                predicates.add(cb.equal(root.get("experience").get("id"), Integer.parseInt(experience)));
//            }
//
//            String salary = filters.get("salary");
//            if (isNumeric(salary)) {
//                predicates.add(cb.equal(root.get("salary").get("id"), Integer.parseInt(salary)));
//            }
//
//            String position = filters.get("position");
//            if (isNumeric(position)) {
//                predicates.add(cb.equal(root.get("position").get("id"), Integer.parseInt(position)));
//            }
//
//            String workingForm = filters.get("working_form");
//            if (isNumeric(workingForm)) {
//                predicates.add(cb.equal(root.get("working_form").get("id"), Integer.parseInt(workingForm)));
//            }
//
//            String career = filters.get("career");
//            if (isNumeric(career)) {
//                predicates.add(cb.equal(root.get("career").get("id"), Integer.parseInt(career)));
//            }
//
//            query.orderBy(cb.desc(root.get("created_date")));
//            return cb.and(predicates.toArray(new Predicate[0]));
//        };
//
//        return repository.findAll(spec, pageable);
//    }

    @Override
    public Page<JobportalsJobpost> getJobPostsWithFilters(Map<String, String> filters, Pageable pageable) {
        String keyword = filters.get("keyword");
//        String useSemantic = filters.getOrDefault("semantic", "false");

        if ( keyword != null && !keyword.isBlank()) {
            List<Integer> ids = getSemanticSearchIds(keyword);
            if (ids.isEmpty()) return Page.empty();
            Specification<JobportalsJobpost> spec = (root, query, cb) -> root.get("id").in(ids);
            return repository.findAll(spec, pageable);
        }

        // 🔁 Fallback: full filter logic như bạn đã có
        Specification<JobportalsJobpost> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (keyword != null && !keyword.trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("job_name")), "%" + keyword.trim().toLowerCase() + "%"));
            }
            // thêm các filter khác...
            String city = filters.get("city");
            if (isNumeric(city)) {
                predicates.add(cb.equal(root.get("city").get("id"), Integer.parseInt(city)));
            }

            // các filter còn lại...
            query.orderBy(cb.desc(root.get("created_date")));
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return repository.findAll(spec, pageable);
    }

    private List<Integer> getSemanticSearchIds(String keyword) {
        try {
            if (keyword == null || keyword.trim().isEmpty()) return Collections.emptyList();

            String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
            String apiUrl = "https://e74e-35-197-132-232.ngrok-free.app/search?keyword=" + encodedKeyword;

            System.out.println(keyword);
            System.out.println(encodedKeyword);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root = objectMapper.readTree(response.body());
                JsonNode content = root.path("data").path("content");

                List<Integer> ids = new ArrayList<>();
                for (JsonNode item : content) {
                    if (item.has("id")) {
                        ids.add(item.path("id").asInt());
                    }
                }

                return ids;
            } else {
                System.err.println("❌ Semantic API trả về mã lỗi: " + response.statusCode());
            }
        } catch (Exception e) {
            System.err.println("❌ Lỗi khi gọi semantic search: " + e.getMessage());
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    private boolean isNumeric(String str) {
        if (str == null || str.trim().isEmpty()) return false;
        try {
            Integer.parseInt(str.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
