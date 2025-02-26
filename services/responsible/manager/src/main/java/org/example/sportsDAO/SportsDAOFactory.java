package org.example.sportsDAO;


import org.example.donnees.Discipline;
import org.example.donnees.Sport;
import org.example.donnees.Sportif;

/**
 * Fabrique abstraite de DAO pour le sch�ma sports
 * @author Eric
 */
public abstract class SportsDAOFactory {
    
    /**
     * @return le DAO pour la classe/table Sport.
     * @throws DAOException en cas de probl�me
     */
    public abstract DAO<Sport> getDAOSport() throws DAOException;
    
    /**
     * @return le DAO pour la classe/table Discipline.
     * @throws DAOException en cas de probl�me
     */
    public abstract DAO<Discipline> getDAODiscipline() throws DAOException;
    
    
    /**
     * @return le DAO pour la classe/table Sportif.
     * @throws DAOException en cas de problème
     */
    public abstract DAO<Sportif> getDAOSportif() throws DAOException;
}
