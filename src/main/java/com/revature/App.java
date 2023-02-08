package com.revature;

//import controllers.*;

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

        server.createContext("/auth", new AuthController());
        server.createContext("/ticket", new TicketController());

        server.setExecutor(null);
        server.start();
        System.out.println("Server online");
    }



    
}
