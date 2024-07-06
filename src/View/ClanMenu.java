package View;

import Model.Clan;
import Model.User;
import Enum.Commands;

import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;

public class ClanMenu extends Menu{
    Random random =new Random();
    Matcher matcher;
    private static ArrayList<Clan> clans = new ArrayList<>();
    private int cost=10;
    Clan clan;
    private Clan currClan;
    public void joinClan(Matcher matcher, User user){
        String invalid="Invalid command";
        boolean joined=false;
        int ind;
        ind = Integer.parseInt(matcher.group("code"));
        for (Clan clan: clans){
            if (clan.getCode()==ind){
                currClan=clan;
                joined=true;
                System.out.println(clan.joinClan(user));

                break;
            }
        }
        if (joined) System.out.println(currClan.showMyClan());
        else System.out.println("Unable to join clan: wrong code");
    }
    public void allClans(){
        System.out.println("List of clans:");
        for (Clan clan: clans){
            System.out.println("\t Name: "+clan.getName()+" Code: "+clan.getCode());
        }
    }
    public String createClan(Matcher matcher,User user){
        String name= matcher.group("name");
        for (Clan clan: clans){
            if (clan.getName().equals(name)){
                return "Name has already been used.";
            }
        }
        clan=new Clan(user,name, random.nextInt());
        user.setClanCode(clan.getCode());
        user.setGold(user.getGold()-cost);
        clans.add(clan);
        return "Clan created successfully.";
    }
    public String play(Matcher matcher){
        int code=Integer.parseInt(matcher.group("code"));
        Clan oppCLan = null;
        boolean clanFound=false;
        for (Clan clan1: clans){
            if (clan1.getCode()==code){
                oppCLan=clan1;
                clanFound=true;
                break;
            }
        }
        if (clanFound){
            if (currClan!=null) return currClan.play(oppCLan);
            else return "You don't have a clan.";
        }
        else return "Invalid clan code.";
    }

    @Override
    public boolean checkCommandAndExit(String input){
        if ((matcher=getCommandMatcher(input, Commands.joinClan.regex))!=null){
            joinClan(matcher,User.getLoggedInUser());
        } else if ((matcher=getCommandMatcher(input,Commands.myClan.regex))!=null) {
            System.out.println( currClan.showMyClan());
        } else if ((matcher=getCommandMatcher(input,Commands.createClan.regex))!=null) {
            System.out.println(createClan(matcher,User.getLoggedInUser()));
        } else if ((matcher=getCommandMatcher(input,Commands.playClan.regex))!=null) {
            if (User.getLoggedInUser().getNickname().equals(currClan.getChief().getNickname())){
                System.out.println(play(matcher));
            } else System.out.println("Only Chief can start clan war");
        } else if ((matcher=getCommandMatcher(input,Commands.playClanWar.regex))!=null) {
            System.out.println(currClan.playMe(User.getLoggedInUser()));
        }
        return false;
    }

    public static ArrayList<Clan> getClans() {
        return clans;
    }
}
