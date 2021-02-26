package com.udacity.jdnd.course3.critter.pet;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<PetEntity, Long> {
    @Query("SELECT p FROM PetEntity p WHERE p.owner.id = :ownerId")
    List<PetEntity> findAllByOwnerId(Long ownerId);
}
