package com.example.CampusHub.course;

public class CourseNotFound extends RuntimeException {

    public CourseNotFound(Long id){
        super("Course not found with id: " + id);
    }
    public CourseNotFound(String message) {
        super(message);
    }
}
