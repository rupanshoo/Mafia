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
    public int size(){
        return Players.size();
    }
    public void add(T o){
        Players.add(o);
    }
    public T get(int i){
        return Players.get(i);
    }
}


public class Game {

    public static int N;  //total no. of players in the game
    public static int MafiaCnt;
    public static int DetectiveCnt;
    public static int HealerCnt;
    public static int CommonerCnt;
    public static List<Integer> assigned = new ArrayList();   //to store which ids have been assigned
    public static Random rand = new Random();   //a random variable to use throughout the program


    //Generic List of Characters
    public static GenericPlayerList<Mafia> maf;
    public static GenericPlayerList<Detective> det;
    public static GenericPlayerList<Healer> heal;
    public static GenericPlayerList<Commoner> com;


    //to randomly assign players
    public static void assign(int N){
        int rand_1 = rand.nextInt(100);
        int id = rand_1 % N;
        if(assigned.size() == 0){   //if 1st id is being assigned
            assigned.add(id);

            if(MafiaCnt!=0){
                Mafia M = new Mafia(id);
                maf.add(M);
                MafiaCnt--;
            }
            else if(DetectiveCnt!=0){
                Detective D = new Detective(id);
                det.add(D);
                DetectiveCnt--;
            }
            else if(HealerCnt!=0){
                Healer H = new Healer(id);
                heal.add(H);
                HealerCnt--;
            }
            else {
                Commoner C = new Commoner(id);
                com.add(C);
                CommonerCnt--;
            }
        }


        else{
                if(!assigned.contains(id)){
                    //assign(N);
                //}
                //else{
                    assigned.add(id);
                    if(MafiaCnt!=0){
                        Mafia M = new Mafia(id);
                        maf.add(M);
                        MafiaCnt--;
                    }
                    else if(DetectiveCnt!=0){
                        Detective D = new Detective(id);
                        det.add(D);
                        DetectiveCnt--;
                    }
                    else if(HealerCnt!=0){
                        Healer H = new Healer(id);
                        heal.add(H);
                        HealerCnt--;
                    }
                    else {
                        Commoner C = new Commoner(id);
                        com.add(C);
                        CommonerCnt--;
                    }
                }

        }

    }


    public static void main(String[] args){
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


        maf = new GenericPlayerList<Mafia>();
        det = new GenericPlayerList<Detective>();
        heal = new GenericPlayerList<Healer>();
        com = new GenericPlayerList<Commoner>();

        System.out.println("Choose a Character\n");
        System.out.println("1. Mafia\n2. Detective\n3. Healer\n4. Commoner\n5. Assign Randomly\n");
        UserCharacChoice = scan.nextInt();

        while(assigned.size()!=N){    //to assign characters to player IDs
            assign(N);
        }

        switch(UserCharacChoice){
            case 1:    //User - mafia
                int rand_M = rand.nextInt(100);
                int UserMafID = rand_M % maf.size();
                break;

            case 2:    //user - detective
                int rand_D = rand.nextInt(100);
                int UserDetID = rand_D % det.size();
                break;

            case 3:    //user - healer
                int rand_H = rand.nextInt(100);
                int UserHealID = rand_H % heal.size();
                break;
            case 4:     //User - Commoner
                int rand_C = rand.nextInt(100);
                int UserComID = rand_C % com.size();
                break;
            case 5:     //user - Randomly assigned
                break;

        }

    }

}

