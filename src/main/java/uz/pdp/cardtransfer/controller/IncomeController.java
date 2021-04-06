package uz.pdp.cardtransfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.cardtransfer.service.IncomeService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/income")
public class IncomeController {
    @Autowired
    IncomeService service;


    @GetMapping
    public ResponseEntity<?> getAll(HttpServletRequest httpServletRequest){
        return ResponseEntity.ok(service.getIncomes(httpServletRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?>getOne(@PathVariable Integer id, HttpServletRequest httpServletRequest){
        return ResponseEntity.ok(service.getIncomeById(id, httpServletRequest));
    }
}
