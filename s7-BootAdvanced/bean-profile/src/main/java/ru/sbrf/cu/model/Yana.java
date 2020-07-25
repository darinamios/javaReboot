package ru.sbrf.cu.model;

import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import ru.sbrf.cu.model.base.Friend;
import ru.sbrf.cu.model.conditions.YanaConditions;

@Conditional(YanaConditions.class)
@Component
public class Yana extends Friend {
    @Override
    public String getName() {
        return "Яна";
    }
}
