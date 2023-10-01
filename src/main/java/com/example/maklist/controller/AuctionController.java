package com.example.maklist.controller;

import com.example.maklist.repository.AuctionDetailsRepository;
import com.example.maklist.view.AuctionDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auction")
@RequiredArgsConstructor
public class AuctionController {

    private final AuctionDetailsRepository auctionDetailsRepository;

//    @GetMapping(value = "/{id}")
//    public ResponseEntity<AuctionDetails> getAccountDetailsById(@PathVariable Long id) {
//        AuctionDetails auctionDetails = auctionDetailsRepository.findByAuctionId(id);
//
//        return new ResponseEntity<>(auctionDetails, HttpStatus.OK);
//    }


    @GetMapping()
    public ResponseEntity<Page<AuctionDetails>> getAllPageable(
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        var pageRequest = PageRequest.of(page, size);
        Page<AuctionDetails> auctionDetails = auctionDetailsRepository.findAll(pageRequest);
        return new ResponseEntity<>(auctionDetails, HttpStatus.OK);
    }



}
