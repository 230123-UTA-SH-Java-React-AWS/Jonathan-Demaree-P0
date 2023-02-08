package com.revature.services;

import java.io.IOException;
import java.util.List;

import com.revature.App;
import com.revature.models.Ticket;
import com.revature.models.User;
import com.revature.repositories.TicketRepository;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


public class TicketService {

    private final ObjectMapper mapper = new ObjectMapper();
    
    private final TicketRepository ticketrepo;

    public TicketService(TicketRepository ticketrepo) {
        this.ticketrepo = ticketrepo;
    }


    public String saveTicket(String ticketJson) {
        String jsonString = "";

        if (App.currUser == null) {
            return "Please login before attempting to create a ticket.";
        }

        try {
            Ticket newTicket = mapper.readValue(ticketJson, Ticket.class);
            newTicket.setUserId(App.currUser.getUserId());
            newTicket.setStatus("PENDING");
            System.out.println(newTicket.getStatus());
            newTicket = ticketrepo.Save(newTicket);

            if (newTicket != null) {
                jsonString = mapper.writeValueAsString(newTicket);
                return jsonString;
            }          
        } catch (JsonParseException e) {
            System.out.println("Unable to parse JSON data provided.");
            e.printStackTrace();
        } catch (JsonMappingException e) {
            System.out.println("Unable to map JSON to Java object.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("JSON input was invalid or not found.");
            e.printStackTrace();
        }
        return "Invalid ticket data was provided";
    }

    public String getUserTickets() {
        List<Ticket> listOfTickets = ticketrepo.getAllUserTickets();
        String jsonString = "";

        if (App.currUser == null) {
            return "Please login before attempting to create a ticket.";
        }

        listOfTickets = ticketrepo.getAllUserTickets();

        if (listOfTickets != null) {
            try {
                jsonString = mapper.writeValueAsString(listOfTickets);
            } catch (IOException e) {
                e.printStackTrace();
                return "Something failed.";
            }
            return jsonString;
        } 
        return "Invalid ticket data was provided or no tickets were found for you.";
    }

    public void processTicket(Ticket ticket, String newStatus) {
        try {
            ticketrepo.Update(ticket, newStatus);

        } catch (Exception e) {
            System.out.println("Unable to parse JSON data provided.");
            e.printStackTrace();
        }
    }

    public String getPendingTickets() {
        List<Ticket> listOfTickets = ticketrepo.getAllUserTickets();
        String jsonString = "";

        if (App.currUser == null) {
            return "Please login before attempting to create a ticket.";
        }

        listOfTickets = ticketrepo.getAllPendingTickets();

        if (listOfTickets != null) {
            try {
                jsonString = mapper.writeValueAsString(listOfTickets);
            } catch (IOException e) {
                e.printStackTrace();
                return "Something failed.";
            }
            return jsonString;
        } 
        return "Invalid ticket data was provided or no pending tickets were found for you.";
    }

}