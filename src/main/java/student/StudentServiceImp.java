package student;

import exceptions.StudentNotFoundExeception;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class StudentServiceImp implements StudentService{


    private final StudentRepository repository;

    @Inject
    public StudentServiceImp(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Student> getStudents(){
        return repository.listAll(Sort.descending("studentId"));
    }

    @Override
    public Student getStudentById(Long id) throws StudentNotFoundExeception{
        return repository.findByIdOptional(id)
                .orElseThrow(() -> new StudentNotFoundExeception("Student not found"));
    }

    @Override
    @Transactional
    public Response insertStudent(Student student) {
         repository.persistAndFlush(student);
         if(repository.isPersistent(student)){
             return Response.ok().build();
         }
         return Response.status(400).build();
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) throws StudentNotFoundExeception {
       try {
           repository.deleteById(id);
       }catch (Exception e){
           throw new StudentNotFoundExeception(e.getMessage());
       }
    }
}
