package student;

import exceptions.ExceptionHandler;
import exceptions.StudentNotFoundExeception;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/students")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StudentController {

    private final StudentService service;

    @Inject
    public StudentController(StudentService service) {
        this.service = service;
    }

    @GET
    @Operation(summary = "Gets students", description = "Lists all registered students")
    @APIResponses(value = @APIResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Student.class))))
    public List<Student> getStudents() {
        return service.getStudents();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Gets a student", description = "Retrieves a student by id")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Student.class))),
            @APIResponse(responseCode = "404", description = "Student not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class)))
    })
    public Student getStudent(@PathParam("id") Long id) throws StudentNotFoundExeception {
        return service.getStudentById(id);
    }

    @POST
    @Operation(summary = "Adds a student", description = "Creates a student and persists into database")
    @APIResponses(value = @APIResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Student.class))))
    public Response createItem(@Valid Student student) {
        return service.insertStudent(student);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "deletes a student", description = "Delete a student by id")
    @APIResponses(value = @APIResponse(responseCode = "200", description = "success",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Student.class))))
    public void deleteStudent(@PathParam("id") Long id) throws StudentNotFoundExeception{
      service.deleteStudent(id);
    }
}
