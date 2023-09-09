package ru.skypro.lessons.springboot.weblibrary_1.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.lessons.springboot.weblibrary_1.DTO.EmployeeReport;
import ru.skypro.lessons.springboot.weblibrary_1.pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary_1.DTO.EmployeeFullInfo;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    @Query("SELECT new ru.skypro.lessons.springboot.weblibrary_1.DTO." +
            "EmployeeFullInfo(e.name , e.salary , p.name) " +
            "FROM Employee e join fetch Position p " +
            "WHERE e.position = p")
    List<EmployeeFullInfo> findAllEmployeeFullInfo();
    @Query("SELECT new ru.skypro.lessons.springboot.weblibrary_1.DTO." +
            "EmployeeFullInfo(e.name , e.salary , p.name) " +
            "FROM Employee e join fetch Position p " +
            "WHERE e.position = p AND e.id=?1")
    Optional<EmployeeFullInfo> findByIdFullInfo(Integer id);

    Optional<Employee> findFirstByOrderBySalaryDesc();

    @Query("SELECT new ru.skypro.lessons.springboot.weblibrary_1.DTO." +
            "EmployeeFullInfo(e.name , e.salary , p.name) " +
            "FROM Employee e join fetch Position p " +
            "WHERE e.position = p AND p.id=?1")
    List<EmployeeFullInfo> findEmployeeByPosition(Integer position);

    @Query("SELECT new ru.skypro.lessons.springboot.weblibrary_1.DTO." +
            "EmployeeReport(e.department , COUNT(e.name) , MIN(e.salary), max(e.salary), avg(e.salary)) " +
            "FROM Employee e GROUP BY e.department")
    List<EmployeeReport> getReport();

    Optional<Employee> findById (Integer id);

    @Override
    <S extends Employee> S save(S entity);

    @Query("SELECT e FROM Employee e join fetch Position p " +
            "WHERE e.position = p AND e.name=?1")
    Employee findByName(String name);
}
