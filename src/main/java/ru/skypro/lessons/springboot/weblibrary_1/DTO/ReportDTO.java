package ru.skypro.lessons.springboot.weblibrary_1.DTO;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.skypro.lessons.springboot.weblibrary_1.pojo.Report;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class ReportDTO implements Serializable {
    private Integer id;
    private String file;



    public static ReportDTO fromReport(Report report) {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setId(report.getId());
        reportDTO.setFile(report.getFile());
        return reportDTO;
    }

    public Report toReport() {
        Report report = new Report();
        report.setId(this.getId());
        report.setFile(this.getFile());
        return report;
    }
}
