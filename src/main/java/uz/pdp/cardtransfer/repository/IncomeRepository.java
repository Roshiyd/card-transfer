package uz.pdp.cardtransfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.cardtransfer.entity.Card;
import uz.pdp.cardtransfer.entity.Income;

import java.util.UUID;

public interface IncomeRepository extends JpaRepository<Income, Integer> {

}
