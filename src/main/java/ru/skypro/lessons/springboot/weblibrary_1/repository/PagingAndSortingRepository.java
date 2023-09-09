package ru.skypro.lessons.springboot.weblibrary_1.repository;


import ru.skypro.lessons.springboot.weblibrary_1.pojo.Employee;

public interface PagingAndSortingRepository extends org.springframework.data.repository.PagingAndSortingRepository<Employee, Integer> {
}
