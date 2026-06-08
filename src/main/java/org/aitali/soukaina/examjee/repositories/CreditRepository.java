package org.aitali.soukaina.examjee.repositories;

import org.aitali.soukaina.examjee.entities.Credit;
import org.aitali.soukaina.examjee.entities.StatutCredit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, Long> {

    List<Credit> findByClientId(Long clientId);

    List<Credit> findByStatut(StatutCredit statut);
}
