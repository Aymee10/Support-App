package dev.aymee.support_api.requests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dev.aymee.support_api.request.RequestDto;
import dev.aymee.support_api.request.RequestEntity;
import dev.aymee.support_api.request.RequestRepository;
import dev.aymee.support_api.request.RequestService;
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
}
