package com.example.CampusHub.course;

public class CourseFullException extends RuntimeException{

    public CourseFullException(String courseCode){
        super("Course " + courseCode + "has no available seats");
    }
}
