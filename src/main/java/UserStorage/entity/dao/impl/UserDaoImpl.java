package UserStorage.entity.dao.impl;

import UserStorage.entity.User;
import UserStorage.entity.dao.UserDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class UserDaoImpl implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
    private final SessionFactory sessionFactory;

    public UserDaoImpl() {
        try {
            logger.info("Initializing Hibernate SessionFactory...");
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .loadProperties("hibernate.properties")
                    .build();


            Metadata metadata = new MetadataSources(registry)
                    .addAnnotatedClass(User.class)
                    .getMetadataBuilder()
                    .build();


            this.sessionFactory = metadata.getSessionFactoryBuilder().build();
            logger.info("SessionFactory initialized successfully");

        } catch (Exception e) {
            logger.error("Failed to initialize SessionFactory", e);
            System.err.println("Ошибка при создании SessionFactory: " + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    @Override
    public User findById(Long id) {
        logger.debug("Finding user by ID: {}", id);
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        }
    }

    @Override
    public List<User> findAll() {
        try (Session session = sessionFactory.openSession()) {
            logger.debug("Finding All user");
            return session.createQuery("from User", User.class).list();
        }
    }

    @Override
    public void save(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            logger.debug("Saving user: {}", user);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public void update(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge("User", user);
            transaction.commit();
            logger.debug("Updating user: {}", user);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Fail  trying to update  user: {}", user + e.getMessage());
            throw e;

        }

    }

    @Override
    public void delete(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {

            User existingUser = session.get(User.class, user.getId());

            if (existingUser == null) {
                System.out.println("Пользователь с таким ID не найден");
                logger.warn("User with ID {} not found, nothing to delete.", user.getId());
                return;
            }

            transaction = session.beginTransaction();
            session.remove(existingUser);
            transaction.commit();

            logger.debug("Deleting user: {}", user);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Fail trying to delete user: {}", user + " " + e.getMessage());
            throw e;
        }
    }
}
