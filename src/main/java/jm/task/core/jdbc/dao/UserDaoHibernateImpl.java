package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    private Transaction transaction;

    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String CREATE = "CREATE TABLE if not exists users (id INT NOT NULL AUTO_INCREMENT, " +
                    "name VARCHAR(40) NULL, " +
                    "lastname VARCHAR(40) NULL, " +
                    "age TINYINT(3) NULL, PRIMARY KEY (id))";
            Query query = session.createSQLQuery(CREATE).addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {

        }
    }

    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String DROP = "DROP TABLE users";
            Query query = session.createSQLQuery(DROP).addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {

        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            System.out.println("User с именем – " + name + " добавлен в базу данных");
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            result = session.createQuery("from User").getResultList();
            for (User u : result) {
                System.out.println(u);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return result;
    }

    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
