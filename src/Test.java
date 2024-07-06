//import Model.History;
//import Model.User;
//
//import java.sql.*;
//
//public class Test {
//
//    public static void main(String[] args) {
//        String username = "helia";
////        getUserHistory(username);
//        createHistory(username);
//        addUserHistory(new History("Ehsan", new Date(System.currentTimeMillis()), true, 2, 100));
//        addUserHistory(new History("Ehsan", new Date(System.currentTimeMillis()), false, 1, -120));
//        addUserHistory(new History("Ehsan", new Date(System.currentTimeMillis()), false, 1, -120));
//        getUserHistory(username);
//        System.out.println(new Date(System.currentTimeMillis()));
//    }
//
//    public static void addUserHistory(History gameResult){
//        Date date = gameResult.getDate();
//        String opponentName = gameResult.getOpponentName();
//        boolean won = gameResult.isWon();
//        int opponentLvl = gameResult.getOpponentLevel();
//        int prize = gameResult.getPrize();
//
//        try {
//            String query = "insert into "+"helia"+" (dat, opponentName, won, opponentLvl, prize)"
//                    + " values ("+date+" , '" +opponentName+"' , "+won+" , "+opponentLvl+" , "+prize+");";
//            System.out.println(query);
//            String url = "jdbc:sqlite:D:/Ehsan/studies/uni/sem_6/OOP/Project/Phase_1/MyPart/Database/db.db";
//            Connection connection = DriverManager.getConnection(url);
//            Statement statement = connection.createStatement();
//            System.out.println("check 1");
//            statement.executeUpdate(query);
//            System.out.println("check 2");
//        } catch (SQLException exception){
//            System.out.println(exception.getMessage());
//        }
//    }
//
//    public static void getUserHistory(String userName){
//        try {
//            String url = "jdbc:sqlite:D:/Ehsan/studies/uni/sem_6/OOP/Project/Phase_1/MyPart/Database/db.db";
//            Connection connection = DriverManager.getConnection(url);
//            Statement statement = connection.createStatement();
//            String query = "select * from "+userName+";";
//            statement.execute(query);
//            ResultSet rs = statement.getResultSet();
//            String opponentName;
//            Date date;
//            boolean won;
//            int opponentLvl, prize;
//            while (rs.next()) {
//                opponentName = rs.getString("opponentName");
//                date = rs.getDate("dat");
//                won = rs.getBoolean("won");
//                opponentLvl = rs.getInt("opponentLvl");
//                prize = rs.getInt("prize");
//                System.out.println("user: " + userName + " -- opponent: " + opponentName + " -- date: " + date + " -- won: " + won + " -- " +
//                        "opponentLvl: " + opponentLvl + " -- prize: " + prize);
//            }
//        } catch (SQLException exception){
//            System.out.println(exception.getMessage());
//        }
//    }
//
//    private static void createHistory(String username){
//        try {
//            String url = "jdbc:sqlite:D:/Ehsan/studies/uni/sem_6/OOP/Project/Phase_1/MyPart/Database/db.db";
//            Connection connection = DriverManager.getConnection(url);
//            String query = "create table if not exists "+username+" (dat date not null , opponentName text not null , won bit not null" +
//                    " , opponentLvl int not null, prize int not null)";
//            Statement statement = connection.createStatement();
//            statement.execute(query);
//        } catch (SQLException exception){
//            System.out.println(exception.getMessage());
//        }
//    }
//
//
//}
