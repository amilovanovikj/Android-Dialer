package com.example.dialer;

import java.util.Comparator;
import java.util.Date;

class Call implements Comparable<Call> {
    private String number, duration, type, timestamp;

    public String getNumber() {
        return number;
    }

    public String getDuration() {
        return duration;
    }

    public String getType() {
        return type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Call(String number, String duration, String timestamp, String type) {
        this.number = number;
        this.duration = duration;
        this.timestamp = timestamp;
        this.type = type;
    }

    @Override
    public int compareTo(Call call) {
        return Comparator.comparing(Call::getTimestamp).reversed()
                .thenComparing(Call::getNumber)
                .compare(this, call);
    }

    @Override
    public String toString(){
        return String.format("%-15s%15d%15s", number, timestamp, duration);
    }
}