package ru.sbrf.cu.repositories;

//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sbrf.cu.models.Student;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

// @Transactional должна стоять на методе сервиса.
// Причем, если метод не подразумевает изменения данных в БД то категорически желательно
// выставить у аннотации параметр readOnly в true.
// Но это только упражнение и транзакции мы пока не проходили.
// Поэтому, для упрощения, пока вешаем над классом репозитория
@Transactional
@Repository
public class StudentRepositoryJpaImpl implements StudentRepositoryJpa {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Student save(Student student) {
        if (student.getId() <= 0) {
            em.persist(student);
            return student;
        } else {
            return em.merge(student);
        }
    }

    @Override
    public Optional<Student> findById(long id) {
        return Optional.ofNullable(em.find(Student.class, id));
    }

    @Override
    public List<Student> findAll() {
        TypedQuery<Student> qu = em.createQuery("select s from Student s join fetch s.emails", Student.class);
        EntityGraph<?> entityGraph = em.getEntityGraph("avatars-entity-graph");
        qu.setHint("javax.persistence.fetchgraph", entityGraph);
        return qu.getResultList();
    }

    @Override
    public List<Student> findByName(String name) {
        TypedQuery<Student> qu = em.createQuery("select s from Student s where s.name = :name", Student.class);
        qu.setParameter("name", name);
        return qu.getResultList();
    }

    @Override
    public void updateNameById(long id, String name) {
        Query qu = em.createQuery("update Student s set s.name = :name where s.id= :id");
        qu.setParameter("name",name);
        qu.setParameter("id",id);
        qu.executeUpdate();
    }

    @Override
    public void deleteById(long id) {
        Query qu = em.createQuery("delete from Student s where s.id= :id");
        qu.setParameter("id",id);
        qu.executeUpdate();
    }

}
