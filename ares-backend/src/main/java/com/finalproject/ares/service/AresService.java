package com.finalproject.ares.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.finalproject.ares.entities.Category;
import com.finalproject.ares.entities.Favorite;
import com.finalproject.ares.dto.frontend.RoverPhotosResponse;
import com.finalproject.ares.dto.frontend.PhotoResponse;
import com.finalproject.ares.repositories.CategoryRepo;
import com.finalproject.ares.repositories.FavoriteRepo;
import com.finalproject.ares.dto.api.Photo;
import com.finalproject.ares.dto.api.Photos;
import com.finalproject.ares.repositories.PhotoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AresService {
    public static final String API_URL = "https://mars-photos.herokuapp.com/api/v1/";
    public static final String[] ACTIVE_ROVERS = {"Curiosity", "Perseverance"};
    public static final Integer LATEST_MAX = 12;

    @Autowired
    FavoriteRepo favoriteRepo;

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    PhotoRepo photoRepo;

    RestTemplate rt = new RestTemplate();

    public List<RoverPhotosResponse> getLatestPhotos() {

        List<RoverPhotosResponse> photosByRover = new ArrayList<>();

        for (String rover: ACTIVE_ROVERS) {
            ResponseEntity<String> response = rt.getForEntity("https://mars-photos.herokuapp.com/api/v1/rovers/"
                            + rover + "/latest_photos", String.class);

            String jsonResponse = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            try {
                List<Photo> apiPhotos = objectMapper.convertValue(objectMapper.readTree(jsonResponse).get("latest_photos"), new TypeReference<List<Photo>>(){});
                List<PhotoResponse> latestPhotos = apiPhotos.stream().limit(LATEST_MAX).map(photo -> {
                    photoRepo.save(new com.finalproject.ares.entities.Photo(photo));
                    PhotoResponse customPhoto = new PhotoResponse(photo);
                    Favorite favorite = favoriteRepo.findByFavoritePhotoId(photo.getId());
                    if(favorite != null) {
                        customPhoto.setFavorite(true);
                    } else {
                        customPhoto.setFavorite(false);
                    }
                    return customPhoto;
                }).collect(Collectors.toList());

                RoverPhotosResponse roverPhotos = new RoverPhotosResponse(rover, latestPhotos);

                photosByRover.add(roverPhotos);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return photosByRover;
    }

    public List<PhotoResponse> searchPhotos(String[] rovers, String[] cameras, LocalDate startDate, LocalDate endDate, String favoriteStatus)
    {
        List<PhotoResponse> finalPhotos = new ArrayList<>();
        List<LocalDate> daysRange = Stream.iterate(startDate, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(startDate, endDate)+1).collect(Collectors.toList());
        ObjectMapper objectMapper = new ObjectMapper();

        for(String rover : rovers) {
            for(LocalDate day : daysRange) {
                String url = API_URL + "rovers/" + rover + "/photos?earth_date=" + day.toString();
                ResponseEntity<String> response = rt.getForEntity(url, String.class);
                String jsonResponse = response.getBody();

                objectMapper.registerModule(new JavaTimeModule());
                try {
                    List<Photo> listPhotos = objectMapper.readValue(jsonResponse, Photos.class).getPhotos();

                    for (Photo photo : listPhotos) {
                        if(Set.of(cameras).contains(photo.getCamera().getName())) {
                            Favorite favorite = favoriteRepo.findByFavoritePhotoId(photo.getId());

                            PhotoResponse customPhoto = new PhotoResponse(photo);
                            photoRepo.save(new com.finalproject.ares.entities.Photo(photo));

                            if(favorite != null && !favoriteStatus.equals("no")) {
                                customPhoto.setFavorite(true);
                                finalPhotos.add(customPhoto);
                            } else if(!favoriteStatus.equals("yes")){
                                customPhoto.setFavorite(false);
                                finalPhotos.add(customPhoto);
                            }
                        }
                    }

                } catch (JsonProcessingException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return finalPhotos;
    }

    public List<PhotoResponse> getUserFavorites() {
        List<PhotoResponse> finalPhotos = new ArrayList<>();
        List<Favorite> favorites = favoriteRepo.findAll();
        for(Favorite favorite : favorites ) {
            com.finalproject.ares.entities.Photo photo
                    = favorite.getFavoritePhoto();
            PhotoResponse customPhoto = new PhotoResponse();
            customPhoto.setId(photo.getId());
            customPhoto.setImgSrc(photo.getImgSrc());
            customPhoto.setRover(photo.getRover());
            customPhoto.setCamera(photo.getCamera());
            customPhoto.setFavorite(true);
            finalPhotos.add(customPhoto);
        }
        return finalPhotos;
    }

    public void addFavorite(int id) {
        Optional<com.finalproject.ares.entities.Photo> photoEntity = photoRepo.findById(id);

        com.finalproject.ares.entities.Photo photo = photoEntity.orElse(null);

        if(photo == null) {
            throw new RuntimeException("Photo not found");
        } else {
            Favorite favorite = new Favorite();
            favorite.setId(id);
            favorite.setDate(LocalDate.now());
            favorite.setFavoritePhoto(photo);
            favoriteRepo.save(favorite);
        }

    }

    public void removeFavorite(int id) {
        favoriteRepo.deleteByFavoritePhotoId(id);
    }

    public void addCategory(String name) {
        Category category = new Category();
        category.setName(name);
        category.setDate(LocalDate.now());
        categoryRepo.save(category);
    }

    public Favorite updateFavorite(int id, int categoryId) {
        Category category = new Category();
        category.setId(categoryId);

        Favorite favorite = new Favorite();
        favorite.setId(id);
        favorite.setFavoriteCategory(category);
        favoriteRepo.save(favorite);
        return favorite;
    }


}
