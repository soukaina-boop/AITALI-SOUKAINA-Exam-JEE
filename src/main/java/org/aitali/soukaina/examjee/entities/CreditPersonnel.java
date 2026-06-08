package org.aitali.soukaina.examjee.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "credits_personnels")
@DiscriminatorValue("PERSONNEL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditPersonnel extends Credit {

    @Column(nullable = false)
    private String motif;
}
