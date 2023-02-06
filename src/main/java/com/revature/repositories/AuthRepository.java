package com.revature.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.revature.models.User;
import com.revature.utils.ConnectionUtil;


public class AuthRepository {

    public boolean Login(String email, String password) {

        String sql = "select exists(select 1 from users where email = ? and password = ?)";

        try (Connection con = ConnectionUtil.getConnection()) {
 
            PreparedStatement prstmt = con.prepareStatement(sql);
            prstmt.setString(1, email);
            prstmt.setString(2, password);

            return prstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean Register(User user) {

        String sql = "insert into employee (fname, lname, email, password, role) values (?, ?, ?, ?, ?) where exists (email) do nothing;";
        ResultSet rs;

         try (Connection con = ConnectionUtil.getConnection()) {
 
            PreparedStatement prstmt = con.prepareStatement(sql);

            prstmt.setString(1, user.getFName());
            prstmt.setString(2, user.getLName());
            prstmt.setString(3, user.getEmail());
            prstmt.setString(4, user.getPassword());
            prstmt.setString(5, user.getRole().toString());

            rs = prstmt.executeQuery();
            System.out.println(rs);

            if (rs.next()) {
                System.out.println(rs.next());
                return true;
            } else {
                System.out.println("New user was not registered");
            }


        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
        return false;
    }
    
}
