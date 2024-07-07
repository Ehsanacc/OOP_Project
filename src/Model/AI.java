package Model;

import Controller.GameController;
import View.GameMenu;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.Random;

public class AI extends User{
    public AI(GameController gameController) {
        super(null, null, "AI", null, null, null);
        gameController=gameController;
    }
    private int n;
    private int b;
    private GameController gameController;
    private int defeats=0;
    private int total=4;
    Random random =new Random();

    public void play (){
        if (defeats==total){
//            bossFightSet();
        }
    }
    public void countWins(String res){
        String[] input= res.split("\s*");
        String nickname=User.getLoggedInUser().getNickname();
        switch (input[0]){
            case "AI":
                System.out.println("You lost and have to start again. =D");
                new GameMenu().run();
                break;
            default:
                System.out.println("Starting next round");
                defeats++;
                GameMenu gameMenu=new GameMenu();
//                gameMenu.setAi(true);
                gameMenu.run();
                break;
        }
    }

    public String selCard(Card[] playerBoard,Card[] aiBoard){
        Card card = null;
        int ruinedDur=0;
        int i=0;
        int place=0;
        while (i<playerBoard.length){
            Card card1=playerBoard[i];
            for (Card card2: gameController.getBattleDeck1()) {
                if (card1.getPoint() < card2.getPoint() && countNulls(aiBoard,i)>=card2.getDuration() && aiBoard[i]==null){
                    if (countRuinDur(playerBoard,card2,i)>ruinedDur) {
                        card = card2;
                        ruinedDur = countRuinDur(playerBoard,card2,i);
                        place=i;
                    }
                }
            }
            i+=card1.getDuration();
        }
        if (ruinedDur==0){
            boolean specialChoice=false;
            boolean findDur=false;
            for (Card card1: gameController.getBattleDeck1()){
                if (card1.isSpecial()){
                    card=card1;
                    if (card1.getName().equals("shield")){
                        int maxOppHit=0;
                        place=0;
                        int c=0;
                        for (Card card2: playerBoard){
                            if (card2.getPoint()>maxOppHit){
                                maxOppHit=card2.getPoint();
                                place=c;
                            }
                            c++;
                        }
                        specialChoice=true;
                        break;
                    }
                    else if (card1.getName().equals("healer") || card1.getName().equals("hole digger")){
                        int c=0;
                        for (Card card2: aiBoard){
                            if (card2==null){
                                place=c;
                                break;
                            }
                            c++;
                        }
                        specialChoice=true;
                        break;
                    }
                    else if (card1.getName().equals("repairman")) {
                        boolean holeExist=false;
                        int c=0;
                        for (Card cardHole: aiBoard){
                            if (cardHole.getName().equals("hole")){
                                place=c;
                                card=card1;
                                holeExist=true;
                                specialChoice=true;
                                break;
                            }
                            c++;
                        }
                        if (holeExist) break;
                    }
                    else if (card1.getName().equals("hurry up") ) {
                        card=card1;
                        specialChoice=true;
                        break;
                    }
                    else if (card1.getName().equals("bandit")) {
                        card=card1;
                        specialChoice=true;
                        break;
                    }
                    else if (card1.getName().equals("poison")){
                        card=card1;
                        specialChoice=true;
                        break;
                    }
                    else if (card1.getName().equals("copycat")){
                        card=card1;
                        specialChoice=true;
                        break;
                    }
                    else { //cover-up
                        card=card1;
                        specialChoice=true;
                        break;
                    }
                }
                if (specialChoice) break;
            }
            if (!specialChoice){
                i = 0;
                while (i < playerBoard.length) {
                    if (playerBoard[i]!=null) i++;
                    else {
                        int oppNulls = countNulls(playerBoard, i);
                        for (Card card1 : gameController.getBattleDeck1()) {
                            if (card1.getDuration() <= oppNulls) {
                                findDur = true;
                                card = card1;
                                break;
                            }
                        }
                        i += oppNulls;
                        if (findDur) break;
                    }
                }
            }
            if (!findDur){
                int minDamage=Integer.MAX_VALUE;
                for (Card card1: gameController.getBattleDeck1()){
                    if (card1.getDamage()<minDamage){
                        minDamage=card1.getDamage();
                        card=card1;
                    }
                }
            }
        }
        Card[] cards1 = gameController.getBattleDeck1();
        List<Card> abcd  = Arrays.asList(cards1);
        n = abcd.indexOf(card);
        b=place;

        return "-Select card number "+n+" player 2";
    }
    public String placeCard(){
        return"-Placing card number "+n+" in block "+b;
    }
    private int countNulls(Card[] board, int start){
        int count=0;
        for (int i=start; i<board.length; i++){
            if (board[i]==null) count++;
            else return count;
        }
        return count;
    }
    private int countRuinDur(Card[] plBoard,Card test, int start){
        int plCardDur=plBoard[start].getDuration();
        int cardPlace=0;
        int aiCardDur=test.getDuration();
        for (Card card:plBoard){
            if (card.getName().equals(plBoard[start].getName())) break;
            cardPlace++;
        }
        int ruinedDur= plCardDur-(start-cardPlace);
        if (aiCardDur>ruinedDur){
            int currPlace=cardPlace+plCardDur;
            Card nextCard=plBoard[currPlace];
            int remainedDur=aiCardDur-ruinedDur;
            while (remainedDur>0 && nextCard==null){
                currPlace++;
                nextCard=plBoard[currPlace];
                remainedDur--;
            }
            if (remainedDur==0) return ruinedDur;
            if (nextCard.getPoint()<test.getPoint()) return ruinedDur+remainedDur;


        } else if (aiCardDur<ruinedDur) {
            ruinedDur=aiCardDur;
        }
        return ruinedDur;
    }
    public void bossFightSet(Card[] aiBoard){
        cards.sort((o1, o2) ->{
            if (o1.getDamage()> o2.getDamage()){
                return 1;
            }
            if (o1.getDamage()== o2.getDamage()){
                if (o1.getPoint()>= o2.getPoint()) return 1;
                return -1;
            }
            return -1;
        });
        int fill=0;
        int c=0;
        boolean notFilled=false;
        while (fill< aiBoard.length) {
            if (countNulls(aiBoard, fill) > cards.get(c).getDuration()) {
                for (int j = 0; j < cards.get(c).getDuration(); j++) aiBoard[j] = cards.get(c);
                fill += cards.get(c).getDuration();
                c++;
            }
            if (c==cards.size() && fill< aiBoard.length){
                notFilled=true;
                break;
            }
        }
        if (notFilled){
            int leftDur=aiBoard.length-fill;
            for (Card card:cards){
                if (card.getDuration()==leftDur){
                    for (int i = fill; i < leftDur+fill; i++) {
                        aiBoard[i]=card;
                    }
                    break;
                }
            }
        }
    }
    public String bossFightBuff(Card[] aiBoard){
        int f=random.nextInt(21);
        int s=random.nextInt(21);
        return "-Buff Cards "+f+" and "+s;
    }

}
