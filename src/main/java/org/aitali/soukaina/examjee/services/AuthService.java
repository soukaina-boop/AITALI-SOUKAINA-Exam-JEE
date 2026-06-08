package org.aitali.soukaina.examjee.services;

import org.aitali.soukaina.examjee.dtos.AuthResponseDTO;
import org.aitali.soukaina.examjee.dtos.LoginRequestDTO;
import org.aitali.soukaina.examjee.dtos.RegisterRequestDTO;
import org.aitali.soukaina.examjee.entities.AppUser;
import org.aitali.soukaina.examjee.entities.Role;
import org.aitali.soukaina.examjee.repositories.AppUserRepository;
import org.aitali.soukaina.examjee.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
            AppUserRepository appUserRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.username(),
                        loginRequestDTO.password()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return buildAuthResponse(userDetails);
    }

    public AuthResponseDTO register(RegisterRequestDTO registerRequestDTO) {
        if (appUserRepository.existsByUsername(registerRequestDTO.username())) {
            throw new IllegalArgumentException("Ce username existe deja");
        }
        if (appUserRepository.existsByEmail(registerRequestDTO.email())) {
            throw new IllegalArgumentException("Cet email existe deja");
        }

        Role role = registerRequestDTO.role() == null ? Role.ROLE_CLIENT : registerRequestDTO.role();

        AppUser appUser = new AppUser();
        appUser.setUsername(registerRequestDTO.username());
        appUser.setEmail(registerRequestDTO.email());
        appUser.setPassword(passwordEncoder.encode(registerRequestDTO.password()));
        appUser.setEnabled(true);
        appUser.setRoles(new HashSet<>(Set.of(role)));

        AppUser savedUser = appUserRepository.save(appUser);

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(savedUser.getUsername())
                .password(savedUser.getPassword())
                .authorities(savedUser.getRoles().stream().map(Role::name).toArray(String[]::new))
                .build();

        return buildAuthResponse(userDetails);
    }

    private AuthResponseDTO buildAuthResponse(UserDetails userDetails) {
        String token = jwtService.generateToken(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return new AuthResponseDTO(
                token,
                "Bearer",
                userDetails.getUsername(),
                roles
        );
    }
}
