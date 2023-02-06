package com.revature.services;

import java.io.IOException;

import com.revature.App;
import com.revature.models.User;
import com.revature.repositories.AuthRepository;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/*
    The service layer is responsible for hold behavior driven classes
    It might have further validation or information process within the service
*/
public class AuthService {

    private final ObjectMapper mapper = new ObjectMapper();
    private final AuthRepository authrepo;

    public AuthService(AuthRepository authrepo) {
        this.authrepo = authrepo;
    }


    public void loginUser(String email, String password) {
        authrepo.Login(email, password);
    }

    public void registerUser(String userJson) {
        try {
            User user = mapper.readValue(userJson, User.class);

            User newUser = authrepo.Register(user);
            App.currUser = newUser;

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

    public void logoutUser() {
        App.currUser = null;
    }

}