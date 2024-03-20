package com.pbutkus.chirper.service;

import com.pbutkus.chirper.entity.User;
import com.pbutkus.chirper.repository.ChirpRepository;
import com.pbutkus.chirper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ChirpRepository chirpRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        users.forEach(user -> Hibernate.initialize(user.getChirps()));
        return users;
    }

    public User findById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist"));
    }

    public User findBySub(String sub) {
        return userRepository.findBySub(sub).orElse(null);
    }

    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }
}
