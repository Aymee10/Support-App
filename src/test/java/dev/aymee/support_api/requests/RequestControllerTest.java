package dev.aymee.support_api.requests;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import dev.aymee.support_api.exception.RequestException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import dev.aymee.support_api.request.RequestCreationDto;
import dev.aymee.support_api.topic.TopicEntity;
import dev.aymee.support_api.topic.TopicRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class RequestControllerTest {

    @Autowired
    private MockMvc mockMvc; 
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TopicRepository topicRepository;

    private Long existingTopicId;

    @BeforeEach
    void setup() {
        TopicEntity topic = topicRepository.findByName("Test Topic for Request")
                .orElseGet(() -> {
                    TopicEntity newTopic = new TopicEntity("Test Topic for Request");
                    return topicRepository.save(newTopic);
                });
        existingTopicId = topic.getId();
    }


     @Test
    void testGetAllRequestsEndpoint() throws Exception {
        
        mockMvc.perform(MockMvcRequestBuilders.get("/api/requests"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.length()", is(0)));
    }

    @Test
    void testCreateRequest_Success() throws Exception {
        RequestCreationDto creationDto = new RequestCreationDto();
        creationDto.setApplicantName("Integration Test User");
        creationDto.setTopicId(existingTopicId);
        creationDto.setDescription("This is a test request description.");

        mockMvc.perform(post("/api/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creationDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.applicantName", is("Integration Test User")))
                .andExpect(jsonPath("$.topicName", is("Test Topic for Request")))
                .andExpect(jsonPath("$.status", is("PENDING")));
    }

    @Test
    void testCreateRequest_InvalidData() throws Exception {
        RequestCreationDto creationDto = new RequestCreationDto();
        creationDto.setApplicantName(""); // Nombre inválido, la validación fallará
        creationDto.setTopicId(existingTopicId);
        creationDto.setDescription("short"); // Descripción muy corta

        mockMvc.perform(post("/api/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creationDto)))
                .andExpect(status().isBadRequest());
    }

@Test
    void testCreateRequest_TopicNotFound() throws Exception {
        RequestCreationDto creationDto = new RequestCreationDto();
        creationDto.setApplicantName("Jane Doe");
        creationDto.setTopicId(999L); // ID de tema que no existe
        creationDto.setDescription("This request should fail because the topic is not found.");

        mockMvc.perform(post("/api/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creationDto)))
                .andExpect(status().isNotFound()); 
    }
}
