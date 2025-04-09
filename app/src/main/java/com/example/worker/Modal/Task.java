package com.example.worker.Modal;

public class Task {
    private String title;
    private String description;
    private String time;
    private String imageUri; // Thêm dòng này

    // Constructor đầy đủ
    public Task(String title, String description, String time, String imageUri) {
        this.title = title;
        this.description = description;
        this.time = time;
        this.imageUri = imageUri;
    }

    // Constructor nếu không có ảnh (tuỳ bạn dùng)
    public Task(String title, String description, String time) {
        this.title = title;
        this.description = description;
        this.time = time;
        this.imageUri = null;
    }

    // Getter Setter cho title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter Setter cho description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter Setter cho time
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    // Getter Setter cho imageUri
    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
