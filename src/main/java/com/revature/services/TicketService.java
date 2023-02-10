package com.revature.services;

import java.io.IOException;
import java.util.List;

import com.jayway.jsonpath.JsonPath;
import com.revature.App;
import com.revature.models.Ticket;
import com.revature.repositories.TicketRepository;

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

        try {
            Ticket newTicket = mapper.readValue(ticketJson, Ticket.class);
            newTicket.setUserId(App.currUser.getUserId());
            newTicket.setStatus("PENDING");

            if (newTicket.getDesc().isEmpty()) {
                return "Error: Description must not be empty.";
            }
            if (newTicket.getAmount() <= 0) {
                return "Error: Amount must be greater than 0.";
            }
            newTicket = ticketrepo.Save(newTicket);

            if (newTicket != null) {
                jsonString = mapper.writeValueAsString(newTicket);
                return "Ticket created: " + jsonString;
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
        return "Error: Check your ticket request data.";
    }

    public String getUserTickets() {
        List<Ticket> listOfTickets = ticketrepo.getAllUserTickets();
        String jsonString = "";

        listOfTickets = ticketrepo.getAllUserTickets();

        if (listOfTickets != null) {
            try {
                jsonString = mapper.writeValueAsString(listOfTickets);
            } catch (IOException e) {
                e.printStackTrace();
                return "Error: Something failed.";
            }
            return jsonString;
        }
        return "Error: Invalid ticket data was provided or no tickets were found for you.";
    }
    
    public String getSortedUserTickets(String sortBy) {
        String jsonString = "";
        String filter = JsonPath.read(sortBy, "$.filter");

        List<Ticket> listOfTickets = ticketrepo.getSortedUserTickets(filter);      
        
        if (listOfTickets != null) {
            try {
                jsonString = mapper.writeValueAsString(listOfTickets);
            } catch (IOException e) {
                e.printStackTrace();
                return "Error: Something failed when parsing your ticket list.";
            }
            return jsonString;
        } 
        return "Error: Invalid ticket data was provided or no tickets were found for you.";
    }

    public String processTicket(String input) {
        int ticketId = JsonPath.read(input, "$.ticketId");
        String newStatus = JsonPath.read(input, "$.status");
        String processResults = "";
        
        try {
            processResults = ticketrepo.Update(ticketId, newStatus);

        } catch (Exception e) {
            System.out.println("Unable to parse JSON data provided.");
            e.printStackTrace();
        }
        return processResults;
    }

    public String getPendingTickets() {
        List<Ticket> listOfTickets = ticketrepo.getAllUserTickets();
        String jsonString = "";

        listOfTickets = ticketrepo.getAllPendingTickets();

        if (!listOfTickets.isEmpty()) {
            try {
                jsonString = mapper.writeValueAsString(listOfTickets);
            } catch (IOException e) {
                e.printStackTrace();
                return "Something failed.";
            }
            return jsonString;
        } 
        return "Error: no pending tickets were found for you.";
    }

}