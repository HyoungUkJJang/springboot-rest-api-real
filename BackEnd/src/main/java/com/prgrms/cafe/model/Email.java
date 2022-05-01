package com.prgrms.cafe.model;

import java.util.Objects;
import java.util.regex.Pattern;
import org.springframework.util.Assert;

public class Email {

    private final String address;

    public Email(String address) {
        Assert.notNull(address,"이메일 주소값은 필수입니다.");
        Assert.isTrue(address.length()>=4 && address.length()<=50,
            "이메일은 4~50글자 사이여야 합니다.");
        Assert.isTrue(checkAddress(address), "이메일 형식이 유효하지 않습니다.");
        this.address = address;
    }

    private static boolean checkAddress(String address) {
        return Pattern.matches("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b", address);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Email email = (Email) o;
        return Objects.equals(address, email.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }

    @Override
    public String toString() {
        return "Email{" +
            "address='" + address + '\'' +
            '}';
    }

    public String getAddress() {
        return address;
    }

}
