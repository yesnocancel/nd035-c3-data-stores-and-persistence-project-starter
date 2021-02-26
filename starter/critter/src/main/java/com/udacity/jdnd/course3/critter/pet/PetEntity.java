package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.CustomerEntity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
public class PetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private PetType type;
    private String name;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity owner;

    private LocalDate birthDate;
    private String notes;

    /* Auxiliary methods for DTO conversion */

    public Long getOwnerId() {
        return owner.getId();
    }
}
