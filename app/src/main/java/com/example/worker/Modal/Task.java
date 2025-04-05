package com.example.worker.Modal;

public class Task {
    private String title;
    private String description;
    private String time;

    // Constructor
    public Task(String title, String description, String time) {
        this.title = title;
        this.description = description;
        this.time = time;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTime(String time) {
        this.time = time;
    }

    // Setter for description (if you want to use it)
    public void setDesc(String desc) {
        this.description = desc;
    }
}
