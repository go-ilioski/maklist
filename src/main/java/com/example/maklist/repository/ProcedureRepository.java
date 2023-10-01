package com.example.maklist.repository;

import com.example.maklist.dto.AddListingDto;
import com.example.maklist.dto.LoginDto;
import com.example.maklist.dto.MessageDto;
import com.example.maklist.dto.RegisterUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@Component
public class ProcedureRepository {

    private static final String JDBC_URL = "jdbc:postgresql://localhost:5431/maklist";
    private static final String JDBC_USERNAME = "postgres";
    private static final String JDBC_PASS = "postgres";

    private static final String START_CONVERSATION_SQL = "CALL startConversation(?, ?);";
    private static final String SEND_MESSAGE_SQL = "CALL sendMessage(?, ?, ?);";
    private static final String EDIT_MESSAGE_SQL = "CALL editMessage(?, ?, ?);";
    private static final String ADD_LISTING_SQL = "CALL addListing(?, ?, ?, ?, ?, ?);";
    private static final String ABORT_AUCTION_SQL = "CALL abortAuction(?, ?);";
    private static final String ADD_LISTING_TO_CART_SQL = "CALL addListingToCart(?, ?);";
    private static final String ADD_LISTING_TO_WISHLIST_SQL = "CALL addlistingtowishlist(?, ?);";
    private static final String REMOVE_LISTING_FROM_WISHLIST_SQL = "CALL removelistingfromwishlist(?, ?);";
    private static final String REMOVE_LISTING_FROM_CART_SQL = "CALL removeListingFromCart(?, ?);";
    private static final String ADD_REVIEW_SQL = "CALL addReview(?, ?, ?, ?);";
    private static final String EDIT_REVIEW_SQL = "CALL editReview(?, ?, ?, ?);";
    private static final String PLACE_BID_SQL = "CALL placeBid(?, ?, ?);";
    private static final String REGISTER_USER_SQL = "CALL registerUser(?, ?, ?, ?, ?, ?);";
    private static final String START_AUCTION_SQL = "CALL startAuction(?, ?);";
    private static final String EDIT_AUCTION_SQL = "CALL editAuction(?, ?, ?, ?, ?, ?);";
    private static final String PUT_LISTING_UP_FOR_AUCTION_SQL = "CALL putListingUpForAuction(?, ?, ?, ?, ?, ?);";
    private static final String EDIT_LISTING_SQL = "CALL editListing(?, ?, ?, ?, ?, ?);";
    private static final String CHECK_OUT_CART_SQL = "CALL checkOutCart(?);";
    private static final String LOGIN_CHECK_SQL = "{ ? = call loginCheck(?, ?) }";
    private static final String PUT_LISTING_UP_AUCTION_SQL = "{ ? = call putlistingupforauction(?, ?, ?, ?, ?, ?) }";

    // funkcija
    public Integer putlistingupforauction(Integer listingId, Integer sellerId, BigDecimal minimumBid, BigDecimal reservePrice){
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASS);
             CallableStatement preparedCall = conn.prepareCall(PUT_LISTING_UP_AUCTION_SQL)) {
            preparedCall.registerOutParameter(1, Types.INTEGER);
            preparedCall.setInt(2, listingId);
            preparedCall.setInt(3, sellerId);
            preparedCall.setBigDecimal(4, minimumBid);
            preparedCall.setBigDecimal(5, reservePrice);
            preparedCall.setTimestamp(6, Timestamp.from(Instant.now()));
            preparedCall.setTimestamp(7, Timestamp.from(Instant.now().plus(5, ChronoUnit.DAYS) ) );
            // Use executeUpdate if the procedure doesnt return any results
            preparedCall.execute();
            return preparedCall.getInt(1);

        } catch (SQLException e) {
            log.error("Error executing putlistingupforauction function {}", e.getMessage(), e);
            throw new RestClientException(e.getMessage());
        }
    }

    public Boolean loginCheck(LoginDto loginDto){
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASS);
             CallableStatement preparedCall = conn.prepareCall(LOGIN_CHECK_SQL)) {
            preparedCall.registerOutParameter(1, Types.BOOLEAN);
            preparedCall.setString(2, loginDto.getEmail());
            preparedCall.setString(3, loginDto.getPassword());
            // Use executeUpdate if the procedure doesnt return any results
            preparedCall.execute();
            return preparedCall.getBoolean(1);

        } catch (SQLException e) {
            log.error("Error executing loginCheck function {}", e.getMessage(), e);
            throw new RestClientException(e.getMessage());
        }
    }

    public void checkOutCart (Integer userId)
    {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASS);
             PreparedStatement preparedStatement = conn.prepareStatement(CHECK_OUT_CART_SQL)) {
            preparedStatement.setInt(1, userId);
            // Use executeUpdate if the procedure doesnt return any results
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            log.error("Error executing checkOutCart procedure {}", e.getMessage(), e);
            throw new RestClientException(e.getMessage());
        }
    }

    public void editListing (Integer listingId, Integer userId, String description, String name, BigDecimal price, Integer quantity)
    {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASS);
             PreparedStatement preparedStatement = conn.prepareStatement(EDIT_LISTING_SQL)) {
            preparedStatement.setInt(1, listingId);
            preparedStatement.setInt(2, userId);
            preparedStatement.setString(3, description);
            preparedStatement.setString(4, name);
            preparedStatement.setBigDecimal(5, price);
            preparedStatement.setInt(6, quantity);
            // Use executeUpdate if the procedure doesnt return any results
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error executing editListing procedure {}", e.getMessage(), e);
            throw new RestClientException(e.getMessage());
        }
    }

    public void putListingUpForAuction (Integer auctionId, Integer userId, BigDecimal minimumBid, BigDecimal reservePrice, Instant startDate, Instant endDate) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASS);
             PreparedStatement preparedStatement = conn.prepareStatement(PUT_LISTING_UP_FOR_AUCTION_SQL)) {
            preparedStatement.setInt(1, auctionId);
            preparedStatement.setInt(2, userId);
            preparedStatement.setBigDecimal(3, minimumBid);
            preparedStatement.setBigDecimal(4, reservePrice);
            preparedStatement.setTimestamp(5, Timestamp.from(startDate));
            preparedStatement.setTimestamp(6, Timestamp.from(endDate));
            // Use executeUpdate if the procedure doesnt return any results
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error executing putListingUpForAuction procedure {}", e.getMessage(), e);
            throw new RestClientException(e.getMessage());
        }
    }

    public void editAuction(Integer auctionId, Integer userId, BigDecimal minimumBid, BigDecimal reservePrice, Instant startDate, Instant endDate)
    {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASS);
             PreparedStatement preparedStatement = conn.prepareStatement(EDIT_AUCTION_SQL)) {
            preparedStatement.setInt(1, auctionId);
            preparedStatement.setInt(2, userId);
            preparedStatement.setBigDecimal(3, minimumBid);
            preparedStatement.setBigDecimal(4, reservePrice);
            preparedStatement.setTimestamp(5, Timestamp.from(startDate));
            preparedStatement.setTimestamp(6, Timestamp.from(endDate));
            // Use executeUpdate if the procedure doesnt return any results
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error executing editAuction procedure {}", e.getMessage(), e);
            throw new RestClientException(e.getMessage());
        }
    }

    public void startAuction(Integer auctionId, Integer userId)
    {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASS);
             PreparedStatement preparedStatement = conn.prepareStatement(START_AUCTION_SQL)) {
            preparedStatement.setInt(1, auctionId);
            preparedStatement.setInt(2, userId);
            // Use executeUpdate if the procedure doesnt return any results
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error executing startAuction procedure {}", e.getMessage(), e);
            throw new RestClientException(e.getMessage());
        }
    }

    public void registerUser(RegisterUserDto registerUserDto) {
        // String name, String surname, String email, String address, String password, Integer number
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASS);
             PreparedStatement preparedStatement = conn.prepareStatement(REGISTER_USER_SQL)) {

            preparedStatement.setString(1, registerUserDto.getName());
            preparedStatement.setString(2, registerUserDto.getSurname());
            preparedStatement.setString(3, registerUserDto.getEmail());
            preparedStatement.setString(4, registerUserDto.getAddress());
            preparedStatement.setString(5, registerUserDto.getPassword());
            preparedStatement.setBigDecimal(6, registerUserDto.getPhoneNumber());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error executing registerUser procedure {}", e.getMessage(), e);
            throw new RestClientException(e.getMessage());
        }
    }

    public void placeBid(Integer auctionId, BigDecimal bidAmount, Integer buyerUserId) {

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASS);
             PreparedStatement preparedStatement = conn.prepareStatement(PLACE_BID_SQL)) {

            preparedStatement.setInt(1, auctionId);
            preparedStatement.setBigDecimal(2, bidAmount);
            preparedStatement.setInt(3, buyerUserId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error executing placeBid procedure {}", e.getMessage(), e);
            throw new RestClientException(e.getMessage());
        }
    }

    public void editReview(Integer userId, Integer listingID, Integer rating, String reviewText) {

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASS);
             PreparedStatement preparedStatement = conn.prepareStatement(EDIT_REVIEW_SQL)) {

            preparedStatement.setInt(1, listingID);
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, rating);
            preparedStatement.setString(4, reviewText);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error executing editReview procedure {}", e.getMessage(), e);
            throw new RestClientException(e.getMessage());
        }
    }

    public void addReview(Integer userId, Integer listingID, Integer rating, String reviewText) {

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASS);
             PreparedStatement preparedStatement = conn.prepareStatement(ADD_REVIEW_SQL)) {

            preparedStatement.setInt(1, listingID);
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, rating);
            preparedStatement.setString(4, reviewText);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error executing addReview procedure {}", e.getMessage(), e);
            throw new RestClientException(e.getMessage());
        }
    }

    public void addListingToCart(Integer listingID, Integer buyerUserId) {

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASS);
             PreparedStatement preparedStatement = conn.prepareStatement(ADD_LISTING_TO_CART_SQL)) {

            preparedStatement.setInt(1, listingID);
            preparedStatement.setInt(2, buyerUserId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error executing addListingToCart procedure {}", e.getMessage(), e);
            throw new RestClientException(e.getMessage());
        }
    }

    public void addListingToWishlist(Integer listingID, Integer buyerUserId) {

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASS);
             PreparedStatement preparedStatement = conn.prepareStatement(ADD_LISTING_TO_WISHLIST_SQL)) {

            preparedStatement.setInt(1, listingID);
            preparedStatement.setInt(2, buyerUserId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error executing addListingToWishlist procedure {}", e.getMessage(), e);
            throw new RestClientException(e.getMessage());
        }
    }

    public void removeListingFromWishlist(Integer listingID, Integer buyerUserId) {

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASS);
             PreparedStatement preparedStatement = conn.prepareStatement(REMOVE_LISTING_FROM_WISHLIST_SQL)) {

            preparedStatement.setInt(1, listingID);
            preparedStatement.setInt(2, buyerUserId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error executing removeListingFromWishlist procedure {}", e.getMessage(), e);
            throw new RestClientException(e.getMessage());
        }
    }





    public void removeListingFromCart(Integer listingID, Integer buyerUserId) {

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASS);
             PreparedStatement preparedStatement = conn.prepareStatement(REMOVE_LISTING_FROM_CART_SQL)) {

            preparedStatement.setInt(1, listingID);
            preparedStatement.setInt(2, buyerUserId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error executing removeListingFromCart procedure {}", e.getMessage(), e);
            throw new RestClientException(e.getMessage());
        }
    }



    public void abortAuction(Integer auctionId, Integer sellerUserId) {

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASS);
             PreparedStatement preparedStatement = conn.prepareStatement(ABORT_AUCTION_SQL)) {

            preparedStatement.setInt(1, auctionId);
            preparedStatement.setInt(2, sellerUserId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error executing abortAuction procedure {}", e.getMessage(), e);
            throw new RestClientException(e.getMessage());
        }
    }





    public void startConversation(Integer listingId, Integer buyerUserId) {

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASS);
             PreparedStatement preparedStatement = conn.prepareStatement(START_CONVERSATION_SQL)) {

            preparedStatement.setInt(1, listingId);
            preparedStatement.setInt(2, buyerUserId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error executing startConversation procedure {}", e.getMessage(), e);
            throw new RestClientException(e.getMessage());
        }
    }

    public void sendMessage(MessageDto messageDto) {

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASS);
             PreparedStatement preparedStatement = conn.prepareStatement(SEND_MESSAGE_SQL)) {

            preparedStatement.setInt(1, messageDto.getFromUserId());
            preparedStatement.setInt(2, messageDto.getConversationId());
            preparedStatement.setString(3, messageDto.getMessage());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error executing sendMessage procedure {}", e.getMessage(), e);
            throw new RestClientException(e.getMessage());
        }
    }

    public void editMessage(MessageDto messageDto) {

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASS);
             PreparedStatement preparedStatement = conn.prepareStatement(EDIT_MESSAGE_SQL)) {

            preparedStatement.setInt(1, messageDto.getFromUserId());
            preparedStatement.setInt(2, messageDto.getMessageId());
            preparedStatement.setString(3, messageDto.getMessage());

            // Use executeUpdate if the procedure doesnt return any results
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error executing editMessage procedure {}", e.getMessage(), e);
            throw new RestClientException(e.getMessage());
        }
    }

    public void addListing(AddListingDto addListing) {

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASS);
             PreparedStatement preparedStatement = conn.prepareStatement(ADD_LISTING_SQL)) {

            preparedStatement.setInt(1, addListing.getSellerId());
            preparedStatement.setString(2, addListing.getDescription());
            preparedStatement.setBigDecimal(3, addListing.getPrice());
            preparedStatement.setString(4, addListing.getName());
            preparedStatement.setInt(5, addListing.getQuantity());
            preparedStatement.setArray(6, conn.createArrayOf("INTEGER", addListing.getCategories().toArray()));

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            log.error("Error executing addListing procedure {}", e.getMessage(), e);
            throw new RestClientException(e.getMessage());
        }
    }


}
