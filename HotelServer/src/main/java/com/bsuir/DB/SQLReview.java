package com.bsuir.DB;

import com.bsuir.hotelorg.Booking;
import com.bsuir.hotelorg.Review;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

public class SQLReview implements ISQLReview{
    private static SQLReview instance;
    private ConnectionDB dbConnection;

    private SQLReview() throws SQLException, ClassNotFoundException {
        dbConnection = ConnectionDB.getInstance();
    }

    public static synchronized SQLReview getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null)
            instance = new SQLReview();
        return instance;
    }

    @Override
    public ArrayList<Review> get() {
        String str = "select tb_reviews.*, tb_clients.client_name, tb_clients.client_surname from tb_reviews" +
                " left join tb_clients on tb_reviews.client_id = tb_clients.client_id;";

        ArrayList<String[]> result = dbConnection.getArrayResult(str);

        ArrayList<Review> reviewList = new ArrayList<>();
        for (String[] items: result){
            Review review = new Review();
            review.setId(Integer.parseInt(items[0]));
            review.setRating(Integer.parseInt(items[1]));
            review.setComment(items[2]);
            review.setClient_id(Integer.parseInt(items[3]));
            review.setClient_name(items[4]);
            review.setClient_surname(items[5]);

            reviewList.add(review);
        }
        return reviewList;
    }

    @Override
    public boolean deleteReview(Review r) {
        return false;
    }

    @Override
    public boolean insertReview(Review r) {
        String proc = "{call insert_review(?,?,?)}";

        try (CallableStatement callableStatement = ConnectionDB.dbConnection.prepareCall(proc)) {
            callableStatement.setInt(1, r.getRating());
            callableStatement.setString(2, r.getComment());
            callableStatement.setInt(3, r.getClient_id());

            callableStatement.execute();

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("ошибка");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
