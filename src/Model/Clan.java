package Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;

public class Clan {
    private User chief;

    private ArrayList<User> members = new ArrayList<>();
    private ArrayList<User> oppClanShuffle = new ArrayList<>();
    private ArrayList<User> clanShuffle = new ArrayList<>();
    private String name;
    int code;
    int wins;
    int defeats;
    int points;
    boolean inBattle;

    public Clan(User chief, String name, int code) {
        this.chief = chief;
        this.name = name;
        this.code = code;
    }

    public String joinClan(User user){
//        String invalid="Invalid command";
        members.add(user);
        user.setClanCode(code);
        return "Joined clan successfully.";
    }
    public void createClan(Matcher matcher){}

    public User getChief() {
        return chief;
    }

    public String showMyClan(){
        String battle;
        if (inBattle) battle="Clan in is war.";
        else battle="No war for now";
        return "Name: "+ name+" Code: "+code+"\n"+
                "Number of Members: "+members.size()+"\n"+
                "Wins: "+wins+" Losts: "+defeats+" Score: "+points+"\n"+
                battle;
    }
    public String play(Clan oppClan){
        inBattle=true;
        ArrayList<User> oppMem=oppClan.getMembers();
        arrCopy(oppMem,oppClanShuffle);
        arrCopy(members,clanShuffle);
        Collections.shuffle(oppClanShuffle);
        Collections.shuffle(clanShuffle);
        return "Started battle with "+oppClan.name;
//        for (int i=0; i<memCopy.size(); i++){
////            game(memCopy.get(i),oppMemCopy.get(i%oppMemCopy.size()));
//        }

    }
    public int getCode(){return code;}
    public String getName(){return  name;}
    public ArrayList<User> getMembers() {
        return members;
    }
    private void arrCopy(ArrayList<User> source,ArrayList<User> dest){
        dest.removeAll(dest);
        for (User user: source){
            dest.add(user);
        }
    }
    public String playMe(User user){
        if (inBattle){
            if (!user.isDidClanWar()) {
                int i = clanShuffle.indexOf(user);
//              return user.battle(oppClanShuffle.get(i));
            } else return "You have already done your part rest.";
        }
        return "All in peace for now.";
    }


}
