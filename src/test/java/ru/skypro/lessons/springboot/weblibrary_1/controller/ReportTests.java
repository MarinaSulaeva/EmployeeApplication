package ru.skypro.lessons.springboot.weblibrary_1.controller;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.skypro.lessons.springboot.weblibrary_1.pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary_1.pojo.Position;
import ru.skypro.lessons.springboot.weblibrary_1.pojo.Report;
import ru.skypro.lessons.springboot.weblibrary_1.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.weblibrary_1.repository.PositionRepository;
import ru.skypro.lessons.springboot.weblibrary_1.repository.ReportRepository;
import ru.skypro.lessons.springboot.weblibrary_1.service.ReportService;

import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
public class ReportTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportRepository reportRepository;


    @Test
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin1234")
    void getReport() throws Exception {
        addEmployeeListInRepository();
        mockMvc.perform(post("/report/"))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$").value(1));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin1234")
    void getReportById() throws Exception {
        addEmployeeListInRepository();
        Integer id = reportService.addReport();
        MvcResult result = mockMvc.perform(get("/report/{id}", id))
                .andExpect(status().isOk())
                .andReturn();
        byte[] resourceContent = result.getResponse().getContentAsByteArray();
        Report report = reportRepository.findById(id).orElse(null);
        String file = report.getFile();
        assertThat(resourceContent).isNotEmpty();
        assertThat(resourceContent).containsExactly(file.getBytes());
    }

    void addEmployeeListInRepository() {
        employeeRepository.deleteAll();
        Position position = new Position(1, "position-1");
        Position position2 = new Position(2, "position-2");
        positionRepository.save(position);
        positionRepository.save(position2);
        List<Employee> employeeList = List.of(
                new Employee(1, "Ivan", 10000, 1, position),
                new Employee(2, "Inna", 20000, 2, position2),
                new Employee(3, "Anna", 30000, 3, position2)
        );
        employeeRepository.saveAll(employeeList);
        reportRepository.deleteAll();
    }
}
