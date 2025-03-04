package com.services;

import com.dtos.RequestDto;

import java.util.List;

public interface RequestService {
    RequestDto saveRequest(RequestDto requestDto);
    RequestDto saveRequest(Long academicYearId, Long studentId);
    boolean deleteRequest(Long requestId);

    boolean deleteRequest(Long academicYearId, Long studentId);
    List<RequestDto> getAllRequests();

    boolean existsRequest(Long academicYearId, Long studentId);
}
