package com.revature;

import java.io.IOException;
import java.net.InetSocketAddress;

import java.sql.*;

import com.revature.controllers.*;
import com.revature.models.User;
import com.sun.net.httpserver.HttpServer;


public final class App {

    public static User currUser;


    private App() {
    }
    

    public static void main(String[] args) throws IOException, SQLException {

        currUser = null;
 
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/auth/register", new AuthController());
        server.createContext("/auth/login", new AuthController());
        server.createContext("/auth/logout", new AuthController());
        server.createContext("/ticket/getUserTickets", new TicketController());
        server.createContext("/ticket/getSortedUserTickets", new TicketController());
        server.createContext("/ticket/createTicket", new TicketController());
        server.createContext("/ticket/getPendingTickets", new TicketController());
        server.createContext("/ticket/processTicket", new TicketController());

        server.setExecutor(null);
        server.start();
        System.out.println("Server online");
    }



    
}
