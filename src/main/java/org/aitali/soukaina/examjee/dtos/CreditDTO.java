package org.aitali.soukaina.examjee.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.aitali.soukaina.examjee.entities.StatutCredit;
import org.aitali.soukaina.examjee.entities.TypeBien;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreditDTO(
        Long id,
        @NotNull(message = "La date de demande est obligatoire")
        LocalDate dateDemande,
        @NotNull(message = "Le statut du credit est obligatoire")
        StatutCredit statut,
        LocalDate dateAcceptation,
        @NotNull(message = "Le montant est obligatoire")
        @Positive(message = "Le montant doit etre positif")
        BigDecimal montant,
        @NotNull(message = "La duree de remboursement est obligatoire")
        @Positive(message = "La duree de remboursement doit etre positive")
        Integer dureeRemboursement,
        @NotNull(message = "Le taux d'interet est obligatoire")
        @PositiveOrZero(message = "Le taux d'interet doit etre positif ou nul")
        BigDecimal tauxInteret,
        @NotNull(message = "Le client est obligatoire")
        Long clientId,
        @NotNull(message = "Le type de credit est obligatoire")
        TypeCredit typeCredit,
        String motif,
        TypeBien typeBien,
        String raisonSociale
) {
}
