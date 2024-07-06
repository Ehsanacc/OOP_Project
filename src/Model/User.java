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
    private int clanCode;
    private boolean didClanWar=false;
    ArrayList<Card> cards = new ArrayList<>();
    private static ArrayList<User> users = new ArrayList<>();

    public User(String userName, String password, String nickname, String email, String question, String answer) {
        this.userName = userName;
        this.Password = password;
        this.nickname = nickname;
        this.email = email;
        this.question = question;
        this.answer = answer;
        this.XP = 0;
        this.gold = 150;
        this.clanCode = -1;
        this.lvl = 1;
    }

    public User(String userName, String password, String nickname, String email, int lvl, String question, String answer, int XP, int gold, ArrayList<Card> cards) {
        this.userName = userName;
        Password = password;
        this.nickname = nickname;
        this.email = email;
        this.lvl = lvl;
        this.question = question;
        this.answer = answer;
        this.XP = XP;
        this.gold = gold;
        this.cards = cards;
    }

    public static void addUser(User user){
        users.add(user);
    }

    @Override
    public String toString() {
        return """
                \tUsername: %s
                \tPassword: %s
                \tNickname: %s
                \tEmail: %s
                \tSecurity Question: %s
                \tSecurity Answer: %s
                """.formatted(this.userName, this.Password, this.nickname, this.email, this.question, this.answer);
    }

    public void heal(){this.HP+=20;}

    public void hurt(int amount){
        this.HP-=amount;
    }

    public void die(){
        this.HP=0;
    }

    public String win(int amount, int bet){
        String output = null;
        this.gold+=50;
        this.XP+=amount;
        output=nickname+" is awarded 50 coins and "+amount+" XP :)";
        if (bet>0) {
            this.gold+=bet;
            output="\n"+nickname+" won the bet for "+bet+" coins";
        }

        return output;
    }

    public String lose(int amount, int bet){
        String output = null;
        this.gold+=5;
        this.XP+=10;
        output=nickname+" gets 5 coins and 10 XP because we pity you :(";
        if (bet>0) {
            this.gold-=bet;
            output="\n"+nickname+" lost the bet for "+bet+" coins";
        }
        return output;
    }

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

    public int getClanCode() {
        return clanCode;
    }

    public void setClanCode(int clanCode) {
        this.clanCode = clanCode;
    }

    public boolean isDidClanWar() {
        return didClanWar;
    }

    public void setDidClanWar(boolean didClanWar) {
        this.didClanWar = didClanWar;
    }

    public void upgradeLVL(){
        if (getXP() > 200){
            setXP(getXP() - 200);
            setLvl(getLvl() + 1);
        }
    }

    public void updateUserInDB(User user){

    }

    public void getUserHistory(String userName){}

    public void addUserHistory(Game gameResult){}

    public static ArrayList<User> getUsers() {
        return users;
    }

}
