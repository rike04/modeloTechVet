package bll;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author rike4
 */
public class PersistenceManager {
    
    private static final String PERSISTENCE_UNIT_NAME = "TechVetBD";
    private static EntityManagerFactory factory;
    private static EntityManager em = null;

    public static EntityManager getEntityManager() {
        try {
            if (em == null) {
                factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
                em = factory.createEntityManager();
            }
        }
        catch(IllegalStateException ex){
                factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
                em = factory.createEntityManager();
                System.out.println("Exception: " + ex.getMessage());
        }
        return em;
    }
    
}
