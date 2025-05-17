package com.huynhduc.backend.service.JobportalsUser;

import com.huynhduc.backend.DTO.AuthDTO;
import com.huynhduc.backend.Enum.ERole;
import com.huynhduc.backend.entity.JobportalsRole;
import com.huynhduc.backend.entity.JobportalsUser;
import com.huynhduc.backend.repository.JobportalsRoleRepo;
import com.huynhduc.backend.repository.JobportalsUserRepo;
import com.huynhduc.backend.utils.JWT.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class JobportalsUserService implements JobportalsUserInterface {
    @Autowired
    private JobportalsUserRepo userRepo;
    @Autowired
    private JobportalsRoleRepo roleRepo;
    @Autowired
    private JWTService jwtService;

    @Autowired
    @Lazy
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public JobportalsUser register(JobportalsUser user, boolean isRecruiter) {
        if (userRepo.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Tên người dùng đã tồn tại");
        }
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email đã tồn tại");
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        ERole roleName = isRecruiter ? ERole.RECRUITER : ERole.SEEKER;
        JobportalsRole role = roleRepo.findByRoleName(roleName.toString())
                        .orElseThrow(() -> new RuntimeException("Role không tồn tại"));


        user.setRoles(Collections.singleton(role));

        return userRepo.save(user);
    }

    @Override
    public AuthDTO login(String username, String password) {
        JobportalsUser user = userRepo.findByUsername(username);
        if(user == null || !bCryptPasswordEncoder.matches(password, user.getPassword()) ) {
            throw new RuntimeException("Tên đăng nhập hoặc mật khẩu không chính xác");
        }

        String accessToken = jwtService.generateAccessToken(username);
        String refreshToken = jwtService.generateRefreshToken(username);

        return new AuthDTO(accessToken, refreshToken, user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        JobportalsUser user = userRepo.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Không tìm thấy người dùng với tên đăng nhập: " + username);
        }
        return new User(
                user.getUsername(),
                user.getPassword(),
                getAuthorities(user)
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(JobportalsUser user) {
        return user.getRoles().stream().map(
                role -> new SimpleGrantedAuthority(role.getRoleName())
        ).collect(Collectors.toList());
    }
}


