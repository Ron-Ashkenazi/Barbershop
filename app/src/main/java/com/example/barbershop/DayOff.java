package com.example.barbershop;

public class DayOff {
    public String dayName;
    public int day;
    public int month;
    public int year;
    public String date;

    public DayOff(String dayName,int day, int month, int year, String date) {
        this.dayName = dayName;
        this.day = day;
        this.month = month;
        this.year = year;
        this.date = date;
    }

    public DayOff(String dayName, String date) {
        this.dayName = dayName;
        this.date = date;
    }

    public DayOff() {
    }
}
