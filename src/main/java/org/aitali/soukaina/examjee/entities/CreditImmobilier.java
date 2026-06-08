package org.aitali.soukaina.examjee.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "credits_immobiliers")
@DiscriminatorValue("IMMOBILIER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditImmobilier extends Credit {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeBien typeBien;
}
