package Controller;

import Controller.DatabaseController.DataBaseController;
import Model.User;
import View.Captchscii;
import View.RegisterMenu;

import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterController {
    Scanner scanner = new Scanner(System.in);
    RegisterMenu registerMenu = new RegisterMenu();
    DataBaseController DataBaseController = new DataBaseController();
    // Sign up functions
    public void createUser(Matcher matcher){
        if (User.getLoggedInUser() != null){
            System.out.println("Some other user has already logged in.");
            registerMenu.run();
        }
        // get parts from matcher
        String username = matcher.group("username");
        String password = matcher.group("password");
        String confirmPassword = matcher.group("passwordConfirmation");
        String email = matcher.group("email");
        String nickname = matcher.group("nickname");
        Matcher questionAnswer = null;

        // take care of errors
        if (noError(username, password, confirmPassword, email, nickname))
            questionAnswer = askSecurityQuestion();
        else {
            System.out.println("Faced an error during Sign up phase. you are in Register Menu again");
            registerMenu.run();
        }

        String[] questions = new String[]{"What is your father’s name ?", "What is your favourite color ?", "What was the name of your first pet?"};
        String question = questions[Integer.parseInt(questionAnswer.group("questionNumber")) - 1];
        String answer = questionAnswer.group("answer");

        // handle captcha
        Captchscii captchscii = new Captchscii(6);
        String captcha = captchscii.getCaptcha();
        String trueAns = captchscii.getTrueStr();
        System.out.println("you need to answer this captcha correctly to create the user");
        String ans = scanner.nextLine();
        if (ans.equals(trueAns))
            System.out.println("Answered Captcha correctly.\nUser created!");
        else {
            System.out.println("Wrong Captcha! you will be sent to start as a punishment");
            registerMenu.run();
        }

        // add user to program
        User newUser = new User(username, password, nickname, email, question, answer);

        // adding user to app and database
        User.addUser(newUser);
        DataBaseController.addUser(newUser);
    }
    private Matcher askSecurityQuestion(){
        String input = null;
        String question = null;
        String answer = null;
        System.out.println("""
                User created successfully. Please choose a security question :
                • 1-What is your father’s name ?
                • 2-What is your favourite color ?
                • 3-What was the name of your first pet?""");
        input = scanner.nextLine();
        Matcher matcher = Pattern.compile("question pick -q (?<questionNumber>.+) -a (?<answer>.+) -c (?<answerConfirm>.+)").matcher(input);
        while (!matcher.group("answer").equals(matcher.group("answerConfirm"))){
            System.out.println("your answer and its confirmation should be the same!");
            input = scanner.nextLine();
            matcher = Pattern.compile("question pick -q (?<questionNumber>.+) -a (?<answer>.+) -c (?<answerConfirm>.+)").matcher(input);
        }
        return matcher;
    }
    private boolean noError(String username, String password, String confirmPassword, String email, String nickName){
        if (emptyValue(username, password, confirmPassword, email, nickName)){
            System.out.println("Please fill all requirements to Sign up");
            return false;
        }
        else if (validate(username, "^[A-Za-z0-9_]+$")){
            System.out.println("your username may only contain uppercase letters, lowercase letters, numbers, and underscores");
            System.out.println("Please act accordingly");
            return false;
        }
        else if (userExists(username)){
            System.out.println("this username already exists please try another one.");
            // TODO : can also implement a system to suggest a unique username
            return false;
        }
        else if (weakPassword(password)) {
            System.out.println("your password is a little weak try to strengthen it");
            System.out.println("your password should have the following conditions to be considered strong:");
            System.out.println("\tcontain at least 8 characters\n"+
                    "\tcontain at least one lowercase letter, one uppercase letter, and one underscore");
            return false;
        }
        else if (invalidEmail(email)){
            System.out.println("invalid email\nPlease fill both username and domain parts");
            return false;
        }
        return true;
    }
    private boolean invalidEmail(String email){
        return !email.matches("^(.+)@(.+).com$");
    }
    private boolean weakPassword(String password){
        if (password.length() < 8)
            return true;
        else return !validate(password, "^(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).+$");
    }
    private boolean userExists(String username){
        for (User user : User.getUsers())
            if (user.getUserName().equals(username))
                return true;
        return false;
    }
    private boolean validate(String input, String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return !matcher.matches();
    }
    private boolean emptyValue(String username, String password, String confirmPassword, String email, String nickName){
        return username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty() || nickName.isEmpty();
    }
    public void createUserRandom(Matcher matcher){
        if (User.getLoggedInUser() != null){
            System.out.println("Some other user has already logged in.");
            registerMenu.run();
        }
        String username = matcher.group("username");
        String email = matcher.group("email");
        String nickname = matcher.group("nickname");
        String password = generateRandomPass();
        String confirmPassword = password;
        Matcher questionAnswer = null;

        if (noError(username, password, confirmPassword, email, nickname))
            questionAnswer = askSecurityQuestion();
        else {
            System.out.println("Faced an error during Sign up phase. you are in Register Menu again");
            registerMenu.run();
        }

        String[] questions = new String[]{"What is your father’s name ?", "What is your favourite color ?", "What was the name of your first pet?"};
        String question = questions[Integer.parseInt(questionAnswer.group("questionNumber")) - 1];
        String answer = questionAnswer.group("answer");

        // handle captcha
        Captchscii captchscii = new Captchscii(6);
        String captcha = captchscii.getCaptcha();
        String trueAns = captchscii.getTrueStr();
        System.out.println("you need to answer this captcha correctly to create the user");
        String ans = scanner.nextLine();
        if (ans.equals(trueAns))
            System.out.println("Answered Captcha correctly.\nUser created!");
        else {
            System.out.println("Wrong Captcha! you will be sent to start as a punishment");
            registerMenu.run();
        }

        // add user to program
        User newUser = new User(username, password, nickname, email, question, answer);

        // adding user to app and database
        User.addUser(newUser);
        DataBaseController.addUser(newUser);
    }
    public static String generateRandomPass() {
        String uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String underscore = "_";

        StringBuilder randomString = new StringBuilder();

        // Add one uppercase letter
        randomString.append(uppercaseLetters.charAt(new Random().nextInt(uppercaseLetters.length())));

        // Add one lowercase letter
        randomString.append(lowercaseLetters.charAt(new Random().nextInt(lowercaseLetters.length())));

        // Add one digit
        randomString.append(digits.charAt(new Random().nextInt(digits.length())));

        // Add one underscore
        randomString.append(underscore);

        // Generate remaining characters (total length >= 8)
        int remainingLength = 8 - randomString.length();
        for (int i = 0; i < remainingLength; i++) {
            String allCharacters = uppercaseLetters + lowercaseLetters + digits + underscore;
            randomString.append(allCharacters.charAt(new Random().nextInt(allCharacters.length())));
        }

        return randomString.toString();
    }
    // Login functions
    public void logIn(Matcher matcher){
        if (User.getLoggedInUser() != null){
            System.out.println("Some other user has already logged in.");
            registerMenu.run();
        }
        String username = matcher.group("username");
        String password = matcher.group("password");
        User user = null;

        user = findUser(username, password);
        // TODO: implement the ban system when the user makes a mistake in logging in
        if (user == null)
            registerMenu.run();

        User.setLoggedInUser(user);
    }
    private User findUser(String username, String password){
        for (User user : User.getUsers())
            if (user.getUserName().equals(username))
                if (user.getPassword().equals(password)) {
                    System.out.println("user logged in successfully!");
                    return user;
                }
                else {
                    System.out.println("Password and Username don’t match!");
                    return null;
                }
        System.out.println("Username doesn't exist!");
        return null;
    }
    public void forgotPassword(Matcher matcher){

        String username = matcher.group("username");
        User user = null;

        for (User tempUser : User.getUsers())
            if (tempUser.getUserName().equals(username))
                user = tempUser;

        if (user == null){
            System.out.println("Username doesn't exist");
            registerMenu.run();
        }

        System.out.println("Please answer the safety question you have set before");
        System.out.println(user.getQuestion());

        String answer = scanner.nextLine();
        while (!user.getAnswer().equals(answer)){
            System.out.println("Wrong answer! Try again");
            answer = scanner.nextLine();
        }
        System.out.println("Enter your new password");
        String newPass = scanner.nextLine();
        while (weakPassword(newPass)){
            System.out.println("Weak password! Try again");
            newPass = scanner.nextLine();
        }
        user.setPassword(newPass);
        DataBaseController.updateUserPassword(user.getUserName(), user.getPassword());
    }
    // log out
    public void logOut(){
        User user = User.getLoggedInUser();
        User.setLoggedInUser(null);
        System.out.println("User with username: "+user.getUserName()+" just logged out ");
    }

}
