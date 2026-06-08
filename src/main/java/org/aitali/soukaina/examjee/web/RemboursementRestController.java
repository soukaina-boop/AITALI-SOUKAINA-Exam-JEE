package org.aitali.soukaina.examjee.web;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.aitali.soukaina.examjee.dtos.RemboursementDTO;
import org.aitali.soukaina.examjee.services.CreditBancaireService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/remboursements")
@Tag(name = "Remboursements", description = "Gestion des remboursements")
@SecurityRequirement(name = "bearerAuth")
public class RemboursementRestController {

    private final CreditBancaireService creditBancaireService;

    public RemboursementRestController(CreditBancaireService creditBancaireService) {
        this.creditBancaireService = creditBancaireService;
    }

    @PostMapping
    public ResponseEntity<RemboursementDTO> saveRemboursement(@Valid @RequestBody RemboursementDTO remboursementDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(creditBancaireService.saveRemboursement(remboursementDTO));
    }
}
