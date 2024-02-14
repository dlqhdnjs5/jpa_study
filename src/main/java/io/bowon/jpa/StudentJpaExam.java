package io.bowon.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import io.bowon.jpa.customer.entity.Major;
import io.bowon.jpa.entity.Student;

public class StudentJpaExam {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("customer-exam");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		tx.begin();


		try {
			/*Major major = new Major("Computer Science", "college of Engineering");
			em.persist(major);

			Student student = new Student("Kim", "3");
			em.persist(student);

			em.flush();
			em.clear();*/


			//Student 검색
			Student foundStudent = em.find(Student.class, 1L);
			System.out.println("foundStudent " + foundStudent);

			System.out.println("major " + foundStudent.getMajor());


			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}

		emf.close();

	}
}
