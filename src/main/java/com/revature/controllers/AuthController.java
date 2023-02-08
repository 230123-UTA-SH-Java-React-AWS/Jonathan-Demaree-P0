package com.revature.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
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

        switch (httpVerb) {
            case "GET":
                loginRequest(exchange);
                break;
            case "POST":
                registerRequest(exchange);
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

        exchange.sendResponseHeaders(200, textBuilder.toString().getBytes().length);

        authService.loginUser(textBuilder.toString());

        OutputStream os = exchange.getResponseBody();

        os.write(textBuilder.toString().getBytes());
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

        exchange.sendResponseHeaders(200, textBuilder.toString().getBytes().length);

        authService.registerUser(textBuilder.toString());

        OutputStream os = exchange.getResponseBody();

        os.write(textBuilder.toString().getBytes());
        os.close();
    }
}
