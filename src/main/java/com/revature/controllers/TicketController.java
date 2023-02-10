package com.revature.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.URI;
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
        URI uri = exchange.getRequestURI();
        String s = uri.getPath();
                

        if (App.currUser != null) {
            Role role = App.currUser.getRole();

            switch (httpVerb) {
                case "GET":
                    if (role == Role.MANAGER) {
                        if (s.equals("/ticket/getPendingTickets")) {
                            getPendingTickets(exchange);
                        } else {
                            String response = "Invalid endpoint for manager access.";

                            exchange.sendResponseHeaders(403, response.getBytes().length);
    
                            OutputStream os = exchange.getResponseBody();
                            os.write(response.getBytes());
                            os.close();
                            break;
                        }
                    }
                    if (role == Role.EMPLOYEE) {
                        if (s.equals("/ticket/getUserTickets")){
                            getUserTickets(exchange);
                        } else if (s.equals("/ticket/getSortedUserTickets")) {
                            getSortedUserTickets(exchange);
                        } else {
                            String response = "Invalid endpoint for employee access.";

                            exchange.sendResponseHeaders(403, response.getBytes().length);
    
                            OutputStream os = exchange.getResponseBody();
                            os.write(response.getBytes());
                            os.close();
                            break;
                        }
                    }
                case "POST":
                    if (role == Role.EMPLOYEE) {
                        if (s.equals("/ticket/createTicket")) {
                            createTicket(exchange);
                        }
                    } else {
                        String response = "Invalid endpoint for Manager access.";

                        exchange.sendResponseHeaders(403, response.getBytes().length);

                        OutputStream os = exchange.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                    }
                    
                    break;
                case "PUT":
                    if (role == Role.MANAGER) {
                        if (s.equals("/ticket/processTicket")) {
                            processTicket(exchange);
                        }
                    } else {
                        String response = "Only managers can access this endpoint.";

                        exchange.sendResponseHeaders(403, response.getBytes().length);

                        OutputStream os = exchange.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                        break;
                    }
                default:
                    String someResponse = "END POINT NOT FOUND.";

                    exchange.sendResponseHeaders(403, someResponse.getBytes().length);

                    OutputStream os = exchange.getResponseBody();
                    os.write(someResponse.getBytes());
                    os.close();
                    break;
            }
        } else {
            String forbid = "Error - Forbidden from accessing. Must log in first.";

            exchange.sendResponseHeaders(401, forbid.getBytes().length);

            OutputStream os = exchange.getResponseBody();
            os.write(forbid.getBytes());
            os.close();
        }
    }

    public void getPendingTickets(HttpExchange exchange) throws IOException
    {
        String response = "";

        response = ticketService.getPendingTickets();
        byte[] output = response.getBytes(Charset.forName("UTF-8"));

        if(!response.toUpperCase().contains("ERROR")) {
            exchange.sendResponseHeaders(200, output.length);
        } else {
            exchange.sendResponseHeaders(400, output.length);
        }
        
        OutputStream os = exchange.getResponseBody();

        os.write(response.getBytes());
        os.close();
    }

    public void getUserTickets(HttpExchange exchange) throws IOException
    {
        String response = "";

        response = ticketService.getUserTickets();
        byte[] output = response.getBytes(Charset.forName("UTF-8"));

        if(!response.toUpperCase().contains("ERROR:")) {
            exchange.sendResponseHeaders(200, output.length);
        } else {
            exchange.sendResponseHeaders(400, output.length);
        }

        OutputStream os = exchange.getResponseBody();

        os.write(output);
        os.close();
    }
    
    public void getSortedUserTickets(HttpExchange exchange) throws IOException
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

        String userInput = textBuilder.toString();

        response = ticketService.getSortedUserTickets(userInput);

        byte[] output = response.getBytes(Charset.forName("UTF-8"));

        if(!response.toUpperCase().contains("ERROR:")) {
            exchange.sendResponseHeaders(200, output.length);
        } else {
            exchange.sendResponseHeaders(400, output.length);
        }

        OutputStream os = exchange.getResponseBody();

        os.write(output);
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


        response = ticketService.saveTicket(textBuilder.toString());
   
        byte[] output = response.getBytes(Charset.forName("UTF-8"));

        if(!response.toUpperCase().contains("ERROR:")) {
            exchange.sendResponseHeaders(200, output.length);
        } else {
            exchange.sendResponseHeaders(400, output.length);
        }

        OutputStream os = exchange.getResponseBody();

        os.write(output);
        os.close();
    }
    
    public void processTicket(HttpExchange exchange) throws IOException
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

        response = ticketService.processTicket(textBuilder.toString());

        byte[] output = response.getBytes(Charset.forName("UTF-8"));

        if(!response.toUpperCase().contains("ERROR:")) {
            exchange.sendResponseHeaders(200, output.length);
        } else {
            exchange.sendResponseHeaders(400, output.length);
        }

        OutputStream os = exchange.getResponseBody();

        os.write(output);
        os.close();
    }
    
}