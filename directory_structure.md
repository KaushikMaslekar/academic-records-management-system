student-management-system/
│
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ └── com/
│ │ │ └── example/
│ │ │ └── studentmanagement/
│ │ │ │
│ │ │ ├── StudentManagementApplication.java (Main Class)
│ │ │ │
│ │ │ ├── config/
│ │ │ │ └── SecurityConfig.java
│ │ │ │
│ │ │ ├── controller/
│ │ │ │ ├── AuthController.java
│ │ │ │ ├── StudentController.java
│ │ │ │ ├── CourseController.java (future)
│ │ │ │ ├── DepartmentController.java (future)
│ │ │ │ └── TeacherController.java (future)
│ │ │ │
│ │ │ ├── entity/
│ │ │ │ ├── User.java
│ │ │ │ ├── Student.java
│ │ │ │ ├── Course.java (future)
│ │ │ │ ├── Department.java (future)
│ │ │ │ ├── Teacher.java (future)
│ │ │ │ ├── Enrollment.java (future)
│ │ │ │ ├── Attendance.java (future)
│ │ │ │ └── Grade.java (future)
│ │ │ │
│ │ │ ├── repository/
│ │ │ │ ├── UserRepository.java
│ │ │ │ ├── StudentRepository.java
│ │ │ │ ├── CourseRepository.java (future)
│ │ │ │ ├── DepartmentRepository.java (future)
│ │ │ │ └── TeacherRepository.java (future)
│ │ │ │
│ │ │ ├── service/
│ │ │ │ ├── CustomUserDetailsService.java
│ │ │ │ ├── UserService.java
│ │ │ │ ├── StudentService.java
│ │ │ │ ├── CourseService.java (future)
│ │ │ │ └── DepartmentService.java (future)
│ │ │ │
│ │ │ ├── dto/ (Data Transfer Objects - Optional but recommended)
│ │ │ │ ├── UserRegistrationDTO.java
│ │ │ │ ├── LoginRequestDTO.java
│ │ │ │ ├── StudentDTO.java
│ │ │ │ └── ResponseDTO.java
│ │ │ │
│ │ │ ├── exception/ (Custom Exception Handling)
│ │ │ │ ├── GlobalExceptionHandler.java
│ │ │ │ ├── ResourceNotFoundException.java
│ │ │ │ └── DuplicateResourceException.java
│ │ │ │
│ │ │ └── util/ (Utility Classes - Optional)
│ │ │ ├── JwtUtil.java (for future JWT implementation)
│ │ │ └── PasswordValidator.java
│ │ │
│ │ └── resources/
│ │ ├── application.properties
│ │ ├── application-dev.properties (optional)
│ │ ├── application-prod.properties (optional)
│ │ │
│ │ ├── static/ (for static files like CSS, JS, images)
│ │ │ ├── css/
│ │ │ ├── js/
│ │ │ └── images/
│ │ │
│ │ └── templates/ (if using Thymeleaf for frontend)
│ │ └── index.html
│ │
│ └── test/
│ └── java/
│ └── com/
│ └── example/
│ └── studentmanagement/
│ ├── StudentManagementApplicationTests.java
│ ├── controller/
│ │ ├── AuthControllerTest.java
│ │ └── StudentControllerTest.java
│ ├── service/
│ │ ├── UserServiceTest.java
│ │ └── StudentServiceTest.java
│ └── repository/
│ ├── UserRepositoryTest.java
│ └── StudentRepositoryTest.java
│
├── pom.xml (Maven dependencies)
├── .gitignore
├── README.md
└── mvnw (Maven wrapper files)
