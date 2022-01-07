package ru.job4j.cars.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.cars.model.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class StoreHibernate implements Store, AutoCloseable {

    private final StandardServiceRegistry registry;
    private final SessionFactory sf;

    private StoreHibernate() {
        registry = new StandardServiceRegistryBuilder()
                .configure().build();
        sf = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory();
    }

    private static final class Lazy {
        private static final Store INST = new StoreHibernate();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    public boolean addBrand(Brand brand) {
        return tu(session -> session.save(brand));
    }

    @Override
    public List<CarBody> findCarBodies() {
        return tx(session -> session.createQuery("from CarBody").list());
    }

    @Override
    public boolean addCarBody(CarBody carBody) {
        return tu(session -> session.save(carBody));
    }

    @Override
    public List<Model> findModelsByBrand(Brand brand) {
        return tx(session -> session.createQuery("from Model where brand = :brand")
                .setParameter("brand", brand).list());
    }

    @Override
    public boolean addModel(Model model) {
        return tu(session -> session.save(model));
    }

    @Override
    public boolean saveAd(Ad ad) {
        return tu(session -> session.save(ad));
    }

    @Override
    public boolean replaceAd(Ad ad) {
        return tu(session -> {
            session.update(ad);
        });
    }

    @Override
    public Ad getAd(int id) {
        return (Ad) tx(session -> session.createQuery("select distinct a from Ad a"
                + " left join fetch a.photo ph where a.id = :id").setParameter("id", id).uniqueResult());
    }

    @Override
    public boolean addUser(User user) {
        return tu(session -> session.save(user));
    }

    @Override
    public User findUser(String email) {
        return (User) tx(session -> session.createQuery("from User where email = :em")
                .setParameter("em", email).uniqueResult());
    }

    @Override
    public List<Ad> findAllAds(String all, Map<String, String> params) {
        return tx(session -> {
            Query query = session.createQuery("select distinct ad from Ad ad left join fetch ad.photo ph" + all);
            if (!"".equals(all)) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String key = entry.getKey();
                    if (key.equals("brand")) {
                        query.setParameter(key, new Brand(Integer.parseInt(entry.getValue())));
                    } else if (key.equals("model")) {
                        query.setParameter(key, new Model(Integer.parseInt(entry.getValue())));
                    } else if (key.equals("carBody")) {
                        query.setParameter(key, new CarBody(Integer.parseInt(entry.getValue())));
                    }
                }
            }
            return query.list();
        });
    }

    public List<Ad> findAdsForLastDay() {
        return tx(session -> session.createQuery("select distinct a from Ad a join fetch a.brand b"
                + " join fetch a.carBody cb where a.created > :yesterday")
                .setParameter("yesterday", new Date(Calendar.getInstance().getTime().getTime() - 86400000)).list());
    }

    public List<Ad> findAdsWithPhoto() {
        return tx(session -> session.createQuery("select distinct a from Ad a"
                + " join fetch a.photo ph").list());
    }

    public List<Ad> findAdsByBrand(Brand brand) {
        return tx(session -> session.createQuery("select distinct a from Ad a"
                + " join fetch a.brand b where a.brand = :brand").setParameter("brand", brand).list());
    }

    @Override
    public List<Brand> findBrands() {
        return tx(session -> session.createQuery("from Brand").list());
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    private boolean tu(final Consumer<Session> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            command.accept(session);
            tx.commit();
            return true;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }

}