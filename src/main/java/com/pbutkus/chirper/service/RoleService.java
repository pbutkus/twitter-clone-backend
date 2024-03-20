package com.pbutkus.chirper.service;

import com.pbutkus.chirper.entity.Role;
import com.pbutkus.chirper.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role findByName(String name) {
        return roleRepository.findByName(name).orElse(null);
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }

}
