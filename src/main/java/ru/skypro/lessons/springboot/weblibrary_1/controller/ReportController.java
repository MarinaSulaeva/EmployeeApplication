package ru.skypro.lessons.springboot.weblibrary_1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.lessons.springboot.weblibrary_1.DTO.ReportDTO;
import ru.skypro.lessons.springboot.weblibrary_1.service.EmployeeService;
import ru.skypro.lessons.springboot.weblibrary_1.service.ReportService;

@RestController
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;
    private final EmployeeService employeeService;

    public ReportController(ReportService reportService, EmployeeService employeeService) {
        this.reportService = reportService;
        this.employeeService = employeeService;
    }

    @PostMapping("/")
    public Integer getReport() {
        return reportService.addReport();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> getReportById(@PathVariable Integer id) {
        return reportService.getReportById(id);
    }



}
