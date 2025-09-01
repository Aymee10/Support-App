package dev.aymee.support_api.request;


import java.time.LocalDateTime;
import java.util.List;

import java.util.stream.Collectors;
import dev.aymee.support_api.exception.RequestException;
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
         TopicEntity topic = topicRepository.findById(requestDto.getTopicId())
                .orElseThrow(() -> new RequestException("Topic not found with ID: " + requestDto.getTopicId())); 
RequestEntity newRequest = new RequestEntity();
        newRequest.setApplicantName(requestDto.getApplicantName());
        newRequest.setTopic(topic);
        newRequest.setDescription(requestDto.getDescription());
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

    public RequestDto getRequestById(Long id) {
    RequestEntity request = requestRepository.findById(id)
            .orElseThrow(() -> new RequestException("Request not found with ID: " + id));
    return convertToDto(request);
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
    public RequestDto updateRequestStatus(Long id, RequestUpdateDto updateDto) {
        RequestEntity existingRequest = requestRepository.findById(id)
                .orElseThrow(() -> new RequestException("Request not found with ID: " + id));

        existingRequest.setStatus(updateDto.getStatus());
        
        RequestEntity updatedRequest = requestRepository.save(existingRequest);
        return convertToDto(updatedRequest);
    }

    public RequestDto editRequest(Long id, RequestEditDto editDto) {
        RequestEntity existingRequest = requestRepository.findById(id)
                .orElseThrow(() -> new RequestException("Request not found with ID: " + id));

        TopicEntity topic = topicRepository.findById(editDto.getTopicId())
                .orElseThrow(() -> new RequestException("Topic not found with ID: " + editDto.getTopicId()));

        existingRequest.setApplicantName(editDto.getApplicantName());
        existingRequest.setTopic(topic);
        existingRequest.setDescription(editDto.getDescription());
        existingRequest.setEditedDate(LocalDateTime.now()); // Actualiza la fecha de edición

        RequestEntity editedRequest = requestRepository.save(existingRequest);
        return convertToDto(editedRequest);
    }

    public void deleteRequest(Long id) {
        RequestEntity request = requestRepository.findById(id)
            .orElseThrow(() -> new RequestException("Request not found with ID: " + id));

        // Solo se puede eliminar si la solicitud está atendida.
        if (request.getStatus() == RequestStatusEntity.PENDING) {
            throw new RequestException("Cannot delete a request that is still pending.");
        }

        requestRepository.delete(request);
    }
}
