package ru.skypro.lessons.springboot.weblibrary_1.pojo;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "report")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode

public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Lob
    @Column(name = "file", columnDefinition="text")
    private String file;

}
