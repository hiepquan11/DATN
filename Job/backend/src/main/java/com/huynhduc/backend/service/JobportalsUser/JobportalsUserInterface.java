package com.huynhduc.backend.service.JobportalsUser;

import com.huynhduc.backend.DTO.AuthDTO;
import com.huynhduc.backend.entity.JobportalsUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface JobportalsUserInterface extends UserDetailsService {
     JobportalsUser register(JobportalsUser user, boolean isRecruiter);
     AuthDTO login(String username, String password);
}
