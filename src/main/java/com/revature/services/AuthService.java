package com.revature.services;

import java.io.IOException;

import com.jayway.jsonpath.JsonPath;
import com.revature.App;
import com.revature.models.User;
import com.revature.models.User.Role;
import com.revature.repositories.AuthRepository;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


public class AuthService {

    private final ObjectMapper mapper = new ObjectMapper();
    private final AuthRepository authrepo;

    public AuthService(AuthRepository authrepo) {
        this.authrepo = authrepo;
    }

    public String loginUser(String userData) throws JsonGenerationException, JsonMappingException, IOException {
        String jsonString = "";
        String email = JsonPath.read(userData, "$.email");
        String password = JsonPath.read(userData, "$.password");

        if (App.currUser == null) {
            User user = authrepo.Login(email, password);
            System.out.println("user after logging in: " + user);
            if (user != null)
                jsonString = mapper.writeValueAsString(user);
                return jsonString;
        } else {
            System.out.println("Must log out current user first.");
            logoutUser();
        }
        return null;
    }

    

    public String registerUser(String userJson) {
        String jsonString = "";
        
        if (App.currUser == null) {
            try {
                User newUser = mapper.readValue(userJson, User.class);

                if (newUser.getRole() == null) {
                    newUser.setRole(Role.EMPLOYEE);
                }

                newUser = authrepo.Register(newUser);

                if (newUser != null) {
                    App.currUser = newUser;
                    jsonString = mapper.writeValueAsString(newUser);
                    return jsonString;
                } else {
                    return "Registration failed";
                }
            } catch (JsonParseException e) {
                e.printStackTrace();
                System.out.println("Could not parse Json");
            } catch (JsonMappingException e) {
                e.printStackTrace();
                System.out.println("Could not map object.");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Input invalid.");
            }
        } else {
            System.out.println("Must log out current user first.");
            logoutUser();
        }
        return null;
    }

    public void logoutUser() {
        App.currUser = null;
    }

}