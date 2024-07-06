package View;

import Model.History;

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
                    histories.get(i).getOpponentName()+"|"+res+"|"+histories.get(i).getDate()+"|");
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
                    if (up) return o1.getDate().compareTo(o2.getDate());
                    return o2.getDate().compareTo(o1.getDate());
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
                    if (up) return o1.getOpponentName().compareTo(o2.getOpponentName());
                    return o2.getOpponentName().compareTo(o1.getOpponentName());
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
