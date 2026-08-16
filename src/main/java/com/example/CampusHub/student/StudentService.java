package com.example.CampusHub.student;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    //Get all Students
    public List<StudentDTO> getAllStudents(){
        return studentRepository.findAll()
                .stream()
                .map(StudentDTO::fromEntity)
                .collect(Collectors.toList());
    }


    //get student by Id
    public StudentDTO getStudentById(Long id){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));

        return StudentDTO.fromEntity(student);
    }


    //Create Student
    public StudentDTO createStudent(StudentDTO dto){
        if(studentRepository.existsByEmail(dto.getEmail())){
            throw new IllegalArgumentException("Email Already registered: " + dto.getEmail());
        }

        if (studentRepository.existsByRollNumber(dto.getRollNumber())){
            throw new IllegalArgumentException("Roll number already exists: " + dto.getRollNumber());
        }

        Student student = studentRepository.save(dto.toEntity());

        return StudentDTO.fromEntity(student);
    }

    public StudentDTO updateStudent(Long id, StudentDTO dto){
        Student existing = studentRepository.findById(dto.getId())
                .orElseThrow(() -> new StudentNotFoundException(id));

        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setRollNo(dto.getRollNumber());
        existing.setDepartment(dto.getDepartment());

        Student updated = studentRepository.save(existing);
        return StudentDTO.fromEntity(updated);
    }

    public void deleteStudent(Long id){
        if(! studentRepository.existsById(id)){
            throw new StudentNotFoundException(id);
        }

        studentRepository.deleteById(id);
    }

    public List<StudentDTO> getStudentsByDepartment(String department){
        return studentRepository.findByDepartmentIgnoreCase(department)
                .stream()
                .map(StudentDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
