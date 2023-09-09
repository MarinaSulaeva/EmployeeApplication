package ru.skypro.lessons.springboot.weblibrary_1.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import ru.skypro.lessons.springboot.weblibrary_1.DTO.EmployeeReport;
import ru.skypro.lessons.springboot.weblibrary_1.pojo.Report;
import ru.skypro.lessons.springboot.weblibrary_1.repository.ReportRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportServiceImplTest {

    @Mock
    private EmployeeServiceImpl employeeServiceMock;
    @Mock
    private ReportRepository repositoryMock;


    @InjectMocks
    private ReportServiceImpl out;

    private static final List<EmployeeReport> employeeReportList = List.of(
            new EmployeeReport(1,1L,100000,100000,100000),
            new EmployeeReport(2, 1L, 120000,120000, 120000),
            new EmployeeReport(3, 1L, 150000, 150000, 150000)
    );

    @Test
    public void ShouldAddReport() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        when(employeeServiceMock.getReport())
                .thenReturn(employeeReportList);
        String json = objectMapper.writeValueAsString(employeeServiceMock.getReport());
        Report report =  new Report();
        report.setFile(json);
        Report reportExpected = new Report();
        reportExpected.setFile(json);
        reportExpected.setId(1);
        when(repositoryMock.save(report)).thenReturn(reportExpected);
        assertEquals(1, out.addReport());

    }

    @Test
    public void shouldGetReportFRomBD() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(employeeServiceMock.getReport());
        Report report = new Report();
        report.setId(1);
        report.setFile(json);
        when(repositoryMock.findById(1))
                .thenReturn(Optional.of(report));
        ResponseEntity<Resource> expected = ResponseEntity.ok().
                header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "report.json" + "\"")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(new ByteArrayResource(json.getBytes()));
        assertEquals(expected, out.getReportById(1));
    }
    @Test
    public void shouldThrowExceptionWhenFindReportById() {
        when(repositoryMock.findById(1))
                .thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> out.getReportById(1));
    }

}
