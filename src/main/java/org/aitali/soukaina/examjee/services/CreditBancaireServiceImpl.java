package org.aitali.soukaina.examjee.services;

import org.aitali.soukaina.examjee.dtos.ClientDTO;
import org.aitali.soukaina.examjee.dtos.CreditDTO;
import org.aitali.soukaina.examjee.dtos.RemboursementDTO;
import org.aitali.soukaina.examjee.entities.Client;
import org.aitali.soukaina.examjee.entities.Credit;
import org.aitali.soukaina.examjee.entities.Remboursement;
import org.aitali.soukaina.examjee.entities.StatutCredit;
import org.aitali.soukaina.examjee.exceptions.ResourceNotFoundException;
import org.aitali.soukaina.examjee.mappers.ClientMapper;
import org.aitali.soukaina.examjee.mappers.CreditMapper;
import org.aitali.soukaina.examjee.mappers.RemboursementMapper;
import org.aitali.soukaina.examjee.repositories.ClientRepository;
import org.aitali.soukaina.examjee.repositories.CreditRepository;
import org.aitali.soukaina.examjee.repositories.RemboursementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class CreditBancaireServiceImpl implements CreditBancaireService {

    private final ClientRepository clientRepository;
    private final CreditRepository creditRepository;
    private final RemboursementRepository remboursementRepository;

    public CreditBancaireServiceImpl(
            ClientRepository clientRepository,
            CreditRepository creditRepository,
            RemboursementRepository remboursementRepository
    ) {
        this.clientRepository = clientRepository;
        this.creditRepository = creditRepository;
        this.remboursementRepository = remboursementRepository;
    }

    @Override
    public ClientDTO saveClient(ClientDTO clientDTO) {
        Client client = ClientMapper.toEntity(clientDTO);
        client.setId(null);
        return ClientMapper.toDto(clientRepository.save(client));
    }

    @Override
    @Transactional(readOnly = true)
    public ClientDTO getClient(Long id) {
        return ClientMapper.toDto(findClientById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientDTO> getClients() {
        return ClientMapper.toDtos(clientRepository.findAll());
    }

    @Override
    public ClientDTO updateClient(Long id, ClientDTO clientDTO) {
        Client client = findClientById(id);
        client.setNom(clientDTO.nom());
        client.setEmail(clientDTO.email());
        return ClientMapper.toDto(clientRepository.save(client));
    }

    @Override
    public void deleteClient(Long id) {
        Client client = findClientById(id);
        clientRepository.delete(client);
    }

    @Override
    public CreditDTO saveCredit(CreditDTO creditDTO) {
        if (creditDTO.clientId() == null) {
            throw new IllegalArgumentException("Le client du credit est obligatoire");
        }

        Client client = findClientById(creditDTO.clientId());
        Credit credit = CreditMapper.toEntity(creditDTO, client);
        credit.setId(null);
        return CreditMapper.toDto(creditRepository.save(credit));
    }

    @Override
    @Transactional(readOnly = true)
    public CreditDTO getCredit(Long id) {
        return CreditMapper.toDto(findCreditById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CreditDTO> getCredits() {
        return CreditMapper.toDtos(creditRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CreditDTO> getCreditsByClient(Long clientId) {
        findClientById(clientId);
        return CreditMapper.toDtos(creditRepository.findByClientId(clientId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CreditDTO> getCreditsByStatut(StatutCredit statut) {
        return CreditMapper.toDtos(creditRepository.findByStatut(statut));
    }

    @Override
    public CreditDTO updateStatutCredit(Long id, StatutCredit statut) {
        Credit credit = findCreditById(id);
        credit.setStatut(statut);

        if (statut == StatutCredit.ACCEPTE && credit.getDateAcceptation() == null) {
            credit.setDateAcceptation(LocalDate.now());
        } else if (statut != StatutCredit.ACCEPTE) {
            credit.setDateAcceptation(null);
        }

        return CreditMapper.toDto(creditRepository.save(credit));
    }

    @Override
    public void deleteCredit(Long id) {
        Credit credit = findCreditById(id);
        creditRepository.delete(credit);
    }

    @Override
    public RemboursementDTO saveRemboursement(RemboursementDTO remboursementDTO) {
        if (remboursementDTO.creditId() == null) {
            throw new IllegalArgumentException("Le credit du remboursement est obligatoire");
        }

        Credit credit = findCreditById(remboursementDTO.creditId());
        Remboursement remboursement = RemboursementMapper.toEntity(remboursementDTO, credit);
        remboursement.setId(null);
        return RemboursementMapper.toDto(remboursementRepository.save(remboursement));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RemboursementDTO> getRemboursementsByCredit(Long creditId) {
        findCreditById(creditId);
        return RemboursementMapper.toDtos(remboursementRepository.findByCreditId(creditId));
    }

    private Client findClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client introuvable avec id : " + id));
    }

    private Credit findCreditById(Long id) {
        return creditRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Credit introuvable avec id : " + id));
    }
}
