package com.revature.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.App;
import com.revature.models.Ticket;
import com.revature.utils.ConnectionUtil;


public class TicketRepository {

    public Ticket Save(Ticket ticket) {

        ResultSet rs;
        String sql = "insert into tickets (amount, description, status, fk_user_id) values (?, ?, ?, ?) returning *";

        try (Connection con = ConnectionUtil.getConnection()) {

            PreparedStatement prstmt = con.prepareStatement(sql);
            prstmt.setInt(1, ticket.getAmount());
            prstmt.setString(2, ticket.getDesc());
            prstmt.setString(3, ticket.getStatus());
            prstmt.setInt(4, App.currUser.getUserId());

            rs = prstmt.executeQuery();

            if(rs.next()) {
                ticket.setTicketId(rs.getInt(1));
                return ticket;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }
    
    public boolean Update(Ticket ticket, String status) {
        return false;
    }

    public List<Ticket> getAllUserTickets() {
        ResultSet rs;
        List<Ticket> userTickets = new ArrayList<Ticket>();

        String sql = "select * from tickets where (fk_user_id) = (?)";

        try (Connection con = ConnectionUtil.getConnection()) {

            PreparedStatement prstmt = con.prepareStatement(sql);
            prstmt.setInt(1, App.currUser.getUserId());

            rs = prstmt.executeQuery();

            while (rs.next()) {
                Ticket newTicket = new Ticket();
                newTicket.setTicketId(rs.getInt(1));
                newTicket.setAmount(rs.getInt(2));
                newTicket.setUserId(rs.getInt(5));
                newTicket.setDesc(rs.getString(3));
                newTicket.setStatus(rs.getString(4));

                userTickets.add(newTicket);
            }
            return userTickets;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public List<Ticket> getAllPendingTickets() {
        ResultSet rs;
        List<Ticket> pendingTickets = new ArrayList<Ticket>();

        String sql = "select * from tickets where (status) = (?)";

        try (Connection con = ConnectionUtil.getConnection()) {

            PreparedStatement prstmt = con.prepareStatement(sql);
            prstmt.setString(1, "PENDING");

            rs = prstmt.executeQuery();
            
            while (rs.next()) {
                Ticket newTicket = new Ticket();
                newTicket.setTicketId(rs.getInt(1));
                newTicket.setAmount(rs.getInt(2));
                newTicket.setUserId(rs.getInt(5));
                newTicket.setDesc(rs.getString(3));
                newTicket.setStatus(rs.getString(4));

                pendingTickets.add(newTicket);
            }
            return pendingTickets;
            

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


}
