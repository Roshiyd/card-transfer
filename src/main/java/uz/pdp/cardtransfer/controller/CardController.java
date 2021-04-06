package uz.pdp.cardtransfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cardtransfer.entity.Card;
import uz.pdp.cardtransfer.payload.ApiResponse;
import uz.pdp.cardtransfer.payload.CardDto;
import uz.pdp.cardtransfer.security.JwtProvider;
import uz.pdp.cardtransfer.service.CardService;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/api/card")
@RestController
public class CardController {
    @Autowired
    CardService service;
    @Autowired
    JwtProvider jwtProvider;




    @PostMapping
    public HttpEntity<ApiResponse> addCard(@RequestBody CardDto dto, HttpServletRequest httpServletRequest){
        ApiResponse apiResponse = service.addCard(dto,httpServletRequest);
        return ResponseEntity.status(apiResponse.isSuccess()?201:209).body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<?> getCards(HttpServletRequest httpServletRequest){
        return ResponseEntity.ok(service.getCards(httpServletRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card>getOne(@PathVariable Integer id, HttpServletRequest httpServletRequest){
        return ResponseEntity.ok(service.getCardById(id, httpServletRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> edit(@RequestBody CardDto dto,
                                            @PathVariable Integer id, HttpServletRequest httpServletRequest){
        ApiResponse apiResponse = service.editCard(id, dto, httpServletRequest);
        if (apiResponse.isSuccess()){
            return ResponseEntity.status(202).body(apiResponse);
        }else {
            return ResponseEntity.status(409).body(apiResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id, HttpServletRequest httpServletRequest){
        ApiResponse response = service.deleteCard(id, httpServletRequest);
        return ResponseEntity.status(response.isSuccess()?202:409).body(response);
    }

}
