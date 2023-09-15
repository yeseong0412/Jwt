package com.example.log.controller;

import com.example.log.dto.BoardDto;
import com.example.log.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService service;
    // C POST :
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(
            @RequestBody BoardDto dto, Authentication authentication
    ){
        String username = authentication.getName();
        service.register(dto,username);
    }

    // R GET : /{id},
    @GetMapping("/{id}")
    public BoardDto read(
            @PathVariable("id") Long id
    ){
        return service.read(id);
    }
    // U PUT :
    @PutMapping("")
    public ResponseEntity modify(
            @RequestBody BoardDto dto
    ){
        service.modify(dto);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("success");
    }

    // D DELETE : /{num}
    @DeleteMapping("/{num}")
    public void remove(@PathVariable("num") Long gno){
        service.remove(gno);
    }
}
