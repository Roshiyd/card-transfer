package uz.pdp.cardtransfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cardtransfer.payload.ApiResponse;
import uz.pdp.cardtransfer.payload.OutcomeDto;
import uz.pdp.cardtransfer.service.OutcomeService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/outcome")
public class OutcomeController {
    @Autowired
    OutcomeService service;

    @PostMapping
    public HttpEntity<ApiResponse> addOutcome(@RequestBody OutcomeDto dto, HttpServletRequest httpServletRequest) {
        ApiResponse apiResponse = service.addOutcome(dto, httpServletRequest);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 209).body(apiResponse);
    }
    @GetMapping
    public ResponseEntity<?> getOutcomeList(HttpServletRequest httpServletRequest){
        return ResponseEntity.ok(service.getOutcomeList(httpServletRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?>getOutcomeById(@PathVariable Integer id, HttpServletRequest httpServletRequest){
        return ResponseEntity.ok(service.getOutcomeById(id, httpServletRequest));
    }
}
