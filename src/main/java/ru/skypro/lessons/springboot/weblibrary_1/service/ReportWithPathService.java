package ru.skypro.lessons.springboot.weblibrary_1.service;


import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import ru.skypro.lessons.springboot.weblibrary_1.DTO.ReportWithPathDTO;

public interface ReportWithPathService {
    Integer addReportWithPath();
    ResponseEntity<Resource> getReportWithPathById(Integer id);
}
