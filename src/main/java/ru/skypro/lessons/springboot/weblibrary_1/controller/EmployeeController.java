package ru.skypro.lessons.springboot.weblibrary_1.controller;

import org.springframework.web.bind.annotation.*;
import ru.skypro.lessons.springboot.weblibrary_1.DTO.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary_1.DTO.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary_1.service.EmployeeService;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<EmployeeFullInfo> showCounter() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/salary/sum")
    public int getSumSalary(){
        return employeeService.getSumSalary();
    }

    @GetMapping("/salary/max")
    public EmployeeFullInfo getMaxSalary() {
    return employeeService.getMaxSalary();
    }

    @GetMapping("/salary/min")
    public EmployeeFullInfo getMinSalary(){
        return employeeService.getMinSalary();
    }

    @GetMapping("/high-salary")
    public List<EmployeeFullInfo> getEmployeeWithSalaryAboveAverage() {
        return employeeService.getEmployeeWithSalaryAboveAverage();
    }

    @GetMapping("/salary/higher")
    public List<EmployeeFullInfo> getEmployeesWithSalaryHigherThan(@RequestParam("compareSalary") Integer compareSalary) {
        return employeeService.getEmployeesWithSalaryHigherThan(compareSalary);
    }

    @GetMapping("/")
    public List<EmployeeFullInfo> getEmployeeByPosition(@RequestParam("position") Integer position) {
        return employeeService.getEmployeeByPosition(position);
    }

    @GetMapping("/{id}/fullInfo")
    public EmployeeFullInfo getEmployeeByIdFullInfo(@PathVariable Integer id) {
        return employeeService.getEmployeeByIdFullInfo(id);
    }

    @GetMapping("/page")
    public List<EmployeeDTO> getEmployeeWithPaging(@RequestParam("page") Integer page) {
        return employeeService.getEmployeeWithPaging(page);
    }

    @GetMapping("/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable Integer id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/withHighestSalary")
    public EmployeeDTO getTheHighestSalary() {
        return employeeService.getTheHighestSalary();
    }


}
