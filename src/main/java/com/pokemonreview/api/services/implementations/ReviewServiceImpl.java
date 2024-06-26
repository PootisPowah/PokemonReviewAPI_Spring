package com.pokemonreview.api.services.implementations;

import com.pokemonreview.api.dto.ReviewDto;
import com.pokemonreview.api.exceptions.PokemonNotFoundException;
import com.pokemonreview.api.exceptions.ReviewNotFoundException;
import com.pokemonreview.api.models.Pokemon;
import com.pokemonreview.api.models.Review;
import com.pokemonreview.api.repository.PokemonRepository;
import com.pokemonreview.api.repository.ReviewRepository;
import com.pokemonreview.api.services.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final PokemonRepository pokemonRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, PokemonRepository pokemonRepository) {
        this.reviewRepository = reviewRepository;
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public List<ReviewDto> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream().map(r -> mapToDto(r)).collect(Collectors.toList());

    }

    @Override
    public List<ReviewDto> getReviewsByPokemonId(int id) {
        List<Review> reviews = reviewRepository.findByPokemonId(id);
        return reviews.stream().map(r -> mapToDto(r)).collect(Collectors.toList());
    }

    @Override
    public  ReviewDto getReviewByPokemonId(int pokemonId, int reviewId) {
       Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(()-> new PokemonNotFoundException("Pokemon with associated review not found"));
       Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review with associated pokemon not found"));
        if(review.getPokemon().getId() != pokemon.getId()){ //equals is better, but here we use primitive instead of wrapper
            throw new ReviewNotFoundException("This review does not belong to this pokemon");
        }
        return mapToDto(review);
    }

    @Override
    public ReviewDto getReviewById(int id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException("Review not found"));
        return mapToDto(review);
    }

    @Override
    public ReviewDto createReview(int pokemonId, ReviewDto reviewDto) {
        Review review = mapToEntity(reviewDto);
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(()-> new PokemonNotFoundException("Pokemon not found"));
        review.setPokemon(pokemon);
        Review newReview = reviewRepository.save(review);
        return mapToDto(newReview);
    }

    @Override
    public ReviewDto updateReview(int pokemonId, int reviewId, ReviewDto reviewDto) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review not found"));
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() -> new PokemonNotFoundException("Pokemon not found"));
        if(review.getPokemon().getId() != pokemon.getId()){ //equals is better, but here we use primitive instead of wrapper
            throw new ReviewNotFoundException("This review does not belong to this pokemon");
        }
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());

        Review updatedReview = reviewRepository.save(review);
        return mapToDto(updatedReview);
    }

    @Override
    public void deleteReview(int pokemonId, int reviewId) {
    Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() -> new PokemonNotFoundException("Pokemon not found"));
    Review review = reviewRepository.findById(reviewId).orElseThrow(()-> new ReviewNotFoundException("Review not found"));
        if(review.getPokemon().getId() != pokemon.getId()){ //equals is better, but here we use primitive instead of wrapper
            throw new ReviewNotFoundException("This review does not belong to this pokemon");
        }
    reviewRepository.delete(review);
    }

    private ReviewDto mapToDto(Review review){
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setTitle(review.getTitle());
        reviewDto.setContent(review.getContent());
        reviewDto.setStars(review.getStars());
        return reviewDto;
    }

    private Review mapToEntity(ReviewDto reviewDto){
        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());
        return review;
    }
}
