package View;
import Enum.Commands;
import Controller.GameController;
import Model.AI;
import Model.User;

import java.util.regex.Matcher;

public class GameMenu extends Menu{
    Matcher matcher;
    AI opp;
    boolean ai, aiTurn;
    GameController gameController=new GameController();
    public GameMenu(){
        this.ai=false;
        this.aiTurn=false;
        this.gameController=new GameController();
    }
    @Override
    public void run() {
        gameController=new GameController();
        String input;
        System.out.println("Welcome to the Game Menu.");
        System.out.println("""
                    Select game mode:
                    \t1-One player
                    \t2-Two players
                    \t3-Clan war
                    \t4-Place a bet""");
        while (true) {
            if (aiTurn) {
                input=opp.selCard(gameController.getBoard1(), gameController.getBoard2());
                aiTurn=false;
            }
            else {
                input = scanner.nextLine();
                if (ai) aiTurn=true;
            }
            boolean exit = checkCommandAndExit(input);
            if (exit)
                return;
        }
    }

    @Override
    protected boolean checkCommandAndExit(String input) {
        if (input.equals("-exit")) {
            System.exit(0);
        }
        else if ((matcher = getCommandMatcher(input, Commands.selectCard.regex)) != null) {
            System.out.println(gameController.selectCard(matcher));
        }
        else if ((matcher = getCommandMatcher(input, Commands.placeCard.regex)) != null) {
            String output=gameController.placeCard(matcher);
            System.out.println(output);
        }
        else if((matcher = getCommandMatcher(input, Commands.whichCard.regex)) != null){
            System.out.println(gameController.copyCat(matcher));
        }
        else if ((matcher = getCommandMatcher(input, Commands.selectMode.regex)) != null){
            if (matcher.group("number").equals("1")){
                ai=true;
                opp=new AI(gameController);
                User.setOpponent(opp);
                aiTurn=(gameController.getPlayer().equals(User.getOpponent()));
            }
            if (matcher.group("number").equals("3")){
                ClanMenu clanMenu=new ClanMenu();
                clanMenu.run();
            }
            System.out.println(gameController.selectModes(matcher));
        }
        else if ((matcher = getCommandMatcher(input, Commands.selectCharacter.regex)) != null){

            System.out.println(gameController.selectCharacter(matcher));
        }
        else if ((matcher = getCommandMatcher(input, Commands.logIn.regex)) != null){
            System.out.println(gameController.opponentLogIn(matcher));
        }
        else if ((matcher = getCommandMatcher(input, Commands.betAmount.regex)) != null){
            System.out.println(gameController.betting(matcher));
        }
        else if ((matcher = getCommandMatcher(input, Commands.startGame.regex)) != null){
            System.out.println(gameController.startGame());
        }
        else
            System.out.println("invalid command");
        return false;
    }
}
