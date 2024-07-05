package View;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menu {
    protected static final Scanner scanner = new Scanner(System.in);

    public void run() {
        String input;
        while (true) {
            input = scanner.nextLine();
            boolean exit = checkCommandAndExit(input);
            if (exit)
                return;
        }
    }

    protected boolean checkCommandAndExit(String input) {
        return true;
    }

    protected Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}

