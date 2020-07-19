package ru.pogo.sbrf.cu.cassette;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.pogo.sbrf.cu.models.Cassette;
import ru.pogo.sbrf.cu.exceptions.IncorrectValue;
import ru.pogo.sbrf.cu.exceptions.NotAvailableRequestCount;
import ru.pogo.sbrf.cu.models.CassetteImpl;
import ru.pogo.sbrf.cu.ref.Nominal;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName(value = "Кассета должна ...")
class CassetteImplTest {
    private Cassette cassette;

    @BeforeEach
    public void prepareCassette(){
       cassette = new CassetteImpl(Nominal.ONE_HUND);
       cassette.add(5);
    }

    @DisplayName(value=" принимать положительную сумму, меняя баланс")
    @Test
    void add() {
        cassette.add(4);
        assertEquals(9, cassette.count());
    }

    @DisplayName(value=" принимать 0, не меняя баланс")
    @Test
    void addZero() {
        cassette.add(0);
        assertEquals(5, cassette.count());
    }

    @DisplayName(value=" не принимать отрицательную сумму")
    @Test
    void addMinus() {
        assertThrows(IncorrectValue.class, () -> {
            cassette.add(-1);
        });
    }

    @DisplayName(value=" принимать положительную сумму, меняя баланс (денег достаточно)")
    @Test
    void extract() {
        cassette.extract(3);
        assertEquals(2, cassette.count());
    }

    @DisplayName(value=" принимать 0, не меняя баланс")
    @Test
    void extractZero() {
        cassette.extract(0);
        assertEquals(5, cassette.count());
    }

    @DisplayName(value=" не позволять снимать больше, чем есть")
    @Test
    void extractNotEnough() {
        assertThrows(NotAvailableRequestCount.class, () -> {
            cassette.extract(10);
        });
    }

    @DisplayName(value=" не принимать отрицательные значения")
    @Test
    void extractMinus() {
        assertThrows(IncorrectValue.class, () -> {
            cassette.extract(-1);
        });
    }

}