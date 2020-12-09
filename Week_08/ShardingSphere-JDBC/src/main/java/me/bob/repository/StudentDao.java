package me.bob.repository;

import me.bob.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentDao extends JpaRepository<Student, Long> {

}
