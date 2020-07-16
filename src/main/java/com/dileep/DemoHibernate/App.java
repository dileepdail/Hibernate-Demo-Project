package com.dileep.DemoHibernate;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
		/*
		 * Laptop laptop = new Laptop(); laptop.setlId(102); laptop.setLapName("Dell");
		 */
        
		/*
		 * Student student = new Student(); student.setRollno(4);
		 * student.setName("Dileep"); student.setMarks(50); student.setLaptop(laptop);
		 */
        
        Configuration config = new Configuration().configure().addAnnotatedClass(Student.class).addAnnotatedClass(Laptop.class);
        
        ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
        SessionFactory sf = config.buildSessionFactory(reg);
        
        //Create two session to implement Hibernate cache.
        Session session1 = sf.openSession();
        
        session1.beginTransaction();
        
        //Laptop laptop1 = (Laptop)session1.get(Laptop.class, 101);
        
        Query q1 = session1.createQuery("from Laptop where lId=101");
        q1.setCacheable(true);
        Laptop laptop1 = (Laptop)q1.uniqueResult();
        System.out.println(laptop1);
        
        session1.getTransaction().commit();
        session1.close();
        
        //Second session
        
        Session session2 = sf.openSession();
        session2.beginTransaction();
        
        //Laptop laptop2 = (Laptop)session2.get(Laptop.class, 101);
        Query q2 = session2.createQuery("from Laptop where lId=101");
        q2.setCacheable(true);
        Laptop laptop2 = (Laptop)q2.uniqueResult();
        System.out.println(laptop2);
        
        session2.getTransaction().commit();
        session2.close();
        
    }
}
