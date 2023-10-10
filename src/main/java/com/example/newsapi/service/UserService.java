package com.example.newsapi.service;

import com.example.newsapi.entity.user.User;
import com.example.newsapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public User save(User user){
        Optional<User> byUsername = userRepository.findByUsername(user.getUsername());
        if (byUsername.isPresent()) {
            throw new RuntimeException("");
        }
        user.setCreationDate(LocalDate.now());
        user.setLastEditDate(LocalDate.now());
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User login(User user){
        User authUser = findByUsername(user.getUsername());
        if (passwordEncoder().matches(user.getPassword(), authUser.getPassword())) {
            return authUser;
        }
        throw new RuntimeException("INCORRECT PASSWORD");
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username){
        return getCheckedUser(userRepository.findByUsername(username));
    }

    private User getCheckedUser(Optional<User> user){
        if (user.isEmpty()) {
            throw new RuntimeException("NOT FOUND");
        }
        return user.get();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("NOT_FOUND_MESSAGE");
        }
        return user.get();
    }
}
