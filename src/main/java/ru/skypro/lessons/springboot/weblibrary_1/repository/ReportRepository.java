package ru.skypro.lessons.springboot.weblibrary_1.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.lessons.springboot.weblibrary_1.pojo.Report;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends CrudRepository<Report, Integer> {

    List<Report> findAll();

    @Override
    Optional<Report> findById(Integer integer);
}
