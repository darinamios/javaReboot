package ru.sbrf.cu.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import ru.sbrf.cu.domain.Person;

import java.util.List;
import java.util.Optional;

@Service
public interface PersonRepository extends CrudRepository<Person, Integer> {
    List<Person> findAllByName(String name);
    //Optional<Person> findById(int id);
   // void deleteById(int id);
}
