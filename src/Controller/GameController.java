package Controller;
import Model.Card;
import Model.User;
import java.util.Random;
import java.util.regex.Matcher;

public class GameController {
    private final Random random=new Random();
    private Card[] battleDeck1, battleDeck2, board1, board2;
    private User player;
    private int betAmount;
    private int round1, round2, guestDamage, hostDamage, hole1, hole2;
    private Card extra1, extra2;
    private boolean four1, four2, hide1, hide2;

    public String selectModes(Matcher matcher){
        String output = "";
        String invalid="Invalid command";
        int m;
        try{
            m=Integer.parseInt(matcher.group("number"));
        }
        catch (Exception e){
            return invalid;
        }
        if (m%2==0){
            output="Opponent should log in now.";
        }
        if (m==4) betAmount=-1;
        return output;
    }

    public String opponentLogIn(Matcher matcher){
        String output;
        String username= matcher.group("username");
        String password= matcher.group("password");
        User user=new RegisterController().findUser(username, password);
        if (user==null) output="Try again";
        else {
            User.setOpponent(user);
            output=user.getNickname()+"just logged in";
            if (betAmount==-1){
                output+="\nHow much do you want to bet?";
            }
            else output+="\nChoose your character from number 1, 2, 3, 4";
        }
        return output;
    }

    public String selectCharacter(Matcher matcher){
        String output;
        String invalid="Invalid command";
        int c, p;
        try{
            c=Integer.parseInt(matcher.group("number"));
            p=Integer.parseInt(matcher.group("player"));
        }
        catch (Exception e){
            return invalid;
        }
        if (p==1){
            User.getLoggedInUser().setCharacter(c);
            output=User.getLoggedInUser().getNickname()+" chose character number "+c;
            if (User.getOpponent().getCharacter()==0)
                return output+"\n"+User.getOpponent().getNickname()+" choose your character from 1, 2, 3, 4";
        }
        else{
            User.getOpponent().setCharacter(c);
            output=User.getOpponent().getNickname()+" chose character number "+c;
            if (User.getLoggedInUser().getCharacter()==0)
                return output+"\n"+User.getLoggedInUser().getNickname()+" choose your character from 1, 2, 3, 4";
        }
        startGame();
        output+="\nStart the game?";
        return output;
    }

    public String betting(Matcher matcher){
        String output;
        String invalid="Invalid command";
        int b;
        try{
            b=Integer.parseInt(matcher.group("number"));
        }
        catch (Exception e){
            return invalid;
        }
        if (User.getLoggedInUser().getGold()<b){
            output=User.getLoggedInUser().getNickname()+" doesn't have "+b+" to bet";
        }
        else if ( User.getOpponent().getGold()<b){
            output=User.getOpponent().getNickname()+" doesn't have "+b+" to bet\nGive another number";
        }
        else {
            betAmount=b;
            output="You bet "+b+" coins";
            output+="\nChoose your character from number 1, 2, 3, 4";
        }
        return output;
    }

    public Card[] setBattleDeck(int n, User user){
        Card card;
        Card[] battleDeck=new Card[n];
        int s=0;
        for (int i = 0; i < n; i++) {
            card=user.getCards().get(random.nextInt(user.getCards().size()));
            while (card.isSpecial()){
                s++;
                if (s>n/2){
                    card=user.getCards().get(random.nextInt(user.getCards().size()));
                    s--;
                }
            }
            battleDeck[i]=card;
        }
        return battleDeck;
    }

    public String selectCard(Matcher matcher){
        int n, p;
        String invalid="Invalid command";
        try {
            n=Integer.parseInt(matcher.group("number"));
            p=Integer.parseInt(matcher.group("player"));
        }
        catch (Exception e){
            return invalid;
        }
        if (n<1 || p<1 || p>2){
            return invalid;
        }
        if ((p==1 && four1 && n>4) || (p==2 && four2 && n>4)) return invalid;
        if ((p==1 && extra1==null && n>5) || (p==2 && extra2==null && n>5)) return null;
        if (n>6) return invalid;
        if ((p==1 && !player.equals(User.getLoggedInUser())) || (p==2 && !player.equals(User.getOpponent()))){
            return "It's not your turn";
        }
        if (p==1)
            return battleDeck1[n-1].toString();
        return battleDeck2[n-1].toString();
    }

    public String placeCard(Matcher matcher){
        int n, b;
        Random random=new Random();
        String invalid="Invalid command";
        String cant="You can't place a card here";
        try {
            n=Integer.parseInt(matcher.group("number"));
            b=Integer.parseInt(matcher.group("block"));
        }
        catch (Exception e){
            return invalid;
        }
        if (n<1 || n>5 || b<1 || b>21){
            return invalid;
        }
        Card card, extra;
        int round, h1, h2;
        Card[] b1, b2, deck2;
        boolean hide;
        if (player.equals(User.getLoggedInUser())) {
            card = battleDeck1[n - 1];
            round=round1;
            b1=board1;
            b2=board2;
            h1=hole1;
            h2=hole2;
            deck2=battleDeck2;
            extra=extra1;
            hide=hide2;
            if (card.getType()==User.getLoggedInUser().getCharacter()) card.buff();
        }
        else{
            card = battleDeck2[n - 1];
            round=round2;
            b1=board2;
            b2=board1;
            h2=hole1;
            h1=hole2;
            deck2=battleDeck1;
            extra=extra2;
            hide=hide1;
            if (card.getType()==User.getOpponent().getCharacter()) card.buff();
        }
        switch (card.getName()) {
            case "repairman":
                if (!b1[n - 1].getName().equals("hole"))
                    return "You can only place this card on a hole";
                b1[n - 1] = null;
                break;
            case "power buff":
                if (round > 3) return "No cards are there to buff";
                while (true) {
                    int r = random.nextInt(21);
                    if (b1[r] != null) {
                        b1[r].buff();
                        break;
                    }
                }
                break;
            case "hurry up":
                round1--;
                round2--;
                break;
            case "poison":
                Card.poison(deck2);
                break;
            case "bandit":
                Card.steal(extra, deck2);
                break;
            case "copy cat":
                return "Which card?";
            case "cover-up":
                Card.scramble(deck2);
                hide = true;
                break;
            default:
                for (int i = b - 1; i < b + card.getDuration() - 1; i++) {
                    if (b1[i] != null) return cant;
                }
                break;
        }
        int[] damages=setCard(b1, b2, card, b);
        if (card.getName().equals("healer")){
            player.heal();
        }
        else if (card.getName().equals("hole digger")){
            int h;
            while (true) {
                h = random.nextInt(21);
                if (b1[h] == null) {
                    b1[h] = new Card("hole");
                    b1[h1] = null;
                    break;
                }
            }
            while (true){
                h=random.nextInt(21);
                if (b2[h]==null){
                    b2[h]=new Card("hole");
                    b2[h2]=null;
                    break;
                }
            }
        }
        if (!card.getName().equals("bandit") && extra!=null){
            if (player.equals(User.getLoggedInUser())) extra1=null;
            else extra2=null;
        }
        if (!card.getName().equals("cover-up") && hide){
            if (player.equals(User.getLoggedInUser())) extra1=null;
            else extra2=null;
        }
        if (player.equals(User.getLoggedInUser())){
            player=User.getOpponent();
            round1--;
            hostDamage+=damages[0];
            guestDamage+=damages[1];
        }
        else {
            player=User.getLoggedInUser();
            round2--;
            guestDamage+=card.getDamage();
            hostDamage+=damages[1];
            guestDamage+=damages[0];
        }
        if (round1==0 && round2==0){
            return endOfRound();
        }
        return nextRound();
    }

    public String copyCat(Matcher matcher){
        int n;
        String invalid="Invalid command";
        try {
            n=Integer.parseInt(matcher.group("number"));
        }
        catch (Exception e){
            return invalid;
        }
        if (n<1) return invalid;
        Card card;
        if (player.equals(User.getLoggedInUser())) {
            if (n>battleDeck1.length) return invalid;
            card = battleDeck1[n - 1];
            round1--;
            player=User.getOpponent();
            extra1=card;
        }
        else{
            if (n>battleDeck2.length) return invalid;
            card = battleDeck2[n - 1];
            round2--;
            player=User.getLoggedInUser();
            extra2=card;
        }
        if (round1<=0 || round2<=0){
            return endOfRound();
        }
        return nextRound();
    }

    public int[] setCard(Card[] board1, Card[] board2, Card card, int b){
        int[] damages=new int[]{0, 0};
        int dur= card.getDuration();
        for (int i = b; i < b+dur; i++) {
            board1[i-1]= card;
            if (board2[i-1]!=null ){
                if (!board2[i-1].getName().equals("hole")) {
                    Card card1 = board2[i - 1];
                    if (card1.getName().equals("healer")) continue;
                    if (card1.getPoint() > card.getPoint() && card1.getDamage() != 0) {
                        board1[i - 1].setDamage(0);
                    } else if (card1.getPoint() < card.getPoint()) {
                        damages[1] -= card1.getDamage() / card1.getDuration();
                        board2[i - 1].setDamage(0);
                    } else {
                        damages[1] -= card1.getDamage() / card1.getDuration();
                        board2[i - 1].setDamage(0);
                        board1[i - 1].setDamage(0);
                    }
                }
            }
            damages[0]+=card.getDamage()/card.getDuration();
        }
        return damages;
    }

    public String endOfRound(){
        String output = null;
        for (int i = 0; i < 21; i++) {
            if (battleDeck2[i]!=null && !battleDeck2[i].isSpecial()) {
                if (battleDeck2[i].getDamage() / battleDeck2[i].getDuration() >= User.getLoggedInUser().getHP()) {
                    User.getLoggedInUser().die();
                    output=endGame();
                    startGame();
                    break;
                }
                User.getLoggedInUser().hurt(battleDeck2[i].getDamage()/battleDeck2[i].getDuration());
                guestDamage+=battleDeck2[i].getDamage() / battleDeck2[i].getDuration();
            }
            if (battleDeck1[i]!=null && !battleDeck1[i].isSpecial()) {
                if (battleDeck1[i].getDamage() / battleDeck1[i].getDuration() >= User.getOpponent().getHP()) {
                    User.getOpponent().die();
                    output=endGame();
                    startGame();
                    break;
                }
                User.getOpponent().hurt(battleDeck1[i].getDamage() / battleDeck1[i].getDuration());
                hostDamage+=battleDeck1[i].getDamage() / battleDeck1[i].getDuration();
            }
        }
        if (round1>0) output=nextRound();
        return output;
    }

    public String endGame(){
        String output;
        User winner;
        User loser;
        int won;
        if (User.getLoggedInUser().getHP()==0){
            winner=User.getOpponent();
            loser=User.getLoggedInUser();
            won=200;
            output=User.getOpponent().getNickname()+" won!";
        }
        else {
            winner=User.getOpponent();
            loser=User.getLoggedInUser();
            won=200;
            output=User.getLoggedInUser().getNickname()+" won!";
        }
        output+=winner.win(won, betAmount);
        output+=loser.lose(won, betAmount);
        return output;
    }

    public String startGame(){
        round1=4;
        round2=4;
        board1=new Card[21];
        board2=new Card[21];
        hole1=random.nextInt(21);
        board1[hole1]=new Card("hole");
        hole2=random.nextInt(21);
        board2[hole2]=new Card("hole");
        battleDeck1=setBattleDeck(5, User.getLoggedInUser());
        battleDeck2=setBattleDeck(5, User.getOpponent());
        User.getLoggedInUser().setHP(100);
        User.getOpponent().setHP(100);
        //probability is 2 to 1
        if (random.nextInt(6)>1){
            player=User.getLoggedInUser();
        }
        else {
            player=User.getOpponent();
        }
        extra1=null;
        extra2=null;
        four1=false;
        four2=false;
        hide1=false;
        hide2=false;
        User guest= User.getOpponent();
        User host= User.getLoggedInUser();
        int c=9;
        StringBuilder div2=new StringBuilder(repeat('#', 21*c));
        StringBuilder div=new StringBuilder(repeat('*', 21*c));
        return "The game has started.\n"+player.getNickname()+" starts\n" + div2 + "\n" +
                printBattleDeck(guest, battleDeck2, extra2, four2, hide2) + div + "\n" +
                printBoard(board2, round2, guestDamage) + div + "\n" +
                printBoard(board1, round1, hostDamage) + div + "\n" +
                printBattleDeck(host, battleDeck1, extra1, four1, hide1) + div2 + "\n";
    }

    public String nextRound(){
        User guest= User.getOpponent();
        User host= User.getLoggedInUser();
        int c=9;
        StringBuilder div2=new StringBuilder(repeat('#', 21*c));
        StringBuilder div=new StringBuilder(repeat('*', 21*c));
        return "Card Placed Successfully.\nNext turn.\n" + div2 + "\n" +
                printBattleDeck(guest, battleDeck2, extra2, four2, hide2) + div + "\n" +
                printBoard(board2, round2, guestDamage) + div + "\n" +
                printBoard(board1, round1, hostDamage) + div + "\n" +
                printBattleDeck(host, battleDeck1, extra1, four1, hide1) + div2 + "\n";
    }

    public static StringBuilder repeat(char c, int n){
        StringBuilder input=new StringBuilder();
        input.append(String.valueOf(c).repeat(Math.max(0, n)));
        return input;
    }

    public StringBuilder printBattleDeck(User user, Card[] battleDeck, Card extra, boolean four, boolean hide){
        int n=8;
        String div = String.valueOf(repeat('*', 21 * 9));
        StringBuilder output=new StringBuilder();
        output.append("Guest: \"").append(user.getNickname()).append("\", Character: \"").append(user.getCharacter());
        output.append("\", hit-point: \"").append(user.getHP()).append("\", Battle Deck:\n");
        output.append(div).append("\n").append('|');
        int count=5, c=2;
        if (four) count=4;
        for (int i = 0; i < count; i++) {
            output.append("#").append(i+1).append(":").append(repeat(' ', n-3)).append("|");
        }
        if (extra!=null)
            output.append("#").append(6).append(":").append(repeat(' ', n-3)).append("|");
        output.append("\n").append('|');
        if (!hide) {
            for (int i = 0; i < count; i++) {
                if (battleDeck[i].isSpecial()) {
                    output.append("spell").append(repeat(' ', n - 5)).append('|');
                } else {
                    output.append("DMG\\HL").append(repeat(' ', n - 6)).append('|');
                }
            }
            if (extra != null) {
                if (extra.isSpecial())
                    output.append("spell").append(repeat(' ', n - 5)).append('|');
                else
                    output.append("DMG\\HL").append(repeat(' ', n - 6)).append('|');
            }
        }
        output.append("\n").append('|');
        if (!hide) {
            for (int i = 0; i < count; i++) {
                output.append("PNT= ").append(battleDeck[i].getPoint()).append(repeat(' ', n - 7)).append('|');
            }
            if (extra != null)
                output.append("PNT= ").append(extra.getPoint()).append(repeat(' ', n - 7)).append('|');
        }
        output.append("\n").append('|');
        if (!hide) {
            for (int i = 0; i < count; i++) {
                c = 2;
                output.append("DMG= ").append(battleDeck[i].getDamage()).append(repeat(' ', n - 7)).append('|');
            }
            if (extra != null) {
                if (extra.getDamage() < 10) c = 1;
                output.append("DMG= ").append(extra.getDamage()).append(repeat(' ', n - 7)).append('|');
            }
        }
        output.append("\n").append('|');
        if (!hide) {
            for (int i = 0; i < 5; i++) {
                output.append("DUR= ").append(battleDeck[i].getDuration()).append(repeat(' ', n - 6)).append('|');
            }
            if (extra != null)
                output.append("DUR= ").append(extra.getDuration()).append(repeat(' ', n - 6)).append('|');
        }
        output.append("\n");
        return output;
    }

    public static StringBuilder printBoard(Card[] board, int round, int damage){
        StringBuilder output=new StringBuilder();
        int c=8;
        output.append("RND= ").append(round).append(repeat(' ', c-6)).append('|');
        for (int i = 0; i < 21; i++) {
            if (board[i]!=null){
                if (board[i].getName().equals("hole")){
                    output.append("HOLE!").append(repeat(' ', c-5)).append('|');
                }
                else{
                    output.append("PNT= ").append(board[i].getPoint()).append(repeat(' ', c-7)).append('|');
                }
            }
            else{
                output.append(repeat(' ', c)).append('|');
            }
        }
        int s=2;
        if (damage>99) s=3;
        else if (damage<10) s=1;
        output.append("DMG= ").append(damage).append(repeat(' ', c-5-s)).append('|');
        output.append("\n").append(repeat(' ', c)).append('|');
        for (int i = 0; i < 21; i++) {
            if (board[i]!=null){
                if (board[i].getName().equals("hole")){
                    output.append(repeat(' ', c)).append('|');
                }
                else{
                    int dmg=board[i].getDamage();
                    s=2;
                    output.append("DMG= ").append(dmg).append(repeat(' ', c-7)).append('|');
                }
            }
            else{
                output.append(repeat(' ', c)).append('|');
            }
        }
        output.append('|').append("\n");
        return output;
    }


    public Card[] getBattleDeck1() {
        return battleDeck1;
    }

    public Card[] getBattleDeck2() {
        return battleDeck2;
    }

    public Card[] getBoard1() {
        return board1;
    }

    public Card[] getBoard2() {
        return board2;
    }

    public void setPlayer(User player) {
        this.player = player;
    }

    public User getPlayer() {
        return player;
    }
}
