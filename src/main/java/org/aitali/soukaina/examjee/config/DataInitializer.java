package org.aitali.soukaina.examjee.config;

import org.aitali.soukaina.examjee.entities.Client;
import org.aitali.soukaina.examjee.entities.Credit;
import org.aitali.soukaina.examjee.entities.CreditImmobilier;
import org.aitali.soukaina.examjee.entities.CreditPersonnel;
import org.aitali.soukaina.examjee.entities.CreditProfessionnel;
import org.aitali.soukaina.examjee.entities.Remboursement;
import org.aitali.soukaina.examjee.entities.StatutCredit;
import org.aitali.soukaina.examjee.entities.TypeBien;
import org.aitali.soukaina.examjee.entities.TypeRemboursement;
import org.aitali.soukaina.examjee.repositories.ClientRepository;
import org.aitali.soukaina.examjee.repositories.CreditRepository;
import org.aitali.soukaina.examjee.repositories.RemboursementRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ClientRepository clientRepository;
    private final CreditRepository creditRepository;
    private final RemboursementRepository remboursementRepository;

    public DataInitializer(
            ClientRepository clientRepository,
            CreditRepository creditRepository,
            RemboursementRepository remboursementRepository
    ) {
        this.clientRepository = clientRepository;
        this.creditRepository = creditRepository;
        this.remboursementRepository = remboursementRepository;
    }

    @Override
    public void run(String... args) {
        if (clientRepository.count() > 0) {
            return;
        }

        Client client1 = new Client();
        client1.setNom("Soukaina Aitali");
        client1.setEmail("soukaina.aitali@email.com");

        Client client2 = new Client();
        client2.setNom("Ahmed Benali");
        client2.setEmail("ahmed.benali@email.com");

        CreditPersonnel creditPersonnel = new CreditPersonnel();
        remplirCredit(
                creditPersonnel,
                LocalDate.of(2026, 1, 10),
                StatutCredit.ACCEPTE,
                LocalDate.of(2026, 1, 15),
                "80000.00",
                48,
                "4.50"
        );
        creditPersonnel.setMotif("Achat de voiture");
        ajouterCredit(client1, creditPersonnel);
        ajouterRemboursement(creditPersonnel, creerRemboursement(
                LocalDate.of(2026, 2, 15),
                "1800.00",
                TypeRemboursement.MENSUALITE
        ));

        CreditImmobilier creditImmobilier = new CreditImmobilier();
        remplirCredit(
                creditImmobilier,
                LocalDate.of(2026, 2, 3),
                StatutCredit.EN_COURS,
                null,
                "650000.00",
                180,
                "3.90"
        );
        creditImmobilier.setTypeBien(TypeBien.APPARTEMENT);
        ajouterCredit(client1, creditImmobilier);

        CreditProfessionnel creditProfessionnel = new CreditProfessionnel();
        remplirCredit(
                creditProfessionnel,
                LocalDate.of(2026, 3, 5),
                StatutCredit.ACCEPTE,
                LocalDate.of(2026, 3, 12),
                "250000.00",
                84,
                "5.20"
        );
        creditProfessionnel.setMotif("Achat de materiel professionnel");
        creditProfessionnel.setRaisonSociale("Benali Digital Services");
        ajouterCredit(client2, creditProfessionnel);
        ajouterRemboursement(creditProfessionnel, creerRemboursement(
                LocalDate.of(2026, 4, 12),
                "3500.00",
                TypeRemboursement.MENSUALITE
        ));
        ajouterRemboursement(creditProfessionnel, creerRemboursement(
                LocalDate.of(2026, 5, 20),
                "10000.00",
                TypeRemboursement.REMBOURSEMENT_ANTICIPE
        ));

        clientRepository.saveAll(List.of(client1, client2));

        System.out.println("Clients crees : " + clientRepository.count());
        System.out.println("Credits crees : " + creditRepository.count());
        System.out.println("Remboursements crees : " + remboursementRepository.count());
    }

    private void remplirCredit(
            Credit credit,
            LocalDate dateDemande,
            StatutCredit statut,
            LocalDate dateAcceptation,
            String montant,
            Integer dureeRemboursement,
            String tauxInteret
    ) {
        credit.setDateDemande(dateDemande);
        credit.setStatut(statut);
        credit.setDateAcceptation(dateAcceptation);
        credit.setMontant(new BigDecimal(montant));
        credit.setDureeRemboursement(dureeRemboursement);
        credit.setTauxInteret(new BigDecimal(tauxInteret));
    }

    private void ajouterCredit(Client client, Credit credit) {
        credit.setClient(client);
        client.getCredits().add(credit);
    }

    private void ajouterRemboursement(Credit credit, Remboursement remboursement) {
        remboursement.setCredit(credit);
        credit.getRemboursements().add(remboursement);
    }

    private Remboursement creerRemboursement(
            LocalDate date,
            String montant,
            TypeRemboursement type
    ) {
        Remboursement remboursement = new Remboursement();
        remboursement.setDate(date);
        remboursement.setMontant(new BigDecimal(montant));
        remboursement.setType(type);
        return remboursement;
    }
}
