package com.example.maklist.controller;

import com.example.maklist.dto.ReportDto;
import com.example.maklist.dto.UserDto;
import com.example.maklist.repository.*;
import com.example.maklist.view.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/views")
@RequiredArgsConstructor
public class ViewController {

    private final AuctionDetailsRepository auctionDetailsRepository;
    private final ConversationMessagesRepository conversationMessagesRepository;
    private final DeliveryDetailsRepository deliveryDetailsRepository;
    private final ListingDetailsRepository listingDetailsRepository;
    private final ListingSellerBuyerReviewsRepository listingSellerBuyerReviewsRepository;
    private final WishlistByUserRepository wishlistByUserRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final AuctionPagesRepository auctionPagesRepository;
    private final ReportRepository reportRepository;

    @GetMapping("/auctions")
    public String viewAuctionsPage(Model model) {
        return getAllAuctionsPagable(model, 1);
    }

    @GetMapping("/auctions/page/{pageNo}")
    public String getAllAuctionsPagable(Model model, @PathVariable(value = "pageNo") int pageNo){

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();

        UserDto user = (UserDto) session.getAttribute("user");
        Long sellerId = user.getId();

        Slice<AuctionDetails> auctionDetails = auctionDetailsRepository.findByBidderId(sellerId,PageRequest.of(pageNo - 1, 10));

        model.addAttribute("auctionDetails", auctionDetails);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("pageNumber", auctionDetails.getPageable().getPageNumber());
        model.addAttribute("hasNext", auctionDetails.hasNext());

        return "auction-view";

    }

    @GetMapping("/my_auctions")
    public String myAuctionsPage(Model model) {
        return getAllMyAuctionsPage(model, 1);
    }

    @GetMapping("/my_auctions/page/{pageNo}")
    public String getAllMyAuctionsPage(Model model, @PathVariable(value = "pageNo") int pageNo){

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();

        UserDto user = (UserDto) session.getAttribute("user");
        Long sellerId = user.getId();

        Slice<AuctionPages> auctionPages = auctionPagesRepository.findBySellerId(sellerId,PageRequest.of(pageNo - 1, 10));

        model.addAttribute("auctionPages", auctionPages);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("pageNumber", auctionPages.getPageable().getPageNumber());
        model.addAttribute("hasNext", auctionPages.hasNext());

        return "my-auctions";

    }

    @GetMapping("/conversations")
    public String viewConversationsPage(Model model) {
        return getAllConversationsPagable(model, 1);
    }

    @GetMapping("/conversations/page/{pageNo}")
    public String getAllConversationsPagable(Model model, @PathVariable(value = "pageNo") int pageNo){

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();

        UserDto user = (UserDto) session.getAttribute("user");
        Long sellerId = user.getId();


        Slice<ConversationMessages> conversationMessages = conversationMessagesRepository.findBySellerIdOrBuyerId(sellerId, sellerId, PageRequest.of(pageNo - 1, 10));

        model.addAttribute("conversationMessages", conversationMessages);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("pageNumber", conversationMessages.getPageable().getPageNumber());
        model.addAttribute("hasNext", conversationMessages.hasNext());

        return "conversations-msg-view";

    }

    @GetMapping("/delivery_details")
    public String viewDeliveryDetailsPage(Model model) {
        return getAllDeliveryDetailsPagable(model, 1);
    }

    @GetMapping("/delivery_details/page/{pageNo}")
    public String getAllDeliveryDetailsPagable(Model model, @PathVariable(value = "pageNo") int pageNo){
        //Slice<DeliveryDetails> deliveryDetails = deliveryDetailsRepository.findAll(PageRequest.of(pageNo - 1, 10));

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();

        UserDto user = (UserDto) session.getAttribute("user");
        Long buyerID = user.getId();

        Slice<DeliveryDetails> deliveryDetails = deliveryDetailsRepository.findByBuyerId(buyerID, PageRequest.of(pageNo - 1, 10));

        model.addAttribute("deliveryDetails", deliveryDetails);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("pageNumber", deliveryDetails.getPageable().getPageNumber());
        model.addAttribute("hasNext", deliveryDetails.hasNext());

        return "delivery-details-view";

    }

    @GetMapping("/reports")
    public String reportsPage(Model model) {
        return getReportsPage(model, 1);
    }

    @GetMapping("/reports/page/{pageNo}")
    public String getReportsPage(Model model, @PathVariable(value = "pageNo") int pageNo){
        //Slice<DeliveryDetails> deliveryDetails = deliveryDetailsRepository.findAll(PageRequest.of(pageNo - 1, 10));

        Slice<ReportDto> reportDtos = reportRepository.findAll(PageRequest.of(pageNo - 1, 10));

        model.addAttribute("reportDtos", reportDtos);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("pageNumber", reportDtos.getPageable().getPageNumber());
        model.addAttribute("hasNext", reportDtos.hasNext());

        return "admin-reports";

    }


//    @GetMapping("/delivery_details/page/{pageNo}")
//    public String getAllDeliveryDetails(Model model, @PathVariable(value = "pageNo") int pageNo){
//        //Page<DeliveryDetails> deliveryDetails = deliveryDetailsRepository.findAll(PageRequest.of(pageNo - 1, 10));
//        Slice<DeliveryDetails> deliveryDetails = deliveryDetailsRepository.findByListingName("25 inch luxury phone",PageRequest.of(pageNo - 1, 10));
//
//        model.addAttribute("deliveryDetails", deliveryDetails);
//        model.addAttribute("currentPage", pageNo);
//        model.addAttribute("pageNumber", deliveryDetails.getPageable().getPageNumber());
//        model.addAttribute("pageSize", deliveryDetails.getPageable().getPageSize());
//        model.addAttribute("hasNext", deliveryDetails.hasNext());
//
//        return "delivery";
//
//    }

    @GetMapping("/listing_details/{id}")
    public String viewListingDetailsPage(@PathVariable Long id, Model model) {
        ListingDetails listingDetails = listingDetailsRepository.findByListingId(id);

        model.addAttribute("listingDetails", listingDetails);

        return "listing-details-view";
    }


    @GetMapping("/getReviews/{id}")
    public String getReviews(@PathVariable Long id, Model model){
        model.addAttribute("listRevId", id);
        return viewListingSellerBuyerReviewsPage(model);
    }

    @GetMapping("/listing_seller_buyer_reviews")
    public String viewListingSellerBuyerReviewsPage(Model model) {
        return getAllListingSellerBuyerReviewsPagable(model, 1);
    }

    @GetMapping("/listing_seller_buyer_reviews/page/{pageNo}")
    public String getAllListingSellerBuyerReviewsPagable(Model model, @PathVariable(value = "pageNo") int pageNo){

        Long listRevId = (Long) model.getAttribute("listRevId");
        Slice<ListingSellerBuyerReviews> listingSellerBuyerReviews = listingSellerBuyerReviewsRepository.findByListingId(listRevId,PageRequest.of(pageNo - 1, 10));

        model.addAttribute("listingSellerBuyerReviews", listingSellerBuyerReviews);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("pageNumber", listingSellerBuyerReviews.getPageable().getPageNumber());
        model.addAttribute("hasNext", listingSellerBuyerReviews.hasNext());

        return "listing_seller_buyer_reviews-view";

    }



    // poslednoto
    @GetMapping("/wishlist_by_user")
    public String viewWishlistByUserPage(Model model,  HttpSession session) {
        return getAllWishlistByUserPagable(model, 1, session);
    }

    @GetMapping("/wishlist_by_user/page/{pageNo}")
    public String getAllWishlistByUserPagable(Model model, @PathVariable(value = "pageNo") int pageNo,  HttpSession session){

        UserDto user = (UserDto) session.getAttribute("user");
        Long userId = user.getId();

        Slice<WishlistByUser> wishlistByUsers = wishlistByUserRepository.findByUserId(userId ,PageRequest.of(pageNo - 1, 10));

        model.addAttribute("wishlistByUsers", wishlistByUsers);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("pageNumber", wishlistByUsers.getPageable().getPageNumber());
        model.addAttribute("hasNext", wishlistByUsers.hasNext());

        return "wishlist-by-user-view";

    }

    @GetMapping("/shoppingCart")
    public String viewShoppingCart(Model model,  HttpSession session) {
        return getShoppingCart(model, 1, session);
    }

    @GetMapping("/shoppingCart/page/{pageNo}")
    public String getShoppingCart(Model model, @PathVariable(value = "pageNo") int pageNo,  HttpSession session){

        UserDto user = (UserDto) session.getAttribute("user");
        Long userId = user.getId();

        Slice<ShoppingCart> shoppingCarts = shoppingCartRepository.findByUserId(userId ,PageRequest.of(pageNo - 1, 10));

        model.addAttribute("shoppingCarts", shoppingCarts);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("pageNumber", shoppingCarts.getPageable().getPageNumber());
        model.addAttribute("hasNext", shoppingCarts.hasNext());

        return "shoppingCart-by-user-view";

    }

    @GetMapping("/auctionPages")
    public String viewAuctionPages(Model model) {
        return getAuctionPages(model, 1);
    }

    @GetMapping("/auctionPages/page/{pageNo}")
    public String getAuctionPages(Model model, @PathVariable(value = "pageNo") int pageNo){

//        UserDto user = (UserDto) session.getAttribute("user");
//        Long userId = user.getId();

        Slice<AuctionPages> auctionPages = auctionPagesRepository.findAll(PageRequest.of(pageNo - 1, 10));

        model.addAttribute("auctionPages", auctionPages);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("pageNumber", auctionPages.getPageable().getPageNumber());
        model.addAttribute("hasNext", auctionPages.hasNext());

        return "auction-pages";

    }





}
