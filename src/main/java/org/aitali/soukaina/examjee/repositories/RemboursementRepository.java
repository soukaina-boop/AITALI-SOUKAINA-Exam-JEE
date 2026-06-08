package org.aitali.soukaina.examjee.repositories;

import org.aitali.soukaina.examjee.entities.Remboursement;
import org.aitali.soukaina.examjee.entities.TypeRemboursement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RemboursementRepository extends JpaRepository<Remboursement, Long> {

    List<Remboursement> findByCreditId(Long creditId);

    List<Remboursement> findByType(TypeRemboursement type);
}
