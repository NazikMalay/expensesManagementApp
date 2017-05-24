package repository;

import entity.Expenses;
import org.hibernate.Query;
import org.hibernate.Session;
import service.HibernateUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by nazar on 23.05.17.
 */

public class ExpensesRepository {
    public void addExpenses(Expenses expenses){
        Session session= HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(expenses);
        session.getTransaction().commit();
        session.close();
    }


    public List<Expenses> getAllExpenses(){
        Session session= HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List arr = session.createQuery("from Expenses").list();
        session.getTransaction().commit();
        session.close();
        return arr;
    }
    public void deleteByDate(Date selectDate){
        Session session= HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
//           session.createSQLQuery("DELETE FROM EXPENSES WHERE DATE = :myDate").setParameter("myDate", selectDate).executeUpdate();
        Query query = session.createQuery("delete Expenses where date = :myDate");
        query.setParameter("myDate", selectDate);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
