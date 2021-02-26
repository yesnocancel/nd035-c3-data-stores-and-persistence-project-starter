package com.udacity.jdnd.course3.critter.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    @Query("SELECT c FROM CustomerEntity c, PetEntity p WHERE c.id = p.owner.id AND p.id = :petId")
    Optional<CustomerEntity> findPetOwner(@Param("petId") Long petId);
}
