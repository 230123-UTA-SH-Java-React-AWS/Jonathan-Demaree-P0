package com.revature.services;

import java.io.IOException;

import com.revature.App;
import com.revature.models.User;
import com.revature.repositories.AuthRepository;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


public class UserService {

    private final ObjectMapper mapper = new ObjectMapper();
    private final AuthRepository authrepo;

    public UserService(AuthRepository authrepo) {
        this.authrepo = authrepo;
    }


    public void saveUser(String userJson) {
        boolean success;
        try {
            User newUser = mapper.readValue(userJson, User.class);
            success = authrepo.Register(newUser);

            if (success) {
                App.currUser = newUser;
                System.out.println("Registration succesful!");
                System.out.println("User information: ");
            }

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

    // public User getUser(String email) {
    //     User currentUser = null;
    //     try {
            
    //     }
    // }

}