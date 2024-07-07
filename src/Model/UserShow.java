package Model;

public class UserShow {
    private String username;
    private int level;
    private int gold;

    public UserShow(String username, int level, int gold) {
        this.username = username;
        this.level = level;
        this.gold = gold;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
}
