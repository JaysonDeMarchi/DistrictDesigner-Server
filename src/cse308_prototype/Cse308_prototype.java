/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cse308_prototype;

import entity.Precinct;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

//import config/constants.java as config;

/**
 *
 * @author hqzhu
 */
public class Cse308_prototype {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
						    .addAnnotatedClass(Precinct.class)
					            .buildSessionFactory();
 
        Session session = factory.getCurrentSession();
        
        //this is create
        try {			
            // create a student object
            //hard code
//            Precinct precinct = new Precinct("2","stonybrook");
//			
//	    // start a transaction
//	    session.beginTransaction();
//			
//	    // save the student object
//            session.save(precinct);
//	    // commit transaction
//	    session.getTransaction().commit();
            
            
            //read
//            session = factory.getCurrentSession();
//	    session.beginTransaction();
//            Precinct myPre = session.get(Precinct.class,"1");
//            System.out.println(myPre.getCountyName());
//            session.getTransaction().commit();
            session = factory.getCurrentSession();
            session.beginTransaction();
            List<Precinct> precincts = session.createQuery("from Precinct").list();
            displayAll(precincts);
            
            List<Precinct> prec = session.createQuery("from Precinct p where p.countyName='flushing'").list();
            displayAll(prec);
            
            session.getTransaction().commit();
            
	}finally {
            factory.close();
	}   

    }
    
    private static void displayAll(List<Precinct> precincts) {
		for (Precinct pre : precincts) {
			System.out.println(pre.getCountyName());
		}
	}
    
    
}
