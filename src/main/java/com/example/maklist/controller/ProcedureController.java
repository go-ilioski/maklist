package com.example.maklist.controller;

import com.example.maklist.dto.AddListingDto;
import com.example.maklist.dto.LoginDto;
import com.example.maklist.dto.MessageDto;
import com.example.maklist.dto.RegisterUserDto;
import com.example.maklist.repository.ProcedureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProcedureController {

    private final ProcedureRepository procedureRepository;

    @PostMapping(value = "/conversation/{listingId}/{buyerUserId}")
    public ResponseEntity<?> createConversation(@PathVariable Integer listingId, @PathVariable Integer buyerUserId) {
        procedureRepository.startConversation(listingId, buyerUserId);

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/message/send")
    public ResponseEntity<?> createMessage(@RequestBody MessageDto messageDto) {
        procedureRepository.sendMessage(messageDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/message/edit")
    public ResponseEntity<?> editMessage(@RequestBody MessageDto messageDto) {
        procedureRepository.editMessage(messageDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/listing")
    public ResponseEntity<?> createListing(@RequestBody AddListingDto addListingDto) {
        procedureRepository.addListing(addListingDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginCheck(@RequestBody LoginDto loginDto){
        Boolean login = procedureRepository.loginCheck(loginDto);
        if (login){
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserDto registerUserDto){
        procedureRepository.registerUser(registerUserDto);

        return ResponseEntity.ok().build();

    }


}
