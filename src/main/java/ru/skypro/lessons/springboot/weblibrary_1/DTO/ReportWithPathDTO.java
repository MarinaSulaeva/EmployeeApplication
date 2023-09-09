package ru.skypro.lessons.springboot.weblibrary_1.DTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.skypro.lessons.springboot.weblibrary_1.pojo.ReportWithPath;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class ReportWithPathDTO implements Serializable {
    private Integer id;
    private String path;



    public static ReportWithPathDTO fromReportWithPath(ReportWithPath reportWithPath) {
        ReportWithPathDTO reportWithPathDTO = new ReportWithPathDTO();
        reportWithPathDTO.setId(reportWithPath.getId());
        reportWithPathDTO.setPath(reportWithPath.getPath());
        return reportWithPathDTO;
    }

    public ReportWithPath toReportWithPath() {
        ReportWithPath reportWithPath = new ReportWithPath();
        reportWithPath.setId(this.getId());
        reportWithPath.setPath(this.getPath());
        return reportWithPath;
    }
}
