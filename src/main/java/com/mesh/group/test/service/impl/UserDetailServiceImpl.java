package com.mesh.group.test.service.impl;

import com.mesh.group.test.exception.UnauthorizedException;
import com.mesh.group.test.model.User;
import com.mesh.group.test.repository.UserRepository;
import com.mesh.group.test.request.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserIdByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User with this email doesn't found");
        }
        return new CurrentUser(user.get(), email);
    }

    public User loadLoggedInUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CurrentUser) {
            return ((CurrentUser) principal).getUser();
        } else if (principal instanceof org.springframework.security.core.userdetails.User) {
            String username = ((org.springframework.security.core.userdetails.User) principal).getUsername();
            return ((CurrentUser) (loadUserByUsername(username))).getUser();
        }
        throw new UnauthorizedException();
    }
}