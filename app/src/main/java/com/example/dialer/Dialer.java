package com.example.dialer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

class Dialer {
    private Map<Contact, TreeSet<Call>> contactMap;
    private Map<String, TreeSet<Call>> cachedCalls;

    public Dialer(List<Contact> contacts){
        contactMap = new TreeMap<>();
        cachedCalls = new HashMap<>();
        for (Contact c : contacts) {
            contactMap.put(c, new TreeSet<>());
        }
    }

    public Contact getContactWithNumber(String number){
        return contactMap.keySet()
                .stream()
                .filter(contact -> contact.getNumbers().contains(number))
                .findFirst()
                .orElse(new Contact("Unknown", Arrays.asList(number)));
    }

    public void readCalls(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;
        while((line = br.readLine()) != null) {
            String[] parts = line.split(" ");
            String number = parts[0];
            long timestamp = Long.parseLong(parts[1]);
            String duration = parts[2];
            String type = parts[3];
            makeCall(number, timestamp, duration, type);
        }
    }

    public void makeCall(String number, long timestamp, String duration, String type){
        Call call = new Call(number, duration, timestamp, type);
        try{
            checkForExceptions(call);
            Contact newContact = findContactInMap(number);
            addCallToCachedCalls(call);
            addCallToContactMap(call, newContact);
        }catch (WrongCallException e){
            System.out.println(e.getMessage());
        }
    }

    public void printCallsOfType(OutputStream outputStream, String type){
        PrintWriter pw = new PrintWriter(outputStream);
        cachedCalls.get(type)
                .forEach(each -> pw.printf("%-15s%10d%15s%10s\n",
                        each.getNumber(), each.getTimestamp(), each.getDuration(), each.getType()));
        pw.flush();
    }

    public void printCallsByContact(OutputStream outputStream, Contact contact){
        PrintWriter pw = new PrintWriter(outputStream);
        contactMap.get(contact).forEach(call -> pw.println(contact.toString() + call.toString()));
        pw.flush();
    }

    public void printAllCalls(OutputStream outputStream){
        contactMap.keySet().forEach(contact -> printCallsByContact(outputStream, contact));
    }

    private void addCallToContactMap(Call call, Contact newContact) {
        contactMap.putIfAbsent(newContact, new TreeSet<>());
        contactMap.get(newContact).add(call);
    }

    private void addCallToCachedCalls(Call call) {
        cachedCalls.putIfAbsent(call.getType(), new TreeSet<>(Comparator.comparing(Call::getDuration)));
        cachedCalls.computeIfPresent(call.getType(), (callType, set) -> {
            set.add(call);
            return set;
        });
    }

    private Contact findContactInMap(String number) {
        Contact newContact = contactMap.keySet()
                .stream()
                .filter(contact -> contact.getNumbers().contains(number))
                .findFirst()
                .orElse(null);
        if(newContact == null) {
            newContact = contactMap.keySet()
                    .stream()
                    .filter(c -> c.getName().equals("Unknown"))
                    .findFirst()
                    .orElse(new Contact("Unknown", new ArrayList<>()));
        }
        return newContact;
    }

    private void checkForExceptions(Call call) throws WrongCallException {
        List<String> exceptionsList = new ArrayList<>();
        if(call.getNumber().length() != 9){
            exceptionsList.add("Number must be 9 characters long");
        }
        if(!call.getNumber().startsWith("07")){
            exceptionsList.add("Number must start on 07");
        }
        if(call.getDuration().split(":").length != 3){
            exceptionsList.add("Wrong duration format " + call.getDuration());
        }
        if(!exceptionsList.isEmpty()){
            String message = "Exception for call from number " + call.getNumber() + ", reasons: ";
            throw new WrongCallException(message + exceptionsList.toString());
        }
    }
}