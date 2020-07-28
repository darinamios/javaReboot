package ru.sbrf.cu.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "email")
@NoArgsConstructor
public class Email {

    @Id
    @GeneratedValue
    private Integer id;

    private String email;

    public Email(String email) {
        this.email = email;
    }

}
