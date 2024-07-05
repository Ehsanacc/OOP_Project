package Controller.DatabaseController;

import Model.User;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;

public class DataBaseController {
    private static Connection connection;
    private static String url;
    public DataBaseController(){
        try {
            url = "jdbc:sqlite:D:/Ehsan/studies/uni/sem 6/OOP/Project/Phase 1/MyPart/Database/db.db";
            connection = DriverManager.getConnection(url);

        } catch (SQLException e) {
            System.out.println("Database couldn't connect");
            System.out.println(e.getMessage());
        }
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                "username TEXT PRIMARY KEY," +
                "password TEXT NOT NULL," +
                "nickname TEXT NOT NULL," +
                "email TEXT NOT NULL," +
                "question TEXT NOT NULL," +
                "answer TEXT NOT NULL" +
                ");";
        try {
            Statement statement = connection.createStatement();
            statement.execute(createTableSQL);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }


    public void addUser(User user){
        try {
            String query = "INSERT INTO users (username, password, nickname, email, question, answer) VALUES ('"
                    + user.getUserName() + "', '" + user.getPassword() + "', '" + user.getNickname() + "', '"
                    + user.getEmail() + "', '" + user.getQuestion() + "', '"
                    + user.getAnswer() + "')";
            Statement st = connection.createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("exception in insertUser in Database \n" + e.getMessage());
        }

        try {
            String query = "CREATE TABLE "+user.getUserName()+" ()";
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
    }

    public void updateUserEmail(String username, String email){
        try {
            String query = "UPDATE users SET email = "+email+" WHERE username = "+username;
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        }
        catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
    }

    public void updateUserPassword(String username, String password){
        try {
            String query = "UPDATE users SET password = " + password + " WHERE username = " + username;
            java.sql.Statement st = connection.createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("exception in updatePassword in Database \n" + e.getMessage());
        }
    }

    public void updateUserUserName(String oldUserName, String newUserName){
        try {
            String query = "UPDATE users SET username = "+newUserName+" WHERE username = "+oldUserName;
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Updated username from"+oldUserName+" to "+newUserName);

        } catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
    }

    public ArrayList<String> getAllUsernames(){
        ArrayList<String> usernames = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT username FROM users");
            while (resultSet.next())
                usernames.add(resultSet.getString("username"));
        }
        catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
        return usernames;
    }

    public void updateUserNickname(String oldNickname, String newNickname){
        try {
            String query = "UPDATE users SET nickname = "+newNickname+" WHERE nickname = "+oldNickname;
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Updated username from"+oldNickname+" to "+newNickname);

        } catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
    }

    public ArrayList<String> getAllUserNicknames(){
        ArrayList<String> nicknames = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT nickname FROM users");
            while (resultSet.next())
                nicknames.add(resultSet.getString("nickname"));
        }
        catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
        return nicknames;
    }

}
