package dev.aymee.support_api.request.entity;

import java.time.LocalDateTime;

import dev.aymee.support_api.topic.entity.Topic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class SupportRequest {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String requesterName;
    
    @NotNull
    private LocalDateTime requestDate;

    @ManyToOne (optional = false, fetch = FetchType.LAZY)
    private Topic topic;

   @NotBlank
   @Column(length = 2000)
   private String description;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false)
   private RequestStatus status=RequestStatus.PENDIENTE;

   private String attendedBy;
   private LocalDateTime attendedTime;

   @Column(nullable = false,updatable = false)
   private LocalDateTime creationDate;
   private LocalDateTime editionDate;

    // Hooks de JPA: se ejecutan automáticamente
    @PrePersist
    void onCreate(){
        this.creationDate=LocalDateTime.now();
        if(this.requestDate==null){
            this.requestDate=this.creationDate;
        }
        if(this.status==null){
            this.status=RequestStatus.PENDIENTE;
        }
    }

@PreUpdate
void onUpdate(){
    this.editionDate=LocalDateTime.now();
}

public Long getId() {
    return id;
}


public String getRequesterName() {
    return requesterName;
}

public void setRequesterName(String nameSolicitant) {
    this.requesterName = nameSolicitant;
}

public LocalDateTime getRequestDate() {
    return requestDate;
}

public void setRequestDate(LocalDateTime dateSolicitud) {
    this.requestDate = dateSolicitud;
}

public Topic getTopic() {
    return topic;
}

public void setTopic(Topic topic) {
    this.topic = topic;
}

public String getDescription() {
    return description;
}

public void setDescription(String description) {
    this.description = description;
}

public RequestStatus getStatus() {
    return status;
}

public void setStatus(RequestStatus status) {
    this.status = status;
}

public String getAttendedBy() {
    return attendedBy;
}

public void setAttendedBy(String attendedBy) {
    this.attendedBy = attendedBy;
}

public LocalDateTime getAttendedTime() {
    return attendedTime;
}

public void setAttendedTime(LocalDateTime attendedTime) {
    this.attendedTime = attendedTime;
}

public LocalDateTime getCreationDate() {
    return creationDate;
}

public void setCreationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
}

public LocalDateTime getEditionDate() {
    return editionDate;
}

public void setEditionDate(LocalDateTime editionDate) {
    this.editionDate = editionDate;
}



}
