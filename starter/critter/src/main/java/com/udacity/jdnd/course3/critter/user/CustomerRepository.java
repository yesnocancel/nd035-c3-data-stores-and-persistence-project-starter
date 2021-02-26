package com.udacity.jdnd.course3.critter.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c, Pet p WHERE c.id = p.customer.id AND p.id = :petId")
    Optional<Customer> findPetOwner(@Param("petId") Long petId);
}
