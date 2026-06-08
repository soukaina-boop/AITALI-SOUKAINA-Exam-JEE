package org.aitali.soukaina.examjee.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.aitali.soukaina.examjee.entities.Role;

public record RegisterRequestDTO(
        @NotBlank(message = "Le username est obligatoire")
        String username,
        @NotBlank(message = "L'email est obligatoire")
        @Email(message = "L'email doit etre valide")
        String email,
        @NotBlank(message = "Le mot de passe est obligatoire")
        @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caracteres")
        String password,
        Role role
) {
}
