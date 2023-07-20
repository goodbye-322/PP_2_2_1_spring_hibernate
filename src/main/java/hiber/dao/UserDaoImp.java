package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    private UserDaoImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User getUserByCar(String model, int series) {
        String hql = "SELECT user FROM User user JOIN FETCH user.car car WHERE car.model = :model AND car.series = :series";
        return sessionFactory.getCurrentSession().createQuery(hql, User.class)
                .setParameter("model", model)
                .setParameter("series", series)
                .setMaxResults(1)
                .uniqueResult();
    }

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("SELECT user FROM User user JOIN FETCH user.car");
        return query.getResultList();
    }

}
