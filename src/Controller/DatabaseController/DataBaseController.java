package Controller.DatabaseController;

import Model.Card;
import Model.Game;
import Model.History;
import Model.User;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class DataBaseController {
    private static Connection connection;

    public DataBaseController() {
        try {
            // Initialize the connection
            String url = "jdbc:sqlite:D:/Ehsan/studies/uni/sem_6/OOP/Project/Phase_1/MyPart/Database/db.db";
            connection = DriverManager.getConnection(url);
            connection.setAutoCommit(false);  // Use transactions manually

            // Create the users table
            String query = "CREATE TABLE IF NOT EXISTS users (" +
                    "username TEXT PRIMARY KEY, " +
                    "password TEXT NOT NULL, " +
                    "nickname TEXT NOT NULL, " +
                    "email TEXT NOT NULL, " +
                    "question TEXT NOT NULL, " +
                    "answer TEXT NOT NULL, " +
                    "lvl INT NOT NULL, " +
                    "xp INT NOT NULL, " +
                    "gold INT NOT NULL, " +
                    "clan_code INT NOT NULL);";

            try (Statement statement = connection.createStatement()) {
                statement.execute(query);
                connection.commit();  // Commit the transaction
            } catch (SQLException exception) {
                System.out.println("Exception in creating users table\n" + exception.getMessage());
                connection.rollback();  // Rollback the transaction if there's an error
            }
        } catch (SQLException e) {
            System.out.println("Database couldn't connect");
            System.out.println(e.getMessage());
        }
    }



    public synchronized void addUser(User user) {
        String query = "INSERT INTO users (username, password, nickname, email, question, answer, lvl, xp, gold, clan_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getNickname());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getQuestion());
            pstmt.setString(6, user.getAnswer());
            pstmt.setInt(7, user.getLvl());
            pstmt.setInt(8, user.getXP());
            pstmt.setInt(9, user.getGold());
            pstmt.setInt(10, user.getClanCode());
            pstmt.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Either user was already added or " + e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        if (!user.isGotGiftPack()) {
            System.out.println("Entered this section");
            GiftPack(user);
        }
        System.out.println("And here");
        createHistory(user.getUserName());
    }
//    public synchronized void addUser(User user){
//        try {
//            String query = "INSERT INTO users (username, password, nickname, email, question, answer, lvl, xp, gold, clan_code) VALUES ('"
//                    + user.getUserName() + "' , '" + user.getPassword() + "' , '" + user.getNickname() + "' , '"
//                    + user.getEmail() + "' , '" + user.getQuestion() + "' , '"
//                    + user.getAnswer() + "' , "+user.getLvl()+" , "+user.getXP()+" , "+user.getGold()+" , "+user.getClanCode()+");";
//            Statement st = connection.createStatement();
//            st.executeUpdate(query);
//        } catch (Exception e) {
//            System.out.println("Either user was already added \tor \t"+e.getMessage());
//        }
//        if (!user.isGotGiftPack())
//            GiftPack(user);
//        System.out.println("And here");
//        createHistory(user.getUserName());
//    }

//    private void createHistory(String username){
//        try {
//            String query = "create table if not exists "+username+" (dat date not null , opponentName text not null , won bit not null" +
//                    " , opponentLvl int not null, prize int not null)";
//            Statement statement = connection.createStatement();
//            statement.execute(query);
//        } catch (SQLException exception){
//            System.out.println(exception.getMessage());
//        }
//    }
    private void createHistory(String username) {
        String query = "CREATE TABLE IF NOT EXISTS " + username.concat("History") + " (dat DATE NOT NULL, opponentName TEXT NOT NULL, won BIT NOT NULL, opponentLvl INT NOT NULL, prize INT NOT NULL)";

        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
            connection.commit();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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

    private void GiftPack(User user) {
        String query = "CREATE TABLE IF NOT EXISTS " + user.getUserName() + " (name TEXT PRIMARY KEY, point INT, duration INT, dmg INT, char INT, spell BIT, required_lvl INT, upgrade_cost INT, upgraded BIT)";

        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        Set<Integer> uniqueNumbers = new HashSet<>();

        uniqueNumbers = getRandomNumbers(uniqueNumbers,15, 1, 20);
        uniqueNumbers = getRandomNumbers(uniqueNumbers,20, 21, 30);

        addCardToUser(user, uniqueNumbers);
//        for (Integer i : uniqueNumbers) {
//            addCardToUser(user, i);
//        }

//        for (Integer i : uniqueNumbers) {
//            addCardToUser(user, i);
//        }

        user.setGotGiftPack(true);
    }

//    private void GiftPack(User user){
//        try {
//            String query = "create table if not exists "+user.getUserName()+" " +
//                    "(name text primary key , point int, duration int, " +
//                    "dmg int, char int, spell bit, required_lvl int, upgrade_cost int, upgraded bit)";
//            Statement statement = connection.createStatement();
//            statement.execute(query);
//        } catch (SQLException e){
//            System.out.println(e.getMessage());
//        }
//
//        Set<Integer> uniqueNumbers = getRandomNumbers(15, 1, 20);
//        for (Integer i : uniqueNumbers)
//            addCardToUser(user, i);
//
//        uniqueNumbers = getRandomNumbers(5, 21, 30);
//        for (Integer i : uniqueNumbers)
//            addCardToUser(user, i);
//
//        user.setGotGiftPack(true);
//    }

    private void addCardToUser(User user, Set<Integer> indices) {
        String query = "SELECT * FROM cards WHERE rowid = ?";
        String insertQuery = "INSERT INTO " + user.getUserName() + " (name, point, duration, dmg, char, spell, required_lvl, upgrade_cost, upgraded) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        ArrayList<Card> cards = user.getCards();

        try (PreparedStatement selectPstmt = connection.prepareStatement(query);
             PreparedStatement insertPstmt = connection.prepareStatement(insertQuery)) {

            // Loop over all indices to fetch and insert card details
            for (Integer i : indices) {
                selectPstmt.setInt(1, i);
                try (ResultSet rs = selectPstmt.executeQuery()) {
                    if (rs.next()) {
                        String name = rs.getString("name");
                        int point = rs.getInt("att_def_point");
                        int duration = rs.getInt("duration");
                        int dmg = rs.getInt("player_dmg");
                        int ch = rs.getInt("char");
                        boolean isSpell = rs.getBoolean("spell");
                        int reqLVL = rs.getInt("required_lvl");
                        int upgrade_cost = rs.getInt("upgrade_cost");

                        // Add to user's card collection
                        cards.add(new Card(name, point, dmg, duration, isSpell, ch, reqLVL, false, upgrade_cost));

                        // Insert into user's table
                        insertPstmt.setString(1, name);
                        insertPstmt.setInt(2, point);
                        insertPstmt.setInt(3, duration);
                        insertPstmt.setInt(4, dmg);
                        insertPstmt.setInt(5, ch);
                        insertPstmt.setBoolean(6, isSpell);
                        insertPstmt.setInt(7, reqLVL);
                        insertPstmt.setInt(8, upgrade_cost);
                        insertPstmt.setBoolean(9, false);
                        insertPstmt.addBatch();
                    }
                }
            }

            // Execute batch insert
            insertPstmt.executeBatch();
            connection.commit();

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        user.setCards(cards);
    }


//    private void addCardToUser(User user, int i) {
//        String query = "SELECT * FROM cards LIMIT 1 OFFSET ?;";
//        String insertQuery = "INSERT INTO " + user.getUserName() + " (name, point, duration, dmg, char, spell, required_lvl, upgrade_cost, upgraded) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
//
//        String name = "";
//        int point=0, duration=0, dmg=0, ch=0, reqLVL=0, upgrade_cost=0;
//        boolean isSpell = false;
//
//        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
//            pstmt.setInt(1, i - 1);
//            ResultSet rs = pstmt.executeQuery();
//
//            if (rs.next()) {
//                name = rs.getString("name");
//                point = rs.getInt("att_def_point");
//                duration = rs.getInt("duration");
//                dmg = rs.getInt("player_dmg");
//                ch = rs.getInt("char");
//                isSpell = rs.getBoolean("spell");
//                reqLVL = rs.getInt("required_lvl");
//                upgrade_cost = rs.getInt("upgrade_cost");
//
//                try (PreparedStatement insertPstmt = connection.prepareStatement(insertQuery)) {
//                    insertPstmt.setString(1, name);
//                    insertPstmt.setInt(2, point);
//                    insertPstmt.setInt(3, duration);
//                    insertPstmt.setInt(4, dmg);
//                    insertPstmt.setInt(5, ch);
//                    insertPstmt.setBoolean(6, isSpell);
//                    insertPstmt.setInt(7, reqLVL);
//                    insertPstmt.setInt(8, upgrade_cost);
//                    insertPstmt.setBoolean(9, false);
//                    insertPstmt.executeUpdate();
//                    connection.commit();
//                }
//            }
//        } catch (SQLException exception) {
//            System.out.println(exception.getMessage());
//            try {
//                connection.rollback();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        ArrayList<Card> cards = user.getCards();
//        cards.add(new Card(name, point, dmg, duration, isSpell, ch, reqLVL, false, upgrade_cost));
//        user.setCards(cards);
//    }

//    private void addCardToUser(User user, int i){
//        String name = "";
//        int point=0, duration=0, dmg=0, ch=0, reqLVL=0, upgrade_cost=0;
//        boolean isSpell = false;
//        try {
//            String query = "SELECT * FROM cards LIMIT 1 OFFSET "+(i-1)+";";
//            Statement statement = connection.createStatement();
//            statement.execute(query);
//            ResultSet rs = statement.getResultSet();
//
//            name = rs.getString("name");
//            point = rs.getInt("att_def_point");
//            duration = rs.getInt("duration");
//            dmg = rs.getInt("player_dmg");
//            ch = rs.getInt("char");
//            isSpell = rs.getBoolean("spell");
//            reqLVL = rs.getInt("required_lvl");
//            upgrade_cost = rs.getInt("upgrade_cost");
//        } catch (SQLException exception){
//            System.out.println(exception.getMessage());
//        }
//        try {
//            String query = "INSERT INTO "+user.getUserName()+" (name, point, duration, dmg, char, spell, required_lvl, upgrade_cost, upgraded)" +
//                    " values ('"+name+"' , "+point+" , "+duration+" , "+dmg+" , "+ch+" , "+isSpell+" , "+reqLVL+
//                    " , "+upgrade_cost+" , "+0+");";
//            Statement statement = connection.createStatement();
//            statement.execute(query);
//        } catch (SQLException e){
//            System.out.println(e.getMessage());
//        }
//        ArrayList<Card> cards = user.getCards();
//        cards.add(new Card(name, point, dmg, duration, isSpell, ch, reqLVL, false, upgrade_cost));
//        user.setCards(cards);
//    }

    private Set<Integer> getRandomNumbers(Set<Integer> set, int n, int min, int max){

        while (set.size() < n) {
            Random random = new Random();
            int randomNumber = random.nextInt(max - min + 1) + min;
            set.add(randomNumber);
        }
        return set;
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
//        System.out.println("is it here?");
        ArrayList<User> allUsers = new ArrayList<>();
//        System.out.println(connection.toString());
        try {
            String query = "SELECT * FROM main.users;";
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
                int clanCode = resultSet.getInt("clan_code");
                ArrayList<Card> cards = new ArrayList<>();

                String queryCards = "select * from "+username+" ;";
                Statement statementCards = connection.createStatement();
                ResultSet resultSetCards = statementCards.executeQuery(queryCards);
                String cardName;
                int point=0, duration=0, dmg=0, ch=0, reqLvl=0, upCost=0;
                boolean isSpell=false, upgraded=false;
                while (resultSetCards.next()) {
                    cardName = resultSetCards.getString("name");
                    point = resultSetCards.getInt("point");
                    duration = resultSetCards.getInt("duration");
                    dmg = resultSetCards.getInt("dmg");
                    ch = resultSetCards.getInt("char");
                    isSpell = resultSetCards.getBoolean("spell");
                    reqLvl = resultSetCards.getInt("required_lvl");
                    upCost = resultSetCards.getInt("upgrade_cost");
                    upgraded = resultSetCards.getBoolean("upgraded");

                    cards.add(new Card(cardName, point, dmg, duration, isSpell, ch, reqLvl, upgraded, upCost));
                }
                allUsers.add(new User(username,password,nickname,email,lvl,question,answer,xp,gold,clanCode,cards));

            }
            connection.commit();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        User.setUsers(allUsers);
//        System.out.println("here is the error");
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
                    " , xp = "+xp+" , gold = "+gold+" , clan_code = "+clanCode+" where username = "+username;
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void getUserHistory(String userName){
        try {
            String query = "select * from "+userName.concat("History")+";";
            Statement statement = connection.createStatement();
            statement.execute(query);
            ResultSet rs = statement.getResultSet();
            String opponentName;
            Date date;
            boolean won;
            int opponentLvl, prize;
            while (rs.next()) {
                opponentName = rs.getString("opponentName");
                date = rs.getDate("dat");
                won = rs.getBoolean("won");
                opponentLvl = rs.getInt("opponentLvl");
                prize = rs.getInt("prize");
                System.out.println("user: " + userName + " -- opponent: " + opponentName + " -- date: " + date + " -- won: " + won + " -- " +
                        "opponentLvl: " + opponentLvl + " -- prize: " + prize);
            }
        } catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
    }

    public void addUserHistory(History gameResult) {
        Date date = gameResult.getDate();
        String opponentName = gameResult.getOpponentName();
        boolean won = gameResult.isWon();
        int opponentLvl = gameResult.getOpponentLevel();
        int prize = gameResult.getPrize();

        try {
            String query = "insert into " + User.getLoggedInUser().getUserName().concat("History")+"" + " (dat, opponentName, won, opponentLvl, prize)"
                    + " values (" + date + " , '" + opponentName + "' , " + won + " , " + opponentLvl + " , " + prize + ");";
            System.out.println(query);
            String url = "jdbc:sqlite:D:/Ehsan/studies/uni/sem_6/OOP/Project/Phase_1/MyPart/Database/db.db";
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            System.out.println("check 1");
            statement.executeUpdate(query);
            System.out.println("check 2");
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

}
