package com.mics.test;

import java.io.FileNotFoundException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

import com.mics.entity.ReportContent;
import com.mics.httpRequest.HttpRequest;
import com.mics.utils.HibernateUtils;

public class HttpTest {

	private HttpRequest httpRequest;
	private SessionFactory sessionFactory;
	private Session session;

	@Before
	public void init() {
		httpRequest = new HttpRequest();
//		Configuration configuration = new Configuration().configure();
////		sessionFactory = configuration.buildSessionFactory();
//		
//		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
//				.configure() // configures settings from hibernate.cfg.xml
//				.build();
//		try {
//			sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
//		}
//		catch (Exception e) {
//			// The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
//			// so destroy it manually.
//			StandardServiceRegistryBuilder.destroy( registry );
//		}
	}

	@Test
	public void test() throws InterruptedException, FileNotFoundException {
		session = HibernateUtils.getInstance().getSession();
		Transaction transaction = session.beginTransaction();
		System.out.println(session.get(ReportContent.class, "402848405948e68e015948e6ba5a0000"));
		transaction.commit();
		session.close();
	}
	
}
