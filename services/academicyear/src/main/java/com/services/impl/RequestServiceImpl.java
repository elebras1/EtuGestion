package com.services.impl;

import com.dtos.RequestDto;
import com.entities.Request;
import com.mappers.RequestMapper;
import com.repositories.RequestRepository;
import com.services.RequestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("request")
@Transactional
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;

    public RequestServiceImpl(RequestRepository requestRepository, RequestMapper requestMapper) {
        this.requestRepository = requestRepository;
        this.requestMapper = requestMapper;
    }
    @Override
    public RequestDto saveRequest(RequestDto requestDto) {
        Request request = this.requestMapper.toEntity(requestDto);
        request = this.requestRepository.save(request);
        return this.requestMapper.toDto(request);
    }

    @Override
    public RequestDto saveRequest(Long academicYearId, Long studentId) {
        Request request = new Request();
        request.setAcademicYearId(academicYearId);
        request.setStudentId(studentId);
        request = this.requestRepository.save(request);
        return this.requestMapper.toDto(request);
    }

    @Override
    public boolean deleteRequest(Long requestId) {
        this.requestRepository.deleteById(requestId);
        return this.requestRepository.findById(requestId).isEmpty();
    }

    @Override
    public boolean deleteRequest(Long academicYearId, Long studentId) {
        Request request = this.requestRepository.findByAcademicYearIdAndStudentId(academicYearId, studentId);
        if(request != null) {
            this.requestRepository.delete(request);
            return this.requestRepository.findByAcademicYearIdAndStudentId(academicYearId, studentId) == null;
        }
        return false;
    }

    @Override
    public List<RequestDto> getAllRequests() {
        List<RequestDto> requestDtos = new ArrayList<>();
        for(Request request : this.requestRepository.findAll()) {
            requestDtos.add(this.requestMapper.toDto(request));
        }
        return requestDtos;
    }

    @Override
    public boolean existsRequest(Long academicYearId, Long studentId) {
        return this.requestRepository.findByAcademicYearIdAndStudentId(academicYearId, studentId) != null;
    }
}
