package dev.aymee.support_api.request;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
 
}
