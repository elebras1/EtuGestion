package com.services;

import com.dtos.RequestDto;

import java.util.List;

public interface RequestService {
    RequestDto saveRequest(RequestDto requestDto);
    boolean deleteRequest(Long requestId);
    List<RequestDto> getAllRequests();
}
