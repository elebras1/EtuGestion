package org.example.sportsDAO;

import org.example.donnees.Discipline;
import org.example.donnees.Sportif;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class DAO_JDBC_Sportif extends DAO<Sportif> {

	private Connection connection = null;
	
	public DAO_JDBC_Sportif() throws DAOException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Sportif find(int id) throws DAOException {
		try {
            Statement req = connection.createStatement();
            ResultSet res = req.executeQuery("select * from sportif where code_sportif=" + id);
            if (res.next()) {
                Sportif sportif = new Sportif();
                //sportif.setCode_sportif(res.getInt(1));
                sportif.setNom(res.getString(2));
                sportif.setRue(res.getString(3));
                sportif.setVille(res.getString(4));
                //sportif.setCode_postale(res.getString(5));
                
                
                
                return sportif;
            } else {
                throw new DAOException("Le sport d'identifiant " + id + " n'existe pas");
            }
        } catch (Exception e) {
            throw new DAOException("Probl�me technique (" + e.getMessage() + ")");
        }
	}
	
	public Set<Discipline> getDiscipline(int id_sportif) throws DAOException {
		DAO_JDBC_Discipline daoDiscipline = new DAO_JDBC_Discipline();
		try {
            Statement req = connection.createStatement();
            ResultSet res = req.executeQuery("select * from discipline where code_sportif=" + id_sportif);
            if (res.next()) {
            	Set<Discipline> disciplines = new HashSet<>();
            	
            	while (res.next()) {
            		disciplines.add(daoDiscipline.find(res.getInt(1)));
            	}
            	
            	return disciplines;
            }
            
		}catch(Exception e) {
			throw new DAOException("Probl�me technique (" + e.getMessage() + ")");
		}
		return null;
		
		
	}

	@Override
	public void create(Sportif sportif) throws DAOException {
		// TODO Auto-generated method stub
		try {
            Statement req = connection.createStatement();
            ResultSet res = req.executeQuery("select max(code_sportif) from sportif");
            res.next();
            int codeSportif = res.getInt(1);
            codeSportif++;
            // positionne la cl� primaire g�n�r�e du sport
            // le contenu initial de codeSport n'est donc pas pris en compte
            //sportif.setCode_sportif(codeSportif);

            PreparedStatement reqParam = connection.prepareStatement("insert into sportif values (?,?,?,?,?)");
            //reqParam.setInt(1, sportif.getCode_sportif());
            reqParam.setString(2, sportif.getNom());
            reqParam.setString(3, sportif.getRue());
            reqParam.setString(4, sportif.getVille());
            //reqParam.setString(5, sportif.getCode_postale());
            
            int nb = reqParam.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("Probl�me technique (" + e.getMessage() + ")");
        }
	}

	@Override
	public void update(Sportif data) throws DAOException {
		// TODO Auto-generated method stub
		try {
            Statement req = connection.createStatement();
            PreparedStatement reqParam = connection.prepareStatement("update sportif set nom = ?, rue = ?, ville = ?, code_postale = ? where code_sportif = ?");
            reqParam.setString(1, data.getNom());
            reqParam.setString(2, data.getRue());
            reqParam.setString(3, data.getVille());
            //reqParam.setString(4, data.getCode_postale());
            //reqParam.setInt(5, data.getCode_sportif());
 
            
            int nb = reqParam.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("Probl�me technique (" + e.getMessage() + ")");
        }
	}

	@Override
	public void delete(Sportif data) throws DAOException {
		try {
            // on efface le sportif
            PreparedStatement reqParam = connection.prepareStatement("delete from sportif where code_sportif = ?");
            //reqParam.setInt(1, data.getCode_sportif());
            int nb = reqParam.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("Probl�me technique (" + e.getMessage() + ")");
        }
	}

}
