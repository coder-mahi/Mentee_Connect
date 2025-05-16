// StudentResponse.java
package com.mahesh.mentee_connect.payload.response;

import lombok.Data;

@Data
public class StudentResponse {
    private String id;
    private String name;
    private String email;
    private String batch;
}