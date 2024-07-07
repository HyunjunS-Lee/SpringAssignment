package assignment.spring.controller;

import assignment.spring.model.dto.ReviewRequest;
import assignment.spring.model.entity.Review;
import assignment.spring.repository.StoreRepository;
import assignment.spring.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private StoreRepository storeRepository;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Review> writeReview(@RequestBody ReviewRequest review) {
        Review writtenReview = reviewService.writeReview(review);
        return ResponseEntity.ok(writtenReview);
    }

    @GetMapping("/store/{storeId}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('MANAGER')")
    public ResponseEntity<List<Review>> getReviewsByStore(@PathVariable Long storeId) {
        List<Review> reviews = reviewService.getReviewsByStore(storeId);
        return ResponseEntity.ok(reviews);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody ReviewRequest review) {
        Review updatedReview = reviewService.updateReview(id, review);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<String> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok("Review deleted");
    }
}
