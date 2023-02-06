package com.revature.controllers;

import java.io.IOException;
import java.io.OutputStream;

import com.revature.App;
import com.revature.models.User.Role;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class TicketController implements HttpHandler {

    // @Override
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
                    String someResponse = "HTTP Verb not supported; employees use GET, managers use POST.";

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
        String someResponse = "You selected the get response!";
        
        // Add logic to get all previous submitted tickets from user
        exchange.sendResponseHeaders(200, someResponse.getBytes().length);

        OutputStream os = exchange.getResponseBody();
        os.write(someResponse.getBytes());
        os.close();
    }

    public void getUserTickets(HttpExchange exchange) throws IOException
    {
        String someResponse = "You selected the put response!";


        // Add logic to send new ticket
        exchange.sendResponseHeaders(200, someResponse.getBytes().length);

        OutputStream os = exchange.getResponseBody();
        os.write(someResponse.getBytes());
        os.close();
    }

    public void createTicket(HttpExchange exchange) throws IOException
    {
        String someResponse = "You selected the put response!";

        // Add logic to send new ticket
        exchange.sendResponseHeaders(200, someResponse.getBytes().length);

        OutputStream os = exchange.getResponseBody();
        os.write(someResponse.getBytes());
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