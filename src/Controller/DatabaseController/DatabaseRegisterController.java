package Controller.DatabaseController;

import Model.User;

import java.sql.*;

public class DatabaseRegisterController {
    private static Connection connection;
    private static String url;
    public DatabaseRegisterController(){
        try {
            url = "jdbc:sqlite:D:/Ehsan/studies/uni/sem 6/OOP/Project/Phase 1/MyPart/Database/db.db";
            connection = DriverManager.getConnection(url);

        } catch (SQLException e) {
            System.out.println("Database couldn't connect");
            System.out.println(e.getMessage());
        }
//        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
//                "username TEXT PRIMARY KEY," +
//                "password TEXT NOT NULL," +
//                "nickname TEXT NOT NULL," +
//                "email TEXT NOT NULL," +
//                "question TEXT NOT NULL," +
//                "answer TEXT NOT NULL" +
//                ");";
//        try {
//            Statement statement = connection.createStatement();
//            statement.execute(createTableSQL);
//        } catch (SQLException e){
//            System.out.println(e.getMessage());
//        }
    }


    public void addUser(User user){
        try {
            String query = "INSERT INTO users (username, password, nickname, email, question, answer) VALUES ('"
                    + user.getUserName() + "', '" + user.getPassword() + "', '" + user.getNickname() + "', '"
                    + user.getEmail() + "', '" + user.getQuestion() + "', '"
                    + user.getAnswer() + "')";
            java.sql.Statement st = connection.createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("exception in insertUser in Database \n" + e.getMessage());
        }
    }

    public void updateUser(User user){
        String username = user.getUserName();
        String password = user.getPassword();

        try {
            String query = "UPDATE users SET password = '" + password + "' WHERE username = '" + username + "'";
            java.sql.Statement st = connection.createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("exception in updatePassword in Database \n" + e.getMessage());
        }
    }

}
