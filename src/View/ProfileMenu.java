package View;

import Controller.ProfileController;
import Enum.Commands;

import java.util.regex.Matcher;

public class ProfileMenu extends Menu{
    @Override
    protected boolean checkCommandAndExit(String input) {
        ProfileController profileController = new ProfileController();
        MainMenu mainMenu = new MainMenu();
        Matcher matcher;

        if (input.trim().equals("exit"))
            System.exit(0);
        else if (input.trim().equals("Main Menu"))
            mainMenu.run();
        else if (input.trim().equals("Show information"))
            profileController.showInfo();
        else if ((matcher = getCommandMatcher(input, Commands.changeUserName.regex)) != null)
            profileController.changeUserName(matcher);
        else if ((matcher = getCommandMatcher(input, Commands.changeNickname.regex)) != null)
            profileController.changeNickName(matcher);
        else if ((matcher = getCommandMatcher(input, Commands.changePassword.regex)) != null)
            profileController.changePassWord(matcher);
        else if ((matcher = getCommandMatcher(input, Commands.changeEmail.regex)) != null)
            profileController.changeEmail(matcher);
        return false;
    }

    @Override
    public void run() {
        String input;
        while (true){
            System.out.println("Welcome to Profile Menu.");
            input = scanner.nextLine();
            boolean exit = checkCommandAndExit(input);
            if (exit)
                return;
        }
    }
}
