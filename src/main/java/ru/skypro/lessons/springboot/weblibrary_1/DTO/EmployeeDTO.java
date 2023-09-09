package ru.skypro.lessons.springboot.weblibrary_1.DTO;

import lombok.*;
import ru.skypro.lessons.springboot.weblibrary_1.pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary_1.pojo.Position;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EmployeeDTO implements Serializable {
    private Integer id;
    private String name;
    private int salary;
    private Integer department;
    private PositionDTO positionDTO;


    public static EmployeeDTO fromEmployee(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSalary(employee.getSalary());
        employeeDTO.setPositionDTO(PositionDTO.fromPosition(employee.getPosition()));
        employeeDTO.setDepartment(employee.getDepartment());
        return employeeDTO;
    }

    public Employee toEmployee() {
        Employee employee = new Employee();
        employee.setId(this.getId());
        employee.setName(this.getName());
        employee.setSalary(this.getSalary());
        employee.setPosition(this.getPositionDTO().toPosition());
        employee.setDepartment(this.getDepartment());
        return employee;
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", department=" + department +
                ", position=" + positionDTO +
                '}';
    }
}
