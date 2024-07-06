package View;

import com.sun.tools.javac.Main;

import java.util.regex.Matcher;

public class MainMenu extends Menu{

    @Override
    protected boolean checkCommandAndExit(String input) {

        Matcher matcher;
        if (input.trim().equals("Exit"))
            System.exit(0);
        else if (input.trim().equals("-Register Menu"))
            new RegisterMenu().run();
        else if (input.trim().equals("-Profile Menu"))
            new ProfileMenu().run();
        else if (input.trim().equals("-Game Menu"))
            new GameMenu().run();
        else if (input.trim().equals("-Shop Menu"))
            new ShopMenu().run();
        else if (input.trim().equals("-History Menu"))
            new HistoryMenu().run();
        else if (input.trim().equals("-Admin Menu"))
            new AdminMenu().run();
        else if (input.trim().equals("-Main Menu"))
            new MainMenu().run();
        return false;
    }

    @Override
    public void run() {
        String input;
        while (true){
            System.out.println("Please chose one these to menus to enter");
            System.out.println("""
                    \t1. "-Register Menu"
                    \t2. "-Profile Menu"
                    \t3. "-Game Menu"
                    \t4. "-Shop Menu"
                    \t5. "-History Menu"
                    \t6. "-Admin Menu"
                    \t7. "-Main Menu"
                    \t8. "-Exit\"""");
            input = scanner.nextLine();
            boolean exit = checkCommandAndExit(input);
            if (exit)
                return;
        }
    }
}
