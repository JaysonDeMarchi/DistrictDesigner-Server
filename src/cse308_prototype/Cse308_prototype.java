/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cse308_prototype;

import entity.Precinct;
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
        
        try {			
			// create a student object
			System.out.println("Creating new precinct");
			Precinct precinct = new Precinct("1","flushing");
			
			// start a transaction
			session.beginTransaction();
			
			// save the student object
			System.out.println("Saving the precinct...");
			session.save(precinct);
			
			// commit transaction
			session.getTransaction().commit();
			
			System.out.println("Done!");
		}
		finally {
			factory.close();
		}
        
        
    }
    
}
