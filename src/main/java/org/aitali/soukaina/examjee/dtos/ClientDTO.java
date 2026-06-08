package org.aitali.soukaina.examjee.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClientDTO(
        Long id,
        @NotBlank(message = "Le nom est obligatoire")
        String nom,
        @NotBlank(message = "L'email est obligatoire")
        @Email(message = "L'email doit etre valide")
        String email
) {
}
