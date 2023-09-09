package ru.skypro.lessons.springboot.weblibrary_1.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import ru.skypro.lessons.springboot.weblibrary_1.DTO.ReportDTO;


public interface ReportService {
    Integer addReport();
    ResponseEntity<Resource> getReportById(Integer id);
}
