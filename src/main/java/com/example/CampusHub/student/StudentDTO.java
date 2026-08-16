package com.example.CampusHub.student;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email id is required")
    @Email(message = "Email must valid email address")
    private String email;

    @NotBlank(message = "Roll No is required")
    private String rollNumber;

    @NotBlank(message = "Department is required")
    private String department;

    private double gpa;

    public static StudentDTO fromEntity(Student student){
        return new StudentDTO(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getRollNo(),
                student.getDepartment(),
                student.getGpa()
        );
    }

    public Student toEntity(){
        Student student = new Student();
        student.setName(this.name);
        student.setEmail(this.email);
        student.setRollNo(this.rollNumber);
        student.setDepartment(this.department);
        student.setGpa(0.0);

        return student;
    }

}
