package dev.aymee.support_api.request;

import java.util.List;

public interface IRequestService {
    List<RequestDto> getAllRequests();
    RequestDto getRequestById(Long id);
    RequestDto createRequest(RequestCreationDto requestCreationDto);
    RequestDto updateRequestStatus(Long id, RequestUpdateDto requestUpdateDto);
    RequestDto editRequest(Long id, RequestEditDto editDto);
    void deleteRequest(Long id);
}
