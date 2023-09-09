package ru.skypro.lessons.springboot.weblibrary_1.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skypro.lessons.springboot.weblibrary_1.pojo.Position;

public interface PositionRepository extends CrudRepository<Position, Integer> {
}
