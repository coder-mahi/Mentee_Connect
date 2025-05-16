// MentorAssignRequest.java
package com.mahesh.mentee_connect.payload.request;

import lombok.Data;

@Data
public class MentorAssignRequest {
    private String studentId;
    private String mentorId;
}