package ru.skypro.lessons.springboot.weblibrary_1.pojo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "report_with_path")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ReportWithPath {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String Path;
}
