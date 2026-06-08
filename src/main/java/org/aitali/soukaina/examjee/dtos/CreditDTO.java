package org.aitali.soukaina.examjee.dtos;

import org.aitali.soukaina.examjee.entities.StatutCredit;
import org.aitali.soukaina.examjee.entities.TypeBien;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreditDTO(
        Long id,
        LocalDate dateDemande,
        StatutCredit statut,
        LocalDate dateAcceptation,
        BigDecimal montant,
        Integer dureeRemboursement,
        BigDecimal tauxInteret,
        Long clientId,
        TypeCredit typeCredit,
        String motif,
        TypeBien typeBien,
        String raisonSociale
) {
}
