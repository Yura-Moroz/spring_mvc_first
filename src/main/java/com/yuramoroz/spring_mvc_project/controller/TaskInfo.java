package com.yuramoroz.spring_mvc_project.controller;

import com.yuramoroz.spring_mvc_project.domain.Status;

//DTO
public class TaskInfo {

    private String description;

    private Status status;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
