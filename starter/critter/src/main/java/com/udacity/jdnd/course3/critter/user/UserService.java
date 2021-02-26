package com.udacity.jdnd.course3.critter.user;

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

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer findCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(CustomerNotFoundException::new);
    }

    public Customer findPetOwner(Long petId) {
        Customer customer = customerRepository.findPetOwner(petId).orElseThrow(()
                -> new CustomerNotFoundException("No owner found for pet with ID " + petId));
        return customer;
    }

    /*** Employee functions ***/

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee findEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(EmployeeNotFoundException::new);
    }



    public void setEmployeeAvailability(Set<DayOfWeek> daysAvailable, Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(EmployeeNotFoundException::new);
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }

    public List<Employee> findSkilledEmployeesForDate(Set<EmployeeSkill> skills, LocalDate date) {
        DayOfWeek dayOfWeek = DayOfWeek.from(date);
        List<Employee> availableEmployees = employeeRepository.findAllByDaysAvailable(dayOfWeek);
        List<Employee> skilledEmployees = employeeRepository.findAllBySkills(skills, Long.valueOf(skills.size()));

        // Find intersection of the two lists (https://www.baeldung.com/java-lists-intersection)
        List<Employee> result = availableEmployees.stream()
                .distinct()
                .filter(skilledEmployees::contains)
                .collect(Collectors.toList());

        return result;
    }
}
