package service;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * Created by nazar on 22.05.17.
 */
@SuppressWarnings("deprecation")
public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static{
        try{
        sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();

        }catch (Throwable ex) {
        System.err.println("Session Factory could not be created." + ex);
        throw new ExceptionInInitializerError(ex);
        }
        }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
        }

//    private static final SessionFactory sessionFactory = buildSessionFactory();
//
//    private static SessionFactory buildSessionFactory() {
//        try {
//            // Create the SessionFactory from hibernate.cfg.xml
//            return new Configuration()
//                    .configure()
//                    .buildSessionFactory();
//        } catch (Throwable ex) {
//            System.err.println("Initial SessionFactory creation failed." + ex);
//            throw new ExceptionInInitializerError(ex);
//        }
//    }
//
//    public static SessionFactory getSessionFactory() {
//        return sessionFactory;
//    }

}


