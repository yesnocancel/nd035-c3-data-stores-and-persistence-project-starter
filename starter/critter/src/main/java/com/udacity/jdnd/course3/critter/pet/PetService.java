package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.CustomerEntity;
import com.udacity.jdnd.course3.critter.user.CustomerNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetService {
    @Autowired
    PetRepository petRepository;

    public PetEntity save(PetEntity petEntity) {
        return petRepository.save(petEntity);
    }

    public List<PetEntity> findAllPets() {
        return petRepository.findAll();
    }

    public PetEntity findPetById(Long id) {
        return petRepository.findById(id).orElseThrow(PetNotFoundException::new);
    }

    public List<PetEntity> findAllByOwnerId(Long ownerId) {
        return petRepository.findAllByOwnerId(ownerId);
    }
}
