package Enum;

public enum Commands {
    userCreate("user create -u (?<username>.+) -p (?<password>.+) (?<passwordConfirmation>.+) –email (?<email>.+) -n (?<nickname>.+)"),
    userCreateRandom("user create -u (?<username>.+) -p random –email (?<email>.+) -n (?<nickname>.+)"),
    logIn("user login -u (?<username>.+) -p (?<password>.+)"),
    forgotPassword("Forgot my password -u (?<username>.+)"),
    changeUserName("Profile change -u (?<username>.+)"),
    changeNickname("Profile change -u (?<nickname>.+)"),
    changePassword("profile change password -o (?<oldPassword>.+) -n (?<newPassword>.+)"),
    changeEmail("profile change -e (?<email>.+)"),
    selectCard("-Select card number (?<number>.+) player (?<player>.+)"),
    placeCard("-Placing card number (?<number>.+) in block (?<block>.+)"),
    whichCard("-Card number (?<number>.+)"),

    ;


    public final String regex;

    Commands(String regex) {
        this.regex = regex;
    }
}
