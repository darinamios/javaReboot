package ru.pogo.sbrf.cu.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.pogo.sbrf.cu.models.ATM;
import ru.pogo.sbrf.cu.models.ATMImpl;

public interface ATMRepository extends CrudRepository<ATMImpl, Integer> {
    ATM save(ATM atm);
}
