package com.example.dialer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Contact implements Comparable<Contact> {
    private ArrayList<String> numbers;
    private String name;

    public ArrayList<String> getNumbers() {
        return numbers;
    }

    public String getName() {
        return name;
    }

    public Contact(String name, ArrayList<String> numbers){
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
