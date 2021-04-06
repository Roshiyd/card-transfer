package uz.pdp.cardtransfer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.cardtransfer.entity.Income;
import uz.pdp.cardtransfer.payload.ApiResponse;
import uz.pdp.cardtransfer.repository.CardRepository;
import uz.pdp.cardtransfer.repository.IncomeRepository;
import uz.pdp.cardtransfer.repository.OutcomeRepository;
import uz.pdp.cardtransfer.security.JwtProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IncomeService {
    @Autowired
    OutcomeRepository outcomeRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    IncomeRepository incomeRepository;


    public List<Income> getIncomes(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("Authorization");
        token = token.substring(7);
        String userName = jwtProvider.getUserNameFromToken(token);
        List<Income> userIncome = new ArrayList<>();

        List<Income> all = incomeRepository.findAll();
        for (Income income : all) {
            if (income.getToCard().getUsername().equals(userName))
                userIncome.add(income);
        }
        return userIncome;
    }

    public ApiResponse getIncomeById(Integer id, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");
        token = token.substring(7);
        String userName = jwtProvider.getUserNameFromToken(token);
        Optional<Income> optionalIncome = incomeRepository.findById(id);
        if (!optionalIncome.isPresent()) return new ApiResponse("Such card doesnt exist", false);
        if (!userName.equals(optionalIncome.get().getToCard().getUsername()))
            return new ApiResponse("Such card doesnt belong to you", false);
        return new ApiResponse("", true,optionalIncome.get());
    }
}
