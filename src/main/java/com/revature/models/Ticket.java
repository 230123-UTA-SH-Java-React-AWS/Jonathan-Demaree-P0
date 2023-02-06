package com.revature.models;

import com.revature.App;

public class Ticket {
    private int ticketId;
    private int amount;
    
    private String desc;
    private String userEmail;
    private String status;

    public Ticket(int ticketAmount, String ticketDesc, String userEmail) {
        this.amount = ticketAmount;
        this.desc = ticketDesc;
        this.userEmail = App.currUser.getEmail();
        this.status = "PENDING";
    }

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

    public boolean setStatus(String ticketStatus) {
        boolean changed = false;
        if(status == "PENDING") {
            switch (ticketStatus) {
                case "PENDING":
                    System.out.println("Ticket skipped for later review.");
                    break;
                case "APPROVED":
                    this.status = "APPROVED";
                    changed = true;
                    System.out.println("Ticket has been approved.");
                    break;
                case "DENIED":
                    this.status = "DENIED";
                    changed = true;
                    System.out.println("Ticket has been denied.");
                default:
                    System.out.println("Invalid ticket status change request.");
            }
        } else {
            System.out.println("Ticket has already been processed.");
        }
        return changed;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String ticketDesc) {
        this.desc = ticketDesc;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String email) {
        this.userEmail = email;
    }
    

}
