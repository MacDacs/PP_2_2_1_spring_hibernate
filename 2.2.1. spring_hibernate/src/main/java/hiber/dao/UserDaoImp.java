package hiber.dao;

import hiber.model.Car;
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

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public void saveCar(User user) {
      sessionFactory.getCurrentSession().save(user.getCar());
   }

   @Override
   @SuppressWarnings("unchecked")
   public User getUserByCar(Car car) {
      String SQL = "from User u join fetch u.car c where c.model=:m and c.series=:s";
      TypedQuery<User> query=sessionFactory.getCurrentSession()
              .createQuery(SQL, User.class)
              .setParameter("m", car.getModel())
              .setParameter("s", car.getSeries());
      return query.getResultList().stream().findAny().orElse(null);
   }

}
