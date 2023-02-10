package com.revature.models;

import com.revature.App;

public class Ticket {
    private int ticketId;
    private int amount;
    private int userId;
    
    private String desc;
    private String status;

    public Ticket(int ticketAmount, String ticketDesc) {
        this.amount = ticketAmount;
        this.desc = ticketDesc;
        this.userId = App.currUser.getUserId();
        this.status = "PENDING";
    }

    public Ticket(){}

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int ticketAmount) {
        this.amount = ticketAmount;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String ticketStatus) {
        switch (ticketStatus) {
            case "PENDING":
                this.status = "PENDING";
                break;
            case "APPROVED":
                this.status = "APPROVED";
                break;
            case "DENIED":
                this.status = "DENIED";
                break;
            default:
                System.out.println("Invalid ticket status request.");
        }
    }
    

    public String getDesc() {
        return desc;
    }

    public void setDesc(String ticketDesc) {
        this.desc = ticketDesc;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    

}
