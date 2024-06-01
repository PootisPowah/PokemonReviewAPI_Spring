package com.pokemonreview.api.services;

import com.pokemonreview.api.dto.ReviewDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {
    List<ReviewDto> getAllReviews();
    List<ReviewDto> getReviewsByPokemonId(int id);
    ReviewDto getReviewByPokemonId(int reviewId, int pokemonId);
    ReviewDto getReviewById(int id);
    ReviewDto createReview(int pokemonId, ReviewDto reviewDto);
    ReviewDto updateReview(int pokemonId,int reviewId, ReviewDto reviewDto);
    void deleteReview(int pokemonId, int reviewId);
}
