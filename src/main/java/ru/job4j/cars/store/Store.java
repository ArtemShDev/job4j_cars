package ru.job4j.cars.store;

import ru.job4j.cars.model.*;

import java.util.List;
import java.util.Map;

public interface Store {

    Ad getAd(int id);

    boolean addUser(User user);

    User findUser(String email);

    List<Ad> findAllAds(String all, Map<String, String> params);

    List<Ad> findAdsForLastDay();

    List<Ad> findAdsWithPhoto();

    List<Ad> findAdsByBrand(Brand brand);

    List<Brand> findBrands();

    boolean addBrand(Brand brand);

    List<CarBody> findCarBodies();

    boolean addCarBody(CarBody carBody);

    List<Model> findModelsByBrand(Brand brand);

    boolean addModel(Model model);

    boolean saveAd(Ad ad);

    boolean replaceAd(Ad ad);

}