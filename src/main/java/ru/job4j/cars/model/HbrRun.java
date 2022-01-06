package ru.job4j.cars.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Set;

public class HbrRun {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            User user = new User("Vasya", "2@gmail.com", "123");
            Brand brand = new Brand("Toyota");
            CarBody carBody = new CarBody("Sedan");
            Photo photo1 = new Photo("./photos/1.png");
            Photo photo2 = new Photo("./photos/2.png");
            Photo photo3 = new Photo("./photos/3.png");
            Ad ad = new Ad("Sale Toyota Corolla", "Sale Toyota Corolla 1.8 2009", user, carBody, brand, new Model(3));
            ad.addPhoto(photo1);
            ad.addPhoto(photo2);
            ad.addPhoto(photo3);
            session.save(ad);
            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

}
