package com.example.maklist.controller;

import com.example.maklist.dto.UserDto;
import com.example.maklist.repository.ListingPageRepository;
import com.example.maklist.view.ListingPage;
import com.example.maklist.view.WishlistByUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/maklist")
@RequiredArgsConstructor
public class MaklistHomeListingController {

    private final ListingPageRepository listingPageRepository;

    @GetMapping("/listing")
    //public String listingPage(Model model, HttpSession session) {
    public String listingPage(Model model) {
        //return getListingsPagable(model, 1, session);
        return getListingsPagable(model, 1);
    }

    @GetMapping("/listing/page/{pageNo}")
    //public String getListingsPagable(Model model, @PathVariable(value = "pageNo") int pageNo, HttpSession session){
    public String getListingsPagable(Model model, @PathVariable(value = "pageNo") int pageNo){

        //UserDto user = (UserDto) session.getAttribute("user");
        //Long userId = user.getId();

        Slice<ListingPage> listingPages = listingPageRepository.findAll(PageRequest.of(pageNo - 1, 10));

        model.addAttribute("listingPages", listingPages);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("pageNumber", listingPages.getPageable().getPageNumber());
        model.addAttribute("hasNext", listingPages.hasNext());

        return "listing-page-maklist";

    }

    @GetMapping("/seller")
    public String sellerPage(Model model, HttpSession session) {
        //return getListingsPagable(model, 1, session);
        return getSellerListings(model, 1, session);
    }

    @GetMapping("/seller/page/{pageNo}")
    //public String getListingsPagable(Model model, @PathVariable(value = "pageNo") int pageNo, HttpSession session){
    public String getSellerListings(Model model, @PathVariable(value = "pageNo") int pageNo, HttpSession session){

        UserDto user = (UserDto) session.getAttribute("user");
        Long userId = user.getId();

        Slice<ListingPage> listingPages = listingPageRepository.findBySellerId(userId,PageRequest.of(pageNo - 1, 10));

        model.addAttribute("listingPages", listingPages);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("pageNumber", listingPages.getPageable().getPageNumber());
        model.addAttribute("hasNext", listingPages.hasNext());

        return "seller-listings";

    }


}
