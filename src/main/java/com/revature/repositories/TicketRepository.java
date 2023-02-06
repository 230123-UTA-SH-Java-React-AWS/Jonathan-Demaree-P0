package com.revature.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import com.revature.models.Ticket;
import com.revature.utils.ConnectionUtil;


public class TicketRepository {

    public boolean Save(Ticket ticket) {

        String sql = "insert into tickets (amount, desc, userEmail) values (?, ?, ?, ?, ?)";

        try (Connection con = ConnectionUtil.getConnection()) {

            PreparedStatement prstmt = con.prepareStatement(sql);
            prstmt.setInt(1, ticket.getAmount());
            prstmt.setString(2, ticket.getDesc());
            prstmt.setString(3, ticket.getUserEmail());
            prstmt.setString(4, ticket.getStatus());

            //execute() method does not expect to return anything from the statement
            //executeQuery() method does expect something to result after executing the statement
            return prstmt.execute();

        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }

        return false;
    }
    
    public boolean Update(Ticket ticket) {

    }

    public List<Ticket> getAllUserTickets() {
        return null;
    }


}
