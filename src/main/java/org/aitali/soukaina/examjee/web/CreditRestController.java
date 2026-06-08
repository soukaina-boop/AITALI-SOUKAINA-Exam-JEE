package org.aitali.soukaina.examjee.web;

import org.aitali.soukaina.examjee.dtos.CreditDTO;
import org.aitali.soukaina.examjee.dtos.RemboursementDTO;
import org.aitali.soukaina.examjee.entities.StatutCredit;
import org.aitali.soukaina.examjee.services.CreditBancaireService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/credits")
public class CreditRestController {

    private final CreditBancaireService creditBancaireService;

    public CreditRestController(CreditBancaireService creditBancaireService) {
        this.creditBancaireService = creditBancaireService;
    }

    @GetMapping
    public List<CreditDTO> getCredits(@RequestParam(required = false) StatutCredit statut) {
        if (statut == null) {
            return creditBancaireService.getCredits();
        }
        return creditBancaireService.getCreditsByStatut(statut);
    }

    @GetMapping("/{id}")
    public CreditDTO getCredit(@PathVariable Long id) {
        return creditBancaireService.getCredit(id);
    }

    @PostMapping
    public ResponseEntity<CreditDTO> saveCredit(@RequestBody CreditDTO creditDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(creditBancaireService.saveCredit(creditDTO));
    }

    @PatchMapping("/{id}/statut")
    public CreditDTO updateStatutCredit(@PathVariable Long id, @RequestParam StatutCredit statut) {
        return creditBancaireService.updateStatutCredit(id, statut);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCredit(@PathVariable Long id) {
        creditBancaireService.deleteCredit(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/remboursements")
    public List<RemboursementDTO> getRemboursementsByCredit(@PathVariable Long id) {
        return creditBancaireService.getRemboursementsByCredit(id);
    }
}
