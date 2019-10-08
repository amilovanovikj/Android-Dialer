package com.example.dialer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

class Call implements Comparable<Call> {
    private String number, duration, type;
    Date timestamp;

    public String getNumber() {
        return number;
    }

    public String getDuration() {
        return duration;
    }

    public String getType() {
        return type;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Call(String number, String duration, Date timestamp, String type) {
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
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return String.format("%-15s%15s%15s", number, sdf.format(timestamp), duration);
    }
}