package org.example; /**
 * Fichier de test de fonctionnement de l'acc�s aux donn�es via des DAO.
 * @author Eric
 */
import org.example.donnees.*;
import org.example.sportsDAO.*;

public class TestJPA {

    public static void main(String argv[]) {
        try {

            // cr�ation des DAO via les fabriques
            SportsDAOFactory factory = AbstractDAOFactory.getDAOFactory(PersistenceKind.JPA);
            DAO<Sport> daoSport = factory.getDAOSport();
            DAO<Discipline> daoDisc = factory.getDAODiscipline();

            // affichage du sport 1
            Sport sport = daoSport.find(1);
            System.out.println("Le sport d'id 1 est "+sport.getIntitule() + " et ses disciplines sont :");
            for (Discipline disc : sport.getDisciplines()) {
            	System.out.println(" --> "+disc.getIntitule());
            }

            // cr�ation d'un sport et de disciplines
            Sport s = new Sport();
            s.setIntitule("Pétanque");
            Discipline d1 = new Discipline();
            d1.setIntitule("Triplette");
            d1.setSport(s);
            Discipline d2 = new Discipline();
            d2.setIntitule("Doublette");
            d2.setSport(s);
            s.addDiscipline(d1);
            s.addDiscipline(d2);

            // enregistrement des objets dans la BDD
            daoSport.create(s);
            daoDisc.create(d1);
            daoDisc.create(d2);

            // affichage du sport 1
            sport = daoSport.find(s.getCodeSport());
            System.out.println("Le sport d'id 1 est "+sport.getIntitule() + " et ses disciplines sont :");
            for (Discipline disc : sport.getDisciplines()) {
                System.out.println(" --> "+disc.getIntitule());
            }

            System.out.println("La p�tanque et ses disciplines ont �t� ajout�es dans la BDD.");

        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
}

