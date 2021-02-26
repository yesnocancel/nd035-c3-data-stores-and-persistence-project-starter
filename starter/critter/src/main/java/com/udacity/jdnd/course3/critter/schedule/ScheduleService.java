package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.UserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    PetService petService;

    @Autowired
    UserService userService;

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> findAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> findAllSchedulesByPetId(Long petId) {
        return scheduleRepository.findAllByPetId(petId);
    }

    public List<Schedule> findAllSchedulesByEmployeeId(Long employeeId) {
        return scheduleRepository.findAllByEmployeeId(employeeId);
    }

    public List<Schedule> findAllSchedulesByCustomerId(Long customerId) {
        List<Schedule> overallList = new ArrayList<>();

        List<Pet> petsOfCustomer = petService.findAllByCustomerId(customerId);
        for (Pet p : petsOfCustomer) {
            overallList.addAll(scheduleRepository.findAllByPetId(p.getId()));
        }

        return overallList;
    }
}
