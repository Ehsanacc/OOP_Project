package Enum;

public enum Commands {
    selectCard("-Select card number (?<number>[^\s]+) player (?<player>[^\s]+)"),
    placeCard("-Placing card number (?<number>[^\s]+) in block (?<block>[^\s]+"),
    whichCard("-Card number (?<number>[^\s]+)"),
    startGame("-Start game"),
    selectMode("-Select mode number (?<number>\\d)"),
    selectCharacter("-Select character number (?<number>.+) player (?<player>.+)"),
    betAmount("-Betting amount (?<number>[^\s]+)"),
    createClan("-Create a clan(?<name>[^\s]+)"),
    myClan("-My clan"),
    allClans("-All clans"),
    joinClan("-Join (?<code>[\\d]+)"),
    playClan("-Play (?<code>[\\d]+)"),
    playClanWar("-Play ClanWar"),
    userCreate("user create -u (?<username>[^\s]+) -p (?<password>[^\s]+) -r (?<passwordConfirmation>[^\s]+) -e (?<email>[^\s]+) -n (?<nickname>[^\s]+)"),
    userCreateRandom("user create -u (?<username>[^\s]+) -p random –e (?<email>[^\s]+) -n (?<nickname>[^\s]+)"),
    logIn("user login -u (?<username>[^\s]+) -p (?<password>[^\s]+)"),
    forgotPassword("Forgot my password -u (?<username>[^\s]+)"),
    changeUserName("Profile change -u (?<username>[^\s]+)"),
    changeNickname("Profile change -u (?<nickname>[^\s]+)"),
    changePassword("profile change password -o (?<oldPassword>[^\s]+) -n (?<newPassword>[^\s]+)"),
    changeEmail("profile change -e (?<email>[^\s]+)")
    ;


    public final String regex;

    Commands(String regex) {
        this.regex = regex;
    }
}
