package com.example.dialer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Contact implements Comparable<Contact> {
    private List<String> numbers;
    private String name;

    public List<String> getNumbers() {
        return numbers;
    }

    public String getName() {
        return name;
    }

    public Contact(String name, List<String> numbers){
        this.name = name;
        this.numbers = numbers;
    }

    @Override
    public int compareTo(Contact contact) {
        return Comparator.comparing(Contact::getName).compare(this, contact);
    }

    @Override
    public String toString(){
        return String.format("%-20s", name);
    }
}
