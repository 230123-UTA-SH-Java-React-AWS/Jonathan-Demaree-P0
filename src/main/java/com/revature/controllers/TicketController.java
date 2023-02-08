package com.revature.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.revature.App;
import com.revature.models.User.Role;
import com.revature.repositories.TicketRepository;
import com.revature.services.TicketService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class TicketController implements HttpHandler {

    private final TicketService ticketService = new TicketService(new TicketRepository());

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String httpVerb = exchange.getRequestMethod();
        if(App.currUser != null ) {
            switch (httpVerb) {
                case "GET":
                    if (App.currUser.getRole() == Role.MANAGER) {
                        getPendingTickets(exchange);
                    } 
                    else {
                        getUserTickets(exchange);
                    }
                    break;
                case "POST":
                        createTicket(exchange);
                    break;
                case "PUT":
                    if (App.currUser.getRole() == Role.MANAGER) {
                        processTicket(exchange);
                    }
                default:
                    String someResponse = "HTTP Verb not supported; employees use GET or POST, managers use GET or PUT.";

                    exchange.sendResponseHeaders(404, someResponse.getBytes().length);

                    OutputStream os = exchange.getResponseBody();
                    os.write(someResponse.getBytes());
                    os.close();
                    break;
            }
        } else {
            System.out.println("You must be logged in to complete this action.");
        }
    }

    public void getPendingTickets(HttpExchange exchange) throws IOException
    {
        StringBuilder textBuilder = new StringBuilder();
        String response = "";

        

        exchange.sendResponseHeaders(200, textBuilder.toString().getBytes().length);

        response = ticketService.getPendingTickets();
        System.out.println(response);
        
        OutputStream os = exchange.getResponseBody();

        os.write(textBuilder.toString().getBytes());
        os.close();
    }

    public void getUserTickets(HttpExchange exchange) throws IOException
    {
        StringBuilder textBuilder = new StringBuilder();
        String response = "";

        

        exchange.sendResponseHeaders(200, textBuilder.toString().getBytes().length);

        response = ticketService.getUserTickets();
        System.out.println(response);

        OutputStream os = exchange.getResponseBody();

        os.write(textBuilder.toString().getBytes());
        os.close();
    }

    public void createTicket(HttpExchange exchange) throws IOException
    {
        InputStream input = exchange.getRequestBody();
        StringBuilder textBuilder = new StringBuilder();
        String response = "";

        try (Reader r = new BufferedReader(new InputStreamReader(input, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int charIndex;

            while ((charIndex = r.read()) != -1) {
                textBuilder.append((char) charIndex);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        exchange.sendResponseHeaders(200, textBuilder.toString().getBytes().length);

        response = ticketService.saveTicket(textBuilder.toString());
        System.out.println(response);

        OutputStream os = exchange.getResponseBody();

        os.write(textBuilder.toString().getBytes());
        os.close();
    }
    
    public void processTicket(HttpExchange exchange) throws IOException
    {
        String someResponse = "You selected the put response!";

        // Add logic to send new ticket
        exchange.sendResponseHeaders(200, someResponse.getBytes().length);

        OutputStream os = exchange.getResponseBody();
        os.write(someResponse.getBytes());
        os.close();
    }
    
}