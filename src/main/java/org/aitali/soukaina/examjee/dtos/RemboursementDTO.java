package org.aitali.soukaina.examjee.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.aitali.soukaina.examjee.entities.TypeRemboursement;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RemboursementDTO(
        Long id,
        @NotNull(message = "La date du remboursement est obligatoire")
        LocalDate date,
        @NotNull(message = "Le montant est obligatoire")
        @Positive(message = "Le montant doit etre positif")
        BigDecimal montant,
        @NotNull(message = "Le type de remboursement est obligatoire")
        TypeRemboursement type,
        @NotNull(message = "Le credit est obligatoire")
        Long creditId
) {
}
