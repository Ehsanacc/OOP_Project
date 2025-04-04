package View;

import Controller.RegisterController;
import Enum.Commands;
import com.sun.tools.javac.Main;

import java.util.regex.Matcher;

public class RegisterMenu extends Menu{
    @Override
    protected boolean checkCommandAndExit(String input) {
        RegisterController registerController = new RegisterController();
        MainMenu mainMenu = new MainMenu();
        Matcher matcher;
        if (input.trim().equals("exit"))
            System.exit(0);
        else if (input.trim().equals("Main Menu"))
            mainMenu.run();
        else if (input.trim().equals("log out"))
            registerController.logOut();
        else if ((matcher = getCommandMatcher(input, Commands.userCreate.regex)) != null)
            registerController.createUser(matcher);
        else if ((matcher = getCommandMatcher(input, Commands.userCreateRandom.regex)) != null)
            registerController.createUserRandom(matcher);
        else if ((matcher = getCommandMatcher(input, Commands.logIn.regex)) != null)
            registerController.logIn(matcher, true);
        else if ((matcher = getCommandMatcher(input, Commands.forgotPassword.regex)) != null)
            registerController.forgotPassword(matcher);
        else if ((matcher = getCommandMatcher(input, Commands.adminLog.regex)) != null)
            registerController.logInAdmin(matcher,true);
        else
            System.out.println("invalid command!");

        return false;
    }

    @Override
    public void run() {
        String input;
        while (true){
            System.out.println("Welcome to Register Menu. Please chose one of the options below");
            System.out.println("""
                    \t1- user create -u <username> -p <password> -r <passwordConfirmation> –e <email> -n <nickname>
                    \t2- user create -u <username> -p random –e <email> -n <nickname>
                    \t3- user login -u <username> -p <password>
                    \t4- Forgot my password -u <username>
                    \t5- login admin <password>
                    """);
            input = scanner.nextLine();
            boolean exit = checkCommandAndExit(input);
            if (exit)
                return;
        }
    }
}