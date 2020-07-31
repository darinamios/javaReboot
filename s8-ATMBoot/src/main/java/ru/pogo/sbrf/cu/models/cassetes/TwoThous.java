package ru.pogo.sbrf.cu.models.cassetes;

import org.springframework.stereotype.Component;
import ru.pogo.sbrf.cu.models.CassetteImpl;
import ru.pogo.sbrf.cu.ref.Nominal;

@Component
public class TwoThous extends CassetteImpl {

    public TwoThous() {
        super(Nominal.TWO_THOUS, 0);
    }
}
