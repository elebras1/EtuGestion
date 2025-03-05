package com.services.impl;

import com.dtos.*;
import com.entities.AcademicYear;
import com.entities.Group;
import com.entities.TeachingUnit;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mappers.AcademicYearMapper;
import com.mappers.GroupMapper;
import com.mappers.TeachingUnitMapper;
import com.repositories.AcademicYearRepository;
import com.repositories.GroupRepository;
import com.services.AcademicYearService;
import com.services.RequestService;
import com.services.TeachingUnitService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@Service("academicYear")
@Transactional
public class AcademicYearServiceImpl implements AcademicYearService {
    private final AcademicYearRepository academicYearRepository;
    private final AcademicYearMapper academicYearMapper;
    private final GroupMapper groupMapper;
    private final TeachingUnitService teachingUnitService;
    private final TeachingUnitMapper teachingUnitMapper;
    private final RequestService requestService;
    private final GroupRepository groupRepository;
    private final ObjectMapper objectMapper;

    public AcademicYearServiceImpl(AcademicYearRepository academicYearRepository, AcademicYearMapper academicYearMapper, GroupMapper groupMapper, TeachingUnitMapper teachingUnitMapper, RequestService requestService, GroupRepository groupRepository, ObjectMapper objectMapper, TeachingUnitService teachingUnitService) {
        this.academicYearRepository = academicYearRepository;
        this.academicYearMapper = academicYearMapper;
        this.groupMapper = groupMapper;
        this.teachingUnitMapper = teachingUnitMapper;
        this.requestService = requestService;
        this.groupRepository = groupRepository;
        this.objectMapper = objectMapper;
        this.teachingUnitService = teachingUnitService;
    }

    @Override
    public AcademicYearDto saveAcademicYear(AcademicYearDto academicYearDto) {
        AcademicYear academicYear = this.academicYearMapper.toEntity(academicYearDto);
        AcademicYear savedAcademicYear = this.academicYearRepository.save(academicYear);
        return this.academicYearMapper.toDto(savedAcademicYear);
    }

    @Override
    public AcademicYearDto updateAcademicYear(AcademicYearDto academicYearDto) {
        AcademicYear academicYear = this.academicYearRepository.findById(academicYearDto.getId()).orElseThrow(() ->
                new EntityNotFoundException("Academic year not found"));

        if (academicYearDto.getName() != null) {
            academicYear.setName(academicYearDto.getName());
        }
        if (academicYearDto.getPraticalWorkSize() != null) {
            academicYear.setPraticalWorkSize(academicYearDto.getPraticalWorkSize());
        }
        if (academicYearDto.getDirectedWorkSize() != null) {
            academicYear.setDirectedWorkSize(academicYearDto.getDirectedWorkSize());
        }
        if (academicYearDto.getNumberOptionalTeachingUnit() != null) {
            academicYear.setNumberOptionalTeachingUnit(academicYearDto.getNumberOptionalTeachingUnit());
        }
        if (academicYearDto.getResponsibleId() != null) {
            academicYear.setResponsibleId(academicYearDto.getResponsibleId());
        }

        academicYear = this.academicYearRepository.save(academicYear);
        return this.academicYearMapper.toDto(academicYear);
    }

    @Override
    public AcademicYearDto getAcademicYearById(Long academicYearId) {
        AcademicYear academicYear = this.academicYearRepository.findById(academicYearId).orElseThrow(() ->
                new EntityNotFoundException("Academic year not found"));
        return this.academicYearMapper.toDto(academicYear);
    }

    @Override
    public boolean deleteAcademicYear(Long academicYearId) {
        this.academicYearRepository.deleteById(academicYearId);
        return this.academicYearRepository.findById(academicYearId).isEmpty();
    }

    @Override
    public List<AcademicYearDto> getAllAcademicYears() {
        return this.academicYearRepository.findAll().stream().map(this.academicYearMapper::toDto).toList();
    }

    public List<Long> getStudentsIdByAcademicYear(Long academicYearId) {
        AcademicYear academicYear = this.academicYearRepository.findById(academicYearId).orElse(null);
        if (academicYear != null) {
            return academicYear.getGroups().stream().map(Group::getStudentsIds).flatMap(List::stream).toList();
        }

        return null;
    }

    @Override
    public List<GroupDto> getGroupsByAcademicYear(Long academicYearId) {
        AcademicYear academicYear = this.academicYearRepository.findById(academicYearId).orElse(null);
        if (academicYear == null) {
            return null;
        }

        List<GroupDto> groupDtos = new ArrayList<>();
        for (Group group : academicYear.getGroups()) {
            groupDtos.add(this.groupMapper.toDto(group));
        }

        return groupDtos;
    }

    @Override
    public List<TeachingUnitDto> getTeachingUnitsByAcademicYear(Long academicYearId) {
        AcademicYear academicYear = this.academicYearRepository.findById(academicYearId).orElse(null);
        if (academicYear == null) {
            return null;
        }

        List<TeachingUnitDto> teachingUnitDtos = new ArrayList<>();
        for(TeachingUnit teachingUnit : academicYear.getTeachingUnits()) {
            teachingUnitDtos.add(this.teachingUnitMapper.toDto(teachingUnit));
        }

        return teachingUnitDtos;
    }

    @Override
    public boolean registerStudentToAcademicYear(Long academicYearId, Long studentId) {
        return this.requestService.saveRequest(academicYearId, studentId) != null;
    }

    @Override
    public boolean acceptStudentToAcademicYear(Long academicYearId, Long studentId) {
        AcademicYear academicYear = this.academicYearRepository.findById(academicYearId).orElse(null);
        if (academicYear == null) {
            return false;
        }

        boolean requestExists = this.requestService.existsRequest(academicYearId, studentId);
        if (!requestExists) {
            return false;
        }

        Group groupMin = null;
        int minSize = Integer.MAX_VALUE;

        for (Group group : academicYear.getGroups()) {
            int groupSize = group.getStudentsIds().size();
            if (groupSize < minSize) {
                minSize = groupSize;
                groupMin = group;
            }
        }

        if (groupMin == null) {
            return false;
        }

        groupMin.getStudentsIds().add(studentId);
        this.groupRepository.save(groupMin);

        return this.requestService.deleteRequest(academicYearId, studentId);
    }

    @Override
    public boolean rejectStudentToAcademicYear(Long academicYearId, Long studentId) {
        return this.requestService.deleteRequest(academicYearId, studentId);
    }

    @Override
    public void saveAcademicYearFromScraper(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        CompletableFuture<HttpResponse<Stream<String>>> futureResponse =
                client.sendAsync(request, HttpResponse.BodyHandlers.ofLines());

        futureResponse.thenAccept(response -> {
            StringBuilder jsonBuffer = new StringBuilder();

            response.body().forEach(line -> {
                jsonBuffer.append(line).append("\n");
                if (line.contains("-----")) {
                    processJsonPacket(jsonBuffer.toString());
                    jsonBuffer.setLength(0);
                }
            });
            if (jsonBuffer.length() > 0) {
                processJsonPacket(jsonBuffer.toString());
            }

            System.out.println("Fin du scraping en streaming.");
        }).exceptionally(e -> {
            System.out.println("Erreur lors de la connexion : " + e.getMessage());
            return null;
        });
        futureResponse.join();
    }

    private void processJsonPacket(String jsonPacket) {
        String[] jsonStrings = jsonPacket.split("-----");
        for (String jsonString : jsonStrings) {
            if (!jsonString.trim().isEmpty()) {
                processJson(jsonString);
            }
        }
    }

    private void processJson(String jsonString) {
        try {
            ScraperAcademicYearDto scraperDto = objectMapper.readValue(jsonString, ScraperAcademicYearDto.class);
            saveScraperAcademicYearDto(scraperDto);
        } catch (Exception e) {
            System.out.println("Erreur lors de la conversion du JSON : " + e.getMessage());
        }
    }

    private void saveScraperAcademicYearDto(ScraperAcademicYearDto scraperDto) {
        try {
            AcademicYearDto academicYearDto = new AcademicYearDto();
            academicYearDto.setName(scraperDto.getName());
            academicYearDto.setPraticalWorkSize(scraperDto.getTpSize());
            academicYearDto.setDirectedWorkSize(scraperDto.getTdSize());
            academicYearDto.setNumberOptionalTeachingUnit(scraperDto.getOptionsNumber());
            academicYearDto = this.saveAcademicYear(academicYearDto);

            for (ScraperTeachingUnitDto scraperTeachingUnitDto : scraperDto.getTeachingUnits()) {
                TeachingUnitDto teachingUnitDto = new TeachingUnitDto();
                teachingUnitDto.setName(scraperTeachingUnitDto.getName());
                teachingUnitDto.setAcademicYearId(academicYearDto.getId());
                teachingUnitDto.setCapacity(scraperTeachingUnitDto.getCapacity());
                teachingUnitDto.setIsRequired(scraperTeachingUnitDto.getIsMandatory());
                this.teachingUnitService.saveTeachingUnit(teachingUnitDto);
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la conversion du JSON : " + e.getMessage());
        }
    }
}
