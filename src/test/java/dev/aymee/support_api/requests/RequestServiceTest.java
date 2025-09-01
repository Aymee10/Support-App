package dev.aymee.support_api.requests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.any;

import dev.aymee.support_api.exception.RequestException;
import dev.aymee.support_api.request.RequestCreationDto;
import dev.aymee.support_api.request.RequestDto;
import dev.aymee.support_api.request.RequestEntity;
import dev.aymee.support_api.request.RequestRepository;
import dev.aymee.support_api.request.RequestService;
import dev.aymee.support_api.request.RequestStatusEntity;
import dev.aymee.support_api.topic.TopicEntity;
import dev.aymee.support_api.topic.TopicRepository;


public class RequestServiceTest {
    @Mock
    private RequestRepository requestRepository;
    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private RequestService requestService;

     @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);}

    @Test
    void testGetAllRequests() {
        TopicEntity topic = new TopicEntity();
        topic.setName("Test Topic");
    
        RequestEntity request1 = new RequestEntity();
        request1.setApplicantName("Alice");
        request1.setTopic(topic);

        RequestEntity request2 = new RequestEntity();
       request2.setApplicantName("Bob");
        request2.setTopic(topic);

        when(requestRepository.findAllByOrderByRequestDateAsc()).thenReturn(Arrays.asList(request1, request2));
         List<RequestDto> result = requestService.getAllRequests();
         
         assertEquals(2, result.size());
         assertEquals("Alice", result.get(0).getApplicantName());
        assertEquals("Bob", result.get(1).getApplicantName());

    }
    @Test
    void testCreateRequest_Success() {
        // Given: Datos de entrada y objetos mock
        RequestCreationDto creationDto = new RequestCreationDto();
        creationDto.setApplicantName("Alice");
        creationDto.setTopicId(1L);
        creationDto.setDescription("Test description");

        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setName("Test Topic");

        RequestEntity savedEntity = new RequestEntity();
        savedEntity.setId(1L);
        savedEntity.setApplicantName("Alice");
        savedEntity.setTopic(topicEntity);
        savedEntity.setDescription("Test description");
        savedEntity.setStatus(RequestStatusEntity.PENDING);
        savedEntity.setRequestDate(LocalDateTime.now());

        // Configurar el comportamiento de los mocks
        when(topicRepository.findById(1L)).thenReturn(Optional.of(topicEntity));
        when(requestRepository.save(any(RequestEntity.class))).thenReturn(savedEntity);

        // When: Llamar al método del servicio
        RequestDto result = requestService.createRequest(creationDto);

        // Then: Verificar el resultado
        assertEquals(savedEntity.getId(), result.getId());
        assertEquals(savedEntity.getApplicantName(), result.getApplicantName());
        assertEquals(savedEntity.getTopic().getName(), result.getTopicName());
        assertEquals(savedEntity.getDescription(), result.getDescription());
    }

    @Test
    void testCreateRequest_TopicNotFound() {
        // Given: Un DTO con un ID de tema que no existe
        RequestCreationDto creationDto = new RequestCreationDto();
        creationDto.setTopicId(99L);
        
        // Configurar el mock para que no encuentre el tema
        when(topicRepository.findById(99L)).thenReturn(Optional.empty());

        // Then: Verificar que se lanza una excepción
        assertThrows(RequestException.class, () -> requestService.createRequest(creationDto));
    }
}
