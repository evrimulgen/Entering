package com.mics.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
	
	private HibernateUtils(){}
	
	private static HibernateUtils instance = new HibernateUtils();
	
	public static HibernateUtils getInstance() {
		return instance;
	}

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		Configuration configuration = new Configuration().configure();
		
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure()
				.build();
		try {
			sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
		}
		catch (Exception e) {
			
			StandardServiceRegistryBuilder.destroy( registry );
		}
		return sessionFactory;
	}
	
	public Session getSession(){
		return getSessionFactory().getCurrentSession();
	}

}
