package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.UserService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetService {
    @Autowired
    PetRepository petRepository;

    @Autowired
    UserService userService;

    public Pet save(Pet pet) {
        Pet savedPet = petRepository.save(pet);

        if (savedPet.getCustomer() != null) {
            Customer customer = userService.findCustomerById(savedPet.getCustomer().getId());
            customer.getPets().add(savedPet);
        }
        return savedPet;
    }

    public List<Pet> findAllPets() {
        return petRepository.findAll();
    }

    public Pet findPetById(Long id) {
        return petRepository.findById(id).orElseThrow(PetNotFoundException::new);
    }

    public List<Pet> findAllByCustomerId(Long customerId) {
        return petRepository.findAllByCustomerId(customerId);
    }
}
