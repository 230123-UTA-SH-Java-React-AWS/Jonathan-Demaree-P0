package com.revature;

//import controllers.*;

import java.io.IOException;
import java.net.InetSocketAddress;

import java.sql.*;

import com.revature.controllers.*;
import com.revature.models.User;
import com.sun.net.httpserver.HttpServer;


/**
 * Hello world!
 */
public final class App {

    public static User currUser;


    private App() {
    }
    

    public static void main(String[] args) throws IOException, SQLException {
        System.out.println("Running");

        currUser = null;


 
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/auth", new AuthController());
        server.createContext("/ticket", new TicketController());

        server.setExecutor(null);
        server.start();
    }



    
}
