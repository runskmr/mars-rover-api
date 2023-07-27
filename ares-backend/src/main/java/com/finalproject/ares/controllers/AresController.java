package com.finalproject.ares.controllers;

import com.finalproject.ares.dto.frontend.CategoryRequestParam;
import com.finalproject.ares.dto.frontend.RoverPhotosResponse;
import com.finalproject.ares.dto.frontend.PhotoResponse;
import com.finalproject.ares.service.AresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/ares/api")
@CrossOrigin
public class AresController {
    @Autowired
    private AresService service;

    @GetMapping("/latest-photos")
    public ResponseEntity<List<RoverPhotosResponse>> latestPhotos() {
        List<RoverPhotosResponse> photos = service.getLatestPhotos();
        return ResponseEntity.status(HttpStatus.OK).body(photos);
    }

    @GetMapping("/search-photos")
    public ResponseEntity<List<PhotoResponse>> searchPhotos(@RequestParam String[] rovers,
                                                            @RequestParam String[] cameras,
                                                            @RequestParam String startDate,
                                                            @RequestParam String endDate,
                                                            @RequestParam String favorite)
    {
        List<PhotoResponse> photos = service.searchPhotos(rovers,cameras,LocalDate.parse(startDate),LocalDate.parse(endDate),favorite);
        return ResponseEntity.status(HttpStatus.OK).body(photos);
    }

    @GetMapping("/user-favorites")
    public ResponseEntity<List<PhotoResponse>> favoritePhotos() {
        List<PhotoResponse> photos = service.getUserFavorites();
        return ResponseEntity.status(HttpStatus.OK).body(photos);
    }

    @PostMapping("/favorite/{id}")
    public ResponseEntity<Void> addFavorite(@PathVariable("id") Integer id) {
        try {
            service.addFavorite(id);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/favorite/{id}")
    public ResponseEntity<Void> removeFavorite(@PathVariable("id") Integer id) {
        try {
            service.removeFavorite(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/category")
    public ResponseEntity<Void> addCategory(@RequestBody CategoryRequestParam categoryName) {
        try {
            String name = categoryName.getName();
            service.addCategory(name);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
//
//    @PutMapping("/favorite/{id}/category/{categoryId}")
//    public ResponseEntity<Favorite> updateFavorite(@PathVariable("id") Integer id, @PathVariable("categoryId") Integer categoryId) {
//        try {
//            Favorite favorite = service.updateFavorite(id, categoryId);
//            return ResponseEntity.status(HttpStatus.OK).body(favorite);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
}
