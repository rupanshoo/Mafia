/*
AP Assignment 3 - Mafia
Name: Rupanshoo Saxena
Roll no.: 2019096
Branch: CSE
 */

import java.util.*;


abstract class Characters{
    private int HP;
    private int ID = -1;

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getHP() {
        return HP;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }
}


class Mafia extends Characters{
    public Mafia(int ID){   //constructor
        setHP(2500);
        setID(ID);
    }

    @Override
    public void setHP(int HP) {
        super.setHP(HP);
    }

    @Override
    public void setID(int ID) {
        super.setID(ID);
    }
}


class Detective extends Characters{
    public Detective(int ID){   //constructor
        setHP(800);
        setID(ID);
    }

    @Override
    public void setHP(int HP){
        super.setHP(HP);
    }

    @Override
    public void setID(int ID) {
        super.setID(ID);
    }
}


class Healer extends Characters{
    public Healer(int ID){    //constructor
        setHP(800);
        setID(ID);
    }

    @Override
    public void setHP(int HP) {
        super.setHP(800);
    }

    @Override
    public void setID(int ID) {
        super.setID(ID);
    }
}


class Commoner extends Characters{
    public Commoner(int ID){     //constructor
        setHP(1000);
        setID(ID);
    }

    @Override
    public void setHP(int HP) {
        super.setHP(1000);
    }

    @Override
    public void setID(int ID) {
        super.setID(ID);
    }
}


class GenericPlayerList <T> {
    private ArrayList<T> Players;
    public GenericPlayerList(){
        Players = new ArrayList<T>();
    }
    public void add(T o){
        Players.add(o);
    }
    public T get(int i){
        return Players.get(i);
    }
}


public class Game {

    int[] assigned = new int[N];

    public static void assign(int N){
        Random rand = new Random(100);
        int id = 100%N;
    }

    public static int N;  //total no. of players in the game
    public static int MafiaCnt;
    public static int DetectiveCnt;
    public static int HealerCnt;
    public static int CommonerCnt;

    public static void main(String args[]){
        int UserCharacChoice;


        Scanner scan = new Scanner(System.in);
        System.out.println("WELCOME TO MAFIA!\nEnter Number of Players: \n");
        N = scan.nextInt();
        while(N<6){
            System.out.println("Enter Number of Players greater than 6: \n");
            N = scan.nextInt();
        }
        MafiaCnt = N/5;
        DetectiveCnt = N/5;
        HealerCnt = Math.max(1, N/10);
        CommonerCnt = N - (MafiaCnt+DetectiveCnt+HealerCnt);


        GenericPlayerList<Mafia> maf = new GenericPlayerList<Mafia>();
        GenericPlayerList<Detective> det = new GenericPlayerList<Detective>();
        GenericPlayerList<Healer> heal = new GenericPlayerList<Healer>();
        GenericPlayerList<Commoner> com = new GenericPlayerList<Commoner>();

        System.out.println("Choose a Character\n");
        System.out.println("1. Mafia\n2. Detective\n3. Healer\n4. Commoner\n5. Assign Randomly\n");
        UserCharacChoice = scan.nextInt();

        switch(UserCharacChoice){
            case 1:    //User - mafia
                break;
            case 2:    //user - detective
                break;
            case 3:    //user - healer
                break;
            case 4:     //User - Commoner
                break;
            case 5:     //user - Randomly assigned
                break;

        }

    }

}

