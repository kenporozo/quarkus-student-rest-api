package student;

import exceptions.StudentNotFoundExeception;

import javax.ws.rs.core.Response;
import java.util.List;

public interface StudentService {

    List<Student> getStudents();

    Student getStudentById(Long id) throws StudentNotFoundExeception;

    Response insertStudent(Student student);

    void deleteStudent(Long id) throws StudentNotFoundExeception;
}
