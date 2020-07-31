package ru.pogo.sbrf.cu.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ru.pogo.sbrf.cu.ref.Nominal;

@JsonDeserialize(as = CassetteImpl.class)
public interface Cassette {
    void add(int count);
    void extract(int count);
    int count();
    Nominal getNominal();
}
