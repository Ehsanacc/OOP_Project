package View;

import java.util.regex.Matcher;

import Controller.AdminController;
import Enum.Commands;
public class AdminMenu extends Menu{
    AdminController adminController=new AdminController();
    @Override
    protected boolean checkCommandAndExit(String input){
        Matcher matcher;
        if ((matcher=getCommandMatcher(input,Commands.newCard.regex))!=null){
            System.out.println(adminController.newCard(matcher));
        } else if ((matcher =getCommandMatcher(input, Commands.showCards.regex))!=null) {
            adminController.showCards();
        } else if ((matcher=getCommandMatcher(input,Commands.showCard.regex))!=null){
            System.out.println(adminController.showCard(matcher));
        } else if ((matcher =getCommandMatcher(input,Commands.editCard.regex))!=null) {
            adminController.changeCard(matcher);
        } else if ((matcher=getCommandMatcher(input,Commands.deleteCard.regex))!=null){
            adminController.deleteCard(matcher);
        } else if ((matcher=getCommandMatcher(input,Commands.showPlayers.regex))!=null){
            adminController.seePlayers();
        } else if ((matcher =getCommandMatcher(input,Commands.back.regex))!=null) {
            new RegisterMenu().run();
        } else System.out.println("invalid");
        return false;
    }
}
