package ru.skypro.lessons.springboot.weblibrary_1.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.weblibrary_1.DTO.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary_1.service.EmployeeService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/admin/employees")
public class AdminEmployeeController {

    private final EmployeeService employeeService;

    public AdminEmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @DeleteMapping("/{id}")
    public void deleteEmployeeById(@PathVariable Integer id) {
        employeeService.deleteEmployeeById(id);
    }

    @PostMapping("/")
    public void addEmployee(@RequestBody EmployeeDTO employeeDTO) {
         employeeService.addEmployee(employeeDTO);
    }

    @PutMapping("/")
    public void editEmployee(@RequestBody EmployeeDTO employeeDTO) {
        employeeService.editEmployee(employeeDTO);
    }


    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadEmployeeFromFile(@RequestParam("file") MultipartFile file) {
        employeeService.addEmployeeFromFile(file);
    }
}
