package com.example.dialer;

import java.util.Comparator;

class Call implements Comparable<Call> {
    private String number, duration, type;
    private long timestamp;

    public String getNumber() {
        return number;
    }

    public String getDuration() {
        return duration;
    }

    public String getType() {
        return type;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Call(String number, String duration, long timestamp, String type) {
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