package ru.sbrf.cu.model;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import ru.sbrf.cu.model.base.Friend;

@ConditionalOnBean(Alexey.class)
@Component
public class Anna extends Friend {
    @Override
    public String getName() {
        return "Аня";
    }
}
