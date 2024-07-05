package View;
import Enum.Commands;
import Controller.GameController;
import java.util.regex.Matcher;

public class GameMenu extends Menu{
    Matcher matcher;
    @Override
    protected boolean checkCommandAndExit(String input) {
        if (input.equals("-exit")) {
            System.exit(0);
        }
        else if ((matcher = getCommandMatcher(input, Commands.selectCard.regex)) != null) {
            System.out.println(GameController.selectCard(matcher));
        }
        else if ((matcher = getCommandMatcher(input, Commands.placeCard.regex)) != null) {
            String output=GameController.placeCard(matcher);
            System.out.println(output);
        }
        else if((matcher = getCommandMatcher(input, Commands.whichCard.regex)) != null){
            System.out.println(GameController.copyCat(matcher));
        }
        else
            System.out.println("invalid command");
    return false;
    }
}
