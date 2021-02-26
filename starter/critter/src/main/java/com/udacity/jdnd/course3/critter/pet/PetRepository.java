package com.udacity.jdnd.course3.critter.pet;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    @Query("SELECT p FROM Pet p WHERE p.customer.id = :customerId")
    List<Pet> findAllByCustomerId(Long customerId);
}
