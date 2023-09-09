package ru.skypro.lessons.springboot.weblibrary_1.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.weblibrary_1.DTO.ReportWithPathDTO;
import ru.skypro.lessons.springboot.weblibrary_1.repository.ReportWithPathRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class ReportWithPathServiceImpl implements ReportWithPathService{

    private final ReportWithPathRepository reportWithPathRepository;
    private final EmployeeService employeeService;

    Logger logger = LoggerFactory.getLogger(ReportWithPathServiceImpl.class);

    public ReportWithPathServiceImpl(ReportWithPathRepository reportWithPathRepository, EmployeeService employeeService) {
        this.reportWithPathRepository = reportWithPathRepository;
        this.employeeService = employeeService;
    }

    @Override
    public Integer addReportWithPath() {
        logger.info("Was invoked method to add report with path to DB");
        Integer i = 0;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(employeeService.getReport());
            ReportWithPathDTO reportWithPathDTO = new ReportWithPathDTO();
            File file = new File("report.json");
            Files.writeString(file.toPath(), json);
            reportWithPathDTO.setPath(file.getAbsolutePath());
            i = reportWithPathRepository.save(reportWithPathDTO.toReportWithPath()).getId();
            logger.debug("Report was adding to DB with id={}", i);
        } catch (IOException e) {
            logger.error("Was error to save path report as json string type", e);
        }
        return i;
    }

    @Override
    public ResponseEntity<Resource> getReportWithPathById(Integer id) {
        logger.info("Was invoked method for getting report with path to DB with id={}", id);
        ReportWithPathDTO reportWithPathDTO = ReportWithPathDTO.fromReportWithPath(reportWithPathRepository.findById(id).orElseThrow(() -> {
                IllegalArgumentException e =new IllegalArgumentException("Введен не корректный ID");
                logger.error("Received invalid id={}",id, e);
            return e;
            }));
        String fileName = "report.json";
        String path = reportWithPathDTO.getPath();
        File file = new File(path);
        Resource resource = new PathResource(file.getPath());
        logger.debug("Received the report with path {}", reportWithPathDTO);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(resource);
    }


}
