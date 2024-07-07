package assignment.spring.service;

import assignment.spring.expection.ErrorResponse;
import assignment.spring.model.dto.ReviewRequest;
import assignment.spring.model.entity.Review;
import assignment.spring.model.entity.Store;
import assignment.spring.repository.ReviewRepository;
import assignment.spring.repository.StoreRepository;
import assignment.spring.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import static assignment.spring.type.ErrorCode.*;


@Service
@AllArgsConstructor
public class ReviewService {
    private ReviewRepository reviewRepository;
    private StoreRepository storeRepository;
    private UserRepository userRepository;

    //사용자가 작성한 리뷰를 저장합니다.
    public Review writeReview(ReviewRequest request) {
        Review review = new Review();
        review.setStore(storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new ErrorResponse(STORE_NOT_FOUND)));
        review.setUser(userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ErrorResponse(USER_NOT_FOUND)));
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setCreatedAt(LocalDateTime.now());
        review.setUpdatedAt(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    //특정 상점에 대한 모든 리뷰를 조회합니다.
    public List<Review> getReviewsByStore(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ErrorResponse(STORE_NOT_FOUND));
        return reviewRepository.findByStore(store);
    }

    //특정 리뷰를 업데이트합니다.
    public Review updateReview(Long reviewId, ReviewRequest request) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ErrorResponse(REVIEW_NOT_FOUND));
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setUpdatedAt(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
