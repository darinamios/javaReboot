package ru.sbrf.cu.model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sbrf.cu.model.base.Friend;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Party {
    private final List<Friend> partyMembers;

    public void printPartyMembers() {
        System.out.println("Участники вечеринки:");
        System.out.println(partyMembers.stream().map(Friend::getName).collect(Collectors.joining("\n")));
    }
}
