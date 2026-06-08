package org.aitali.soukaina.examjee.config;

import org.aitali.soukaina.examjee.entities.AppUser;
import org.aitali.soukaina.examjee.entities.Client;
import org.aitali.soukaina.examjee.entities.Credit;
import org.aitali.soukaina.examjee.entities.CreditImmobilier;
import org.aitali.soukaina.examjee.entities.CreditPersonnel;
import org.aitali.soukaina.examjee.entities.CreditProfessionnel;
import org.aitali.soukaina.examjee.entities.Remboursement;
import org.aitali.soukaina.examjee.entities.Role;
import org.aitali.soukaina.examjee.entities.StatutCredit;
import org.aitali.soukaina.examjee.entities.TypeBien;
import org.aitali.soukaina.examjee.entities.TypeRemboursement;
import org.aitali.soukaina.examjee.repositories.AppUserRepository;
import org.aitali.soukaina.examjee.repositories.ClientRepository;
import org.aitali.soukaina.examjee.repositories.CreditRepository;
import org.aitali.soukaina.examjee.repositories.RemboursementRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AppUserRepository appUserRepository;
    private final ClientRepository clientRepository;
    private final CreditRepository creditRepository;
    private final RemboursementRepository remboursementRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(
            AppUserRepository appUserRepository,
            ClientRepository clientRepository,
            CreditRepository creditRepository,
            RemboursementRepository remboursementRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.appUserRepository = appUserRepository;
        this.clientRepository = clientRepository;
        this.creditRepository = creditRepository;
        this.remboursementRepository = remboursementRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        initialiserUtilisateurs();

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

    private void initialiserUtilisateurs() {
        if (appUserRepository.count() > 0) {
            return;
        }

        AppUser admin = creerUtilisateur("admin", "admin@email.com", "admin123", Role.ROLE_ADMIN);
        AppUser employe = creerUtilisateur("employe", "employe@email.com", "employe123", Role.ROLE_EMPLOYE);
        AppUser client = creerUtilisateur("client", "client@email.com", "client123", Role.ROLE_CLIENT);

        appUserRepository.saveAll(List.of(admin, employe, client));

        System.out.println("Utilisateurs de test crees : admin/admin123, employe/employe123, client/client123");
    }

    private AppUser creerUtilisateur(String username, String email, String password, Role role) {
        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setEmail(email);
        appUser.setPassword(passwordEncoder.encode(password));
        appUser.setEnabled(true);
        appUser.setRoles(new HashSet<>(Set.of(role)));
        return appUser;
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
