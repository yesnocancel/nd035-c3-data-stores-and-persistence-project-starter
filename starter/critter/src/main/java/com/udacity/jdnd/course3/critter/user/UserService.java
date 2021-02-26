package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.PetNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    /*** Customer functions ***/

    public CustomerEntity saveCustomer(CustomerEntity customer) {
        return customerRepository.save(customer);
    }

    public List<CustomerEntity> findAllCustomers() {
        return customerRepository.findAll();
    }

    public CustomerEntity findCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(CustomerNotFoundException::new);
    }

    public CustomerEntity findPetOwner(Long petId) {
        return customerRepository.findPetOwner(petId).orElseThrow(()
                -> new CustomerNotFoundException("No owner found for pet with ID " + petId));
    }

    /*** Employee functions ***/

    public EmployeeEntity saveEmployee(EmployeeEntity employeeEntity) {
        return employeeRepository.save(employeeEntity);
    }

    public EmployeeEntity findEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(EmployeeNotFoundException::new);
    }



    public void setEmployeeAvailability(Set<DayOfWeek> daysAvailable, Long employeeId) {
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).orElseThrow(EmployeeNotFoundException::new);
        employeeEntity.setDaysAvailable(daysAvailable);
        employeeRepository.save(employeeEntity);
    }

    public List<EmployeeEntity> findSkilledEmployeesForDate(Set<EmployeeSkill> skills, LocalDate date) {
        DayOfWeek dayOfWeek = DayOfWeek.from(date);
        List<EmployeeEntity> availableEmployees = employeeRepository.findAllByDaysAvailable(dayOfWeek);
        List<EmployeeEntity> skilledEmployees = employeeRepository.findAllBySkillsIn(skills);

        // Find intersection of the two lists (https://www.baeldung.com/java-lists-intersection)
        List<EmployeeEntity> result = availableEmployees.stream()
                .distinct()
                .filter(skilledEmployees::contains)
                .collect(Collectors.toList());

        return result;
    }
}
