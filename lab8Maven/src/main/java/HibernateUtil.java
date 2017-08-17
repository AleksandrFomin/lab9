import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {
    private static final EntityManagerFactory entityManagerFactory;
    static{
        try{
            entityManagerFactory= Persistence.createEntityManagerFactory("lab8Maven");
        } catch(Throwable e){
            throw new ExceptionInInitializerError(e);
        }
    }
    public static EntityManager getEntityManager(){
        return entityManagerFactory.createEntityManager();
    }
}