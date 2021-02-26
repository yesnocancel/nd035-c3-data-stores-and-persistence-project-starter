package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.UserService;
import java.util.ArrayList;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    UserService userService;

    @Autowired
    PetService petService;

    @Autowired
    ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleDTOToEntity(scheduleDTO);
        Schedule savedSchedule = scheduleService.saveSchedule(schedule);
        ScheduleDTO savedScheduleDTO = scheduleEntityToDTO(savedSchedule);

        return savedScheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> scheduleList = scheduleService.findAllSchedules();
        return scheduleEntityListToDTOList(scheduleList);
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> scheduleList = scheduleService.findAllSchedulesByPetId(petId);
        return scheduleEntityListToDTOList(scheduleList);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> scheduleList = scheduleService.findAllSchedulesByEmployeeId(employeeId);
        return scheduleEntityListToDTOList(scheduleList);
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> scheduleList = scheduleService.findAllSchedulesByCustomerId(customerId);
        return scheduleEntityListToDTOList(scheduleList);
    }

    /*** private Entity / DTO conversion methods ***/

    private ScheduleDTO scheduleEntityToDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO, "employees", "pets");

        List<Employee> employeeList = schedule.getEmployees();
        List<Long> employeeIdList = new ArrayList<>();
        for (Employee e : employeeList) {
            employeeIdList.add(e.getId());
        }

        List<Pet> petList = schedule.getPets();
        List<Long> petIdList = new ArrayList<>();
        for (Pet p : petList) {
            petIdList.add(p.getId());
        }

        scheduleDTO.setEmployeeIds(employeeIdList);
        scheduleDTO.setPetIds(petIdList);

        return scheduleDTO;
    }

    private Schedule scheduleDTOToEntity(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule, "employeeIds", "petIds");

        List<Employee> employeeList = new ArrayList<>();
        List<Long> employeeIdList = scheduleDTO.getEmployeeIds();
        for (Long employeeId : employeeIdList) {
            Employee e = userService.findEmployeeById(employeeId);
            employeeList.add(e);
        }

        List<Pet> petList = new ArrayList<>();
        List<Long> petIdList = scheduleDTO.getPetIds();
        for (Long petId : petIdList) {
            Pet p = petService.findPetById(petId);
            petList.add(p);
        }

        schedule.setEmployees(employeeList);
        schedule.setPets(petList);

        return schedule;
    }

    private List<ScheduleDTO> scheduleEntityListToDTOList(List<Schedule> scheduleList) {
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        for (Schedule s : scheduleList) {
            scheduleDTOList.add(scheduleEntityToDTO(s));
        }
        return scheduleDTOList;
    }
}
