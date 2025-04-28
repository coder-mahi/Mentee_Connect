package com.mahesh.mentee_connect.payload;

public class MentorAssignRequest {
    private String studentEmail;
    private String mentorName;

    // Constructors
    public MentorAssignRequest() {}

    public MentorAssignRequest(String studentEmail, String mentorName) {
        this.studentEmail = studentEmail;
        this.mentorName = mentorName;
    }

    // Getters and Setters
    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }
}
