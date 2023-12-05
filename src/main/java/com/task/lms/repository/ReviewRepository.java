package com.task.lms.repository;

import com.task.lms.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    @Query(value = "SELECT * FROM review WHERE book_id = :bookId", nativeQuery = true)
    List<Review> getReviewByBookId(Integer bookId);

    @Query(value = "SELECT * FROM review WHERE user_id = :userId AND book_id = :bookId LIMIT 1", nativeQuery = true)
    Review getReviewByUserIdAndBookId(Integer userId, Integer bookId);

    @Query(value = "SELECT book_id, AVG(rating) AS overall_rating, COUNT(*) AS num_reviews FROM review WHERE book_id IN :bookIds AND rating > 0 GROUP BY book_id HAVING AVG(rating) IS NOT NULL", nativeQuery = true)
    List<Map<String, Object>> getOverallRatingAndNumReviewsForBooks(List<Integer> bookIds);

    @Query(value = "SELECT book_id, AVG(rating) AS overall_rating, COUNT(*) AS num_reviews FROM review WHERE book_id = :bookId AND rating > 0 GROUP BY book_id HAVING AVG(rating) IS NOT NULL", nativeQuery = true)
    List<Map<String, Object>> getOverallRatingAndNumReviewsForSingleBook(Integer bookId);
}
