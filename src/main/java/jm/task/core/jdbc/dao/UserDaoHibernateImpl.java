package jm.task.core.jdbc.dao;

import com.mysql.jdbc.Driver;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;

import javax.persistence.criteria.CriteriaQuery;
import javax.security.auth.login.Configuration;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory sessionFactory = Util.getConnection();
    //        .buildSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.createNativeQuery("CREATE TABLE IF NOT EXISTS users (Id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20),lastName VARCHAR (20), age INT)").executeUpdate();
        transaction.commit();
        System.out.println("Таблица создана");

        session.close();
    }


    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.createNativeQuery("DROP TABLE IF EXISTS users.users").executeUpdate();
        transaction.commit();
        System.out.println("Таблица удалена");

        session.close();
    }


    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.save(new User(name, lastName, age));
        transaction.commit();
        System.out.println("User с именем – " + name + " добавлен в базу данных");

        session.close();
    }


    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.delete(session.get(User.class, id));
        transaction.commit();
        System.out.println("User удален");

        session.close();
    }


    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
        criteriaQuery.from(User.class);
        Transaction transaction = session.beginTransaction();
        List<User> userList = session.createQuery(criteriaQuery).getResultList();

        transaction.commit();
        return userList;


    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.createNativeQuery("TRUNCATE TABLE users.users;").executeUpdate();
        transaction.commit();
        System.out.println("Таблица очищена");

        session.close();
    }
}
