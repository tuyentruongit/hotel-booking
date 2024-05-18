package methodsecuritynew.bookingapp.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.Hotel;
import methodsecuritynew.bookingapp.entity.Review;
import methodsecuritynew.bookingapp.entity.User;
import methodsecuritynew.bookingapp.model.request.UpsertReviewRequest;
import methodsecuritynew.bookingapp.repository.ReviewRepository;
import methodsecuritynew.bookingapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReviewsService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final HotelService hotelService;
    private final HttpSession session;
    public Review createReview(UpsertReviewRequest upsertReviewRequest) {

        // tìm kiếm khách sạn theo id
        Hotel hotel = hotelService.getHotelById(upsertReviewRequest.getIdHotel());

        // tìm usser đang đăng nhập
        User user = userRepository.findByEmail(session.getAttribute("MY_SESSION").toString()).orElseThrow(()-> new UsernameNotFoundException("Không tìm thấy user"));
//        float totalReview = hotel.getRating()*findAllReview(hotel.getId()).size();
//        hotel.setRating((totalReview+upsertReviewRequest.getRating())/findAllReview(hotel.getId()).size()+1);

        Review review = Review.builder()
                .user(user)
                .comment(upsertReviewRequest.getComment())
                .rating(upsertReviewRequest.getRating())
                .createAt(LocalDate.now())
                .hotel(hotel)
                .build();

        reviewRepository.save(review);
        List<Review> list = reviewRepository.findAllByHotel_IdOrderByCreateAtDesc(hotel.getId());
        float rating = 0;
        for (Review re : list ){
            rating+=re.getRating();
        }
        rating=rating/list.size();
        hotel.setRating(rating);
        return  review;
    }

    public List<Review> findAllReview(Integer id) {
       return reviewRepository.findAllByHotel_IdOrderByCreateAtDesc(id);
    }

    public Review updateReview(UpsertReviewRequest upsertReviewRequest, Integer id) {
        User user = userRepository.findByEmail(session.getAttribute("MY_SESSION").toString()).orElseThrow(()-> new UsernameNotFoundException("Không tìm thấy user"));

        System.out.println("--------------------------------------------------------------" + user);
        // kiểm tra hotel có tồn tại không
        Hotel hotel = hotelService.getHotelById(upsertReviewRequest.getIdHotel());
        System.out.println("--------------------------------------------------------------" + hotel);


        // kiểm tra review có tồn tại hay không
        Review review =reviewRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Canno find review by Id : " + id));
        System.out.println("--------------------------------------------------------------" + review.getUser());
        // kiểm tra xem id của người đang thực hiện có phải của user đang log in hay không
        if (!Objects.equals(review.getUser().getId(), user.getId())){
            throw new RuntimeException ("Bạn không có quyền truy cập ");
        }

        //kiểm tra xem review có thuộc movie này hay không
        if (!review.getHotel().getId().equals(hotel.getId())){
            throw new RuntimeException("Review không thuộc hotel này ");
        }

        review.setComment(upsertReviewRequest.getComment());
        review.setRating(upsertReviewRequest.getRating());
        return reviewRepository.save(review);
    }

    public void deleteReview(Integer id) {
        User user = userRepository.findByEmail(session.getAttribute("MY_SESSION").toString()).orElseThrow(()-> new UsernameNotFoundException("Không tìm thấy user"));


        System.out.println("--------------------------------------------------------------" + user);

        // kiểm tra review có tồn tại hay không
        Review review =reviewRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Canno find review by Id : " + id));

        System.out.println("--------------------------------------------------------------" + review.getUser());


        Hotel hotel = review.getHotel();
        System.out.println("--------------------------------------------------------------" + hotel);

        // kiểm tra xem id của người đang thực hiện có phải của user đang log in hay không
        if (!Objects.equals(review.getUser().getId(), user.getId())){
            throw new RuntimeException ("Bạn không có quyền truy cập ");
        }

        reviewRepository.delete(review);
        List<Review> list = reviewRepository.findAllByHotel_IdOrderByCreateAtDesc(hotel.getId());
        float rating = 0;
        for (Review re : list ){
            rating+=re.getRating();
        }
        rating=rating/list.size();
        hotel.setRating(rating);

    }
}
