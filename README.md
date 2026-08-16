# CampusHub

A backend system for managing a college/university, built with **Java + Spring Boot**.

This is a learning project. The goal isn't to ship a production product — it's to practice core Java concepts (OOP, collections, generics, streams, exceptions, concurrency) and real backend patterns (REST APIs, layered architecture, validation, relationships, security) inside one connected, realistic system instead of disconnected tutorial exercises.

## What it does

CampusHub is the backend behind a student portal. It supports:

- **Students** browsing and enrolling in courses
- **Faculty** marking attendance and submitting grades
- **Admins** managing courses and viewing reports

This repo contains the REST API and business logic — no frontend included (test with Postman or similar).

## Tech Stack

- Java 21
- Spring Boot 3.x (Web, Data JPA, Validation)
- H2 (in-memory database for development)
- Lombok
- Maven

## Project Status

| Module | Status | Description |
|---|---|---|
| `course` | ✅ Done | CRUD for courses, seat capacity tracking |
| `student` | ✅ Done | CRUD for students, duplicate email/roll-number checks |
| `enrollment` | ✅ Done | Links students to courses; enforces no duplicate enrollment and seat limits |
| `attendance` | 🔜 Planned | Faculty marks attendance per enrollment; students view attendance % |
| `grade` | 🔜 Planned | Faculty submits grades; GPA calculated via streams |
| `auth` | 🔜 Planned | Spring Security + JWT, role-based access (STUDENT/FACULTY/ADMIN) |
| `notification` | 🔜 Planned | Async notifications on grade/attendance events |
| `report` | 🔜 Planned | PDF/CSV report generation |

## Project Structure

Each feature lives in its own package (entity, DTO, repository, service, controller, exceptions together) rather than being split across generic `controllers/`, `services/` folders. This is called **package-by-feature**.

```
src/main/java/com/example/CampusHub/
├── CampusHubApplication.java
├── course/
│   ├── Course.java
│   ├── CourseDTO.java
│   ├── CourseRepository.java
│   ├── CourseService.java
│   ├── CourseController.java
│   ├── CourseNotFoundException.java
│   └── CourseFullException.java
├── student/
│   ├── Student.java
│   ├── StudentDTO.java
│   ├── StudentRepository.java
│   ├── StudentService.java
│   ├── StudentController.java
│   └── StudentNotFoundException.java
├── enrollment/
│   ├── Enrollment.java
│   ├── EnrollmentDTO.java
│   ├── EnrollmentRepository.java
│   ├── EnrollmentService.java
│   ├── EnrollmentController.java
│   ├── DuplicateEnrollmentException.java
│   └── EnrollmentNotFoundException.java
└── common/
    ├── ApiError.java
    └── GlobalExceptionHandler.java
```

## Getting Started

### Prerequisites
- JDK 21
- Maven (or use the included `mvnw` wrapper)
- An IDE (IntelliJ IDEA recommended)

### Run it

```bash
git clone <your-repo-url>
cd CampusHub
./mvnw spring-boot:run
```

The app starts at `http://localhost:8080`.

### Database

Uses an in-memory H2 database — no setup required, but data resets on every restart.

- Browse it live at: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:campushub`
- Username: `sa` / Password: *(blank)*

## API Endpoints

### Courses
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/courses` | List all courses |
| GET | `/api/courses/{id}` | Get one course |
| POST | `/api/courses` | Create a course |
| PUT | `/api/courses/{id}` | Update a course |
| DELETE | `/api/courses/{id}` | Delete a course |

### Students
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/students` | List all students |
| GET | `/api/students/{id}` | Get one student |
| GET | `/api/students/department/{department}` | List students by department |
| POST | `/api/students` | Create a student |
| PUT | `/api/students/{id}` | Update a student |
| DELETE | `/api/students/{id}` | Delete a student |

### Enrollments
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/enrollments` | Enroll a student in a course |
| GET | `/api/enrollments/student/{studentId}` | List a student's enrollments |
| GET | `/api/enrollments/course/{courseId}` | List a course's enrollments |
| DELETE | `/api/enrollments/{id}` | Drop an enrollment |

## Example Request

```http
POST /api/enrollments
Content-Type: application/json

{
  "studentId": 1,
  "courseId": 1
}
```

## Design Notes

- **DTOs, not entities, cross the API boundary.** Entities are never returned directly — this keeps the API contract stable even if the database schema changes.
- **Business logic lives in the service layer**, not the controller. Controllers only handle HTTP concerns (status codes, request/response mapping).
- **A global exception handler** (`@RestControllerAdvice`) converts custom exceptions into consistent JSON error responses instead of raw stack traces.
- **`@Transactional`** is used wherever an operation touches more than one table (e.g. enrolling updates both the `Enrollment` and `Course` seat count) so a partial failure can't leave the data inconsistent.

## Roadmap

1. `attendance` — attendance tracking per enrollment
2. `grade` — grading + GPA calculation
3. `auth` — JWT authentication and role-based access control
4. `notification` — async notifications via `@Async`
5. `report` — exportable PDF/CSV reports

## License

Personal learning project — no license applied.
