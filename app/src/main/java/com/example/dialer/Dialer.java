package com.example.dialer;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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

    public void readCalls(List<String> calls) throws ParseException {
        for(String line : calls) {
            String[] parts = line.split(" ");
            String number = parts[0];
            String date = parts[1];
            String time = parts[2];
            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date timestamp = sdf.parse(date + " " + time);
            String duration = parts[3];
            String type = parts[4];
            makeCall(number, timestamp, duration, type);
        }
    }

    public void makeCall(String number, Date timestamp, String duration, String type){
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
                .forEach(each -> pw.printf("%-15s%10s%15s%10s\n",
                        each.getNumber(), each.getTimestamp(), each.getDuration(), each.getType()));
        pw.flush();
    }

    public void printCallsByContact(OutputStream outputStream, Contact contact){
        PrintWriter pw = new PrintWriter(outputStream);
        contactMap.get(contact).forEach(call -> pw.println(contact.toString() + call.toString()));
        pw.flush();
    }

    public List<Call> getAllCalls(){
        return contactMap.values()
                .stream()
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(Call::getTimestamp))
                .collect(Collectors.toList());
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

    public Contact findContactInMap(String number) {
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
        /*if(!exceptionsList.isEmpty()){
            String message = "Exception for call from number " + call.getNumber() + ", reasons: ";
            throw new WrongCallException(message + exceptionsList.toString());
        }*/
    }

    public List<Contact> getContacts() {
        return new ArrayList<>(contactMap.keySet());
    }
}