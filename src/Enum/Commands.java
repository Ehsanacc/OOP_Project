package Enum;

public enum Commands {
    adminLog("-login admin (?<pass>[^\s]+)"),
    allClans("-All clans"),
    back("back"),
    betAmount("-Betting amount (?<number>[^\s]+)"),
    changeEmail("profile change -e (?<email>[^\s]+)"),
    changeNickname("Profile change -n (?<nickname>[^\s]+)"),
    changePassword("profile change password -o (?<oldPassword>[^\s]+) -n (?<newPassword>[^\s]+)"),
    changeUserName("Profile change -u (?<username>[^\s]+)"),
    createClan("-Create a clan(?<name>[^\s]+)"),
    forgotPassword("Forgot my password -u (?<username>[^\s]+)"),
    joinClan("-Join (?<code>[\\d]+)"),
    logIn("user login -u (?<username>[^\s]+) -p (?<password>[^\s]+)"),
    myClan("-My clan"),
    next("next"),
    pageNum("page (?<n>\\d)"),
    placeCard("-Placing card number (?<number>[^\s]+) in block (?<block>[^\s]+"),
    playClan("-Play (?<code>[\\d]+)"),
    playClanWar("-Play ClanWar"),
    previous("previous"),
    selectCard("-Select card number (?<number>[^\s]+) player (?<player>[^\s]+)"),
    selectCharacter("-Select character number (?<number>.+) player (?<player>.+)"),
    selectMode("-Select mode number (?<number>\\d)"),
    sortDown("sort by (?<type>\\d) down"),
    sortUp("sort by (?<type>\\d) up"),
    startGame("-Start game"),
    userCreate("user create -u (?<username>[^\s]+) -p (?<password>[^\s]+) -r (?<passwordConfirmation>[^\s]+) -e (?<email>[^\s]+) -n (?<nickname>[^\s]+)"),
    userCreateRandom("user create -u (?<username>[^\s]+) -p random –e (?<email>[^\s]+) -n (?<nickname>[^\s]+)"),
    newCard("new card -n (?<name>[^\s]+) -a (?<attack>[^\s]+) -d (?<duration>[^\s]+) -p (?<damage[^\s]+) -l (?<upLevel>[^\s]+) -c (?<cost>[^\s]+)"),
    showCards("show cards"),
    showCard("edit card (?<n>\\d)"),
    editCard("change (?<type>\\d)"),
    deleteCard("delete card (?<n>\\d)"),
    yesOrNo("(?<answer>[^\s]+)"),
    buyCard("buy card -n (?<name>[^\s]+)"),
    upgradeCard("upgrade card -n (?<name>[^\s]+)"),
    showPlayers("show players"),
    whichCard("-Card number (?<number>[^\s]+)");


    public final String regex;

    Commands(String regex) {
        this.regex = regex;
    }
}
