package Controller;

import Controller.DatabaseController.DataBaseController;
import Model.User;
import View.Captchscii;
import View.ProfileMenu;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileController {
    Scanner scanner = new Scanner(System.in);
    ProfileMenu profileMenu = new ProfileMenu();
    public void showInfo(){
        if (User.getLoggedInUser() == null){
            System.out.println("There is no user logged in! Please log in first in the Register Menu");
            ProfileMenu profileMenu = new ProfileMenu();
            profileMenu.run();
        }
        User user = User.getLoggedInUser();
        System.out.println("User information:");
        System.out.println(user.toString());
    }
    public void changeUserName(Matcher matcher){
        String newUserName = matcher.group("username");
        DataBaseController dataBaseController = new DataBaseController();
        ArrayList<String> allUserNames = dataBaseController.getAllUsernames();

        if (newUserName.isEmpty()){
            System.out.println("username field cannot be empty");
            profileMenu.run();
        }
        if (validate(newUserName, "^[A-Za-z0-9_]+$")){
            System.out.println("username may only be made from lowercase, uppercase, numbers and under scores");
            profileMenu.run();
        }
        for (String username : allUserNames)
            if (username.equals(newUserName)){
                System.out.println("This username already exists!");
                profileMenu.run();
            }

        dataBaseController.updateUserUserName(User.getLoggedInUser().getUserName(), newUserName);
    }
    private boolean validate(String input, String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return !matcher.matches();
    }
    public void changeNickName(Matcher matcher){
        String nickname = matcher.group("nickname");
        DataBaseController dataBaseController = new DataBaseController();

        if (nickname.isEmpty()){
            System.out.println("nickname field cannot be empty");
            profileMenu.run();
        }

        dataBaseController.updateUserNickname(User.getLoggedInUser().getNickname(), nickname);
    }

    public void changePassWord(Matcher matcher){
        String oldPass = matcher.group("oldPassword");
        String newPass = matcher.group("newPassword");

        if (!User.getLoggedInUser().getPassword().equals(oldPass)){
            System.out.println("Current password is incorrect!");
            profileMenu.run();
        }

        if (weakPassword(newPass)){
            System.out.println("your new password is weak!");
            profileMenu.run();
        }

        Captchscii captchscii = new Captchscii(6);
        String captcha = captchscii.getCaptcha();
        String trueAns = captchscii.getTrueStr();

        System.out.println("please fill the captcha");
        System.out.println(captcha);
        System.out.println(trueAns);
        String ans = scanner.nextLine();


        if (!ans.equals(trueAns)){
            System.out.println("wrong captcha");
            profileMenu.run();
        }

        System.out.println("Please enter your new password again");
        String confirmPass = scanner.nextLine();

        if (!confirmPass.equals(newPass)){
            System.out.println("passwords do not mach");
            profileMenu.run();
        }

        DataBaseController dataBaseController = new DataBaseController();
        dataBaseController.updateUserPassword(User.getLoggedInUser().getUserName(), newPass);
    }
    private boolean weakPassword(String password){
        if (password.length() < 8)
            return true;
        else return validate(password, "^(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).+$");
    }

    public void changeEmail(Matcher matcher){
        String newEmail = matcher.group("email");
        if (invalidEmail(newEmail)){
            System.out.println("invalid email\nPlease fill both username and domain parts");
            profileMenu.run();
        }
        DataBaseController dataBaseController = new DataBaseController();
        dataBaseController.updateUserEmail(User.getLoggedInUser().getUserName(), newEmail);
    }

    private boolean invalidEmail(String email){
        return !email.matches("^(.+)@(.+).com$");
    }
}
