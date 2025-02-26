package org.example.sportsDAO;

import org.example.donnees.Discipline;
import org.example.donnees.Sport;
import org.example.donnees.Sportif;

/**
 * Fabrique concr�te de DAO pour le sch�ma relationnel sports avec une impl�mentation en JDBC.
 * @author Eric
 */
public class Sports_JDBC_DAOFactory extends SportsDAOFactory {
    
	/**
	 * Le DAO concret en JDBC pour la table Sport
	 */
    private DAO_JDBC_Sport daoSport = null;
    
    /**
     * Le DAO concret en JDBC pour la table Discipline
     */
    private DAO_JDBC_Discipline daoDiscipline = null;
    
    /**
     * Le DAO concret en JDBC pour la table Sportif
     */
    private DAO_JDBC_Sportif daoSportif = null;
        
    @Override
    public DAO<Sport> getDAOSport() throws DAOException {
        if (daoSport == null) daoSport = new DAO_JDBC_Sport();
        return daoSport;
    }

    @Override
    public DAO<Discipline> getDAODiscipline() throws DAOException {
        if (daoDiscipline == null) daoDiscipline = new DAO_JDBC_Discipline();
        return daoDiscipline;
    }

	@Override
	public DAO<Sportif> getDAOSportif() throws DAOException {
		if (daoSportif == null) daoSportif = new DAO_JDBC_Sportif();
		return daoSportif;
	}
}
