package ru.pogo.sbrf.cu.models.cassetes;

import org.springframework.stereotype.Component;
import ru.pogo.sbrf.cu.models.CassetteImpl;
import ru.pogo.sbrf.cu.ref.Nominal;

@Component
public class OneHund extends CassetteImpl {
    public OneHund() {
        super(Nominal.ONE_HUND, 0);
    }
}
