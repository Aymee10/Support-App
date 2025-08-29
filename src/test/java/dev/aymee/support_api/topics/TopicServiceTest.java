package dev.aymee.support_api.topics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dev.aymee.support_api.topic.TopicDto;
import dev.aymee.support_api.topic.TopicEntity;
import dev.aymee.support_api.topic.TopicRepository;
import dev.aymee.support_api.topic.TopicService;

public class TopicServiceTest {
     @Mock
    private TopicRepository topicRepository;
     @InjectMocks
    private TopicService topicService;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);}

         TopicEntity topic = new TopicEntity();



@Test
    void testGetAllTopics() {
        // Given: Prepara los datos que el repositorio debe devolver
        TopicEntity topic1 = new TopicEntity("Software Issue");
        TopicEntity topic2 = new TopicEntity("Hardware Problem");
        
        // Simula la llamada al repositorio, pero no te preocupes por los IDs.
        when(topicRepository.findAllByOrderByIdAsc()).thenReturn(Arrays.asList(topic1, topic2));

        // When: Llama al método del servicio
        List<TopicDto> topics = topicService.getAllTopics();

        // Then: Solo verifica el resultado esperado, sin validar los IDs, ya que es un test unitario
        assertEquals(2, topics.size());
        assertEquals("Software Issue", topics.get(0).getName());
        assertEquals("Hardware Problem", topics.get(1).getName());
    }}