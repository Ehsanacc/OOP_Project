package Enum;

public enum Commands {
    selectCard("-Select card number (?<number>.+) player (?<player>.+)"),
    placeCard("-Placing card number (?<number>.+) in block (?<block>.+)"),
    whichCard("-Card number (?<number>.+)"),
    startGame("-Start game"),
    selectMode("-Select mode number (?<number>\\d)"),
    selectCharacter("-Select character number (?<number>.+) player (?<player>.+)"),
    betAmount("-Betting amount (?<number>.+)"),
    createClan("-Create a clan(?<name>.+)"),
    myClan("-My clan"),
    allClans("-All clans"),
    joinClan("-Join (?<code>\\d)"),
    playClan("-Play (?<code>\\d)"),
    playClanWar("-Play ClanWar"),
    userCreate("user create -u (?<username>.+) -p (?<password>.+) (?<passwordConfirmation>.+) –email (?<email>.+) -n (?<nickname>.+)"),
    userCreateRandom("user create -u (?<username>.+) -p random –email (?<email>.+) -n (?<nickname>.+)"),
    logIn("user login -u (?<username>.+) -p (?<password>.+)"),
    forgotPassword("Forgot my password -u (?<username>.+)"),
    changeUserName("Profile change -u (?<username>.+)"),
    changeNickname("Profile change -u (?<nickname>.+)"),
    changePassword("profile change password -o (?<oldPassword>.+) -n (?<newPassword>.+)"),
    changeEmail("profile change -e (?<email>.+)")
    ;


    public final String regex;

    Commands(String regex) {
        this.regex = regex;
    }
}
