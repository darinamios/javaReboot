package ru.pogo.sbrf.cu.cassette;

import ru.pogo.sbrf.cu.ref.Nominal;

public interface Cassette {
    void add(int count);
    void extract(int count);
    int count();
    Nominal getNominal();
}
