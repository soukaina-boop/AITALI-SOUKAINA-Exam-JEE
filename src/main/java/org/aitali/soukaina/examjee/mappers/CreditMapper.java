package org.aitali.soukaina.examjee.mappers;

import org.aitali.soukaina.examjee.dtos.CreditDTO;
import org.aitali.soukaina.examjee.dtos.TypeCredit;
import org.aitali.soukaina.examjee.entities.Client;
import org.aitali.soukaina.examjee.entities.Credit;
import org.aitali.soukaina.examjee.entities.CreditImmobilier;
import org.aitali.soukaina.examjee.entities.CreditPersonnel;
import org.aitali.soukaina.examjee.entities.CreditProfessionnel;
import org.aitali.soukaina.examjee.entities.TypeBien;

import java.util.List;

public final class CreditMapper {

    private CreditMapper() {
    }

    public static CreditDTO toDto(Credit credit) {
        if (credit == null) {
            return null;
        }

        TypeCredit typeCredit = null;
        String motif = null;
        TypeBien typeBien = null;
        String raisonSociale = null;

        if (credit instanceof CreditPersonnel creditPersonnel) {
            typeCredit = TypeCredit.PERSONNEL;
            motif = creditPersonnel.getMotif();
        } else if (credit instanceof CreditImmobilier creditImmobilier) {
            typeCredit = TypeCredit.IMMOBILIER;
            typeBien = creditImmobilier.getTypeBien();
        } else if (credit instanceof CreditProfessionnel creditProfessionnel) {
            typeCredit = TypeCredit.PROFESSIONNEL;
            motif = creditProfessionnel.getMotif();
            raisonSociale = creditProfessionnel.getRaisonSociale();
        }

        Long clientId = credit.getClient() == null ? null : credit.getClient().getId();

        return new CreditDTO(
                credit.getId(),
                credit.getDateDemande(),
                credit.getStatut(),
                credit.getDateAcceptation(),
                credit.getMontant(),
                credit.getDureeRemboursement(),
                credit.getTauxInteret(),
                clientId,
                typeCredit,
                motif,
                typeBien,
                raisonSociale
        );
    }

    public static Credit toEntity(CreditDTO creditDTO, Client client) {
        if (creditDTO == null) {
            return null;
        }
        if (creditDTO.typeCredit() == null) {
            throw new IllegalArgumentException("Le type de credit est obligatoire");
        }

        Credit credit = switch (creditDTO.typeCredit()) {
            case PERSONNEL -> {
                CreditPersonnel creditPersonnel = new CreditPersonnel();
                creditPersonnel.setMotif(creditDTO.motif());
                yield creditPersonnel;
            }
            case IMMOBILIER -> {
                CreditImmobilier creditImmobilier = new CreditImmobilier();
                creditImmobilier.setTypeBien(creditDTO.typeBien());
                yield creditImmobilier;
            }
            case PROFESSIONNEL -> {
                CreditProfessionnel creditProfessionnel = new CreditProfessionnel();
                creditProfessionnel.setMotif(creditDTO.motif());
                creditProfessionnel.setRaisonSociale(creditDTO.raisonSociale());
                yield creditProfessionnel;
            }
        };

        credit.setId(creditDTO.id());
        credit.setDateDemande(creditDTO.dateDemande());
        credit.setStatut(creditDTO.statut());
        credit.setDateAcceptation(creditDTO.dateAcceptation());
        credit.setMontant(creditDTO.montant());
        credit.setDureeRemboursement(creditDTO.dureeRemboursement());
        credit.setTauxInteret(creditDTO.tauxInteret());
        credit.setClient(client);

        return credit;
    }

    public static List<CreditDTO> toDtos(List<Credit> credits) {
        return credits.stream()
                .map(CreditMapper::toDto)
                .toList();
    }
}
