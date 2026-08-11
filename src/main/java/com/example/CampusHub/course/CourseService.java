package com.example.CampusHub.course;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public List<CourseDTO> getAllCourse(){
        return courseRepository.findAll()
                .stream()
                .map(CourseDTO::fromEntity)   // method reference, Java 8+ feature
                .collect(Collectors.toList());
    }

    public CourseDTO getCourseById(Long id){
        Course course = courseRepository.findById(id).orElseThrow(() -> new CourseNotFound(id));
        return CourseDTO.fromEntity(course);
    }

    public CourseDTO createCourse(CourseDTO courseDTO){
        if(courseRepository.existsByCode(courseDTO.getCode())){
            throw new IllegalArgumentException("Course code already exists: " + courseDTO.getCode());
        }

        Course course = courseRepository.save(courseDTO.toEntity());
        return CourseDTO.fromEntity(course);
    }

    public CourseDTO updateCourse(Long id, CourseDTO dto){
        Course existing = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFound(id));

        existing.setId(dto.getId());
        existing.setTitle(dto.getTitle());
        existing.setDepartment(dto.getDepartment());
        existing.setCapacity(dto.getCapacity());

        Course course = courseRepository.save(existing);
        return CourseDTO.fromEntity(course);
    }

    public void deleteCourse(Long id){
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFound(id));

        courseRepository.delete(course);
    }


    @Transactional
    public CourseDTO enrollOneSeat(Long id){
        Course course  = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFound(id));

        if(!course.hasAvailableSeats()){
            throw new CourseFullException(course.getCode());
        }

        course.setEnrolledCount(course.getEnrolledCount() + 1);
        return CourseDTO.fromEntity(courseRepository.save(course));
    }

}
