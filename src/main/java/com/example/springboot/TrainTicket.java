package com.example.springboot;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Email;

public class TrainTicket {
    private String from;
    private String to;
    
    @NotBlank(message = "First name cannot be blank")
    private String userFirstName;
    
    @NotBlank(message = "Last name cannot be blank")
    private String userLastName;
    
    @Email(message = "Invalid email format")
    private String userEmail;
    
    private double price;
    private String section;
    private int seat;

    // Constructors
    public TrainTicket() {
    }

    public TrainTicket(String from, String to, String userFirstName, String userLastName, String userEmail, double price) {
        this.from = from;
        this.to = to;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
        this.price = price;
    }
    
    public String toString() {
        return "TrainTicket{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", userFirstName='" + userFirstName + '\'' +
                ", userLastName='" + userLastName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", price=" + price +
                ", section='" + section + '\'' +
                ", seat=" + seat +
                '}';
    }
    // Getters and Setters
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSection() {
    	 return section != null ? section : "";
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }
}
