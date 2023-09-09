package ru.skypro.lessons.springboot.weblibrary_1.DTO;

import lombok.*;
import ru.skypro.lessons.springboot.weblibrary_1.pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary_1.pojo.Position;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PositionDTO {
    private Integer id;

    private String name;

    public static PositionDTO fromPosition(Position position) {
        PositionDTO positionDTO = new PositionDTO();
        positionDTO.setId(position.getId());
        positionDTO.setName(position.getName());
        return positionDTO;
    }

    public Position toPosition() {
        Position position = new Position();
        position.setId(this.getId());
        position.setName(this.getName());
        return position;
    }

    @Override
    public String toString() {
        return "PositionDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
