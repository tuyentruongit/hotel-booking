package methodsecuritynew.bookingapp.rest;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.Review;
import methodsecuritynew.bookingapp.model.request.UpsertReviewRequest;
import methodsecuritynew.bookingapp.service.ReviewsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/reviews")
@RequiredArgsConstructor
public class ReviewApi {
    private final ReviewsService reviewsService;
    @PostMapping("/create")
    public ResponseEntity<?> createReviews(@RequestBody UpsertReviewRequest upsertReviewRequest){
        Review review= reviewsService.createReview(upsertReviewRequest);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateReview(@RequestBody UpsertReviewRequest upsertReviewRequest, @PathVariable Integer id){
        Review review= reviewsService.updateReview(upsertReviewRequest,id);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReview( @PathVariable Integer id){
        reviewsService.deleteReview(id);
        return  ResponseEntity.ok().build();
    }
}
