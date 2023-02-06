package com.revature.services;

import java.io.IOException;
import java.util.List;

import com.revature.models.Ticket;
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


    public void saveTicket(String ticketJson)
    {
        try {
            Ticket newTicket = mapper.readValue(ticketJson, Ticket.class);
            ticketrepo.Save(newTicket);

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
    }

    //Converting List into 
    public String getUserTickets()
    {
        List<Ticket> listOfTickets = ticketrepo.getAllUserTickets();
        String jsonString = "";

        try {
            jsonString = mapper.writeValueAsString(listOfTickets);

        } catch (JsonGenerationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonString;
    }

    public void processTicket(Ticket ticket, String newStatus)
    {
        try {
            ticketrepo.Update(ticket, newStatus);

        } catch (Exception e) {
            System.out.println("Unable to parse JSON data provided.");
            e.printStackTrace();
        }
    }

    public void findPokemon()
    {

    }
}