# 📚 Student Mentoring Application

A **Student Mentoring Web Application** developed using **Spring Boot** for managing students, mentors, and batches efficiently.  
This application allows administrators to manage mentors and students, mentors to update student details, and students to view their assigned mentors.

---

## ✨ Features

- **Admin Panel**
  - Add, View, Update, Delete Mentors
  - Add, View, Update, Delete Students
  - Create and Manage Batches

- **Mentor Panel**
  - View Allocated Students
  - Update Student Details (Contact, Qualification, Certificates)
  - Delete Student (if necessary)

- **Student Panel**
  - View Personal Information
  - View Allocated Mentor Details

- **Authentication**
  - Login system for Admin, Mentor, and Student roles
  - Secure Role-Based Access Control

- **Responsive UI**
  - Clean and user-friendly frontend (using Thymeleaf / HTML templates)

---

## 🛠️ Tech Stack

- **Backend:** Spring Boot, Spring Data JPA, Spring Data MongoDB
- **Frontend:** Thymeleaf (HTML, CSS, JavaScript)
- **Database:** MongoDB
- **Build Tool:** Maven
- **Deployment:** (Render / Railway / AWS / Netlify / Vercel)

---

## 📂 Project Structure

```bash
student-mentoring-app/
├── src/main/java/com/mahesh/mentee_connect
│   ├── controller/
│   ├── model/
│   ├── repository/
│   ├── service/
│   └── StudentMentoringAppApplication.java
├── src/main/resources/
│   ├── static/
│   │   ├── css/
│   │   └── js/
│   ├── templates/
│   │   └── (Thymeleaf HTML files)
│   ├── application.properties
├── pom.xml
└── README.md
