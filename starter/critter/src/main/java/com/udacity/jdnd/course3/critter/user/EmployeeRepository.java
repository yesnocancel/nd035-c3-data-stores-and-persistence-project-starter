package com.udacity.jdnd.course3.critter.user;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByDaysAvailable(DayOfWeek day);

    // Find only employees that have all the required skills
    // https://stackoverflow.com/questions/14340156/finding-items-with-a-set-containing-all-elements-of-a-given-set-with-jpql
    @Query("SELECT e FROM Employee e JOIN e.skills s WHERE s IN :skills GROUP BY e.id HAVING count(e.id) = :skillCount")
    List<Employee> findAllBySkills(Set<EmployeeSkill> skills, Long skillCount);
}
