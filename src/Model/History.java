package Model;

import java.sql.Time;

public class History {
    private User opponent;
    private Time time;
    private boolean won;
    private int opponentLevel;
    private int prize;

    public History(User opponent, Time time, boolean won, int opponentLevel, int prize) {
        this.opponent = opponent;
        this.time = time;
        this.won = won;
        this.opponentLevel = opponentLevel;
        this.prize = prize;
    }

    public User getOpponent() {
        return opponent;
    }
    public Time getTime() {
        return time;
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
