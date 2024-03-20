package com.pbutkus.chirper.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chirps")
public class Chirp {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Column(nullable = false, length = 280)
    private String content;

    @Column(nullable = false)
    private LocalDateTime datePosted = LocalDateTime.now();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "chirp_likes",
            joinColumns = @JoinColumn(name = "chirp_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnore
    private Set<User> likedBy;

    @OneToMany(mappedBy = "parentChirp")
    private Set<Chirp> comments;

    @ManyToOne
    @JoinColumn(name = "parent_chirp_id")
    private Chirp parentChirp;

}
