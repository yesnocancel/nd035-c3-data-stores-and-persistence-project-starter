package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.CustomerEntity;
import com.udacity.jdnd.course3.critter.user.EmployeeEntity;
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
        PetEntity petEntity = petDTOToEntity(petDTO);
        PetEntity savedPetEntity = petService.save(petEntity);
        PetDTO savedPetDTO = petEntityToDTO(savedPetEntity);
        return savedPetDTO;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        PetEntity petEntity = petService.findPetById(petId);
        return petEntityToDTO(petEntity);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<PetEntity> petEntityList = petService.findAllPets();
        List<PetDTO> petDTOList = new ArrayList<>();
        for (PetEntity pe : petEntityList) {
            petDTOList.add(petEntityToDTO(pe));
        }
        return petDTOList;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<PetEntity> petEntityList = petService.findAllByOwnerId(ownerId);
        return petEntityListToDTOList(petEntityList);
    }

    /*** private Entity / DTO conversion methods ***/

    private PetDTO petEntityToDTO(PetEntity petEntity) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(petEntity, petDTO);
        return petDTO;
    }

    private PetEntity petDTOToEntity(PetDTO petDTO) {
        PetEntity petEntity = new PetEntity();
        BeanUtils.copyProperties(petDTO, petEntity);
        CustomerEntity owner = userService.findCustomerById(petDTO.getOwnerId());
        petEntity.setOwner(owner);
        return petEntity;
    }

    private List<PetDTO> petEntityListToDTOList(List<PetEntity> petEntityList) {
        List<PetDTO> petDTOList = new ArrayList<>();
        for (PetEntity pe : petEntityList) {
            petDTOList.add(petEntityToDTO(pe));
        }
        return petDTOList;
    }
}
