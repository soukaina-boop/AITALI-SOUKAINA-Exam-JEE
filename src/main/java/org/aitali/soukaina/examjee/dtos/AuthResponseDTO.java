package org.aitali.soukaina.examjee.dtos;

import java.util.List;

public record AuthResponseDTO(
        String token,
        String type,
        String username,
        List<String> roles
) {
}
