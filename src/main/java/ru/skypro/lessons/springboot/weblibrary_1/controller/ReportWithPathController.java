package ru.skypro.lessons.springboot.weblibrary_1.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.lessons.springboot.weblibrary_1.DTO.ReportWithPathDTO;
import ru.skypro.lessons.springboot.weblibrary_1.service.EmployeeService;
import ru.skypro.lessons.springboot.weblibrary_1.service.ReportWithPathService;

import java.io.*;
import java.nio.file.*;

@RestController
@RequestMapping("/reportWithPath")
public class ReportWithPathController {
    private final ReportWithPathService reportWithPathService;
    private final EmployeeService employeeService;

    public ReportWithPathController(ReportWithPathService reportWithPathService, EmployeeService employeeService) {
        this.reportWithPathService = reportWithPathService;
        this.employeeService = employeeService;
    }

    @PostMapping("/")
    public Integer getReport() {
        return reportWithPathService.addReportWithPath();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> getReportById(@PathVariable Integer id) {
        return reportWithPathService.getReportWithPathById(id);
    }
}
