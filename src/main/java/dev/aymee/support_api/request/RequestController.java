package dev.aymee.support_api.request;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/requests")
public class RequestController {
   private final RequestService requestService;

   public RequestController(RequestService requestService) {
    this.requestService = requestService;
   }

     @GetMapping
    public ResponseEntity<List<RequestDto>> getAllRequests() {
        List<RequestDto> requests = requestService.getAllRequests();
        return ResponseEntity.ok(requests);
    }

    @PostMapping
    public ResponseEntity<RequestDto> createRequest(@Valid @RequestBody RequestCreationDto requestCreationDto) {
        RequestDto createdRequest = requestService.createRequest(requestCreationDto);
        return new ResponseEntity<>(createdRequest, HttpStatus.CREATED);
    }
 
   @PatchMapping("/{id}")
    public ResponseEntity<RequestDto> updateRequestStatus(@PathVariable Long id, @Valid @RequestBody RequestUpdateDto requestUpdateDto) {
        RequestDto updatedRequest = requestService.updateRequestStatus(id, requestUpdateDto);
        return ResponseEntity.ok(updatedRequest);
    }
}
