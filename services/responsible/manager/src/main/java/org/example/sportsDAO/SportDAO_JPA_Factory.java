package org.example.sportsDAO;

import org.example.donnees.Discipline;
import org.example.donnees.Sport;
import org.example.donnees.Sportif;

public class SportDAO_JPA_Factory extends SportsDAOFactory{

    DAO_JPA dataSport = null;

    DAO_JPA dataDiscipline = null;

    DAO_JPA dataSportif = null;

    @Override
    public DAO_JPA getDAOSport() throws DAOException {
        if (dataSport == null){
            dataSport = new DAO_JPA(Sport.class);
        }
        return dataSport;
    }

    @Override
    public DAO_JPA getDAODiscipline() throws DAOException {
        if (dataDiscipline == null){
            dataDiscipline = new DAO_JPA(Discipline.class);
        }
        return dataDiscipline;
    }

    @Override
    public DAO<Sportif> getDAOSportif() throws DAOException {
        if (dataSportif == null){
            dataSportif = new DAO_JPA(Sportif.class);
        }
        return dataSportif;
    }
}
