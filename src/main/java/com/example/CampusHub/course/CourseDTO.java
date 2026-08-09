package com.example.CampusHub.course;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private Long id;

    @NotBlank(message = "Course code is required")
    private String code;

    @NotBlank(message = "Course title is required")
    private String title;

    @NotBlank(message = "Department is required")
    private String department;

    @Min(value = 1, message = "capacity must be at least 1")
    @Max(value = 500, message = "capacity looks unrealistically high")
    private int capacity;

    private int enrolledCount;


    public static CourseDTO fromEntity(Course course){
        return new CourseDTO(
                course.getId(),
                course.getCode(),
                course.getTitle(),
                course.getDepartment(),
                course.getCapacity(),
                course.getEnrolledCount()
        );
    }

    public  Course toEntity(){
        Course course = new Course();
        course.setId(this.id);
        course.setCode(this.code);
        course.setTitle(this.title);
        course.setDepartment(this.department);
        course.setCapacity(this.capacity);
        course.setEnrolledCount(0);
        return course;
    }
}
