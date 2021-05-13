package com.example.musicjump.services;

import com.example.musicjump.DTO.RegistrationDTO;
import com.example.musicjump.DTO.SuccessDTO;
import com.example.musicjump.models.Role;
import com.example.musicjump.models.Skin;
import com.example.musicjump.models.User;
import com.example.musicjump.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findUserByUsername(username);
    }

    public void setSkinToAllUsers(Skin skin){
        for(User user:userRepo.findAll()){
            user.getSkins().add(skin);
            userRepo.save(user);
        }
    }

    public SuccessDTO addUser(RegistrationDTO registration, boolean admin){
        User userFromDb = userRepo.findUserByUsername(registration.getUsername());
        if (userFromDb != null) {
            return new SuccessDTO(false, "User exists");
        }
        if(!registration.getPassword().equals(registration.getConfirm_password())){
            return new SuccessDTO(false, "Different passwords");
        }
        User user = new User();
        user.setUsername(registration.getUsername());
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        user.setActive(true);
        if(admin){
            user.setRoles(Collections.singleton(Role.ADMIN));
        }
        else{
            user.setRoles(Collections.singleton(Role.USER));
        }
        System.out.println("saving");
        userRepo.save(user);
        return new SuccessDTO(true, "");
    }
}
