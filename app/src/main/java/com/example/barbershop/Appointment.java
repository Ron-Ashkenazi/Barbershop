package com.example.barbershop;

public class Appointment {
    public String appointmentID;
    public String userEmailID;
    public String barberEmailID;
    public String userName;
    public String barberName;
    public String dayName;
    public String date;
    public String time;
    public int dayOfMonth;
    public int month;
    public int year;

    public Appointment(String appointmentID, String userEmailID, String barberEmailID,
                       String userName, String barberName, String dayName,
                       String date, String time, int dayOfMonth, int month, int year) {
        this.appointmentID = appointmentID;
        this.userEmailID = userEmailID;
        this.barberEmailID = barberEmailID;
        this.userName = userName;
        this.barberName = barberName;
        this.dayName = dayName;
        this.date = date;
        this.time = time;
        this.dayOfMonth = dayOfMonth;
        this.month = month;
        this.year = year;
    }

    public Appointment() {

    }

    public Appointment(String userName, String dayName, String date, String time) {
        this.userName = userName;
        this.dayName = dayName;
        this.date = date;
        this.time = time;
    }

}
