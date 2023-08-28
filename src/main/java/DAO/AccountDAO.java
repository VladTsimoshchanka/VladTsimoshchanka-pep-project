package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    public List<String> getUsernames(String user){
        Connection connection = ConnectionUtil.getConnection();
        List<String> users = new ArrayList<String>();
        try {
            
            String sql = "select username from Account where username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                
                users.add(rs.getString("username"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return users;
    }
    public Account insertAccount(Account acc){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            List<String> users = getUsernames(acc.getUsername());
            if(users.size() == 0 && acc.getUsername() != "" && acc.getPassword().length() >= 4){
            String sql = "insert into Account (username, password) values (?, ?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setString(1, acc.getUsername());
            preparedStatement.setString(2, acc.getPassword());
            
            preparedStatement.executeUpdate();

            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, acc.getUsername(), acc.getPassword());
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
    public Account checkAccount(Account acc){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            
            
            String sql = "select * from Account where username = ? and password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setString(1, acc.getUsername());
            preparedStatement.setString(2, acc.getPassword());
            
            
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                
                Account checkedAcc = new Account(rs.getInt("account_id"), 
                rs.getString("username"),
                 rs.getString("password"));

                return checkedAcc;
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
