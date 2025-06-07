package com.mahesh.mentee_connect.dto;

import java.util.List;
import org.springframework.data.domain.Page;

public class StudentProfileResponseDTO {
    private List<StudentProfileDTO> students;
    private int currentPage;
    private int totalPages;
    private long totalItems;
    private int pageSize;

    public StudentProfileResponseDTO(Page<StudentProfileDTO> page) {
        this.students = page.getContent();
        this.currentPage = page.getNumber();
        this.totalPages = page.getTotalPages();
        this.totalItems = page.getTotalElements();
        this.pageSize = page.getSize();
    }

    // Getters and Setters
    public List<StudentProfileDTO> getStudents() {
        return students;
    }

    public void setStudents(List<StudentProfileDTO> students) {
        this.students = students;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
} 