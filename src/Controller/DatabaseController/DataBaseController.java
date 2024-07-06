package Controller.DatabaseController;

import Model.Card;
import Model.Game;
import Model.User;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class DataBaseController {
    private static Connection connection;

    public DataBaseController(){
        try {
            String url = "jdbc:sqlite:D:/Ehsan/studies/uni/sem_6/OOP/Project/Phase_1/MyPart/Database/db.db";
            connection = DriverManager.getConnection(url);

        } catch (SQLException e) {
            System.out.println("Database couldn't connect");
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
        GiftPack(user);
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

    private void GiftPack(User user){
        try {
            String query = "create table if not exists "+user.getUserName()+" " +
                    "(name text primary key not null , point int, duration int, " +
                    "dmg int, char int, spell bit, required_lvl int, upgrade_cost int, upgraded bit)";
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        Set<Integer> uniqueNumbers = getRandomNumbers(15, 1, 20);
        for (Integer i : uniqueNumbers)
            addCardToUser(user, i);

        uniqueNumbers = getRandomNumbers(5, 21, 30);
        for (Integer i : uniqueNumbers)
            addCardToUser(user, i);
    }

    private void addCardToUser(User user, int i){
        String name = "";
        int point=0, duration=0, dmg=0, ch=0, reqLVL=0, upgrade_cost=0;
        boolean isSpell = false;
        try {
            String query = "SELECT * FROM cards LIMIT 1 OFFSET "+i+"-1;";
            Statement statement = connection.createStatement();
            statement.execute(query);
            ResultSet rs = statement.getResultSet();

            name = rs.getString("name");
            point = rs.getInt("att_def_point");
            duration = rs.getInt("duration");
            dmg = rs.getInt("player_dmg");
            ch = rs.getInt("char");
            isSpell = rs.getBoolean("spell");
            reqLVL = rs.getInt("required_lvl");
            upgrade_cost = rs.getInt("upgrade_cost");
        } catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
        try {
            String query = "INSERT INTO "+user.getUserName()+" (name, point, duration, dmg, char, spell, required_lvl, upgrade_cost, upgraded)" +
                    " values ("+name+" "+point+" "+duration+" "+dmg+" "+ch+" "+isSpell+" "+reqLVL+
                    " "+upgrade_cost+" "+0+");";
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        ArrayList<Card> cards = user.getCards();
        cards.add(new Card(name, point, dmg, duration, isSpell, ch, 0, reqLVL, false, upgrade_cost));
        user.setCards(cards);
    }

    private Set<Integer> getRandomNumbers(int n, int min, int max){
        Set<Integer> ret = new HashSet<>();

        while (ret.size() < n) {
            Random random = new Random();
            int randomNumber = random.nextInt(max - min + 1) + min;
            ret.add(randomNumber);
        }
        return ret;
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

    public User loadUser(String username){
        return null;
    }

    public void startApp(){
        ArrayList<User> allUsers = new ArrayList<>();
//        System.out.println(connection.toString());
        try {
            String query = "SELECT * FROM main.users ";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()){
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String nickname = resultSet.getString("nickname");
                String email = resultSet.getString("email");
                String question = resultSet.getString("question");
                String answer = resultSet.getString("answer");

                int lvl = resultSet.getInt("lvl");
                int xp = resultSet.getInt("xp");
                int gold = resultSet.getInt("gold");
                ArrayList<String> cards = new ArrayList<>();

                String queryCards = "select * from user_cards where username = "+username;
                Statement statementCards = connection.createStatement();
                ResultSet resultSetCards = statementCards.executeQuery(queryCards);

                int columnCount = resultSetCards.getMetaData().getColumnCount();

                while (resultSetCards.next()) {
                    cards.clear();
                    // Start from the second column as the first is 'username'
                    for (int i = 2; i <= columnCount; i++) {
                        cards.add(resultSetCards.getString(i));
                    }
                }

                allUsers.add(new User(username,password,nickname,email,lvl,question,answer,xp,gold,getCardsFromName(cards)));
            }
        } catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
    }

    private ArrayList<Card> getCardsFromName(ArrayList<String> cardNames){
        ArrayList<Card> cards = new ArrayList<>();

        try {
            String query = "select * from cards";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
        } catch (SQLException exception){
            System.out.println(exception.getMessage());
        }

        for (String name : cardNames) {

        }
        return cards;
    }

    public void updateUserInDB(User user){
        String username = user.getUserName();
        String password = user.getPassword();
        String nickname = user.getNickname();
        String email = user.getEmail();
        String question = user.getQuestion();
        String answer = user.getAnswer();

        int lvl = user.getLvl();
        int xp = user.getXP();
        int gold = user.getGold();
        int clanCode = user.getClanCode();
        ArrayList<Card> cards = user.getCards();

        ArrayList<String> cardNames = new ArrayList<>();
        for (Card card: cards)
            cardNames.add(card.getName());

        try {
            String query = "update main.users set main.users.username = "+username+" , main.users.password = "
                    +password+" , main.users.nickname = "+nickname+" , main.users.email = "+email+
                    ", main.users.question = "+question+" , main.users.answer = "+answer+" , lvl = "+lvl+
                    " , xp = "+xp+" , gold = "+gold+" , clan_code = "+clanCode;
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void getUserHistory(String userName){}

    public void addUserHistory(Game gameResult){}

}
