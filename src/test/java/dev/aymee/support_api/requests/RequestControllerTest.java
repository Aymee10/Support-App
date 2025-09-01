package dev.aymee.support_api.requests;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
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
import dev.aymee.support_api.request.RequestEditDto;
import dev.aymee.support_api.request.RequestRepository;
import dev.aymee.support_api.request.RequestStatusEntity;
import dev.aymee.support_api.request.RequestUpdateDto;
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
     @Autowired 
    private RequestRepository requestRepository;
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
    @AfterEach 
    void cleanup() {
        requestRepository.deleteAll();
        topicRepository.deleteAll();
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

    @Test
    void testUpdateRequestStatus_Success() throws Exception {
        
        RequestCreationDto creationDto = new RequestCreationDto();
        creationDto.setApplicantName("Alice");
        creationDto.setTopicId(existingTopicId);
        creationDto.setDescription("Test to update status");
        
        
        String response = mockMvc.perform(MockMvcRequestBuilders.post("/api/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creationDto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
                
       
        Long requestId = objectMapper.readTree(response).get("id").asLong();

       
        RequestUpdateDto updateDto = new RequestUpdateDto();
        updateDto.setStatus(RequestStatusEntity.ATTENDED);

        
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/requests/{id}", requestId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(requestId.intValue())))
                .andExpect(jsonPath("$.status", is("ATTENDED")));
    }

    @Test
    void testUpdateRequestStatus_NotFound() throws Exception {
        // Dado: un DTO de actualización y un ID que no existe
        RequestUpdateDto updateDto = new RequestUpdateDto();
        updateDto.setStatus(RequestStatusEntity.ATTENDED);
        
        Long nonExistentId = 9999L;

        // Cuando: se envía una petición PATCH con un ID inexistente
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/requests/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isNotFound()); // Entonces: esperamos un 404
    }

    @Test
    void testUpdateRequestStatus_InvalidData() throws Exception {
        // Dado: un DTO de actualización con un valor de estado nulo (inválido)
        RequestUpdateDto updateDto = new RequestUpdateDto();
        updateDto.setStatus(null); 
        
        // Cuando: se envía la petición PATCH con un cuerpo inválido
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/requests/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest()); 
    }

    @Test
    void testEditRequest_Success() throws Exception {
        // Dado: una petición existente que creamos para el test
        RequestCreationDto creationDto = new RequestCreationDto();
        creationDto.setApplicantName("Old Name");
        creationDto.setTopicId(existingTopicId);
        creationDto.setDescription("Original description.");
        
        String response = mockMvc.perform(MockMvcRequestBuilders.post("/api/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creationDto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
                
        Long requestId = objectMapper.readTree(response).get("id").asLong();

        // Cuando: se envía una petición PUT para editar la petición
        RequestEditDto editDto = new RequestEditDto();
        editDto.setApplicantName("New Name");
        editDto.setTopicId(existingTopicId);
        editDto.setDescription("Updated description for the request.");

        // Entonces: el endpoint debe devolver un 200 OK y la petición editada
        mockMvc.perform(MockMvcRequestBuilders.put("/api/requests/{id}", requestId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(editDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(requestId.intValue())))
                .andExpect(jsonPath("$.applicantName", is("New Name")))
                .andExpect(jsonPath("$.topicName", is("Test Topic for Request")))
                .andExpect(jsonPath("$.description", is("Updated description for the request.")));
    }

    @Test
    void testEditRequest_NotFound() throws Exception {
        // Dado: un DTO de edición y un ID que no existe
        RequestEditDto editDto = new RequestEditDto();
        editDto.setApplicantName("Non-existent");
        editDto.setTopicId(existingTopicId);
        editDto.setDescription("This request should not be found.");
        
        Long nonExistentId = 9999L;

        // Cuando: se envía una petición PUT con un ID inexistente
        mockMvc.perform(MockMvcRequestBuilders.put("/api/requests/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(editDto)))
                .andExpect(status().isNotFound()); // Entonces: esperamos un 404
    }


     @Test
    void testDeleteRequest_Success_WhenAttended() throws Exception {
        
        RequestCreationDto creationDto = new RequestCreationDto();
        creationDto.setApplicantName("Attended Request");
        creationDto.setTopicId(existingTopicId);
        creationDto.setDescription("This request is to be deleted.");
        
        String response = mockMvc.perform(MockMvcRequestBuilders.post("/api/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creationDto)))
                .andReturn().getResponse().getContentAsString();
                
        Long requestId = objectMapper.readTree(response).get("id").asLong();

        RequestUpdateDto updateDto = new RequestUpdateDto();
        updateDto.setStatus(RequestStatusEntity.ATTENDED);
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/requests/{id}", requestId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk());

        // Cuando: se envía una petición DELETE con el ID de la petición atendida
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/requests/{id}", requestId))
                .andExpect(status().isNoContent()); 

        // Verificamos que la petición ya no existe
        mockMvc.perform(MockMvcRequestBuilders.get("/api/requests/{id}", requestId))
               .andExpect(status().isNotFound());
    }
    
}
