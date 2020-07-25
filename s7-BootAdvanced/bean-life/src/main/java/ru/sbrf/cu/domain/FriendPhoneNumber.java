package ru.sbrf.cu.domain;

public class FriendPhoneNumber extends PhoneNumber {
    @Override
    public String getOwnerName() {
        return "Друг";
    }
}
