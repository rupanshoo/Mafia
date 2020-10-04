/*
AP Assignment 3 - Mafia
Name: Rupanshoo Saxena
Roll no.: 2019096
Branch: CSE
 */

import java.util.*;


abstract class Characters{     //abstract class
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


class Mafia extends Characters{    //Mafia
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


class Detective extends Characters{    //Detective
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


class Healer extends Characters{       //Healer
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


class Commoner extends Characters{      //Commoner
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


class GenericPlayerList <T> {      //Generic
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


        else{  //if assigned array is not empty

                if(!assigned.contains(id)){   //if it does not contain the id generated rn --> to check if that id has already been assigned a character
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



    //GAME MAIN
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
            case 1:    //User chooses mafia
                int rand_M = rand.nextInt(100);
                int UserMafID = rand_M % maf.size();
                Mafia User_M = new Mafia(maf.get(UserMafID).getID());
                System.out.println("You are Player " + maf.get(UserMafID).getID());
                System.out.println("You are a Mafia. Other Mafias are: ");
                for(int y=0; y< maf.size();y++){
                    if(maf.get(y).getID() != User_M.getID()){
                        System.out.println("Player "+maf.get(y).getID());
                    }
                }
                break;


            case 2:    //user chooses detective
                int rand_D = rand.nextInt(100);
                int UserDetID = rand_D % det.size();
                Detective User_D = new Detective(det.get(UserDetID).getID());
                System.out.println("You are Player " + det.get(UserDetID).getID());
                System.out.println("You are a Detective. Other Detectives are: ");
                for(int y=0; y< det.size();y++){
                    if(det.get(y).getID() != User_D.getID()){
                        System.out.println("Player "+det.get(y).getID());
                    }
                }
                break;


            case 3:    //user chooses healer
                int rand_H = rand.nextInt(100);
                int UserHealID = rand_H % heal.size();
                Healer User_H = new Healer(heal.get(UserHealID).getID());
                System.out.println("You are Player " + heal.get(UserHealID).getID());
                System.out.println("You are a Healer. Other Healers are: ");
                for(int y=0; y< heal.size();y++){
                    if(heal.get(y).getID() != User_H.getID()){
                        System.out.println("Player "+heal.get(y).getID());
                    }
                }
                break;


            case 4:     //User chooses Commoner
                int rand_C = rand.nextInt(100);
                int UserComID = rand_C % com.size();
                Commoner User_C = new Commoner(com.get(UserComID).getID());
                System.out.println("You are Player " + com.get(UserComID).getID());
                System.out.println("You are a Commoner. Other Commoners are: ");
                for(int y=0; y< com.size();y++){
                    if(com.get(y).getID() != User_C.getID()){
                        System.out.println("Player "+com.get(y).getID());
                    }
                }
                break;


            case 5:     //User - Randomly assigned  --> always assign user player 0 and whatever is assigned to 0 id is what the user will be

                for(int i=0; i< maf.size();i++){  //to check if id 0 is a mafia
                    if(maf.get(i).getID() == 0){
                        Mafia User_R = new Mafia(0);

                        System.out.println("You are Player 0");
                        System.out.println("You are a Mafia. Other Mafias are: ");
                        for(int y=0; y< maf.size();y++){
                            if(maf.get(y).getID() != 0){
                                System.out.println("Player "+maf.get(y).getID());
                            }
                        }
                    }
                }


                for(int i=0; i< det.size();i++){   //to check if id 0 is a detective
                    if(det.get(i).getID() == 0){
                        Detective User_R = new Detective(0);

                        System.out.println("You are Player 0");
                        System.out.println("You are a Detective. Other Detectives are: ");
                        for(int y=0; y< det.size();y++){
                            if(det.get(y).getID() != 0){
                                System.out.println("Player "+det.get(y).getID());
                            }
                        }
                    }
                }


                for(int i=0; i< heal.size();i++){    //to check if id 0 is a healer
                    if(heal.get(i).getID() == 0){
                        Healer User_R = new Healer(0);

                        System.out.println("You are Player 0");
                        System.out.println("You are a Healer. Other Healers are: ");
                        for(int y=0; y< heal.size();y++){
                            if(heal.get(y).getID() != 0){
                                System.out.println("Player "+heal.get(y).getID());
                            }
                        }
                    }
                }


                for(int i=0; i< com.size();i++){    //to check if id 0 is a commoner
                    if(com.get(i).getID() == 0){
                        Commoner User_R = new Commoner(0);

                        System.out.println("You are Player 0");
                        System.out.println("You are a Commoner. Other Commoners are: ");
                        for(int y=0; y< com.size();y++){
                            if(com.get(y).getID() !=0){
                                System.out.println("Player "+com.get(y).getID());
                            }
                        }
                    }
                }
                break;

        }  //end of character assignment switch case




    }

}

