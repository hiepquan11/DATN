package com.huynhduc.backend.service.JobportalsUser;

import com.huynhduc.backend.entity.JobportalsUser;
import com.huynhduc.backend.repository.JobportalsRoleRepo;
import com.huynhduc.backend.repository.JobportalsUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class JobportalsUserService implements JobportalsUserInterface {
    @Autowired
    private JobportalsUserRepo userRepo;

    @Autowired
    @Lazy
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public JobportalsUser register(JobportalsUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return user;
    }

    @Override
    public JobportalsUser login(String username, String password) {
        JobportalsUser user = userRepo.findByUsername(username);
        if(user == null ) {
            throw new UsernameNotFoundException("Không tìm thấy người dùng với tên đăng nhập: " + username);
        }
        if(!bCryptPasswordEncoder.matches(password, user.getPassword())){
            throw new RuntimeException("Mật khẩu không chính xác");
        }

        return user;
    }

    @Override
    public JobportalsUser findByUsername(String username) {
        return userRepo.findByUsername(username);
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
                role -> new SimpleGrantedAuthority(role.getRole_name())
        ).collect(Collectors.toList());
    }
}


