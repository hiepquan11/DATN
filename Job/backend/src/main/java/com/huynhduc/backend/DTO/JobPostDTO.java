package com.huynhduc.backend.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JobPostDTO {
    private String job_name;
    private String job_detail;
    private String job_requirement;
    private String benefits_enjoyed;
    private Integer quantity;
    private Integer gender_required;
    private Long experience_id;
    private String degree_required;
    private LocalDateTime deadline;
    private LocalDateTime created_date;
    private LocalDateTime updated_date;
    private Integer is_active;
    private Integer is_urgent_job;
    private String address;
    private String probationary_period;
    private String job_description;
    private String request_profile;
    private String contact_person_name;
    private String contact_address;
    private String contact_phone_number;
    private String contact_email;

    private CareerDTO career;
    private CityDTO city;
    private PositionDTO position;
    private SalaryDTO salary;
    private WorkingFormDTO working_form;
}
