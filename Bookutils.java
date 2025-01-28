package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Book;

public class BookUtils {

	public static Response createBooking(Book booking) {
		return RestAssured.given().header("Content-Type", "application/json").body(booking)
				.post("https://restful-booker.herokuapp.com/booking");
	}

	public static Response getBookingById(int id) {
		return RestAssured.given().get("https://restful-booker.herokuapp.com/booking/" + id);
	}
}