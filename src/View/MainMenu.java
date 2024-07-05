package View;

import java.util.regex.Matcher;

import Controller.ProgramController;
import Enum.Commands;
import com.sun.tools.javac.Main;

public class MainMenu extends Menu{

    @Override
    protected boolean checkCommandAndExit(String input) {

        RegisterMenu registerMenu = new RegisterMenu();
        ProfileMenu profileMenu = new ProfileMenu();
        GameMenu gameMenu = new GameMenu();
        ShopMenu shopMenu = new ShopMenu();
        HistoryMenu historyMenu = new HistoryMenu();
        AdminMenu adminMenu = new AdminMenu();
        MainMenu mainMenu = new MainMenu();

        Matcher matcher;
        if (input.trim().equals("Exit"))
            System.exit(0);
        else if (input.trim().equals("Register Menu"))
            registerMenu.run();
        else if (input.trim().equals("Profile Menu"))
            profileMenu.run();
        else if (input.trim().equals("Game Menu"))
            gameMenu.run();
        else if (input.trim().equals("Shop Menu"))
            shopMenu.run();
        else if (input.trim().equals("History Menu"))
            historyMenu.run();
        else if (input.trim().equals("Admin Menu"))
            adminMenu.run();
        else if (input.trim().equals("Main Menu"))
            mainMenu.run();
        return false;
    }

    @Override
    public void run() {
        String input;
        while (true){
            System.out.println("Please chose one these to menus to enter");
            System.out.println("""
                    \t1. "Register Menu"
                    \t2. "Profile Menu"
                    \t3. "Game Menu"
                    \t4. "Shop Menu"
                    \t5. "History Menu"
                    \t6. "Admin Menu"
                    \t7. "Main Menu"
                    \t8. "Exit\"""");
            input = scanner.nextLine();
            boolean exit = checkCommandAndExit(input);
            if (exit)
                return;
        }
    }
}
