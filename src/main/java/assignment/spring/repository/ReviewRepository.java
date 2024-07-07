package assignment.spring.repository;


import assignment.spring.model.entity.Review;
import assignment.spring.model.entity.Store;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByStore(Store store);
}