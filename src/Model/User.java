package Model;

import java.util.ArrayList;

public class User {
    private static User loggedInUser;
    private static User opponent;
    private String userName;
    private String Password;
    private String nickname;
    private String email;
    private int lvl;
    private String question;
    private String answer;
    private int XP;
    private int gold;
    private int HP;
    private int character;
    ArrayList<Card> battleDeck = new ArrayList<>();
    ArrayList<Card> cards = new ArrayList<>();
    private static ArrayList<User> users = new ArrayList<>();
    public User(String userName, String password, String nickname, String email, String question, String answer) {
        this.userName = userName;
        Password = password;
        this.nickname = nickname;
        this.email = email;
        this.question = question;
        this.answer = answer;
    }

    public static void addUser(User user){
        users.add(user);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public void heal(){this.HP+=20;}

    public static boolean checkUserName(String userName){return true;}

    public static boolean checkPassWord(String password){return true;}

    public static User getUser(String userName){return null;}

    public static boolean checkEmail(String email){return true;}

    public boolean passMatch(String password){return true;}

    public boolean forgotPass(String answer){return true;}

    public void changeUsername(String newUserName){}

    public void changePass(String newPass){}

    public void changeNickName(String newNickName){}

    public void changeEmail(String newEmail){}

    public void adminLogIn(String password){}

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        User.loggedInUser = loggedInUser;
    }

    public static User getOpponent() {
        return opponent;
    }

    public static void setOpponent(User opponent) {
        User.opponent = opponent;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getXP() {
        return XP;
    }

    public void setXP(int XP) {
        this.XP = XP;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public ArrayList<Card> getBattleDeck() {
        return battleDeck;
    }

    public void setBattleDeck(ArrayList<Card> battleDeck) {
        this.battleDeck = battleDeck;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public int getCharacter() {
        return character;
    }

    public void setCharacter(int type) {
        this.character = type;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }
}
