package ru.sbrf.cu.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import ru.sbrf.cu.domain.Email;

@Service
public interface EmailRepository extends PagingAndSortingRepository<Email, Long> {

}
