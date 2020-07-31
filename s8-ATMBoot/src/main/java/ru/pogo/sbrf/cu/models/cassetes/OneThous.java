package ru.pogo.sbrf.cu.models.cassetes;

import org.springframework.stereotype.Component;
import ru.pogo.sbrf.cu.models.CassetteImpl;
import ru.pogo.sbrf.cu.ref.Nominal;

@Component
public class OneThous extends CassetteImpl {
    public OneThous() {
        super(Nominal.ONE_THOUS, 0);
    }
}
