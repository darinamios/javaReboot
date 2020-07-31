package ru.pogo.sbrf.cu.models.cassetes;

import org.springframework.stereotype.Component;
import ru.pogo.sbrf.cu.models.CassetteImpl;
import ru.pogo.sbrf.cu.ref.Nominal;

@Component
public class FiveHund extends CassetteImpl {
    public FiveHund(){
        super(Nominal.FIVE_HUND, 0);
    }
}
