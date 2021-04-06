package uz.pdp.cardtransfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.cardtransfer.entity.Income;
import uz.pdp.cardtransfer.entity.Outcome;

import java.util.UUID;

public interface OutcomeRepository extends JpaRepository<Outcome, Integer> {

}
