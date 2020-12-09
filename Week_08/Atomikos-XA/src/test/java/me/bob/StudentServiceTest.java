package me.bob;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Test
    void insert() {
        studentService.insert();
    }

    @Test
    void insertFailed() {
        try {
            studentService.insertFailed();
        } catch (final Exception ignore) {

        }
    }
}