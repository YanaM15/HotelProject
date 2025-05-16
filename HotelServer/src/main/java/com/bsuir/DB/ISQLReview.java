package com.bsuir.DB;

import com.bsuir.hotelorg.Review;

import java.util.ArrayList;

public interface ISQLReview {
    ArrayList<Review> get();
    boolean deleteReview(Review r);
    boolean insertReview(Review r);
}
