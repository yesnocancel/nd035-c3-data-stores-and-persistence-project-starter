package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
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
        Customer customer = customerDTOToEntity(customerDTO);
        Customer savedCustomer = userService.saveCustomer(customer);
        CustomerDTO savedCustomerDTO = customerEntityToDTO(savedCustomer);
        return savedCustomerDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customerList = userService.findAllCustomers();
        return customerEntityListToDTOList(customerList);
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer customer = userService.findPetOwner(petId);
        return customerEntityToDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeDTOToEntity(employeeDTO);
        Employee savedEmployee = userService.saveEmployee(employee);
        EmployeeDTO savedEmployeeDTO = employeeEntityToDTO(savedEmployee);
        return savedEmployeeDTO;
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = userService.findEmployeeById(employeeId);
        return employeeEntityToDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        userService.setEmployeeAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employeeList =
                userService.findSkilledEmployeesForDate(employeeDTO.getSkills(), employeeDTO.getDate());

        return employeeEntityListToDTOList(employeeList);
    }

    /*** private Entity / DTO conversion methods ***/

    private CustomerDTO customerEntityToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO, "petIds");

        List<Pet> petList = customer.getPets();
        if (petList != null) {
            List<Long> petIdList = new ArrayList<>();
            for (Pet pe : petList) {
                petIdList.add(pe.getId());
            }
            customerDTO.setPetIds(petIdList);
        }

        return customerDTO;
    }

    private Customer customerDTOToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer, "petIds");

        List<Long> petIds = customerDTO.getPetIds();
        if (petIds != null)
        {
            List<Pet> petList = new ArrayList<>();
            for (Long petId : petIds) {
                Pet pet = petService.findPetById(petId);
                petList.add(pet);
            }
            customer.setPets(petList);
        }

        return customer;
    }

    private List<CustomerDTO> customerEntityListToDTOList(List<Customer> customerList) {
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        for (Customer ce : customerList) {
            customerDTOList.add(customerEntityToDTO(ce));
        }
        return customerDTOList;
    }

    private EmployeeDTO employeeEntityToDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

    private Employee employeeDTOToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }

    private List<EmployeeDTO> employeeEntityListToDTOList(List<Employee> employeeList) {
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        for (Employee ee : employeeList) {
            employeeDTOList.add(employeeEntityToDTO(ee));
        }
        return employeeDTOList;
    }
}
