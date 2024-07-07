package Controller;

import Controller.DatabaseController.DataBaseController;
import Model.Card;
import Model.UserShow;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;

public class AdminController {
    DataBaseController dataBaseController=new DataBaseController();
    ArrayList<Card> allCards;
    Scanner scanner=new Scanner(System.in);
    ArrayList<UserShow> userShows=new ArrayList<>();
    ArrayList<Card> adminCards=new ArrayList<>();
    private int n;
    private Card orgCard;

    public AdminController() {
        allCards=dataBaseController.getAllCards();
        for (Card card: allCards){
            if (card.isAdmin()){
                adminCards.add(card);
            }
        }
    }
    public void seePlayers(){
        userShows= dataBaseController.getAllUsers();
        for (int i = 0; i < userShows.size(); i++) {
            System.out.println("| Name: "+userShows.get(i).getUsername()+ " | Level: "+
                    userShows.get(i).getLevel()+" | Gold: "+ userShows.get(i).getGold()+" |");
        }
    }
    public String showCard(Matcher matcher){
        n= Integer.parseInt(matcher.group("n"));
        return "| Name: "+adminCards.get(n).getName()+ " | Points: "+
                adminCards.get(n).getPoint()+" | Duration: "+ adminCards.get(n).getDuration()+" | "+
                "Damage: "+adminCards.get(n).getDamage()+" | Upgrade Level: "+adminCards.get(n).getRequiredLevel()+
                " | Upgrade Cost: "+adminCards.get(n).getUpgradeCost()+" |";
    }
    public String showCardInt(int m){
        return "| Name: "+adminCards.get(m).getName()+ " | Points: "+
                adminCards.get(m).getPoint()+" | Duration: "+ adminCards.get(m).getDuration()+" | "+
                "Damage: "+adminCards.get(m).getDamage()+" | Upgrade Level: "+adminCards.get(m).getRequiredLevel()+
                " | Upgrade Cost: "+adminCards.get(m).getUpgradeCost()+" |";
    }
    public void showCards(){
        System.out.println("List of admin cards:");
        for (int i = 0; i < adminCards.size(); i++) {
            System.out.println("\t"+i+" "+showCardInt(i));
        }
    }
//    public void showCards(){
//        StringBuilder output= new StringBuilder();
//        output.append("Cards:\n");
//        int index, rows=adminCards.size()/5, col=5;
//        int n=17;
//        String div = String.valueOf(repeat('*', 17*5));
//        output.append(div).append("\n").append('|');
//        for (int i = 0; i < rows; i++) {
//            output.append(i+"\n");
//            for (int j = 0; j < col; j++) {
//                index=5*i+j;
//                output.append(adminCards.get(index).getName()).append(repeat(' ', n-adminCards.get(index).getName().length())).append('|');
//            }
//            output.append("\n").append('|');
//            for (int j = 0; j < adminCards.size()/rows; j++) {
//                index=5*i+j;
//                if (adminCards.get(index).isSpecial()){
//                    output.append("spell").append(repeat(' ', n - 5)).append('|');
//                }
//                else {
//                    output.append("DMG\\HL").append(repeat(' ', n - 6)).append('|');
//                }
//            }
//            output.append("\n").append('|');
//            for (int j = 0; j <col; j++) {
//                index=5*i+j;
//                if (!adminCards.get(index).isSpecial()){
//                    output.append("ATT/DEF PNT= ").append(adminCards.get(index).getPoint()).append(repeat(' ', n - 13)).append('|');
//                }
//                else {
//                    output.append(repeat(' ', n )).append('|');
//                }
//            }
//            output.append("\n").append('|');
//            for (int j = 0; j < col; j++) {
//                index=5*i+j;
//                output.append("DUR= ").append(adminCards.get(index).getDuration()).append(repeat(' ', n - 7)).append('|');
//            }
//            output.append("\n").append('|');
//            for (int j = 0; j < col; j++) {
//                index=5*i+j;
//                if (!adminCards.get(index).isSpecial())
//                    output.append("DMG= ").append(adminCards.get(index).getDamage()).append(repeat(' ', n - 7)).append('|');
//                else {
//                    output.append(repeat(' ', n )).append('|');
//                }
//            }
//            output.append("\n").append('|');
//            for (int j = 0; j < col; j++) {
//                index=5*i+j;
//                output.append("PRICE= ").append(adminCards.get(index).getDuration()).append(repeat(' ', n - 9)).append('|');
//            }
//            output.append("\n");
//        }
//        output.append(div).append("\n").append('|');
//        return output.toString();
//    }


    public static StringBuilder repeat(char c, int n){
        StringBuilder input=new StringBuilder();
        input.append(String.valueOf(c).repeat(Math.max(0, n)));
        return input;
    }

    public void changeCard(Matcher matcher){
        orgCard=adminCards.get(n);
        Card cardCopy=adminCards.get(n);
        String name;
        int attack;
        int duration;
        int plDamage;
        int level;
        int cost;
        String name1= orgCard.getName();
        int attack1= orgCard.getPoint();
        int duration1= orgCard.getDuration();
        int plDamage1= orgCard.getDamage();
        int level1= orgCard.getRequiredLevel();
        int cost1= orgCard.getPrice();
        int type=-1;
        String in=matcher.group("type");
        if (in.equals("back")){
            System.out.println("exiting editing menu");
            return;
        }
        type=Integer.parseInt(in);
        switch (type){
            case 0:
                name=scanner.nextLine();
                cardCopy.setName(name);
                break;
            case 1:
                attack=scanner.nextInt();
                cardCopy.setPoint(attack);
                break;
            case 2:
                duration=scanner.nextInt();
                cardCopy.setDuration(duration);
                break;
            case 3:
                plDamage=scanner.nextInt();
                cardCopy.setDamage(plDamage);
                break;
            case 4:
                level=scanner.nextInt();
                cardCopy.setRequiredLevel(level);
                break;
            case 5:
                cost=scanner.nextInt();
                cardCopy.setPrice(cost);
                break;
        }
        System.out.println("Are you sure you want to edit this card? (y/n)");
        scanner.nextLine();
        String comm=scanner.nextLine();
        if (comm.equals("y")){
            System.out.println("Card changed");
            adminCards.remove(n);
            adminCards.add(n,cardCopy);
//            dataBaseController.changeCard(cardCopy.getName());
        } else {
            System.out.println("No changes were applied");
            adminCards.get(n).setPrice(cost1);
            adminCards.get(n).setDamage(plDamage1);
            adminCards.get(n).setName(name1);
            adminCards.get(n).setPoint(attack1);
            adminCards.get(n).setRequiredLevel(level1);
            adminCards.get(n).setDuration(duration1);
        }
    }

    public void deleteCard(Matcher matcher){
        int i=Integer.parseInt(matcher.group("n"));
        System.out.println("Are you sure you want to delete this card? (y/n)");
        String comm= scanner.nextLine();
        if (comm.equals("y")){
            adminCards.remove(i);
        } else {
            System.out.println("No cards were deleted.");
        }
    }
    public String newCard(Matcher matcher){
        try{
            String name = matcher.group("name");
            int attack = Integer.parseInt(matcher.group("attack"));
            int duration = Integer.parseInt(matcher.group("duration"));
            int plDamage = Integer.parseInt(matcher.group("damage"));
            int level = Integer.parseInt(matcher.group("upLevel"));
            int cost = Integer.parseInt(matcher.group("cost"));
            Card newCard = new Card(name, attack, plDamage, duration, false, -1, level, false, cost, 0);
            newCard.setAdmin(true);
            adminCards.add(newCard);
            dataBaseController.addCard(newCard);
            return "Card added successfully.";
        }
        catch (Exception e){
            return "Card addition failed.";
        }
    }
}
