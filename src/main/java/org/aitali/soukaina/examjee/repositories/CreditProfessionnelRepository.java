package org.aitali.soukaina.examjee.repositories;

import org.aitali.soukaina.examjee.entities.CreditProfessionnel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditProfessionnelRepository extends JpaRepository<CreditProfessionnel, Long> {

    List<CreditProfessionnel> findByRaisonSocialeContainingIgnoreCase(String raisonSociale);
}
