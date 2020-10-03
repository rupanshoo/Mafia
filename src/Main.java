/*
AP Assignment 3 - Mafia
Name: Rupanshoo Saxena
Roll no.: 2019096
CSE
 */

import java.util.*;


abstract class Characters{
    private int HP;

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getHP() {
        return HP;
    }

}


class Mafia extends Characters{
    public Mafia(){   //constructor
        setHP(2500);
    }
    @Override
    public void setHP(int HP) {
        super.setHP(HP);
    }
}


class Detective extends Characters{
    public Detective(){   //constructor
        setHP(800);
    }
    @Override
    public void setHP(int HP){
        super.setHP(HP);
    }
}


class Healer extends Characters{
    public Healer(){    //constructor
        setHP(800);
    }

    @Override
    public void setHP(int HP) {
        super.setHP(800);
    }
}


class Commoner extends Characters{
    public Commoner(){     //constructor
        setHP(1000);
    }

    @Override
    public void setHP(int HP) {
        super.setHP(1000);
    }
}


public class Main {

    public static void main(String args[]){
        int N;  //total no. of players in the game
        Scanner scan = new Scanner(System.in);
        System.out.println("WELCOME TO MAFIA!\nEnter Number of Players: ");
        N = scan.nextInt();
        int MafiaCnt = N/5;
        int DetectiveCnt = N/5;
        int HealerCnt = Math.max(1, N/10);
        int CommonerCnt = N - (MafiaCnt+DetectiveCnt+HealerCnt);

        List<Characters> Players = new ArrayList<Characters>();
        Mafia a = new Mafia();
        Detective x = new Detective();
        Players.add(a);
        Players.add(x);
    }

}

