package com.pbutkus.chirper.service;

import com.pbutkus.chirper.entity.Chirp;
import com.pbutkus.chirper.entity.User;
import com.pbutkus.chirper.repository.ChirpRepository;
import com.pbutkus.chirper.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChirpService {

    private final ChirpRepository chirpRepository;
    private final UserRepository userRepository;

    public Chirp save(Chirp chirp, Principal principal) {
        User user = userRepository
                .findByUsername(principal
                        .getName())
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + principal.getName()));

        chirp.setUser(user);
        Chirp savedChirp = chirpRepository.save(chirp);
        return savedChirp;
    }

    @Transactional
    public List<Chirp> getAll() {
        return chirpRepository.findAll();
    }

    public Chirp saveComment(UUID id, Chirp comment) {
        Optional<Chirp> chirpOptional = chirpRepository.findById(id);

        if (chirpOptional.isPresent()) {
            Chirp chirp = chirpOptional.get();
            Set<Chirp> comments = chirp.getComments();
            comments.add(comment);
            return chirpRepository.save(chirp);
        }

        return null;
    }

}
