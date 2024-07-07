package View;

import Controller.HistoryController;
import Model.History;
import Model.User;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

import Enum.Commands;

public class HistoryMenu extends Menu{
    Matcher matcher;
    HistoryController historyController;
    private Scanner scanner=new Scanner(System.in);
    @Override
    public void run(){
        if (User.getLoggedInUser()==null){
            System.out.println("You have to login first.");
            return;
        }
        historyController=new HistoryController();
        historyController.printHistory(0);
        String input;
        while (true) {
            input = scanner.nextLine();
            boolean exit = checkCommandAndExit(input);
            if (exit)
                return;
        }
    }
    @Override
    public boolean checkCommandAndExit(String input){
        if ((matcher=getCommandMatcher(input,Commands.sortUp.regex))!=null){
            historyController.customSort(matcher,true);
        } else if ((matcher=getCommandMatcher(input,Commands.sortDown.regex))!=null){
            historyController.customSort(matcher,false);
        } else if ((matcher=getCommandMatcher(input,Commands.next.regex))!=null ||
                (matcher=getCommandMatcher(input,Commands.previous.regex))!=null ||
                (matcher=getCommandMatcher(input,Commands.previous.regex))!=null){
            historyController.moveHistory(input);
        } else if ((matcher=getCommandMatcher(input,Commands.back.regex))!=null){
            new MainMenu().run();
        }
        return false;
    }

}
