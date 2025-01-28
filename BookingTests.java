package test;

import models.Book;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import utils.BookUtils;
import utils.ReportUtils;
import com.google.gson.Gson;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class BookingTests {

    @BeforeSuite
    public void setUp() {
        ReportUtils.initializeReport();
    }

    @Test
    public void testCreateAndValidateBooking() {
        ReportUtils.createTest("testCreateAndValidateBooking");

        // Create a booking
        Book booking = new Book();
        booking.setFirstname("testFirstName");
        booking.setLastname("lastName");
        booking.setTotalprice(10.11);
        booking.setDepositpaid(true);

        Map<String, String> bookingDates = new HashMap<>();
        bookingDates.put("checkin", "2022-01-01");
        bookingDates.put("checkout", "2024-01-01");
        booking.setBookingdates(bookingDates);

        booking.setAdditionalneeds("testAdd");

        Response createResponse = BookUtils.createBooking(booking);
        if (createResponse.getStatusCode() == 200) {
            ReportUtils.logPass("Booking created successfully");
        } else {
            ReportUtils.logFail("Failed to create booking");
        }
        Assert.assertEquals(createResponse.getStatusCode(), 200);

        int bookingId = createResponse.jsonPath().getInt("bookingid");

        // Validate the booking
        Response getResponse = BookUtils.getBookingById(bookingId);
        if (getResponse.getStatusCode() == 200) {
            ReportUtils.logPass("Booking retrieved successfully");
        } else {
            ReportUtils.logFail("Failed to retrieve booking");
        }
        Assert.assertEquals(getResponse.getStatusCode(), 200);

        Booking responseBooking = new Gson().fromJson(getResponse.getBody().asString(), Book.class);
        Assert.assertEquals(responseBooking.getFirstname(), booking.getFirstname());
        Assert.assertEquals(responseBooking.getLastname(), booking.getLastname());
        Assert.assertEquals(responseBooking.getTotalprice(), booking.getTotalprice());
        Assert.assertEquals(responseBooking.isDepositpaid(), booking.isDepositpaid());
        Assert.assertEquals(responseBooking.getBookingdates(), booking.getBookingdates());
        Assert.assertEquals(responseBooking.getAdditionalneeds(), booking.getAdditionalneeds());
    }

    @Test
    public void testNegativeCase() {
        ReportUtils.createTest("testNegativeCase");

        // Attempt to get a booking with an invalid ID
        Response getResponse = BookUtils.getBookingById(-1);
        if (getResponse.getStatusCode() == 404) {
            ReportUtils.logPass("Negative test case passed");
        } else {
            ReportUtils.logFail("Negative test case failed");
        }
        Assert.assertEquals(getResponse.getStatusCode(), 404);
    }

    @AfterSuite
    public void tearDown() {
        ReportUtils.flushReport();
    }
}