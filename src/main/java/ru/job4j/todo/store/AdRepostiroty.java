package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.cars.model.Ad;
import ru.job4j.cars.model.Brand;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class AdRepostiroty {

    private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
            .configure().build();
    private static final SessionFactory SF = new MetadataSources(REGISTRY).buildMetadata().buildSessionFactory();

    public static void main(String[] args) {
        System.out.println(findAdsForLastDay());
        System.out.println(findAdsWithPhoto());
        Brand brand = new Brand("Any");
        brand.setId(1);
        System.out.println(findAdsByBrand(brand));
    }

    private static List<Ad> findAdsForLastDay() {
        return tx(session -> session.createQuery("select distinct a from Ad a join fetch a.brand b"
                + " join fetch a.carBody cb where a.created > :yesterday")
                .setParameter("yesterday", new Date(Calendar.getInstance().getTime().getTime() - 86400000)).list());
    }

    private static List<Ad> findAdsWithPhoto() {
        return tx(session -> session.createQuery("select distinct a from Ad a"
                + " join fetch a.photo ph").list());
    }

    private static List<Ad> findAdsByBrand(Brand brand) {
        return tx(session -> session.createQuery("select distinct a from Ad a"
                + " join fetch a.brand b where a.brand = :brand").setParameter("brand", brand).list());
    }

    private static <T> T tx(final Function<Session, T> command) {
        final Session session = SF.openSession();
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
}
