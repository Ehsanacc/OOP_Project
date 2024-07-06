package View;

import Model.History;
import Model.User;

import java.sql.Time;
import java.util.ArrayList;

public class HistoryMenu extends Menu{
    ArrayList<History> histories;
    private int count=8;
    private int curr=0;
    public void printHistory(int start){
        for (int i = start; i < count; i++) {
            String res="";
            if (histories.get(i).isWon())   res="Won";
            else res="Lost";
            System.out.println("|"+histories.get(i).getPrize()+"|"+histories.get(i).getOpponentLevel()+" "+
                    histories.get(i).getOpponent().getNickname()+"|"+res+"|"+histories.get(i).getTime()+"|");
        }
    }
    public void back(){
//        menu="main";
        new MainMenu().run();
    }
    public void moveHistory(String comm){
        String[] commSplit=comm.split("\s+");
        switch (commSplit[0]){
            case "next":
                curr+=count;
                printHistory(curr);
                break;
            case "previous":
                curr-=count;
                printHistory(curr);
            case "page":
                int n=Integer.parseInt(commSplit[1]);
                curr=count*n;
                printHistory(curr);
        }
    }
    public void customSort(int base, boolean up){
        switch (base){
            case 0:
                histories.sort((o1, o2) -> {
                    if (up) return o1.getTime().compareTo(o2.getTime());
                    return o2.getTime().compareTo(o1.getTime());
                });
                printHistory(curr);
                break;
            case 1:
                histories.sort((o1, o2) -> {
                    int compValue=0;
                    if (up) {
                        if (o1.isWon()) compValue=1;
                        else if (o2.isWon())compValue=-1;
                    } else {
                        if (o1.isWon()) compValue=-1;
                        else if (o2.isWon())compValue=1;
                    }
                    return compValue;
                });
                printHistory(curr);
                break;
            case 2:
                histories.sort((o1, o2) -> {
                    if (up) return o1.getOpponent().getNickname().compareTo(o2.getOpponent().getNickname());
                    return o2.getOpponent().getNickname().compareTo(o1.getOpponent().getNickname());
                });
                printHistory(curr);
                break;
            case 3:
                histories.sort((o1, o2) -> {
                    int compValue=0;
                    if (o1.getOpponentLevel()>= o2.getOpponentLevel()) compValue=1;
                    else compValue=-1;
                    if (up) return compValue;
                    return -compValue;
                });
                printHistory(curr);
                break;
        }
    }

}
