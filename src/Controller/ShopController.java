package Controller;

import Controller.DatabaseController.DataBaseController;
import Model.Card;
import Model.User;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class ShopController {

    private static ArrayList<Card> allCards = new ArrayList<>();
    private static ArrayList<Card> upgradeable = new ArrayList<>();


    public ShopController(){
//        System.out.println("here");
        allCards=getAllCardsFromDB();
        upgradeable=getUpgradeables();
//        System.out.println("here2");

    }

    public void showUpgradableCards(User user){}

    public ArrayList<Card> getAllCardsFromDB(){
        ArrayList<Card> all=new ArrayList<>(30);
        DataBaseController dataBaseController=new DataBaseController();
        System.out.println("here");
        return dataBaseController.getAllCards();
    }

    public ArrayList<Card> getUpgradeables(){
        ArrayList<Card> all=new ArrayList<>(30);
        return User.getLoggedInUser().getCards();
    }

    public String buyCard(Matcher matcher){
        String name = matcher.group("name");
        Card card=getCard(name, allCards);
        if (card==null){
            return "No card with this name, choose another";
        }
        if (User.getLoggedInUser().getCards().contains(card)){
            return "You already have this card";
        }
        if (User.getLoggedInUser().getGold()<card.getPrice()){
            return "You don't have enough gold";
        }
        User.getLoggedInUser().getCards().add(card);
        User.getLoggedInUser().buy(card.getPrice());
        return "You just bought card"+card.getName();
    }

    public String showCards(){
        StringBuilder output= new StringBuilder();
        output.append("All the cards you can buy\n");
        int index, rows=allCards.size()/5, col=5;
        int n=17;
        String div = String.valueOf(repeat('*', 17*5));
        output.append(div).append("\n").append('|');
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < col; j++) {
                index=5*i+j;
                output.append(allCards.get(index).getName()).append(repeat(' ', n-allCards.get(index).getName().length())).append('|');
            }
            output.append("\n").append('|');
            for (int j = 0; j < allCards.size()/rows; j++) {
                index=5*i+j;
                if (allCards.get(index).isSpecial()){
                    output.append("spell").append(repeat(' ', n - 5)).append('|');
                }
                else {
                    output.append("DMG\\HL").append(repeat(' ', n - 6)).append('|');
                }
            }
            output.append("\n").append('|');
            for (int j = 0; j <col; j++) {
                index=5*i+j;
                if (!allCards.get(index).isSpecial()){
                    output.append("ATT/DEF PNT= ").append(allCards.get(index).getPoint()).append(repeat(' ', n - 13)).append('|');
                }
                else {
                    output.append(repeat(' ', n )).append('|');
                }
            }
            output.append("\n").append('|');
            for (int j = 0; j < col; j++) {
                index=5*i+j;
                output.append("DUR= ").append(allCards.get(index).getDuration()).append(repeat(' ', n - 7)).append('|');
            }
            output.append("\n").append('|');
            for (int j = 0; j < col; j++) {
                index=5*i+j;
                if (!allCards.get(index).isSpecial())
                    output.append("DMG= ").append(allCards.get(index).getDamage()).append(repeat(' ', n - 7)).append('|');
                else {
                    output.append(repeat(' ', n )).append('|');
                }
            }
            output.append("\n").append('|');
            for (int j = 0; j < col; j++) {
                index=5*i+j;
                output.append("PRICE= ").append(allCards.get(index).getDuration()).append(repeat(' ', n - 9)).append('|');
            }
            output.append("\n");
        }
        output.append(div).append("\n").append('|');
        output.append("All the cards you can upgrade\n");
        output.append(div).append("\n").append('|');
        col=5;
        rows=upgradeable.size()/5;
        if (upgradeable.size()%5!=0) rows++;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < col; j++) {
                index=5*i+j;
                output.append(upgradeable.get(index).getName()).append(repeat(' ', n-upgradeable.get(index).getName().length())).append('|');
            }
            output.append("\n").append('|');
            for (int j = 0; j < upgradeable.size()/rows; j++) {
                output.append("DMG\\HL").append(repeat(' ', n - 6)).append('|');
            }
            output.append("\n").append('|');
            for (int j = 0; j <col; j++) {
                index=5*i+j;
                output.append("ATT/DEF PNT= ").append(upgradeable.get(index).getPoint()).append(repeat(' ', n - 15)).append('|');
            }
            output.append("\n").append('|');
            for (int j = 0; j < col; j++) {
                index=5*i+j;
                output.append("DUR= ").append(upgradeable.get(index).getDuration()).append(repeat(' ', n - 7)).append('|');
            }
            output.append("\n").append('|');
            for (int j = 0; j < col; j++) {
                index=5*i+j;
                output.append("DMG= ").append(upgradeable.get(index).getDamage()).append(repeat(' ', n - 7)).append('|');
            }
            output.append("\n").append('|');
            for (int j = 0; j < col; j++) {
                index=5*i+j;
                output.append("REQ LVL= ").append(upgradeable.get(index).getRequiredLevel()).append(repeat(' ', n - 10)).append('|');
            }
            output.append("\n").append('|');
            for (int j = 0; j < col; j++) {
                index=5*i+j;
                output.append("UP COST= ").append(upgradeable.get(index).getUpgradeCost()).append(repeat(' ', n - 11)).append('|');
            }
            output.append("\n").append('|');
            for (int j = 0; j < col; j++) {
                index=5*i+j;
                output.append("NEW DMG= ").append(upgradeable.get(index).getDamage()+upgradeable.get(index).getDuration()*4).append(repeat(' ', n - 11)).append('|');
            }
            output.append("\n").append('|');
            for (int j = 0; j < col; j++) {
                index=5*i+j;
                output.append("NEW PNT= ").append(upgradeable.get(index).getPoint()+20).append(repeat(' ', n - 11)).append('|');
            }
            output.append("\n");
        }
        return output.toString();
    }

    public static StringBuilder repeat(char c, int n){
        StringBuilder input=new StringBuilder();
        input.append(String.valueOf(c).repeat(Math.max(0, n)));
        return input;
    }

    public Card getCard(String name, ArrayList<Card> cards){
        for (Card card1:cards){
            if (card1.getName().equals(name))
                return card1;
        }
        return null;
    }

    public String upgradeCards(Matcher matcher){
        String name = matcher.group("name");
        Card card=getCard(name, upgradeable);
        if (getCard(name, allCards)==null){
            return "No card with this name, choose another";
        }
        if (!User.getLoggedInUser().getCards().contains(card)){
            return "You don't even have this card";
        }
        if (card.isUpgraded()){
            return "This card is already upgraded";
        }
        if (User.getLoggedInUser().getGold()<card.getUpgradeCost()){
            return "You don't have enough gold";
        }
        getCard(name, upgradeable).setUpgraded(true);
        User.getLoggedInUser().buy(card.getUpgradeCost());
        return "You just upgraded card"+card.getName();
    }
}
