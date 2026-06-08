package org.aitali.soukaina.examjee.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.aitali.soukaina.examjee.dtos.AuthResponseDTO;
import org.aitali.soukaina.examjee.dtos.LoginRequestDTO;
import org.aitali.soukaina.examjee.dtos.RegisterRequestDTO;
import org.aitali.soukaina.examjee.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentification", description = "Connexion et inscription avec JWT")
public class AuthRestController {

    private final AuthService authService;

    public AuthRestController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return authService.login(loginRequestDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO registerRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.register(registerRequestDTO));
    }
}
