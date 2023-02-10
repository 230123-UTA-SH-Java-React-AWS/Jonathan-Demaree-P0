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

import com.revature.repositories.AuthRepository;
import com.revature.services.AuthService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class AuthController implements HttpHandler {

    private final AuthService authService = new AuthService(new AuthRepository());

    @Override
    public void handle(HttpExchange exchange) throws IOException {
  
        String httpVerb = exchange.getRequestMethod();
        URI uri = exchange.getRequestURI();
        String s = uri.getPath();

        switch (httpVerb) {
            case "GET":
                if (s.equals("/auth/login")) {
                    loginRequest(exchange);
                }         
                break;
            case "POST":
                if (s.equals("/auth/register")) {
                    registerRequest(exchange);
                }   
                break;
            default:
                String response = "HTTP Verb not supported; use GET to login or POST to register.";

                exchange.sendResponseHeaders(404, response.getBytes().length);

                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
                break;
        }
    }

    public void loginRequest(HttpExchange exchange) throws IOException {
        InputStream input = exchange.getRequestBody();
        StringBuilder textBuilder = new StringBuilder();

        try (Reader r = new BufferedReader(new InputStreamReader(input, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int charIndex;

            while ((charIndex = r.read()) != -1) {
                textBuilder.append((char) charIndex);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        OutputStream os = exchange.getResponseBody();

        String s = authService.loginUser(textBuilder.toString());
        byte[] output = s.getBytes(Charset.forName("UTF-8"));


        if(!s.toUpperCase().contains("ERROR:")) {
            exchange.sendResponseHeaders(200, output.length);
        } else {
            exchange.sendResponseHeaders(400, output.length);
        }
        
        os.write(output);
        os.close();
    }
    

    public void registerRequest(HttpExchange exchange) throws IOException
    {
        InputStream input = exchange.getRequestBody();
        StringBuilder textBuilder = new StringBuilder();

        try (Reader r = new BufferedReader(new InputStreamReader(input, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int charIndex;

            while ((charIndex = r.read()) != -1) {
                textBuilder.append((char) charIndex);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String s = authService.registerUser(textBuilder.toString());
        byte[] output = s.getBytes(Charset.forName("UTF-8"));

        OutputStream os = exchange.getResponseBody();

        if(!s.toUpperCase().contains("ERROR:")) {
            exchange.sendResponseHeaders(200, output.length);
        } else {
            exchange.sendResponseHeaders(400, output.length);
        }

        os.write(output);
        os.close();
    }
}
