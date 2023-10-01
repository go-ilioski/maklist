package com.example.maklist.controller;

import com.example.maklist.dto.AddListingDto;
import com.example.maklist.dto.Categories;
import com.example.maklist.dto.EditListingDto;
import com.example.maklist.dto.UserDto;
import com.example.maklist.repository.CategoryRepository;
import com.example.maklist.repository.EditListingRepository;
import com.example.maklist.repository.ListingDetailsRepository;
import com.example.maklist.repository.ProcedureRepository;
import com.example.maklist.view.ListingDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class OperationsController {

    private final ProcedureRepository procedureRepository;
    private final CategoryRepository categoryRepository;
    private final ListingDetailsRepository listingDetailsRepository;
    private final EditListingRepository editListingRepository;

    @GetMapping("addToCart/{id}")
    public String addToCart(@PathVariable Long id){
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();

        UserDto user = (UserDto) session.getAttribute("user");
        Long userId = user.getId();

        procedureRepository.addListingToCart(id.intValue(),userId.intValue());

        return "redirect:/home";

    }

    @GetMapping("/addListing")
    public String addProductPage(Model model){
        List<Categories> categories = categoryRepository.findAll();

        model.addAttribute("categories",categories);

        return "add-listing";
    }

    @GetMapping("/editListing/{id}")
    public String editProductPage(@PathVariable Long id, Model model){

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();

        UserDto user = (UserDto) session.getAttribute("user");
        Long sellerId = user.getId();

        EditListingDto product = editListingRepository.findByIdAndSellerUserId(id, sellerId.intValue());
        //product.getQuantity()
        List<Categories> categories = categoryRepository.findAll();

        model.addAttribute("help",null);
        model.addAttribute("product",product);
        model.addAttribute("categories",categories);

        return "edit-listing";

    }

    @PostMapping("/editListing")
    public String editProduct(@RequestParam Integer id,
                              @RequestParam String description,
                              @RequestParam BigDecimal price,
                              @RequestParam String name,
                              @RequestParam Integer quantity,
                              @RequestParam Integer category){

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();

        UserDto user = (UserDto) session.getAttribute("user");
        Long sellerId = user.getId();

        List<Integer> cat = new ArrayList<Integer>();
        cat.add(category);

        procedureRepository.editListing(id,sellerId.intValue(),description,name,price,quantity);

        return "redirect:/maklist/seller";
    }

    @PostMapping("/addListing")
    public String saveProduct(@RequestParam String description,
                              @RequestParam BigDecimal price,
                              @RequestParam String name,
                              @RequestParam Integer quantity,
                              @RequestParam Integer category){

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();

        UserDto user = (UserDto) session.getAttribute("user");
        Long sellerId = user.getId();

        List<Integer> cat = new ArrayList<Integer>();
        cat.add(category);

        AddListingDto addListingDto = new AddListingDto();
        addListingDto.setSellerId(sellerId.intValue());
        addListingDto.setCategories(cat);
        addListingDto.setName(name);
        addListingDto.setDescription(description);
        addListingDto.setPrice(price);
        addListingDto.setQuantity(quantity);

        procedureRepository.addListing(addListingDto);

        return "redirect:/maklist/seller";
    }

    @GetMapping("bidAuction/{id}")
    public String bidAuction(@PathVariable Long id, Model model){

        model.addAttribute("id",id);
        return "bid-auction";
    }

    @PostMapping("bidAuction")
    public String bidAuction(@RequestParam Integer id, @RequestParam BigDecimal price){
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();

        UserDto user = (UserDto) session.getAttribute("user");
        Long userId = user.getId();

        procedureRepository.placeBid(id,price,userId.intValue());

        return "redirect:/home";

    }

    @GetMapping("putUpForAuction/{id}")
    public String putUpForAuction(@PathVariable Long id, Model model){

        model.addAttribute("id",id);
        return "put-up-auction";
    }

    @PostMapping("putUpForAuction")
    public String putUpForAuction(@RequestParam Integer id, @RequestParam BigDecimal minimumBid, @RequestParam BigDecimal reservePrice){
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();

        UserDto user = (UserDto) session.getAttribute("user");
        Long userId = user.getId();

        Integer au_id = procedureRepository.putlistingupforauction(id, userId.intValue(), minimumBid, reservePrice);

        Long auct = au_id.longValue();

        return "redirect:/home";

    }


    @GetMapping("removeFromCart/{id}")
    public String removeFromCart(@PathVariable Long id){
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();

        UserDto user = (UserDto) session.getAttribute("user");
        Long userId = user.getId();

        procedureRepository.removeListingFromCart(id.intValue(),userId.intValue());

        return "redirect:/views/shoppingCart";

    }

    //TODO
    @GetMapping("addToWishlist/{id}")
    public String addToWishlist(@PathVariable Long id){
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();

        UserDto user = (UserDto) session.getAttribute("user");
        Long userId = user.getId();

        procedureRepository.addListingToWishlist(id.intValue(),userId.intValue());

        return "redirect:/home";

    }

    //TODO
    @GetMapping("removeFromWishlist/{id}")
    public String removeFromWishlist(@PathVariable Long id){
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();

        UserDto user = (UserDto) session.getAttribute("user");
        Long userId = user.getId();

        procedureRepository.removeListingFromWishlist(id.intValue(),userId.intValue());

        return "redirect:/views/wishlist_by_user";

    }

    @GetMapping("checkoutCart")
    public String checkoutCart(){

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();

        UserDto user = (UserDto) session.getAttribute("user");
        Long userId = user.getId();

        procedureRepository.checkOutCart(userId.intValue());

        return "redirect:/views/shoppingCart";

    }

}
