package com.revature.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.postgresql.util.PSQLException;

import com.revature.App;
import com.revature.models.User;
import com.revature.models.User.Role;
import com.revature.utils.ConnectionUtil;


public class AuthRepository {

    public User Login(String email, String password) {

        User user = new User();
        ResultSet rs;
        String sql = "select * from users where email = ? and user_password = ?;";

        try (Connection con = ConnectionUtil.getConnection()) {
 
            PreparedStatement prstmt = con.prepareStatement(sql);
            prstmt.setString(1, email);
            prstmt.setString(2, password);
            rs = prstmt.executeQuery();
            while (rs.next()) {
                user.setUserId(rs.getInt(1));
                user.setFName(rs.getString(2));
                user.setLName(rs.getString(3));
                user.setEmail(rs.getString(4));
                user.setPassword(rs.getString(6));
                String roleName = rs.getString(5);
                if (roleName.equals(Role.MANAGER.toString())) {
                    user.setRole(Role.MANAGER);
                } else {
                    user.setRole(Role.EMPLOYEE);
                }
                
            }

            App.currUser = user;

            return user;

        } catch (Exception e) {
            System.out.println("Failed to connect to DB");
            e.printStackTrace();
        }

        return null;
    }

    public User Register(User user) {

        String sql = "insert into users (first_name, last_name, email, user_password, user_role) values (?, ?, ?, ?, ?) returning *";
        ResultSet rs = null;

         try (Connection con = ConnectionUtil.getConnection()) {
 
             PreparedStatement prstmt = con.prepareStatement(sql);

            prstmt.setString(1, user.getFName());
            prstmt.setString(2, user.getLName());
            prstmt.setString(3, user.getEmail());
            prstmt.setString(4, user.getPassword());
            prstmt.setString(5, user.getRole().toString());
            try {
                rs = prstmt.executeQuery();
            } catch (PSQLException e) {
                System.out.println("Email is already in use.");
            }

            if (rs == null) {
                return null;
            };
            
            if (rs.getInt(1) > 0) {
                user.setUserId(rs.getInt(1));
                
                return user;
            } else {
                System.out.println("New user was not registered.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
