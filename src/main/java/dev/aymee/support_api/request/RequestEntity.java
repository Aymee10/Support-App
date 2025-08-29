package dev.aymee.support_api.request;

import java.time.LocalDateTime;
import dev.aymee.support_api.topic.TopicEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="requests")
public class RequestEntity {

 @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

      @Column(nullable = false)
    private String applicantName;

    @Column(nullable = false)
    private LocalDateTime requestDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "topic_id", nullable = false)
    private TopicEntity topic;

    @Lob
    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private RequestStatusEntity status = RequestStatusEntity.PENDING;

    private String attendedBy;

    private LocalDateTime attendedDate;

    private LocalDateTime editedDate;

    public RequestEntity() {
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

    public TopicEntity getTopic() {
        return topic;
    }

    public void setTopic(TopicEntity topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RequestStatusEntity getStatus() {
        return status;
    }

    public void setStatus(RequestStatusEntity status) {
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
