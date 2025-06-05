# 🚀 MenteeConnect – Backend

<div align="center">
  <img src="https://img.shields.io/badge/Spring_Boot-3.0+-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" />
  <img src="https://img.shields.io/badge/MongoDB-4.4+-47A248?style=for-the-badge&logo=mongodb&logoColor=white" />
  <img src="https://img.shields.io/badge/JWT-Auth-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white" />
  <img src="https://img.shields.io/badge/WebRTC-Socket.io-FE6F61?style=for-the-badge&logo=webrtc&logoColor=white" />
</div>

<div align="center">
  <h3>🎯 Backend for Role-Based Mentor-Student Management System</h3>
  <p><em>Secure, scalable backend service built with Spring Boot and MongoDB to power an academic mentorship platform.</em></p>
</div>

---

## ✨ Features

- Role-based authentication using JWT (Admin, Mentor, Student)
- MongoDB-based data storage
- CRUD APIs for user, batch, student, mentor management
- Profile update and file upload support
- Goal assignment and certificate tracking for students
- RESTful design with Spring Boot
- Real-time communication setup with WebRTC (under development)
- WebSocket notifications integration (partial)

---

## 🛠️ Technologies Used

| Category     | Tech Stack                                 |
|--------------|---------------------------------------------|
| Language     | Java 17+                                    |
| Framework    | Spring Boot 3+                              |
| Database     | MongoDB 4.4+ (NoSQL)                        |
| Security     | Spring Security, JWT Authentication         |
| Realtime     | WebSocket (Spring Messaging)                |
| API Format   | REST APIs (JSON-based)                      |
| Build Tool   | Maven                                       |
| Logging      | SLF4J + Logback                             |
| Dev Tools    | MongoDB Compass, Postman                    |
| Deployment   | Docker (backend container support)          |

---

## 🧩 Backend Folder Structure

```
backend/
└── src/
    └── main/
        ├── java/com/menteeconnect/
        │   ├── controllers/       # REST Controllers
        │   ├── services/          # Service Layer
        │   ├── models/            # MongoDB Models
        │   ├── repositories/      # MongoDB Repositories
        │   ├── security/          # JWT, Auth Filters
        │   └── config/            # CORS, WebSocket, etc.
        └── resources/
            ├── application.yml    # Environment Configs
            └── static/            # Static files (if any)
```

---

## ⚙️ Configuration

### `application.yml`

```yaml
server:
  port: 8080

spring:
  data:
    mongodb:
      uri: mongodb://localhost:27017/menteeconnect
      database: menteeconnect
  servlet:
    multipart:
      enabled: true
      max-file-size: 10MB
      max-request-size: 10MB

jwt:
  secret: ${JWT_SECRET}   # Load this from ENV in production
  expiration: 86400000    # 1 day in ms
```

---

## 🚀 How to Run

### Prerequisites
- Java 17+
- Maven 3.6+
- MongoDB 4.4+ (running on localhost)

### Run Commands

```bash
# Navigate to backend folder
cd backend

# Build the project
mvn clean install

# Start the server
mvn spring-boot:run
```

> Ensure MongoDB is running locally on `localhost:27017` with a `menteeconnect` database created (it will auto-create if not present).

---

## 🔒 Security Highlights

- **JWT-Based Authentication**: Every API is protected with token validation.
- **Role-Based Access Control**: Admin/Mentor/Student-specific routes.
- **Password Hashing**: All user passwords are stored using BCrypt encryption.

---

## 📡 API Modules

- **Auth APIs**: `/api/auth/` (Login, Register, Token)
- **User Management**: `/api/users/`
- **Mentor APIs**: `/api/mentors/`
- **Student APIs**: `/api/students/`
- **Batch Management**: `/api/batches/`
- **File Uploads**: `/api/uploads/`
- **Certificates**: `/api/certificates/`
- **Progress Tracking**: `/api/goals/`
- **Notifications**: `/api/notifications/`

---

## 🐳 Docker Deployment

```dockerfile
# Dockerfile for backend
FROM openjdk:17-jdk-slim
COPY target/menteeconnect.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Build & Run:

```bash
docker build -t menteeconnect-backend .
docker run -p 8080:8080 menteeconnect-backend
```

---

## 🔭 Future Enhancements

- [ ] Email notifications via SMTP/Mailgun
- [ ] Cloud storage for files (AWS S3/GCP)
- [ ] Kafka-based event handling
- [ ] Redis caching layer
- [ ] Admin analytics dashboard
- [ ] Unit + Integration Tests
- [ ] OAuth2 integration (Google/GitHub)

---

## 🙋‍♂️ Developed By

**Mahesh Shinde**  
📫 Email: contact.shindemahesh2112@gmail.com  
🌐 Portfolio: [https://shindemaheshportfolio.netlify.app](https://shindemaheshportfolio.netlify.app)

> 🛡️ This backend service is a part of the MenteeConnect platform built for academic mentorship management. All rights reserved. Please do not reuse without permission.