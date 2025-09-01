package dev.aymee.support_api.request;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import dev.aymee.support_api.topic.TopicEntity;
import dev.aymee.support_api.topic.TopicRepository;

@Service
public class RequestService {
   private final RequestRepository requestRepository;
    private final TopicRepository topicRepository;

    public RequestService(RequestRepository requestRepository, TopicRepository topicRepository) {
        this.requestRepository = requestRepository;
        this.topicRepository = topicRepository;
    }

public RequestDto createRequest(RequestCreationDto requestDto) {
        Optional<TopicEntity> topic = topicRepository.findById(requestDto.getTopicId());
        
        if (topic.isEmpty()) {
            throw new IllegalArgumentException("Topic not found with ID: " + requestDto.getTopicId());
        }

        RequestEntity newRequest = new RequestEntity();
        newRequest.setApplicantName(requestDto.getApplicantName());
        newRequest.setTopic(topic.get());
        newRequest.setDescription(requestDto.getDescription());
        newRequest.setRequestDate(LocalDateTime.now());
        newRequest.setStatus(RequestStatusEntity.PENDING);
        
        RequestEntity savedRequest = requestRepository.save(newRequest);
        return convertToDto(savedRequest);
    }

    public List<RequestDto> getAllRequests() {
        return requestRepository.findAllByOrderByRequestDateAsc()
            .stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    private RequestDto convertToDto(RequestEntity request) {
        RequestDto dto = new RequestDto();
        dto.setId(request.getId());
        dto.setApplicantName(request.getApplicantName());
        dto.setRequestDate(request.getRequestDate());
        dto.setDescription(request.getDescription());
        dto.setStatus(request.getStatus().name());
        dto.setAttendedBy(request.getAttendedBy());
        dto.setAttendedDate(request.getAttendedDate());
        dto.setEditedDate(request.getEditedDate());
        
        // Mapea el nombre del tema
        if (request.getTopic() != null) {
            dto.setTopicName(request.getTopic().getName());
        }
        
        return dto;
    }
    
}
