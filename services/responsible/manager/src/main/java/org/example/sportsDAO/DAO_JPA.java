package org.example.sportsDAO;

import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class DAO_JPA<D> extends DAO<D> {

    EntityManager em = null;

    Class<D> dclass;
    /**
     * Construit le DAO pour la classe param�tr�e.
     *
     * @throws DAOException en cas de probl�me
     */
    public DAO_JPA(Class<D> d) throws DAOException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SportsPU");
        em = emf.createEntityManager();
        System.out.println("PU chargée");
        this.dclass = d;

    }

    @Override
    public D find(int id) throws DAOException {
        return em.find(this.dclass, id);
    }

    @Override
    public void create(D data) throws DAOException {
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();  // Démarre la transaction
            em.persist(data);            // Persiste l'entité dans la base de données
            transaction.commit(); // Commit la transaction
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Si une erreur survient, annule la transaction
            }
            throw new DAOException("Erreur lors de la création de l'entité");
        }
    }

    @Override
    public void update(D data) throws DAOException {
        EntityTransaction trans = null;
        try {
            trans = em.getTransaction();
            trans.begin();
            em.merge(data);
            trans.commit();
        }catch(Exception e){
            if(trans != null){
                trans.rollback();
            }
            throw new DAOException("Erreur lors de l'update");
        }
    }

    @Override
    public void delete(D data) throws DAOException {
        EntityTransaction trans = null;
        try {
            trans = em.getTransaction();
            trans.begin();
            em.remove(data);
            trans.commit();
        }catch(Exception e){
            if(trans != null){
                trans.rollback();
            }
            throw new DAOException("Erreur lors de la suppression");
        }
    }
}
