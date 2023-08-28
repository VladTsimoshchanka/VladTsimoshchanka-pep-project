package DAO;

import Model.Message;
import Service.MessageService;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class MessageDAO {
    public Message lookUpMessageById(int id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "select * from Message where message_id = ?";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                        rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Message deleteMessage(int id){
        Connection connection = ConnectionUtil.getConnection();
        Message foundMessage = lookUpMessageById(id);
        if(foundMessage != null){
        try {
            //Write SQL logic here
            String sql = "delete from Message where message_id = ?";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
            //return foundMessage;
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            }
        }
        return foundMessage;
    }
    public Message updateMessage(int id, String text){
        Connection connection = ConnectionUtil.getConnection();
        
        try {
            //System.out.println("Passed text: " + text);
            //Write SQL logic here
            Message foundMessage = lookUpMessageById(id);
            
            if(foundMessage != null && text.length() != 0 && text.length() < 255){
            //System.out.println("Still here text: " + text);
            String sql = "update Message set message_text = ? where message_id = ?" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setString(1, text);
            preparedStatement.setInt(2, id);
            
            preparedStatement.executeUpdate();
            foundMessage.setMessage_text(text);
            return foundMessage;
        }
            
            /* 
            while(rs.next()){
                Account addedAccount = new Account(rs.getString("username"),
                        rs.getString("password"));
                return addedAccount;
            }
            */
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public List<Message> lookUpMessagesByAccountId(int id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<Message>();
        try {
            //Write SQL logic here
            
            String sql = "select * from Message where posted_by = ?";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                
                messages.add( new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                rs.getString("message_text"), rs.getLong("time_posted_epoch")));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<Message>();
        try {
            
            String sql = "select * from Message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                
                messages.add( new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                rs.getString("message_text"), rs.getLong("time_posted_epoch")));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }
    public String getUser(int user_id){
        Connection connection = ConnectionUtil.getConnection();
        String foundUser = "";
        try {
            
            String sql = "select username from Account where account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user_id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                foundUser = rs.getString("username");
                
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return foundUser;
    }
    public Message addMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String user = getUser(message.getPosted_by());
            if(user != "" && message.getMessage_text() != "" && message.getMessage_text().length() < 255){
            String sql = "insert into Message (posted_by, message_text, time_posted_epoch) values (?, ?, ?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            
            preparedStatement.executeUpdate();

            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        }
            
            /* 
            while(rs.next()){
                Account addedAccount = new Account(rs.getString("username"),
                        rs.getString("password"));
                return addedAccount;
            }
            */
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
}
