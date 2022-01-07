package ru.job4j.cars.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ads")
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private boolean sold;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created = new Date(System.currentTimeMillis());
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "carBody_id")
    private CarBody carBody;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "brand_id")
    private Brand brand;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "model_id")
    private Model model;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Photo> photo = new HashSet<>();

    public Ad() {
    }

    public Ad(String name, String description, User user, CarBody carBody, Brand brand, Model model) {
        this.name = name;
        this.description = description;
        this.user = user;
        this.carBody = carBody;
        this.brand = brand;
        this.model = model;
    }

    public Ad(String name, String description, CarBody carBody, Brand brand, Model model) {
        this.name = name;
        this.description = description;
        this.carBody = carBody;
        this.brand = brand;
        this.model = model;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CarBody getCarBody() {
        return carBody;
    }

    public void setCarBody(CarBody carBody) {
        this.carBody = carBody;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Set<Photo> getPhoto() {
        return photo;
    }

    public void setPhoto(Set<Photo> photo) {
        this.photo = photo;
    }

    public void addPhoto(Photo photo) {
        this.photo.add(photo);
    }

    @Override
    public String toString() {
        return "Ad{" + "id=" + id + ", name='" + name + '\'' + ", description='" + description + '\''
                + ", sold=" + sold + ", created=" + created + ", user=" + user
                + ", carBody=" + carBody + ", brand=" + brand + ", model=" + model + '}';
    }
}
