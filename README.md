# 🎓 Academic Records Management System

A comprehensive Spring Boot application for managing academic records with role-based authentication and MongoDB integration.

## 📋 Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Setup & Installation](#setup--installation)
- [API Endpoints](#api-endpoints)
- [Authentication & Authorization](#authentication--authorization)
- [Database Schema](#database-schema)
- [Configuration](#configuration)
- [Future Enhancements](#future-enhancements)

---

## ✨ Features

### Implemented Features

- ✅ **User Management**
  - User registration with password encryption (BCrypt)
  - Role-based access control (ADMIN, USER)
  - Custom UserDetailsService for Spring Security integration

- ✅ **Student Management**
  - CRUD operations for student records
  - Role-based endpoint protection
  - Department and course associations

- ✅ **Security**
  - HTTP Basic Authentication
  - Method-level security with @PreAuthorize
  - Password encryption using BCrypt
  - CSRF protection (disabled for API)

- ✅ **Database**
  - MongoDB integration
  - Repository pattern with Spring Data MongoDB
  - Unique constraints on critical fields

### Planned Features

- 🔜 JWT token-based authentication
- 🔜 Course management module
- 🔜 Department management module
- 🔜 Teacher management module
- 🔜 Enrollment tracking system
- 🔜 Attendance management
- 🔜 Grade/marks management
- 🔜 Advanced search and filtering
- 🔜 Reporting and analytics
- 🔜 Email notifications
- 🔜 File upload (student documents)

---

## 🛠 Technologies Used

| Technology          | Version | Purpose                        |
| ------------------- | ------- | ------------------------------ |
| Java                | 21      | Programming Language           |
| Spring Boot         | 4.0.2   | Application Framework          |
| Spring Security     | 6.x     | Authentication & Authorization |
| Spring Data MongoDB | Latest  | Database Integration           |
| MongoDB             | 5.x+    | NoSQL Database                 |
| Lombok              | Latest  | Reduce Boilerplate Code        |
| Maven               | 3.x     | Build Tool                     |

---

## 🏗 Architecture

### Spring Security Authentication Flow

```
Client Request
    ↓
SecurityFilterChain (Security Rules)
    ↓
┌─────────────────┬──────────────────┐
│ Public Endpoint │ Protected Endpoint│
│ /api/auth/**    │ /api/students/**  │
│ (permitAll)     │ (hasRole)         │
└────────┬────────┴─────────┬─────────┘
         ↓                  ↓
    AuthController   HTTP Basic Auth
         ↓                  ↓
    UserService     DaoAuthenticationProvider
         ↓                  ↓
         └──→ UserRepository ←──┘
                    ↓
              MongoDB (users)
```

### Application Layers

```
┌─────────────────────────────────────┐
│         Controllers Layer           │
│  (REST API - HTTP Request/Response) │
└──────────────┬──────────────────────┘
               ↓
┌─────────────────────────────────────┐
│          Service Layer              │
│     (Business Logic & Validation)   │
└──────────────┬──────────────────────┘
               ↓
┌─────────────────────────────────────┐
│        Repository Layer             │
│    (Data Access - MongoDB CRUD)     │
└──────────────┬──────────────────────┘
               ↓
┌─────────────────────────────────────┐
│           Database Layer            │
│          (MongoDB Collections)      │
└─────────────────────────────────────┘
```

---

## 📁 Project Structure

```
academic-records-management-system/
├── src/
│   ├── main/
│   │   ├── java/com/example/student_management_system/
│   │   │   ├── config/
│   │   │   │   └── SecurityConfig.java              # Spring Security configuration
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java             # Authentication endpoints
│   │   │   │   ├── StudentController.java          # Student CRUD endpoints
│   │   │   │   ├── CourseController.java           # Course endpoints (future)
│   │   │   │   ├── DepartmentController.java       # Department endpoints (future)
│   │   │   │   └── TeacherController.java          # Teacher endpoints (future)
│   │   │   ├── entity/
│   │   │   │   ├── User.java                       # User entity (MongoDB)
│   │   │   │   ├── Student.java                    # Student entity
│   │   │   │   ├── Course.java                     # Course entity (future)
│   │   │   │   ├── Department.java                 # Department entity (future)
│   │   │   │   └── Teacher.java                    # Teacher entity (future)
│   │   │   ├── repository/
│   │   │   │   ├── CustomUserDetailsService.java   # Spring Security UserDetailsService
│   │   │   │   ├── UserRepository.java             # User data access
│   │   │   │   ├── StudentRepository.java          # Student data access
│   │   │   │   ├── CourseRepository.java           # Course data access (future)
│   │   │   │   ├── DepartmentRepository.java       # Department data access (future)
│   │   │   │   └── TeacherRepository.java          # Teacher data access (future)
│   │   │   ├── service/
│   │   │   │   ├── UserService.java                # User business logic
│   │   │   │   ├── StudentService.java             # Student business logic
│   │   │   │   ├── CourseService.java              # Course business logic (future)
│   │   │   │   ├── DepartmentService.java          # Department logic (future)
│   │   │   │   └── TeacherService.java             # Teacher logic (future)
│   │   │   └── StudentManagementSystemApplication.java
│   │   └── resources/
│   │       ├── application.properties              # Application configuration
│   │       ├── static/                            # Static resources
│   │       └── templates/                         # Templates (if needed)
│   └── test/                                      # Unit and integration tests
├── pom.xml                                        # Maven dependencies
└── README.md                                      # This file
```

---

## ⚙️ Setup & Installation

### Prerequisites

- Java 21 or higher
- Maven 3.x
- MongoDB 5.x or higher (running locally or remote)
- Git (for cloning)
- IDE (IntelliJ IDEA, Eclipse, VS Code)

### Installation Steps

1. **Clone the repository**

   ```bash
   git clone <repository-url>
   cd academic-records-management-system
   ```

2. **Start MongoDB**

   ```bash
   # Local MongoDB
   mongod --dbpath /path/to/data/directory

   # Or use MongoDB Atlas (cloud)
   # Update connection string in application.properties
   ```

3. **Configure Application**

   Edit `src/main/resources/application.properties`:

   ```properties
   # MongoDB Configuration
   spring.data.mongodb.host=localhost
   spring.data.mongodb.port=27017
   spring.data.mongodb.database=academic_records

   # Server Configuration
   server.port=8080

   # Logging
   logging.level.org.springframework.security=DEBUG
   ```

4. **Build the project**

   ```bash
   mvn clean install
   ```

5. **Run the application**

   ```bash
   mvn spring-boot:run
   ```

6. **Verify application is running**
   ```bash
   # Open browser or use curl
   curl http://localhost:8080/api/auth/test
   ```

---

## 🔌 API Endpoints

### Authentication Endpoints

| Method | Endpoint             | Description       | Auth Required |
| ------ | -------------------- | ----------------- | ------------- |
| POST   | `/api/auth/register` | Register new user | No            |
| GET    | `/api/auth/test`     | Test endpoint     | No            |

**Register User Example:**

```bash
POST http://localhost:8080/api/auth/register
Content-Type: application/json

{
    "username": "admin",
    "password": "admin123",
    "email": "admin@example.com",
    "role": "ROLE_ADMIN"
}
```

### Student Endpoints

| Method | Endpoint             | Description      | Required Role |
| ------ | -------------------- | ---------------- | ------------- |
| GET    | `/api/students`      | Get all students | ADMIN, USER   |
| POST   | `/api/students`      | Add new student  | ADMIN         |
| DELETE | `/api/students/{id}` | Delete student   | ADMIN         |

**Get All Students Example:**

```bash
GET http://localhost:8080/api/students
Authorization: Basic YWRtaW46YWRtaW4xMjM=
```

**Add Student Example:**

```bash
POST http://localhost:8080/api/students
Authorization: Basic YWRtaW46YWRtaW4xMjM=
Content-Type: application/json

{
    "name": "John Doe",
    "email": "john.doe@example.com",
    "phone": "1234567890",
    "course": "Computer Science",
    "department": "Engineering",
    "teacher": "Dr. Smith",
    "enrollmentDate": "2024-01-15"
}
```

### Future Endpoints (Planned)

- `/api/courses` - Course management
- `/api/departments` - Department management
- `/api/teachers` - Teacher management
- `/api/enrollments` - Enrollment tracking
- `/api/attendance` - Attendance management
- `/api/grades` - Grade management

---

## 🔐 Authentication & Authorization

### Authentication Method

**HTTP Basic Authentication**

- Encode credentials as Base64: `username:password`
- Send in Authorization header: `Authorization: Basic <base64-encoded-credentials>`

**Example:**

```
Username: admin
Password: admin123
Base64: YWRtaW46YWRtaW4xMjM=
Header: Authorization: Basic YWRtaW46YWRtaW4xMjM=
```

### Roles

| Role         | Permissions                                |
| ------------ | ------------------------------------------ |
| `ROLE_ADMIN` | Full access - Create, Read, Update, Delete |
| `ROLE_USER`  | Limited access - Read only for students    |

### Password Security

- **Algorithm:** BCrypt
- **Strength:** 10 rounds (default)
- **Salt:** Automatically generated per password
- **Storage:** Hashed password stored in MongoDB

---

## 💾 Database Schema

### Users Collection

```json
{
  "_id": "ObjectId",
  "username": "string (unique)",
  "password": "string (BCrypt hashed)",
  "email": "string",
  "role": "string (ROLE_ADMIN | ROLE_USER)",
  "active": "boolean (default: true)"
}
```

### Students Collection

```json
{
  "_id": "ObjectId",
  "name": "string",
  "email": "string (unique)",
  "phone": "string",
  "course": "string",
  "department": "string",
  "teacher": "string",
  "enrollmentDate": "date"
}
```

### Future Collections

- **courses** - Course information
- **departments** - Department details
- **teachers** - Teacher profiles
- **enrollments** - Student-course enrollments
- **attendance** - Attendance records
- **grades** - Student grades

---

## 🔧 Configuration

### Security Configuration

**File:** `SecurityConfig.java`

Key configurations:

- CSRF disabled for API
- HTTP Basic authentication enabled
- URL-based security rules
- Method-level security enabled
- BCrypt password encoder
- Custom UserDetailsService integration

### MongoDB Configuration

**File:** `application.properties`

```properties
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=academic_records
```

For MongoDB Atlas:

```properties
spring.data.mongodb.uri=mongodb+srv://username:password@cluster.mongodb.net/academic_records
```

---

## 🚀 Future Enhancements

### Phase 1: Complete Core Modules

- [ ] Implement Course CRUD operations
- [ ] Implement Department CRUD operations
- [ ] Implement Teacher CRUD operations
- [ ] Add validation and error handling
- [ ] Write unit and integration tests

### Phase 2: Advanced Features

- [ ] **JWT Authentication**
  - Replace HTTP Basic with JWT tokens
  - Token refresh mechanism
  - Token blacklist for logout

- [ ] **Enrollment System**
  - Student-course enrollment
  - Enrollment capacity management
  - Waitlist functionality

- [ ] **Attendance Management**
  - Mark attendance
  - Attendance reports
  - Attendance alerts

### Phase 3: Analytics & Reporting

- [ ] Dashboard with statistics
- [ ] Student performance reports
- [ ] Attendance analytics
- [ ] Grade distribution charts
- [ ] Export reports (PDF, Excel)

### Phase 4: Communication

- [ ] Email notifications
- [ ] SMS alerts
- [ ] In-app messaging
- [ ] Announcements system

### Phase 5: UI/UX

- [ ] React/Angular frontend
- [ ] Responsive design
- [ ] Student portal
- [ ] Admin dashboard
- [ ] Teacher portal

### Phase 6: Advanced Security

- [ ] Two-factor authentication (2FA)
- [ ] OAuth2 integration (Google, GitHub)
- [ ] Audit logging
- [ ] Session management
- [ ] Rate limiting

### Phase 7: Deployment

- [ ] Docker containerization
- [ ] CI/CD pipeline
- [ ] Cloud deployment (AWS, Azure, GCP)
- [ ] Load balancing
- [ ] Monitoring and logging

---

## 📝 Development Notes

### Common Commands

```bash
# Build project
mvn clean install

# Run application
mvn spring-boot:run

# Run tests
mvn test

# Package as JAR
mvn package

# Skip tests during build
mvn clean install -DskipTests

# Check for dependency updates
mvn versions:display-dependency-updates
```

### Testing with Postman

1. **Import collection** (create one with all endpoints)
2. **Set up environment variables:**
   - `baseUrl`: `http://localhost:8080`
   - `username`: `admin`
   - `password`: `admin123`
3. **Use Collection Runner** for automated testing

### MongoDB Commands

```bash
# Connect to MongoDB
mongosh

# Use database
use academic_records

# View collections
show collections

# Query users
db.users.find().pretty()

# Query students
db.students.find().pretty()

# Drop collection (be careful!)
db.users.drop()
```

---

## 🐛 Troubleshooting

### Common Issues

**1. Port 8080 already in use**

```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <process_id> /F

# Linux/Mac
lsof -i :8080
kill -9 <process_id>
```

**2. MongoDB connection failed**

- Verify MongoDB is running
- Check connection string in `application.properties`
- Ensure MongoDB port (27017) is not blocked

**3. 401 Unauthorized on public endpoints**

- Clear browser cache
- Check SecurityConfig permitAll rules
- Restart application after security changes

**4. Password encoding mismatch**

- Ensure PasswordEncoder bean is properly configured
- Use same encoder for registration and authentication

---

## 👥 Contributors

- **Developer:** [Your Name]
- **Project Type:** Academic/Learning Project
- **Framework:** Spring Boot 4.0.2
- **Database:** MongoDB

---

## 📄 License

This project is created for educational purposes.

---

## 📞 Support

For issues or questions:

- Create an issue in the repository
- Contact: [your-email@example.com]

---

## 🙏 Acknowledgments

- Spring Boot Documentation
- Spring Security Documentation
- MongoDB Documentation
- Baeldung Spring Security Tutorials
- Stack Overflow Community

---

**Last Updated:** February 7, 2026

**Status:** 🚧 In Development - Core authentication and student management implemented
