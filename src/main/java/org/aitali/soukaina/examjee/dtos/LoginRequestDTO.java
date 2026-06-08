package org.aitali.soukaina.examjee.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank(message = "Le username est obligatoire")
        String username,
        @NotBlank(message = "Le mot de passe est obligatoire")
        String password
) {
}
