package dev.aymee.support_api.request;

import java.time.LocalDateTime;

public class RequestDto {
    private Long id;
    private String applicantName;
    private LocalDateTime requestDate;
    private String topicName;
    private String description;
    private String status;
    private String attendedBy;
    private LocalDateTime attendedDate;
    private LocalDateTime editedDate;
    public RequestDto() {
    }
    public RequestDto(Long id, String applicantName, LocalDateTime requestDate, String topicName, String description,
            String status, String attendedBy, LocalDateTime attendedDate, LocalDateTime editedDate) {
        this.id = id;
        this.applicantName = applicantName;
        this.requestDate = requestDate;
        this.topicName = topicName;
        this.description = description;
        this.status = status;
        this.attendedBy = attendedBy;
        this.attendedDate = attendedDate;
        this.editedDate = editedDate;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getApplicantName() {
        return applicantName;
    }
    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }
    public LocalDateTime getRequestDate() {
        return requestDate;
    }
    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }
    public String getTopicName() {
        return topicName;
    }
    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getAttendedBy() {
        return attendedBy;
    }
    public void setAttendedBy(String attendedBy) {
        this.attendedBy = attendedBy;
    }
    public LocalDateTime getAttendedDate() {
        return attendedDate;
    }
    public void setAttendedDate(LocalDateTime attendedDate) {
        this.attendedDate = attendedDate;
    }
    public LocalDateTime getEditedDate() {
        return editedDate;
    }
    public void setEditedDate(LocalDateTime editedDate) {
        this.editedDate = editedDate;
    }

    
}
