package com.revature.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.revature.repositories.AuthRepository;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class AuthController implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
  
        String httpVerb = exchange.getRequestMethod();

        switch (httpVerb) {
            case "GET":
                loginRequest(exchange);
                break;
            case "POST":
                registerRequest(exchange);
            default:
                String response = "HTTP Verb not supported; use GET to login or POST to register.";

                exchange.sendResponseHeaders(404, response.getBytes().length);

                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
                break;
        }
    }

    public void loginRequest(HttpExchange exchange) throws IOException
    {
        String response = "Login request chosen!";
        AuthRepository authRepo = new AuthRepository();

        InputStream input = exchange.getRequestBody();

        

        exchange.sendResponseHeaders(200, response.getBytes().length);

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public void registerRequest(HttpExchange exchange) throws IOException
    {
        String response = "Register request chosen!";
        AuthRepository authRepo = new AuthRepository();

        InputStream input = exchange.getRequestBody();

        exchange.sendResponseHeaders(200, response.getBytes().length);

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
