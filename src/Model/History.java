package Model;

import java.sql.Date;
import java.sql.Time;

public class History {
    private String opponentName;
    private Date date;
    private boolean won;
    private int opponentLevel;
    private int prize;

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

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public void setOpponentLevel(int opponentLevel) {
        this.opponentLevel = opponentLevel;
    }

    public void setPrize(int prize) {
        this.prize = prize;
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
}
