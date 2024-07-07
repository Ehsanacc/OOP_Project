package Model;

import java.sql.Time;
import java.sql.Date;

public class History {
    private String opponentName;
    private boolean won;
    private int opponentLevel;
    private int prize;
    private Date date;

    public History(String opponentName, Date date, boolean won, int opponentLevel, int prize) {
        this.opponentName = opponentName;
        this.date = date;
        this.won = won;
        this.opponentLevel = opponentLevel;
        this.prize = prize;
    }

    public String getOpponentName() {
        return opponentName;
    }
    public boolean isWon() {
        return won;
    }
    public int getOpponentLevel() {
        return opponentLevel;
    }
    public int getPrize() {
        return prize;
    }
    public Date getDate() {
        return date;
    }
}
