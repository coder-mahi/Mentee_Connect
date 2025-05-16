// MentorResponse.java
package com.mahesh.mentee_connect.payload.response;

import lombok.Data;
import java.util.List;

@Data
public class MentorResponse {
    private String id;
    private String name;
    private String email;
    private List<String> expertise;
}