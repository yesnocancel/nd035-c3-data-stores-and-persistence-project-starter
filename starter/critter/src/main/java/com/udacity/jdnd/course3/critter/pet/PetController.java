package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.UserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    PetService petService;

    @Autowired
    UserService userService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = petDTOToEntity(petDTO);
        Pet savedPet = petService.save(pet);
        PetDTO savedPetDTO = petEntityToDTO(savedPet);
        return savedPetDTO;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.findPetById(petId);
        return petEntityToDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> petList = petService.findAllPets();
        List<PetDTO> petDTOList = new ArrayList<>();
        for (Pet pe : petList) {
            petDTOList.add(petEntityToDTO(pe));
        }
        return petDTOList;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> petList = petService.findAllByCustomerId(ownerId);
        return petEntityListToDTOList(petList);
    }

    /*** private Entity / DTO conversion methods ***/

    private PetDTO petEntityToDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO, "ownerId");
        if (pet.getCustomer() != null) {
            petDTO.setOwnerId(pet.getCustomer().getId());
        }
        return petDTO;
    }

    private Pet petDTOToEntity(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet, "ownerId");
        if (petDTO.getOwnerId() > 0) {
            Customer owner = userService.findCustomerById(petDTO.getOwnerId());
            pet.setCustomer(owner);
        }
        return pet;
    }

    private List<PetDTO> petEntityListToDTOList(List<Pet> petList) {
        List<PetDTO> petDTOList = new ArrayList<>();
        for (Pet pe : petList) {
            petDTOList.add(petEntityToDTO(pe));
        }
        return petDTOList;
    }
}
