package ru.skypro.lessons.springboot.weblibrary_1.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.skypro.lessons.springboot.weblibrary_1.DTO.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary_1.DTO.PositionDTO;
import ru.skypro.lessons.springboot.weblibrary_1.pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary_1.pojo.Position;
import ru.skypro.lessons.springboot.weblibrary_1.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.weblibrary_1.repository.PositionRepository;

import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PositionRepository positionRepository;




    @Test
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin1234")
    void givenNoUsersInDatabase_whenUserAdded_thenStatusOK() throws Exception {

        employeeRepository.deleteAll();
        positionRepository.save(new Position(1, "position_1"));
        JSONObject employee = new JSONObject();
        employee.put("name", "Ivan");
        employee.put("salary", 100000);
        employee.put("department", 1);
        JSONObject position = new JSONObject();
        position.put("id", 1);
        position.put("name", "position_1");
        employee.put("positionDTO", position);

        mockMvc.perform(post("/admin/employees/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employee.toString()))
                .andExpect(status().isOk());
        Integer id = employeeRepository.findByName("Ivan").getId();

        mockMvc.perform(get("/employee/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ivan"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin1234")
    void getEmployeesTest() throws Exception {
        addEmployeeListInRepository();
        mockMvc.perform(get("/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));
//                .andExpect(jsonPath("$[0].name").value("Ivan"))
//                .andExpect(jsonPath("$[1].name").value("Inna"))
//                .andExpect(jsonPath("$[2].name").value("Anna"));

    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin1234")
    void whenGetEmployees_thenEmptyJsonArray() throws Exception {
        employeeRepository.deleteAll();
        mockMvc.perform(get("/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin1234")
    void getSumSalary() throws Exception {
        addEmployeeListInRepository();
        mockMvc.perform(get("/employee/salary/sum"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNumber())
                .andExpect(jsonPath("$").value(60000));
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
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin1234")
    void getEmployeeWithMaxSalary() throws Exception {
        addEmployeeListInRepository();
        mockMvc.perform(get("/employee/salary/max"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Anna"))
                .andExpect(jsonPath("$.salary").value(30000));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin1234")
    void getEmployeeWithMinSalary() throws Exception {
        addEmployeeListInRepository();
        mockMvc.perform(get("/employee/salary/min"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ivan"))
                .andExpect(jsonPath("$.salary").value(10000));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin1234")
    void getEmployeesWithSalaryAboveAverage() throws Exception {
        addEmployeeListInRepository();
        mockMvc.perform(get("/employee/high-salary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Anna"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin1234")
    void getEmployeesWithSalaryHigherThan() throws Exception {
        addEmployeeListInRepository();
        mockMvc.perform(get("/employee/salary/higher")
                        .param("compareSalary", String.valueOf(25000)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Anna"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin1234")
    void getEmployeesByPosition() throws Exception {
        addEmployeeListInRepository();
        mockMvc.perform(get("/employee/")
                        .param("position", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Ivan"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin1234")
    void getEmployeeByIdFullInfo() throws Exception {
        addEmployeeListInRepository();
        Integer id = employeeRepository.findByName("Ivan").getId();
        mockMvc.perform(get("/employee/{id}/fullInfo", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ivan"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin1234")
    void getEmployeeByPage() throws Exception {
        addEmployeeListInRepository();
        mockMvc.perform(get("/employee/page")
                        .param("page", String.valueOf(0)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin1234")
    void getEmployeeById() throws Exception {
        addEmployeeListInRepository();
        Integer id = employeeRepository.findByName("Ivan").getId();
        mockMvc.perform(get("/employee/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ivan"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin1234")
    void getEmployeeWithHighestSalary() throws Exception {
        addEmployeeListInRepository();
        mockMvc.perform(get("/employee/withHighestSalary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Anna"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin1234")
    void deleteEmployee() throws Exception {
        addEmployeeListInRepository();
        Integer id = employeeRepository.findByName("Ivan").getId();
        mockMvc.perform(delete("/admin/employees/{id}", id))
                .andExpect(status().isOk());
        mockMvc.perform(get("/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin1234")
    void editEmployee() throws Exception {
        addEmployeeListInRepository();
        JSONObject employee = new JSONObject();
        employee.put("id", 1);
        employee.put("name", "Ilya");
        employee.put("salary", 10000);
        employee.put("department", 1);
        JSONObject position = new JSONObject();
        position.put("id", 1);
        position.put("name", "position-1");
        employee.put("positionDTO", position);

        mockMvc.perform(put("/admin/employees/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employee.toString()))
                .andExpect(status().isOk());

        Integer id = employeeRepository.findByName("Ilya").getId();
        mockMvc.perform(get("/employee/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ilya"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin1234")
    void addEmployeeFromFile() throws Exception {
        addEmployeeListInRepository();
        EmployeeDTO employeeDTO = new EmployeeDTO(4, "Petr", 40000, 2, new PositionDTO(2, "position-2"));
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(employeeDTO);
        MockMultipartFile file = new MockMultipartFile("file", "employee.json", MediaType.MULTIPART_FORM_DATA_VALUE, json.getBytes());

        mockMvc.perform(multipart("/admin/employees/upload")
                        .file(file))
                .andExpect(status().isOk());
        Integer id = employeeRepository.findByName("Petr").getId();
        mockMvc.perform(get("/employee/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Petr"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin1234")
    void whenBDIsEmpty_getStatus404() throws Exception {
        employeeRepository.deleteAll();
//        проверка get методов на выбрасывание исключения и возвращение 404 статуса
        mockMvc.perform(get("/employee/salary/max"))
                .andExpect(status().isBadRequest());
        mockMvc.perform(get("/employee/salary/min"))
                .andExpect(status().isBadRequest());
        mockMvc.perform(get("/employee/high-salary"))
                .andExpect(status().isBadRequest());
        mockMvc.perform(get("/employee/withHighestSalary"))
                .andExpect(status().isBadRequest());
        mockMvc.perform(get("/employee/{id}", 1))
                .andExpect(status().isBadRequest());
        mockMvc.perform(get("/employee/{id}/fullInfo", 1))
                .andExpect(status().isBadRequest());
        //        проверка delete, edit методов на выбрасывание исключения и возвращение 404 статуса
        mockMvc.perform(delete("/admin/employees/{id}", 1))
                .andExpect(status().isBadRequest());
        JSONObject employee = new JSONObject();
        employee.put("id", 1);
        employee.put("name", "Ilya");
        employee.put("salary", 10000);
        employee.put("department", 1);
        JSONObject position = new JSONObject();
        position.put("id", 1);
        position.put("name", "position-1");
        employee.put("positionDTO", position);
        mockMvc.perform(put("/admin/employees/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employee.toString()))
                .andExpect(status().isBadRequest());

    }
}

