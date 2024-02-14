package io.bowon.jpa.customer;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import io.bowon.jpa.customer.entity.Customer;

public class CustomerJpaExam {

	public static void main(String[] args) {
		flush();
	}

	public static void insert() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("customer-exam");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();

		entityTransaction.begin();

		try {
			entityManager.persist(new Customer("id1", "bowon", System.currentTimeMillis())); // 영속성 컨택스트(1차 캐시) 에 먼저 저장
			entityManager.persist(new Customer("id1", "bowon", System.currentTimeMillis()));  // 두번 넣는다 해서 두번 db저장되지 않음. 왜냐면 영속성 컨텍스트에 넣기 때문.

			entityTransaction.commit(); // db에 기록
		} catch (Exception exception) {
			entityTransaction.rollback();
		} finally {
			entityManager.close();
		}
		entityManagerFactory.close();
	}


	public static void find() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("customer-exam");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();

		entityTransaction.begin();

		try {
			Customer customer = entityManager.find(Customer.class, "id1"); // 객체의 id를 통한 조회, 영속성 컨텍스트를 먼저 조회해서 잇으면 리턴, 없으면 db 까지 가서 조회
			entityTransaction.commit();

			System.out.println("customer : " + customer.toString());
		} catch (Exception exception) {
			entityTransaction.rollback();
		} finally {
			entityManager.close();
		}
		entityManagerFactory.close();
	}


	public static void update() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("customer-exam");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();

		entityTransaction.begin();

		try {
			Customer customer = entityManager.find(Customer.class, "id1"); // 조회해서 저장한 1차 캐시의 결과값을 스냅샷에 저장

			customer.setName("bowon2"); // 이 라인에서 name이 update 된다. 즉, entityManager 에 관리되고 있는 하에 객체를 변경하면 자동으로 업데이트가 진행된다.
			// 1차캐시의 스냅샷 정보와 비교하여 바뀌엇을 경우 update쿼리 생성 및 변경.


			entityTransaction.commit(); // 실제 DB에 업데이트가 되어 저장.

			System.out.println("customer : " + customer.toString());
		} catch (Exception exception) {
			entityTransaction.rollback();
		} finally {
			entityManager.close();
		}
		entityManagerFactory.close();
	}

	public static void delete() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("customer-exam");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();

		entityTransaction.begin();

		try {
			Customer customer = entityManager.find(Customer.class, "id1");

			entityManager.remove(customer);  // entityManager 에 관리되고 있는 하에 객체를 삭제하면 자동으로 업데이트가 진행된다.
			System.out.println("커밋전 삭제 로그");

			entityTransaction.commit(); // 실제 DB에 업데이트가 되어 저장.

			System.out.println("customer : " + customer.toString());
		} catch (Exception exception) {
			entityTransaction.rollback();
		} finally {
			entityManager.close();
		}
		entityManagerFactory.close();
	}

	public static void flush() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("customer-exam");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();

		entityTransaction.begin();

		try {
			entityManager.persist(new Customer("id8", "bowon", System.currentTimeMillis())); // 영속성 컨택스트(1차 캐시) 에 먼저 저장

			// jpql을 만들때에는 entity maneger의 flush() 가 자동으로 선행된다.
			Query query = entityManager.createQuery("SELECT c FROM Customer c", Customer.class);
			List<Customer> customers = query.getResultList();
			System.out.println(customers);


			// entityTransaction.commit(); // db에 기록
		} catch (Exception exception) {
			entityTransaction.rollback();
		} finally {
			entityManager.close();
		}
		entityManagerFactory.close();
	}

	public static void detach() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("customer-exam");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();

		entityTransaction.begin();

		try {
			Customer customer = new Customer("id9", "bowon", System.currentTimeMillis()); // 비영속 상태 (new)

			entityManager.persist(customer); // 영속성 컨택스트(1차 캐시) 에 먼저 저장 ( 영속 상태 )
			entityManager.detach(customer); // customer 객체를 준 영속 상태로 만듬.
			Customer foundCustomer = entityManager.find(Customer.class, "id9");

			System.out.println(foundCustomer);

			entityTransaction.commit(); // db에 기록
		} catch (Exception exception) {
			entityTransaction.rollback();
		} finally {
			entityManager.close();
		}
		entityManagerFactory.close();
	}

	public static void identityOption() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("customer-exam");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();


		entityTransaction.begin();

		Customer customer = new Customer();
		customer.setName("kiny");
		customer.setRegisterData(System.currentTimeMillis());

		try {
			entityManager.persist(customer); //

			System.out.println("-- before commit -- ");

			entityTransaction.commit(); // db에 기록
		} catch (Exception exception) {
			entityTransaction.rollback();
		} finally {
			entityManager.close();
		}
		entityManagerFactory.close();
	}
}
