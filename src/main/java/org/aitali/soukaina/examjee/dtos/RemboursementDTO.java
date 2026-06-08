package org.aitali.soukaina.examjee.dtos;

import org.aitali.soukaina.examjee.entities.TypeRemboursement;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RemboursementDTO(
        Long id,
        LocalDate date,
        BigDecimal montant,
        TypeRemboursement type,
        Long creditId
) {
}
