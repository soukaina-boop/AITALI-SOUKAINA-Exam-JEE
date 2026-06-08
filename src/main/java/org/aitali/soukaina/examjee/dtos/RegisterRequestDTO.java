package org.aitali.soukaina.examjee.dtos;

import org.aitali.soukaina.examjee.entities.Role;

public record RegisterRequestDTO(
        String username,
        String email,
        String password,
        Role role
) {
}
