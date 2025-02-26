package org.example.sportsDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.example.donnees.Discipline;
import org.example.donnees.Sport;

/**
 * DAO pour la classe/table Discipline avec impl�mentation en JDBC.
 * @author Eric
 */
class DAO_JDBC_Discipline extends DAO<Discipline> {

    private Connection connection = null;
    
    @Override
    public Discipline find(int id) throws DAOException {
    	try {
            Statement req = connection.createStatement();
            ResultSet resDiscipline = req.executeQuery("select * from discipline where id=" + id);
            
            DAO_JDBC_Sport dao_jdbc_sport = new DAO_JDBC_Sport();
            
            Sport sport = dao_jdbc_sport.find(resDiscipline.getInt(2));
            
            Discipline disci = new Discipline();
            
            disci.setCodeDiscipline(resDiscipline.getInt(0));
            disci.setIntitule(resDiscipline.getString(1));
            //disci.setSport(sport);
            
            ResultSet resSportif = req.executeQuery("select * from sportif where code_discipline=" + id);
            
            
            return disci;
        
    	 } catch (Exception e) {
             throw new DAOException("Probl�me technique (" + e.getMessage() + ")");
         }
        //throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void create(Discipline disc) throws DAOException {
        try {
            Statement req = connection.createStatement();
            ResultSet res = req.executeQuery("select max(code_discipline) from discipline");
            res.next();
            int codeDisc = res.getInt(1);
            codeDisc++;
            disc.setCodeDiscipline(codeDisc);

            req = connection.createStatement();
            //int nb = req.executeUpdate("insert into discipline values (" + codeDisc + " , '" + disc.getIntitule() + "' , " + disc.getSport().getCodeSport() + ")");
        } catch (Exception e) {
            throw new DAOException("Probl�me technique (" + e.getMessage() + ")");
        }
    }

    @Override
    public void update(Discipline data) throws DAOException {
    	
    	try {
            PreparedStatement reqParam = connection.prepareStatement("update discipline set intitule = ?, code_sport = ? where code_discipline = ?");
            reqParam.setString(1, data.getIntitule());
            //reqParam.setInt(2, data.getSport().getCodeSport());
            reqParam.setInt(3, data.getCodeDiscipline());
            int nb = reqParam.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("Probl�me technique (" + e.getMessage() + ")");
        }
    	
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(Discipline data) throws DAOException {
    	try {
            // on efface la discipline
            PreparedStatement reqParam = connection.prepareStatement("delete from discipline where code_discipline = ?");
            reqParam.setInt(1, data.getCodeDiscipline());
            int nb = reqParam.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("Probl�me technique (" + e.getMessage() + ")");
        }
    	
        //throw new UnsupportedOperationException("Not supported yet."); 
    }

    public DAO_JDBC_Discipline() throws DAOException {
    	super();
        this.connection = SQLConnection.getConnection();
    }
    
}
