package uz.pdp.cardtransfer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.cardtransfer.entity.Card;
import uz.pdp.cardtransfer.entity.Income;
import uz.pdp.cardtransfer.entity.Outcome;
import uz.pdp.cardtransfer.payload.ApiResponse;
import uz.pdp.cardtransfer.payload.OutcomeDto;
import uz.pdp.cardtransfer.repository.CardRepository;
import uz.pdp.cardtransfer.repository.IncomeRepository;
import uz.pdp.cardtransfer.repository.OutcomeRepository;
import uz.pdp.cardtransfer.security.JwtProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OutcomeService {
    @Autowired
    OutcomeRepository outcomeRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    IncomeRepository incomeRepository;


    public ApiResponse addOutcome(OutcomeDto dto, HttpServletRequest httpServletRequest) {
        Optional<Card> optionalCardTo = cardRepository.findById(dto.getToCardId());
        if (!optionalCardTo.isPresent()) return new ApiResponse("To card not found", false);
        Optional<Card> optionalCardFrom = cardRepository.findById(dto.getFromCardId());
        if (!optionalCardFrom.isPresent()) return new ApiResponse("From card not found", false);
        Card cardFrom = optionalCardFrom.get();
        Card cardTo = optionalCardTo.get();

        String token = httpServletRequest.getHeader("Authorization");
        token = token.substring(7);
        String userName = jwtProvider.getUserNameFromToken(token);
        if (!userName.equals(cardFrom.getUsername())) return
                new ApiResponse("The card you sending from does not belong to you", false);

        Double balance = cardFrom.getBalance();
        Double totalAmount = dto.getAmount() + (dto.getAmount() / 100 * dto.getCommissionPercent());
        if (balance < totalAmount) return new ApiResponse("Balance is not sufficient", false);

        Outcome outcome = new Outcome();
        Income income = new Income();

        outcome.setAmount(dto.getAmount());
        outcome.setCommissionPercent(dto.getCommissionPercent());
        outcome.setFromCard(cardFrom);
        outcome.setToCard(cardTo);
        outcome.setDate(new Date());
        outcomeRepository.save(outcome);

        income.setAmount(dto.getAmount());
        income.setDate(new Date());
        income.setFromCard(cardFrom);
        income.setToCard(cardTo);
        incomeRepository.save(income);

        cardFrom.setBalance(balance - totalAmount);
        cardTo.setBalance(cardTo.getBalance() + dto.getAmount());
        cardRepository.save(cardFrom);
        cardRepository.save(cardTo);


        return new ApiResponse("Transaction sent successfully", true);
    }


    public List<Outcome> getOutcomeList(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");
        token = token.substring(7);
        String userName = jwtProvider.getUserNameFromToken(token);
        List<Outcome> userOutcome = new ArrayList<>();

        List<Outcome> all = outcomeRepository.findAll();
        for (Outcome outcome : all) {
            if (outcome.getFromCard().getUsername().equals(userName))
                userOutcome.add(outcome);
        }
        return userOutcome;
    }

    public ApiResponse getOutcomeById(Integer id, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");
        token = token.substring(7);
        String userName = jwtProvider.getUserNameFromToken(token);
        Optional<Outcome> optionalOutcome = outcomeRepository.findById(id);
        if (!optionalOutcome.isPresent()) return new ApiResponse("Such outcome doesnt exist", false);
        if (!userName.equals(optionalOutcome.get().getFromCard().getUsername()))
            return new ApiResponse("Such transaction doesnt belong to you", false);
        return new ApiResponse("", true, optionalOutcome.get());
    }

}
