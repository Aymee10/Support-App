package dev.aymee.support_api.request;

public class RequestCreationDto {
    private String applicantName;
    private Long topicId;
    private String description;
    public RequestCreationDto() {
    }
    public String getApplicantName() {
        return applicantName;
    }
    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }
    public Long getTopicId() {
        return topicId;
    }
    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    
}
