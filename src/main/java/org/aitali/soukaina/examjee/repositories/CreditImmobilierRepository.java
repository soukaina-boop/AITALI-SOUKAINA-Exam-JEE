package org.aitali.soukaina.examjee.repositories;

import org.aitali.soukaina.examjee.entities.CreditImmobilier;
import org.aitali.soukaina.examjee.entities.TypeBien;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditImmobilierRepository extends JpaRepository<CreditImmobilier, Long> {

    List<CreditImmobilier> findByTypeBien(TypeBien typeBien);
}
