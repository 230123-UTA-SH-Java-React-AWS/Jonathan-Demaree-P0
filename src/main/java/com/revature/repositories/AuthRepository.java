package com.revature.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.postgresql.util.PSQLException;

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
                user.setUserId(rs.getInt("user_id"));
                user.setEmail(rs.getString("email"));
                String role = rs.getString("user_role");
                if(role.equals("MANAGER")) {
                    user.setRole(Role.MANAGER);
                } else {
                    user.setRole(Role.EMPLOYEE);
                }
                user.setPassword(rs.getString("password"));

            }
            
            return user;

        } catch (Exception e) {
            System.out.println("Failed to connect to DB");
            e.printStackTrace();
        }

        return null;
    }

    public User Register(User user) {

        String sql = "insert into users (email, user_password, user_role) values (?, ?, ?) returning *";
        ResultSet rs = null;

        try (Connection con = ConnectionUtil.getConnection()) {

            PreparedStatement prstmt = con.prepareStatement(sql);

            prstmt.setString(1, user.getEmail());
            prstmt.setString(2, user.getPassword());
            prstmt.setString(3, user.getRole().toString());

            try {
                rs = prstmt.executeQuery();
            } catch (PSQLException e) {
                System.out.println("Email is already in use.");
            }

            if (rs == null) {
                return null;
            };

            if (rs.next()) {
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
