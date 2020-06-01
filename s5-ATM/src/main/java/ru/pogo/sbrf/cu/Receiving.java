package ru.pogo.sbrf.cu;

import ru.pogo.sbrf.cu.ref.Nominal;

import java.util.List;

public interface Receiving {
    List<Nominal> receiveMoney(Integer sum) throws Exception;
}
