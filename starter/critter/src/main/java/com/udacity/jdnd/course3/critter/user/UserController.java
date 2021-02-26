package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.PetEntity;
import com.udacity.jdnd.course3.critter.pet.PetService;
import java.util.ArrayList;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    PetService petService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        CustomerEntity customerEntity = customerDTOToEntity(customerDTO);
        CustomerEntity savedCustomerEntity = userService.saveCustomer(customerEntity);
        CustomerDTO savedCustomerDTO = customerEntityToDTO(savedCustomerEntity);
        return savedCustomerDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        List<CustomerEntity> customerEntityList = userService.findAllCustomers();
        return customerEntityListToDTOList(customerEntityList);
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        CustomerEntity customerEntity = userService.findPetOwner(petId);
        return customerEntityToDTO(customerEntity);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeEntity employeeEntity = employeeDTOToEntity(employeeDTO);
        EmployeeEntity savedEmployeeEntity = userService.saveEmployee(employeeEntity);
        EmployeeDTO savedEmployeeDTO = employeeEntityToDTO(savedEmployeeEntity);
        return savedEmployeeDTO;
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        EmployeeEntity employeeEntity = userService.findEmployeeById(employeeId);
        return employeeEntityToDTO(employeeEntity);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        userService.setEmployeeAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<EmployeeEntity> employeeEntityList =
                userService.findSkilledEmployeesForDate(employeeDTO.getSkills(), employeeDTO.getDate());

        return employeeEntityListToDTOList(employeeEntityList);
    }

    /*** private Entity / DTO conversion methods ***/

    private CustomerDTO customerEntityToDTO(CustomerEntity customerEntity) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customerEntity, customerDTO, "petIds");

        List<PetEntity> petEntityList = customerEntity.getPets();
        if (petEntityList != null) {
            List<Long> petIdList = new ArrayList<>();
            for (PetEntity pe : petEntityList) {
                petIdList.add(pe.getId());
            }
            customerDTO.setPetIds(petIdList);
        }

        return customerDTO;
    }

    private CustomerEntity customerDTOToEntity(CustomerDTO customerDTO) {
        CustomerEntity customerEntity = new CustomerEntity();
        BeanUtils.copyProperties(customerDTO, customerEntity, "petIds");

        List<Long> petIds = customerDTO.getPetIds();
        if (petIds != null)
        {
            List<PetEntity> petEntityList = new ArrayList<>();
            for (Long petId : petIds) {
                PetEntity petEntity = petService.findPetById(petId);
                petEntityList.add(petEntity);
            }
            customerEntity.setPets(petEntityList);
        }

        return customerEntity;
    }

    private List<CustomerDTO> customerEntityListToDTOList(List<CustomerEntity> customerEntityList) {
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        for (CustomerEntity ce : customerEntityList) {
            customerDTOList.add(customerEntityToDTO(ce));
        }
        return customerDTOList;
    }

    private EmployeeDTO employeeEntityToDTO(EmployeeEntity employeeEntity) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employeeEntity, employeeDTO);
        return employeeDTO;
    }

    private EmployeeEntity employeeDTOToEntity(EmployeeDTO employeeDTO) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        BeanUtils.copyProperties(employeeDTO, employeeEntity);
        return employeeEntity;
    }

    private List<EmployeeDTO> employeeEntityListToDTOList(List<EmployeeEntity> employeeEntityList) {
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        for (EmployeeEntity ee : employeeEntityList) {
            employeeDTOList.add(employeeEntityToDTO(ee));
        }
        return employeeDTOList;
    }
}
