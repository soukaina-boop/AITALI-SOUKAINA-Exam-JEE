package org.aitali.soukaina.examjee.repositories;

import org.aitali.soukaina.examjee.entities.CreditPersonnel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditPersonnelRepository extends JpaRepository<CreditPersonnel, Long> {

    List<CreditPersonnel> findByMotifContainingIgnoreCase(String motif);
}
