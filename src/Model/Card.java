package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Card {
    private String name;
    private int point;
    private int damage;
    private int duration;
    private boolean special;
    public boolean isAdmin() {
        return isAdmin;
    }
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
    private boolean isAdmin=false;
    private int type;
    private int requiredLevel;
    private boolean upgraded;
    private int upgradeCost;
    private int price;

    public Card(String name){
        this.name=name;
    }

    public Card(String name, int point, int damage, int duration, boolean special, int type, int requiredLevel, boolean upgraded, int upgradeCost, int price) {
        this.name = name;
        this.point = point;
        this.damage = damage;
        this.duration = duration;
        this.special = special;
        this.type = type;
        this.requiredLevel = requiredLevel;
        this.upgraded = upgraded;
        this.upgradeCost = upgradeCost;
        this.price=price;
    }

    public Card(String name, int point, int damage, int duration, boolean special, boolean isAdmin, int type, int requiredLevel, boolean upgraded, int upgradeCost) {
        this.name = name;
        this.point = point;
        this.damage = damage;
        this.duration = duration;
        this.special = special;
        this.isAdmin = isAdmin;
        this.type = type;
        this.requiredLevel = requiredLevel;
        this.upgraded = upgraded;
        this.upgradeCost = upgradeCost;
    }

    public void buff(){
        this.damage+=this.duration*2;
    }

    public void poisonP(){
        this.point-=20;
    }

    public void poisonD(){
        this.damage-=this.duration*2;
    }

    public static void poison(Card[] deck){
        Random random=new Random();
        int a=random.nextInt(deck.length), b=random.nextInt(deck.length);
        deck[a].poisonP();
        deck[b].poisonD();
    }

    public static void steal(Card extra, Card[] deck2){
        Random random=new Random();
        int r=random.nextInt(deck2.length);
        Card[] temp=new Card[deck2.length-1];
        int count=0;
        for (int i = 0; i < deck2.length; i++) {
            if (i!=r) temp[count++]=deck2[i];
        }
        extra=deck2[r];
        deck2=temp;
    }

    public static void scramble(Card[] deck){
        Random random=new Random();
        ArrayList<Integer> indexes=new ArrayList<>(deck.length);
        Card[] temp=new Card[deck.length];
        Integer a= random.nextInt(deck.length);
        for (int i = 0; i < deck.length ; i++) {
            while (indexes.contains(a)){
                a= random.nextInt(deck.length);
            }
            indexes.add(a);
            temp[i]=deck[a];
        }
        deck=temp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isSpecial() {
        return special;
    }

    public void setSpecial(boolean special) {
        this.special = special;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public void setRequiredLevel(int requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    public boolean isUpgraded() {
        return upgraded;
    }

    public void setUpgraded(boolean upgraded) {
        this.upgraded = upgraded;
    }

    public int getUpgradeCost() {
        return upgradeCost;
    }

    public void setUpgradeCost(int upgradeCost) {
        this.upgradeCost = upgradeCost;
    }

    public void upgradeCard(Card card){
        this.damage+=4*this.duration;
        this.point+=20;
    }

    public static void addCard(Card card){}

    @Override
    public String toString() {
        return super.toString();
    }

    public void changeCard(HashMap<Integer, String> news){}

    private boolean checkCard(HashMap<Integer, String> news){return true;}

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

