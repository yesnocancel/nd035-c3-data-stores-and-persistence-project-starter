package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.PetEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String phoneNumber;
    private String notes;

    // @OneToMany(targetEntity = PetEntity.class, cascade = CascadeType.ALL)
    // @JoinColumn(name = "pet_id")
    @OneToMany(cascade = CascadeType.ALL)
    private List<PetEntity> pets;

    /* Auxiliary methods for DTO conversion */

    public List<Long> getPetIds() {
        List<Long> petIds = new ArrayList<>();
        for (PetEntity p : pets) {
            petIds.add(p.getId());
        }
        return petIds;
    }
}
