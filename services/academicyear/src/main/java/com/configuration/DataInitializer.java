package com.configuration;

import com.entities.AcademicYear;
import com.entities.Group;
import com.entities.Request;
import com.entities.TeachingUnit;
import com.repositories.AcademicYearRepository;
import com.repositories.GroupRepository;
import com.repositories.TeachingUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AcademicYearRepository academicYearRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private TeachingUnitRepository teachingUnitRepository;

    @Override
    public void run(String... args) throws Exception {
        groupRepository.deleteAll();
        teachingUnitRepository.deleteAll();
        academicYearRepository.deleteAll();

        AcademicYear year2021 = new AcademicYear();
        year2021.setName("2021-2022");
        year2021.setPraticalWorkSize((short) 20);
        year2021.setDirectedWorkSize((short) 10);
        year2021.setNumberOptionalTeachingUnit((short) 3);
        year2021.setResponsibleId(101L);
        year2021 = academicYearRepository.save(year2021);

        TeachingUnit tu1 = new TeachingUnit();
        tu1.setName("Informatique");
        tu1.setIsRequired(true);
        tu1.setCapacity((short) 30);
        tu1.setResponsibleId(101L);
        tu1.setStudentsIds(Arrays.asList(1001L, 1002L, 1003L));
        tu1.setAcademicYear(year2021);
        teachingUnitRepository.save(tu1);

        TeachingUnit tu2 = new TeachingUnit();
        tu2.setName("Mathématiques");
        tu2.setIsRequired(true);
        tu2.setCapacity((short) 25);
        tu2.setResponsibleId(102L);
        tu2.setStudentsIds(Arrays.asList(1004L, 1005L, 1006L));
        tu2.setAcademicYear(year2021);
        teachingUnitRepository.save(tu2);

        Group groupA = new Group();
        groupA.setName("Groupe A");
        groupA.setAcademicYear(year2021);
        groupA.setStudentsIds(Arrays.asList(1001L, 1002L, 1003L, 1004L));
        groupRepository.save(groupA);

        Group groupB = new Group();
        groupB.setName("Groupe B");
        groupB.setAcademicYear(year2021);
        groupB.setStudentsIds(Arrays.asList(1005L, 1006L, 1007L, 1008L));
        groupRepository.save(groupB);

        AcademicYear year2022 = new AcademicYear();
        year2022.setName("2022-2023");
        year2022.setPraticalWorkSize((short) 25);
        year2022.setDirectedWorkSize((short) 15);
        year2022.setNumberOptionalTeachingUnit((short) 2);
        year2022.setResponsibleId(103L);
        year2022 = academicYearRepository.save(year2022);

        TeachingUnit tu3 = new TeachingUnit();
        tu3.setName("Physique");
        tu3.setIsRequired(true);
        tu3.setCapacity((short) 20);
        tu3.setResponsibleId(104L);
        tu3.setStudentsIds(Arrays.asList(2001L, 2002L, 2003L));
        tu3.setAcademicYear(year2022);
        teachingUnitRepository.save(tu3);

        TeachingUnit tu4 = new TeachingUnit();
        tu4.setName("Chimie");
        tu4.setIsRequired(false);
        tu4.setCapacity((short) 15);
        tu4.setResponsibleId(105L);
        tu4.setStudentsIds(Arrays.asList(2004L, 2005L, 2006L));
        tu4.setAcademicYear(year2022);
        teachingUnitRepository.save(tu4);

        Group groupC = new Group();
        groupC.setName("Groupe C");
        groupC.setAcademicYear(year2022);
        groupC.setStudentsIds(Arrays.asList(2001L, 2002L, 2003L, 2004L));
        groupRepository.save(groupC);

        Group groupD = new Group();
        groupD.setName("Groupe D");
        groupD.setAcademicYear(year2022);
        groupD.setStudentsIds(Arrays.asList(2005L, 2006L, 2007L, 2008L));
        groupRepository.save(groupD);

        AcademicYear year2023 = new AcademicYear();
        year2023.setName("2023-2024");
        year2023.setPraticalWorkSize((short) 30);
        year2023.setDirectedWorkSize((short) 20);
        year2023.setNumberOptionalTeachingUnit((short) 4);
        year2023.setResponsibleId(106L);
        year2023 = academicYearRepository.save(year2023);

        TeachingUnit tu5 = new TeachingUnit();
        tu5.setName("Biologie");
        tu5.setIsRequired(true);
        tu5.setCapacity((short) 35);
        tu5.setResponsibleId(107L);
        tu5.setStudentsIds(Arrays.asList(3001L, 3002L, 3003L));
        tu5.setAcademicYear(year2023);
        teachingUnitRepository.save(tu5);

        TeachingUnit tu6 = new TeachingUnit();
        tu6.setName("Informatique Avancée");
        tu6.setIsRequired(false);
        tu6.setCapacity((short) 25);
        tu6.setResponsibleId(108L);
        tu6.setStudentsIds(Arrays.asList(3004L, 3005L, 3006L));
        tu6.setAcademicYear(year2023);
        teachingUnitRepository.save(tu6);

        Group groupE = new Group();
        groupE.setName("Groupe E");
        groupE.setAcademicYear(year2023);
        groupE.setStudentsIds(Arrays.asList(3001L, 3002L, 3003L, 3004L));
        groupRepository.save(groupE);

        Group groupF = new Group();
        groupF.setName("Groupe F");
        groupF.setAcademicYear(year2023);
        groupF.setStudentsIds(Arrays.asList(3005L, 3006L, 3007L, 3008L));
        groupRepository.save(groupF);

        Request request1 = new Request();
        request1.setAcademicYearId(year2021.getId());
        request1.setStudentId(4001L);

        Request request2 = new Request();
        request2.setAcademicYearId(year2021.getId());
        request2.setStudentId(4002L);

        Request request3 = new Request();
        request3.setAcademicYearId(year2023.getId());
        request3.setStudentId(4003L);
    }
}
