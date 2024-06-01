package com.pokemonreview.api.controllers;

import com.pokemonreview.api.dto.ReviewDto;
import com.pokemonreview.api.models.Review;
import com.pokemonreview.api.services.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("review")
    public ResponseEntity<List<ReviewDto>> getReviews(){
        return new ResponseEntity<>(reviewService.getAllReviews(), HttpStatus.OK);
    }

    @GetMapping("review/{id}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable int id){
        return new ResponseEntity<>(reviewService.getReviewById(id), HttpStatus.OK);
    }
    @GetMapping("pokemon/{pokemonId}/reviews")
    public List<ReviewDto> getReviewsByPokemonId(@PathVariable(value="pokemonId") int pokemonId){
        return reviewService.getReviewsByPokemonId(pokemonId);
    }
    @GetMapping("pokemon/{pokemonId}/reviews/{reviewId}")
    public ResponseEntity<ReviewDto> getReviewByPokemonId(@PathVariable(value = "pokemonId") int pokemonId,
                                                          @PathVariable(value = "reviewId") int reviewId){
        ReviewDto reviewDto = reviewService.getReviewByPokemonId(pokemonId, reviewId);
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }

    @PostMapping("pokemon/{pokemonId}/review/create")
    public ResponseEntity<ReviewDto> createReview(@PathVariable(value="pokemonId") int pokemonId, @RequestBody ReviewDto reviewDto){
        return new ResponseEntity<>(reviewService.createReview(pokemonId,reviewDto),HttpStatus.OK);
    }

    @PutMapping("pokemon/{pokemonId}/reviews/{id}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable(value = "pokemonId") int pokemonId,
                                                  @PathVariable(value = "id") int reviewId,
                                                  @RequestBody ReviewDto reviewDto){
        ReviewDto updatedReview = reviewService.updateReview(pokemonId,reviewId,reviewDto);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);

    }
        @DeleteMapping("pokemon/{pokemonId}/reviews/{id}")
        public ResponseEntity<String> deleteReview(@PathVariable(value = "pokemonId") int pokemonId,
                                                      @PathVariable(value = "id") int reviewId){
        reviewService.deleteReview(pokemonId,reviewId);
        return new ResponseEntity<>("Review deleted successfully.",HttpStatus.OK);
    }
}
