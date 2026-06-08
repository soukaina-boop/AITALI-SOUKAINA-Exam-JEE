package org.aitali.soukaina.examjee.mappers;

import org.aitali.soukaina.examjee.dtos.RemboursementDTO;
import org.aitali.soukaina.examjee.entities.Credit;
import org.aitali.soukaina.examjee.entities.Remboursement;

import java.util.List;

public final class RemboursementMapper {

    private RemboursementMapper() {
    }

    public static RemboursementDTO toDto(Remboursement remboursement) {
        if (remboursement == null) {
            return null;
        }

        Long creditId = remboursement.getCredit() == null ? null : remboursement.getCredit().getId();

        return new RemboursementDTO(
                remboursement.getId(),
                remboursement.getDate(),
                remboursement.getMontant(),
                remboursement.getType(),
                creditId
        );
    }

    public static Remboursement toEntity(RemboursementDTO remboursementDTO, Credit credit) {
        if (remboursementDTO == null) {
            return null;
        }

        Remboursement remboursement = new Remboursement();
        remboursement.setId(remboursementDTO.id());
        remboursement.setDate(remboursementDTO.date());
        remboursement.setMontant(remboursementDTO.montant());
        remboursement.setType(remboursementDTO.type());
        remboursement.setCredit(credit);

        return remboursement;
    }

    public static List<RemboursementDTO> toDtos(List<Remboursement> remboursements) {
        return remboursements.stream()
                .map(RemboursementMapper::toDto)
                .toList();
    }
}
