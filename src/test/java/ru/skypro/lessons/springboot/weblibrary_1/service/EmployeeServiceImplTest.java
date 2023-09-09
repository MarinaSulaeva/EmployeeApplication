package ru.skypro.lessons.springboot.weblibrary_1.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import ru.skypro.lessons.springboot.weblibrary_1.DTO.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary_1.DTO.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary_1.DTO.EmployeeReport;
import ru.skypro.lessons.springboot.weblibrary_1.DTO.PositionDTO;
import ru.skypro.lessons.springboot.weblibrary_1.pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary_1.pojo.Position;
import ru.skypro.lessons.springboot.weblibrary_1.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.weblibrary_1.repository.PagingAndSortingRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository repositoryMock;
    @Mock
    private PagingAndSortingRepository pagingRepositoryMock;

    @InjectMocks
    private EmployeeServiceImpl out;

    private static final List<Employee> employeeList = List.of(
            new Employee(1, "Inna", 100000,1, new Position(1, "Tester")),
            new Employee(2, "Ivan", 120000, 2, new Position(2, "Devepoler")),
            new Employee(3, "Irina", 150000, 3, new Position(3, "Analyst"))
    );

    private static final List<EmployeeFullInfo> employeeFullInfoList = List.of(
            new EmployeeFullInfo("Inna", 100000,"Tester"),
            new EmployeeFullInfo("Ivan", 120000, "Devepoler"),
            new EmployeeFullInfo("Irina", 150000, "Analyst")
    );

    private static final List<EmployeeFullInfo> emptyEmployeeFullInfoList = new ArrayList<>();

    private static final List<EmployeeFullInfo> employeeFullInfoByPosition = List.of(
            new EmployeeFullInfo("Inna", 100000,"Tester"),
            new EmployeeFullInfo("Ivan", 120000, "Tester"),
            new EmployeeFullInfo("Irina", 150000, "Tester")
    );

    private static final Page<Employee> employeePage  = new PageImpl<>(employeeList);

    private static final EmployeeDTO employeeDTOExpected = new EmployeeDTO(3, "Irina", 150000, 3, new PositionDTO(3, "Analyst"));
    private static final Employee employeeExpected = new Employee(3, "Irina", 150000, 3, new Position(3, "Analyst"));
    private static final List<EmployeeDTO> employeeListDTOExpected = List.of(
            new EmployeeDTO(1, "Inna", 100000,1, new PositionDTO(1, "Tester")),
            new EmployeeDTO(2, "Ivan", 120000, 2, new PositionDTO(2, "Devepoler")),
            new EmployeeDTO(3, "Irina", 150000, 3, new PositionDTO(3, "Analyst"))
    );

    private static final EmployeeFullInfo employeeFullInfo = new EmployeeFullInfo("Irina", 150000, "Tester");

    private static final List<EmployeeReport> employeeReportList = List.of(
            new EmployeeReport(1,1L,100000,100000,100000),
            new EmployeeReport(2, 1L, 120000,120000, 120000),
            new EmployeeReport(3, 1L, 150000, 150000, 150000)
    );



    @Test
    public void shouldGetAllEmployeesWithFormatEmployeeInfo() {
        when(repositoryMock.findAllEmployeeFullInfo())
                .thenReturn(employeeFullInfoList);
        assertIterableEquals(employeeFullInfoList, out.getAllEmployees());
        verify(repositoryMock, times(1)).findAllEmployeeFullInfo();
    }

    @Test
    public void shouldGetSumSalaryAmongAllEmployees() {
        int sum = 370000;
        when(repositoryMock.findAllEmployeeFullInfo())
                .thenReturn(employeeFullInfoList);
        assertEquals(sum, out.getSumSalary());
    }
    @ParameterizedTest
    @CsvSource({"10000,20000,30000",
            "40000,400000,440000"})
    public void shouldGetSumSalary(int sal1, int sal2, int expectedSum) {
        EmployeeFullInfo e1 = new EmployeeFullInfo("Irina", sal1, "Analyst");
        EmployeeFullInfo e2 = new EmployeeFullInfo("Marina", sal2, "Programmer");
        when(repositoryMock.findAllEmployeeFullInfo()).thenReturn(List.of(e1, e2));
        assertEquals(expectedSum, out.getSumSalary());
    }

    @Test
    public void shouldGetEmployeeWithMaxSalary() {
        EmployeeFullInfo employeeFullInfo = new EmployeeFullInfo("Irina", 150000, "Analyst");
        when(repositoryMock.findAllEmployeeFullInfo())
                .thenReturn(employeeFullInfoList);
        assertEquals(employeeFullInfo, out.getMaxSalary());
    }
    @ParameterizedTest
    @MethodSource("provideParamsForTests")
    public void shouldGetEmployeeWithMaxSalary(List<EmployeeFullInfo> listEmployeeFullInfo, EmployeeFullInfo employee, int expectedMax) {

        when(repositoryMock.findAllEmployeeFullInfo()).thenReturn(listEmployeeFullInfo);
        assertEquals(employee, out.getMaxSalary());
        assertEquals(expectedMax, out.getMaxSalary().getSalary());
    }

    public static Stream<Arguments> provideParamsForTests() {
        List<EmployeeFullInfo> employeeFullInfoList1 = List.of(
                new EmployeeFullInfo("Inna", 200000, "Tester"),
                new EmployeeFullInfo("Ivan", 20000, "Devepoler"),
                new EmployeeFullInfo("Irina", 15000, "Analyst")
        );

        return Stream.of(
                Arguments.of(employeeFullInfoList, new EmployeeFullInfo("Irina", 150000, "Analyst"), 150000),
                Arguments.of(employeeFullInfoList1, new EmployeeFullInfo("Inna", 200000, "Tester"), 200000)
        );
    }

    @Test
    public void shouldThrowExceptionWhenListIsEmpty() {
        when(repositoryMock.findAllEmployeeFullInfo())
                .thenReturn(emptyEmployeeFullInfoList);
        assertThrows(IllegalArgumentException.class, () -> out.getMaxSalary());
        assertThrows(IllegalArgumentException.class, () -> out.getMinSalary());
    }


    @Test
    public void shouldGetEmployeeWithMinSalary() {
        EmployeeFullInfo employeeFullInfo = new EmployeeFullInfo("Inna", 100000,"Tester");
        when(repositoryMock.findAllEmployeeFullInfo())
                .thenReturn(employeeFullInfoList);
        assertEquals(employeeFullInfo, out.getMinSalary());
    }

    @Test
    public void shouldGetEmployeesWithSalaryAboveAverage() {
        List<EmployeeFullInfo> expected = List.of(new EmployeeFullInfo("Irina", 150000, "Analyst"));

        when(repositoryMock.findAllEmployeeFullInfo())
                .thenReturn(employeeFullInfoList);
        assertEquals(expected, out.getEmployeeWithSalaryAboveAverage());
    }

    @Test
    public void shouldGetEmployeesWithSalaryHigherThan() {
        List<EmployeeFullInfo> expected = List.of(new EmployeeFullInfo("Irina", 150000, "Analyst"));
        Integer compareSalary = 130000;

        when(repositoryMock.findAllEmployeeFullInfo())
                .thenReturn(employeeFullInfoList);
        assertEquals(expected, out.getEmployeesWithSalaryHigherThan(compareSalary));
    }

    @Test
    public void shouldDeleteEmployee() {
        when(repositoryMock.findAll())
                .thenReturn(employeeList);
        out.deleteEmployeeById(1);
        verify(repositoryMock, times(1)).deleteById(1);
    }

    @Test
    public void shouldThrowExceptionWhenDoNotFindEmployee() {

        when(repositoryMock.findAll())
                .thenReturn(employeeList);
        assertThrows(IllegalArgumentException.class, () -> out.deleteEmployeeById(4));
    }

    @Test
    public void shouldEditEmployee() {
        EmployeeDTO employeeDTO= new EmployeeDTO(3, "Irina", 150000,2, new PositionDTO(3, "Analyst"));
        when(repositoryMock.findAllEmployeeFullInfo())
                .thenReturn(employeeFullInfoList);
        out.editEmployee(employeeDTO);
        verify(repositoryMock, times(1)).save(employeeDTO.toEmployee());
    }

    @Test
    public void shouldThrowExceptionWhenDoNotFindEmployeeToEdit() {
        EmployeeDTO employeeDTO= new EmployeeDTO(4, "Irina", 150000,2, new PositionDTO(3, "Analyst"));
        when(repositoryMock.findAllEmployeeFullInfo())
                .thenReturn(employeeFullInfoList);
        assertThrows(IllegalArgumentException.class, () -> out.editEmployee(employeeDTO));
    }

    @Test
    public void shouldAddEmployee() {
        EmployeeDTO employeeDTO= new EmployeeDTO(4, "Victor", 170000,2, new PositionDTO(3, "Analyst"));
        out.addEmployee(employeeDTO);
        verify(repositoryMock, times(1)).save(employeeDTO.toEmployee());
    }

    @Test
    public void shouldGetAllEmployeesByPositionWithFormatEmployeeInfo() {
        when(repositoryMock.findEmployeeByPosition(1))
                .thenReturn(employeeFullInfoByPosition);
        assertIterableEquals(employeeFullInfoByPosition, out.getEmployeeByPosition(1));
        verify(repositoryMock, times(1)).findEmployeeByPosition(1);
    }

    @Test
    public void shouldGetEmployeeWithPaging() {

        when(pagingRepositoryMock.findAll(PageRequest.of(0, 10)))
                .thenReturn(employeePage);
        assertIterableEquals(employeeListDTOExpected, out.getEmployeeWithPaging(0));
        verify(pagingRepositoryMock, times(1)).findAll(PageRequest.of(0,10));
    }

    @Test
    public void ShouldGetTheHighestSalary() {
        when(repositoryMock.findFirstByOrderBySalaryDesc())
                .thenReturn(Optional.of(employeeExpected));
        assertEquals(employeeDTOExpected, out.getTheHighestSalary());


    }


    @Test
    public void ShouldGetThrowExceptionWhenFindTheHighestSalary() {
        when(repositoryMock.findFirstByOrderBySalaryDesc())
                .thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> out.getTheHighestSalary());
    }

    @Test
    public void ShouldGetEmployeeById() {
        when(repositoryMock.findById(3))
                .thenReturn(Optional.of(employeeExpected));
        assertEquals(employeeDTOExpected, out.getEmployeeById(3));
    }

    @Test
    public void ShouldGetThrowExceptionWhenFindEmployee() {
        when(repositoryMock.findById(4))
                .thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> out.getEmployeeById(4));
    }

    @Test
    public void ShouldGetEmployeeFullInfoById() {
        when(repositoryMock.findByIdFullInfo(3))
                .thenReturn(Optional.of(employeeFullInfo));
        assertEquals(employeeFullInfo, out.getEmployeeByIdFullInfo(3));
    }

    @Test
    public void ShouldGetThrowExceptionWhenFindEmployeeFullInfo() {
        when(repositoryMock.findByIdFullInfo(4))
                .thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> out.getEmployeeByIdFullInfo(4));
    }

    @Test
    public void ShouldGetReport() {
        when(repositoryMock.getReport())
                .thenReturn(employeeReportList);
        assertIterableEquals(employeeReportList, out.getReport());
    }

    @Test
    public void ShouldReadEmployeeFromFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(employeeDTOExpected);
        MockMultipartFile file = new MockMultipartFile("employee", "employee.json", MediaType.MULTIPART_FORM_DATA_VALUE, json.getBytes());
        assertEquals(employeeDTOExpected, out.uploadEmployeeFromFile(file));
    }

    @Test
    public void ShouldAddEmployeeFromFileToDB() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(employeeDTOExpected);
        MockMultipartFile file = new MockMultipartFile("employee", "employee.json", MediaType.MULTIPART_FORM_DATA_VALUE, json.getBytes());
        out.addEmployee(out.uploadEmployeeFromFile(file));
        verify(repositoryMock, times(1)).save(out.uploadEmployeeFromFile(file).toEmployee());
    }
}
