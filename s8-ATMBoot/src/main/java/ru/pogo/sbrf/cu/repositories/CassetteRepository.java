package ru.pogo.sbrf.cu.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.pogo.sbrf.cu.models.Cassette;
import ru.pogo.sbrf.cu.models.CassetteImpl;

public interface CassetteRepository extends CrudRepository<CassetteImpl, Integer> {
}
