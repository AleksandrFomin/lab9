
import org.hibernate.Session;

import javax.ejb.Stateless;
import javax.faces.convert.Converter;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class DBOperations implements Serializable {

    public void addPoint(Point point){
        EntityManager em= HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(point);
        em.getTransaction().commit();
    }

    public void deleteAllPoints(){
        EntityManager em= HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        Query query=em.createNativeQuery("DELETE FROM points");
        query.executeUpdate();
        em.getTransaction().commit();
    }

    public ArrayList<Point> readAllTable(){
        EntityManager em= HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        ArrayList<Point> points=new ArrayList<Point>();
        Query query=em.createQuery("SELECT p from Point p ");
        em.getTransaction().commit();
        List list =query.getResultList();
        for(Object obj : list)
            points.add((Point)obj);
        return points;
    }

    public void addUser(LabUser user) {
        EntityManager em= HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    public boolean userExists(String userName){
        EntityManager em= HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        Query query=em.createQuery("SELECT l FROM LabUser l WHERE login='"+userName+"'");
        em.getTransaction().commit();
        List list =query.getResultList();
        if(list.isEmpty())
            return false;
        else
            return true;

    }

    public boolean checkPassword(LabUser user){
        EntityManager em= HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        Query query=em.createQuery("SELECT l FROM LabUser l WHERE login='"+user.getLogin()+"'");
        em.getTransaction().commit();
        List list =query.getResultList();
        for(Object obj : list){
            if(((LabUser)obj).getPassword().equals(user.getPassword()))
                return true;
        }
        return false;
    }
}
