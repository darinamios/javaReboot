package ru.pogo.sbrf.cu.models.cassetes;

import org.springframework.stereotype.Component;
import ru.pogo.sbrf.cu.models.CassetteImpl;
import ru.pogo.sbrf.cu.ref.Nominal;

@Component
public class FiveThous extends CassetteImpl {

    public FiveThous() {
        super(Nominal.FIVE_THOUS, 0);
    }
}
