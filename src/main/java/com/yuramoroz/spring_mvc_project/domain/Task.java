package com.yuramoroz.spring_mvc_project.domain;

import jakarta.persistence.*;

@Entity
@Table(schema = "todo", name = "task")
public class Task {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "status", nullable = false, columnDefinition = "tinyint")
    @Enumerated(EnumType.ORDINAL)
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
