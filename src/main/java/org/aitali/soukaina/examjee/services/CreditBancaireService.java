package org.aitali.soukaina.examjee.services;

import org.aitali.soukaina.examjee.dtos.ClientDTO;
import org.aitali.soukaina.examjee.dtos.CreditDTO;
import org.aitali.soukaina.examjee.dtos.RemboursementDTO;
import org.aitali.soukaina.examjee.entities.StatutCredit;

import java.util.List;

public interface CreditBancaireService {

    ClientDTO saveClient(ClientDTO clientDTO);

    ClientDTO getClient(Long id);

    List<ClientDTO> getClients();

    ClientDTO updateClient(Long id, ClientDTO clientDTO);

    void deleteClient(Long id);

    CreditDTO saveCredit(CreditDTO creditDTO);

    CreditDTO getCredit(Long id);

    List<CreditDTO> getCredits();

    List<CreditDTO> getCreditsByClient(Long clientId);

    List<CreditDTO> getCreditsByStatut(StatutCredit statut);

    CreditDTO updateStatutCredit(Long id, StatutCredit statut);

    void deleteCredit(Long id);

    RemboursementDTO saveRemboursement(RemboursementDTO remboursementDTO);

    List<RemboursementDTO> getRemboursementsByCredit(Long creditId);
}
