package View;

import Controller.GameController;
import Controller.ShopController;
import Model.AI;
import Model.Card;
import Model.User;
import Enum.Commands;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class ShopMenu extends Menu{

    ShopController shopController;
    @Override
    public void run() {
        if (User.getLoggedInUser()==null){
            System.out.println("You have to login first");
            return;
        }
        shopController=new ShopController();
        System.out.println(shopController.showCards());
        String input;
        while (true){
            System.out.println("Welcome to the Shop. You can buy or upgrade a card with the instructions below:");
            System.out.println("""
                    \t1- buy card -n <name>
                    \t2- upgrade card -n <name>
                    """);
            input = scanner.nextLine();
            boolean exit = checkCommandAndExit(input);
            if (exit)
                return;
        }
    }
    @Override
    protected boolean checkCommandAndExit(String input) {
        Matcher matcher;
        if (input.equals("-exit")) {
            System.exit(0);
        }
        else if ((matcher = getCommandMatcher(input, Commands.buyCard.regex)) != null){
            System.out.println(shopController.buyCard(matcher));
        }
        else if ((matcher = getCommandMatcher(input, Commands.upgradeCard.regex)) != null){
            System.out.println(shopController.upgradeCards(matcher));
        } else if ((matcher=getCommandMatcher(input,Commands.back.regex))!=null){
            new MainMenu().run();
        }
        else
            System.out.println("invalid command");
        return false;
    }


}
