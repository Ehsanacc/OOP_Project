package Controller;
import Model.Card;
import Model.User;
import java.util.Random;
import java.util.regex.Matcher;

public class GameController {
    private final Random random=new Random();
    private static Card[] battleDeck1, battleDeck2, board1, board2;
    private static User player;
    private int betAmount;
    private static int round1, round2, guestDamage, hostDamage, hole1, hole2;
    private static Card extra1, extra2;
    private static boolean four1, four2, hide1, hide2;

    GameController(){
        board1=new Card[21];
        board2=new Card[21];
        hole1=random.nextInt(21);
        board1[hole1]=new Card("hole");
        hole2=random.nextInt(21);
        board2[hole2]=new Card("hole");
        battleDeck1=setBattleDeck(5, User.getLoggedInUser());
        battleDeck2=setBattleDeck(5, User.getOpponent());
        //probability is 2 to 1
        if (random.nextInt(6)>1){
            player=User.getLoggedInUser();
        }
        else {
            player=User.getOpponent();
        }
        round1=4;
        round2=4;
        extra1=null;
        extra2=null;
        hostDamage=0;
        guestDamage=0;
        four1=false;
        four2=false;
        hide1=false;
        hide2=false;
    }

    public void selectModes(Matcher matcher){

    }

    public void selectCharacter(Matcher matcher){}

    public void betting(Matcher matcher){}

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

    public static String selectCard(Matcher matcher){
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

    public static String placeCard(Matcher matcher){
        int n, b;
        Random random=new Random();
        String invalid="Invalid command";
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
        int round, d1, d2, h1, h2;
        Card[] b1, b2, deck1, deck2;
        User next;
        boolean hide;
        if (player.equals(User.getLoggedInUser())) {
            card = battleDeck1[n - 1];
            round=round1;
            next=User.getOpponent();
            b1=board1;
            b2=board2;
            d1=hostDamage;
            d2=guestDamage;
            h1=hole1;
            h2=hole2;
            deck1=battleDeck1;
            deck2=battleDeck2;
            extra=extra1;
            hide=hide2;
        }
        else{
            card = battleDeck2[n - 1];
            round=round2;
            next=User.getLoggedInUser();
            b1=board2;
            b2=board1;
            d2=hostDamage;
            d1=guestDamage;
            h2=hole1;
            h1=hole2;
            deck2=battleDeck1;
            deck1=battleDeck2;
            extra=extra2;
            hide=hide1;
        }
        if (card.getName().equals("repairman")){
            if (!b1[n-1].getName().equals("hole"))
                return "You can only place this card on a hole";
            b1[n-1]=null;
        }
        else if (card.getName().equals("power buff")){
            if (round>3) return "No cards are there to buff";
            while(true){
                int r=random.nextInt(21);
                if(b1[r]!=null){
                    b1[r].buff();
                    break;
                }
            }
        }
        else if (card.getName().equals("hurry up")){
            round1--;
            round2--;
        }
        else if (card.getName().equals("poison")){
            Card.poison(deck2);
        }
        else if (card.getName().equals("bandit")){
            Card.steal(extra, deck2);
        }
        else if (card.getName().equals("copy cat")) return "Which card?";
        else if (card.getName().equals("cover-up")){
            Card.scramble(deck2);
            hide=true;
        }
        else if (!setCard(b1, b2, card, b, d1, d2)){
            return "You can't place a card on a hole";
        }
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
        if (!card.getName().equals("bandit") && extra!=null) extra=null;
        if (!card.getName().equals("cover-up") && hide) hide=false;
        player=next;
        round--;
        if (round<0){
            return endOfRound();
        }
        return nextRound();
    }

    public static String copyCat(Matcher matcher){
        int n;
        String invalid="Invalid command";
        try {
            n=Integer.parseInt(matcher.group("number"));
        }
        catch (Exception e){
            return invalid;
        }
        if (n<1) return invalid;
        Card card, extra;
        int round;
        User next;
        if (player.equals(User.getLoggedInUser())) {
            if (n>battleDeck1.length) return invalid;
            card = battleDeck1[n - 1];
            round=round1;
            next=User.getOpponent();
            extra=extra1;
        }
        else{
            if (n>battleDeck2.length) return invalid;
            card = battleDeck2[n - 1];
            round=round2;
            next=User.getLoggedInUser();
            extra=extra2;
        }
        extra=card;
        player=next;
        round--;
        if (round<0){
            return endOfRound();
        }
        return nextRound();
    }

    public static boolean setCard(Card[] board1, Card[] board2, Card card, int b, int dmg1, int dmg2){
        int dur= card.getDuration();
        for (int i = 0; i < dur; i++) {
            if (board1[b+i - 1].getName().equals("hole")) {
                return false;
            }
        }
        for (int i = b; i < b+dur; i++) {
            board1[i-1]= card;
            if (board2[i-1]!=null || !board2[i-1].getName().equals("hole")){
                Card card1=board2[i-1];
                if (card1.getName().equals("healer")) continue;
                if (card1.getPoint()> card.getPoint() && card1.getDamage()!=0){
                    board1[i-1].setDamage(0);
                }
                else if (card1.getPoint()< card.getPoint()){
                    dmg2-=card1.getDamage()/card1.getDuration();
                    board2[i-1].setDamage(0);
                }
                else{
                    dmg2-=card1.getDamage()/card1.getDuration();
                    board2[i-1].setDamage(0);
                    board1[i-1].setDamage(0);
                }
            }
            dmg1+=card.getDamage()/card.getDuration();
        }
        return true;
    }

    public static String endOfRound(){
        return null;
    }

    public static String nextRound(){
        User guest= User.getOpponent();
        User host= User.getLoggedInUser();
        int c=9;
        StringBuilder div2=new StringBuilder(repeat('#', 21*c));
        StringBuilder div=new StringBuilder(repeat('*', 21*c));
        StringBuilder output= new StringBuilder("Card Placed Successfully.\nNext turn.\n");
        output.append(div2).append("\n");
        output.append(printBattleDeck(guest, battleDeck2, extra2, four2, hide2)).append(div).append("\n");
        output.append(printBoard(board2, round2, guestDamage)).append(div).append("\n");
        output.append(printBoard(board1, round1, hostDamage)).append(div).append("\n");
        output.append(printBattleDeck(host, battleDeck1, extra1, four1, hide1)).append(div2).append("\n");
        return output.toString();
    }

    public static StringBuilder repeat(char c, int n){
        StringBuilder input=new StringBuilder();
        for (int i = 0; i < n; i++) {
            input.append(c);
        }
        return input;
    }

    public static StringBuilder printBattleDeck(User user, Card[] battleDeck, Card extra, boolean four, boolean hide){
        int n=8;
        StringBuilder div=new StringBuilder(repeat('*', 21*9));
        StringBuilder output=new StringBuilder();
        output.append("Guest: \"").append(user.getUserName()).append("\", Character: \"").append(user.getCharacter());
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
                if (battleDeck[i].getDamage() < 10) c = 1;
                output.append("DMG= ").append(battleDeck[i].getDamage()).append(repeat(' ', n - 5 - c)).append('|');
            }
            if (extra != null) {
                if (extra.getDamage() < 10) c = 1;
                output.append("DMG= ").append(extra.getDamage()).append(repeat(' ', n - 5 - c)).append('|');
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
                    int dmg=board[i].getDamage()/board[i].getDuration();
                    s=2;
                    if (dmg<10) s=1;
                    output.append("DMG= ").append(dmg).append(repeat(' ', c-5-s)).append('|');
                }
            }
            else{
                output.append(repeat(' ', c)).append('|');
            }
        }
        output.append("\n");
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
}
