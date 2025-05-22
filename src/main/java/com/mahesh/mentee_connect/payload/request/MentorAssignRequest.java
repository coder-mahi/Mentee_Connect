// MentorAssignRequest.java
package com.mahesh.mentee_connect.payload.request;

import lombok.Data;
import java.util.List;

@Data
public class MentorAssignRequest {
    private String mentorId;
    private List<String> studentIds;  // Changed to a list to support multiple students
}