package Controller;

import Controller.DatabaseController.DataBaseController;
import Model.History;
import Model.User;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class HistoryController {
    DataBaseController dataBaseController=new DataBaseController();

    public HistoryController() {
        dataBaseController.createHistory(User.getLoggedInUser().getUserName());
    }

    ArrayList<History> histories= dataBaseController.getUserHistory(User.getLoggedInUser().getUserName());
    private int count=8;
    private int curr=0;
    public void printHistory(int start){
        if (histories.size() > 0){
            for (int i = start; i < count; i++) {
                String res="";
                if (histories.get(i).isWon())   res="Won";
                else res="Lost";
                System.out.println("|"+histories.get(i).getPrize()+"|"+histories.get(i).getOpponentLevel()+" "+
                        histories.get(i).getOpponentName()+"|"+res+"|"+histories.get(i).getDate()+"|");
            }
        } else {
            System.out.println("No history;");
        }
    }
    //    public void back(){
//        menu="main";
//    }
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
    public void customSort(Matcher matcher, boolean up){
        int base=Integer.parseInt(matcher.group("type"));
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


    private int gameInPage;

    public void showRecentGames(Matcher matcher){}
    private void loadFromDB(){}
    private void nextPage(){}
}
