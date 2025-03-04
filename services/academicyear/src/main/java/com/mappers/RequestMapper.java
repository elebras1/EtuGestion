package com.mappers;

import com.dtos.RequestDto;
import com.entities.Request;
import org.springframework.stereotype.Component;

@Component
public class RequestMapper {

    public RequestDto toDto(Request request) {
        if(request == null) {
            return null;
        }

        RequestDto requestDto = new RequestDto();
        requestDto.setStudentId(request.getStudentId());
        requestDto.setAcademicYearId(request.getAcademicYearId());

        return requestDto;
    }

    public Request toEntity(RequestDto requestDto) {
        if(requestDto == null) {
            return null;
        }

        Request request = new Request();
        request.setStudentId(requestDto.getStudentId());
        request.setAcademicYearId(requestDto.getAcademicYearId());

        return request;
    }
}
