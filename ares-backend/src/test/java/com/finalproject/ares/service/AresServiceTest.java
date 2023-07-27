package com.finalproject.ares.service;

import com.finalproject.ares.TestApplicationConfiguration;
import com.finalproject.ares.dto.frontend.PhotoResponse;
import com.finalproject.ares.dto.frontend.RoverPhotosResponse;
import com.finalproject.ares.entities.Favorite;
import com.finalproject.ares.repositories.CategoryRepo;
import com.finalproject.ares.repositories.FavoriteRepo;
import com.finalproject.ares.repositories.PhotoRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
class AresServiceTest {

    @Mock
    private FavoriteRepo favoriteRepo;

    @Mock
    private CategoryRepo categoryRepo;

    @Mock
    private PhotoRepo photoRepo;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private AresService aresService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        aresService = new AresService();
        aresService.favoriteRepo = favoriteRepo;
        aresService.categoryRepo = categoryRepo;
        aresService.photoRepo = photoRepo;
        aresService.rt = restTemplate;
    }

    @Test
    public void testGetLatestPhotos() {
        // Mock the response from the external API
        ResponseEntity<String> responseEntity = ResponseEntity.ok("{\"latest_photos\": []}");
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(responseEntity);

        // Mock the repository calls
        when(favoriteRepo.findByFavoritePhotoId(anyInt())).thenReturn(null);

        List<RoverPhotosResponse> expectedPhotosByRover = new ArrayList<>();
        RoverPhotosResponse roverPhotosResponse = new RoverPhotosResponse("Curiosity", new ArrayList<>());
        expectedPhotosByRover.add(roverPhotosResponse);

        List<RoverPhotosResponse> actualPhotosByRover = aresService.getLatestPhotos();

        assertEquals(expectedPhotosByRover.get(0).getRoverName(),
                actualPhotosByRover.get(0).getRoverName());
    }

    @Test
    public void testSearchPhotos() {
        // Mock the response from the external API
        ResponseEntity<String> responseEntity = ResponseEntity.ok("{\"photos\": []}");
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(responseEntity);

        // Mock the repository calls
        when(favoriteRepo.findByFavoritePhotoId(anyInt())).thenReturn(null);

        String[] rovers = {"Curiosity"};
        String[] cameras = {"Camera1"};
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 3);
        String favoriteStatus = "no";

        List<PhotoResponse> expectedFinalPhotos = new ArrayList<>();

        List<PhotoResponse> actualFinalPhotos = aresService.searchPhotos(rovers, cameras, startDate, endDate, favoriteStatus);

        assertEquals(expectedFinalPhotos, actualFinalPhotos);
    }

    @Test
    public void testGetUserFavorites() {
        // Mock the repository call
        when(favoriteRepo.findAll()).thenReturn(new ArrayList<>());

        List<PhotoResponse> expectedFinalPhotos = new ArrayList<>();

        List<PhotoResponse> actualFinalPhotos = aresService.getUserFavorites();

        assertEquals(expectedFinalPhotos, actualFinalPhotos);
    }

    @Test
    public void testAddFavorite() {
        // Create a mock photo
        com.finalproject.ares.entities.Photo mockPhoto = new com.finalproject.ares.entities.Photo();
        mockPhoto.setId(1);
        mockPhoto.setImgSrc("img1.jpg");
        mockPhoto.setRover("Rover1");
        mockPhoto.setCamera("Camera1");

        // Mock the repository call to return the mock photo
        when(photoRepo.findById(1)).thenReturn(Optional.of(mockPhoto));

        // Perform the method under test
        aresService.addFavorite(1);

        // Verify that the save method is called with the correct parameters
        ArgumentCaptor<Favorite> argumentCaptor = ArgumentCaptor.forClass(Favorite.class);
        verify(favoriteRepo).save(argumentCaptor.capture());

        Favorite savedFavorite = argumentCaptor.getValue();
        assertNotNull(savedFavorite);
        assertEquals(1, savedFavorite.getId());
        assertEquals(LocalDate.now(), savedFavorite.getDate());
        assertEquals(mockPhoto, savedFavorite.getFavoritePhoto());
    }



}