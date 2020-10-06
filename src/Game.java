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

    //Global variables
    public static Scanner scan = new Scanner(System.in);
    public static int N;  //total no. of players in the game
    public static int MafiaCnt;
    public static int DetectiveCnt;
    public static int HealerCnt;
    public static int CommonerCnt;
    public static List<Integer> assigned = new ArrayList();   //to store which ids have been assigned
    public static List<Integer> ActivePlayers;
    public static List<Integer> dead = new ArrayList<>();  //to store player ids that were removed.
    public static Random rand = new Random();   //a random variable to use throughout the program

    public static Mafia User_RM, User_M;
    public static Detective User_RD, User_D;
    public static Healer User_RH, User_H;
    public static Commoner User_RC, User_C;


    //Generic List of Characters
    public static GenericPlayerList<Mafia> maf;
    public static GenericPlayerList<Detective> det;
    public static GenericPlayerList<Healer> heal;
    public static GenericPlayerList<Commoner> com;

    public static Detective tD;
    public static Healer tH;
    public static Commoner tC;


    public static int mSize;
    public static int dSize;
    public static int hSize;
    public static int cSize;



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



    /////////////////////GAME PLAY//////////////////////

    //User Commoner
    public static void GamePlay_Commoner(Commoner User){
        int target = Random_Mafia();  //for selecting target by mafia

        int voting_decision = Random_Detective();
        if(voting_decision!= -2){System.out.println("Detectives have tested a player.");}

        //Healer - is false then the healer did not heal the target => dead target
        int saved = Random_Healer(target);
        if(hSize!=0){System.out.println("Healers have chosen someone to heal.");}

        System.out.println("--End of Actions--");

        if(saved == 1){
            System.out.println("No one died");
        }
        else{
            System.out.println("Player "+target+" is dead.");
        }

        for(int c=0; c< det.size();c++) {   //make hp 0 for dead person -- target detective
            if (det.get(c).getID() == target) {
                det.get(c).setHP(0);
                dSize--;
            }
        }

        for(int c=0; c< heal.size();c++) {   //make hp 0 for dead person  -- target healer
            if (heal.get(c).getID() == target) {
                heal.get(c).setHP(0);
                hSize--;
            }
        }

        for(int c=0; c< com.size();c++) {   //make hp 0 for dead person -- target commoner
            if (com.get(c).getID() == target) {
                com.get(c).setHP(0);
                cSize--;
            }
        }

        dead.add(target);
        for(int j=0; j<ActivePlayers.size();j++){
            if(ActivePlayers.get(j) == target) {ActivePlayers.remove(j);}
        }

        if((mSize/(dSize+ hSize+ cSize) < 1 ) && (mSize>0)) {

            //VOTING DECISION
            if (voting_decision < 0) {   //detectives did not detect the mafia
                Voting(User.getID());
            } else if (voting_decision >= 0) {   //detectives detected the mafia
                System.out.println("Player " + voting_decision + " has been voted out!");
                for (int c = 0; c < maf.size(); c++) {   //mafia voted out so 0 hp
                    if (maf.get(c).getID() == voting_decision) {
                        maf.get(c).setHP(0);
                    }
                }
                dead.add(voting_decision);
                mSize--;
                for (int k = 0; k < ActivePlayers.size(); k++) {
                    if (ActivePlayers.get(k) == voting_decision) {
                        ActivePlayers.remove(k);
                    }
                }
            }
        }


    }


    //User Healer
    public static void GamePlay_Healer(Healer User){

        int check_tar = 0;

        //for mafias to select target
        int target = Random_Mafia();

        int voting_decision = Random_Detective();
        if(voting_decision!= -2){System.out.println("Detectives have tested a player.");}

        //if user healer is alive
        if(ActivePlayers.contains(User.getID())) {
            System.out.println("Choose a player to heal: ");
            int heal_P = scan.nextInt();   //id of player to be healed

            while(!ActivePlayers.contains(heal_P)){    //if id of player to be healed is alive
                System.out.println("This player is dead. Choose another Player to heal!!");
                heal_P=scan.nextInt();
            }

            /////no need to check if id is a healer id as healer can heal itself too//////

            for (int i = 0; i < maf.size(); i++) {  //if healer chose a mafia
                if (maf.get(i).getID() == heal_P) {
                    maf.get(i).setHP(maf.get(i).getHP() + 500);
                }
            }
            for (int i = 0; i < det.size(); i++) {  //if healer chose a detective
                if (det.get(i).getID() == heal_P) {
                    det.get(i).setHP(det.get(i).getHP() + 500);
                    if (det.get(i).getID() == target) {
                        check_tar = 1;
                    }
                }
            }
            for (int i = 0; i < heal.size(); i++) {  //if healer chose a healer
                if (heal.get(i).getID() == heal_P) {
                    heal.get(i).setHP(heal.get(i).getHP() + 500);
                    if (heal.get(i).getID() == target) {
                        check_tar = 1;
                    }
                }
            }
            for (int i = 0; i < com.size(); i++) {  //if healer chose a commoner
                if (com.get(i).getID() == heal_P) {
                    com.get(i).setHP(com.get(i).getHP() + 500);
                    if (com.get(i).getID() == target) {
                        check_tar = 1;
                    }
                }
            }


        }

        //if user healer is dead
        else{
            check_tar = Random_Healer(target);
            if(hSize!=0){System.out.println("Healers have chosen someone to heal.");}
        }

        //CHECKING
        if(check_tar == 1){
            System.out.println("No one died");
        }
        else{
            System.out.println("Player "+target+" is dead.");


            for(int c=0; c< det.size();c++) {   //make hp 0 for dead person -- target detective
                if (det.get(c).getID() == target) {
                    det.get(c).setHP(0);
                    dSize--;
                }
            }

            for(int c=0; c< heal.size();c++) {   //make hp 0 for dead person  -- target healer
                if (heal.get(c).getID() == target) {
                    heal.get(c).setHP(0);
                    hSize--;
                }
            }

            for(int c=0; c< com.size();c++) {   //make hp 0 for dead person -- target commoner
                if (com.get(c).getID() == target) {
                    com.get(c).setHP(0);
                    cSize--;
                }
            }

            dead.add(target);
            for(int j=0; j<ActivePlayers.size();j++){
                if(ActivePlayers.get(j) == target) {ActivePlayers.remove(j);}
            }
        }

        if((mSize/(dSize+ hSize+ cSize) < 1 ) && (mSize>0)) {

            //VOTING DECISION
            if (voting_decision < 0) {   //detectives did not detect the mafia
                Voting(User.getID());
            } else if (voting_decision >= 0) {   //detectives detected the mafia
                System.out.println("Player " + voting_decision + " has been voted out!");
                for (int c = 0; c < maf.size(); c++) {   //mafia voted out so 0 hp
                    if (maf.get(c).getID() == voting_decision) {
                        maf.get(c).setHP(0);
                    }
                }
                dead.add(voting_decision);
                mSize--;
                for (int k = 0; k < ActivePlayers.size(); k++) {
                    if (ActivePlayers.get(k) == voting_decision) {
                        ActivePlayers.remove(k);
                    }
                }
            }
        }

    }


    //User Detective
    public static void GamePlay_Detective(Detective User){
        int Test;
        int Voting_decision = -1;

        //for mafias to select target
        int target = Random_Mafia();


        //if user is detective and user is alive
        if(ActivePlayers.contains(User.getID())){
            System.out.println("Choose Player to be tested: ");   //enter test from user
            Test = scan.nextInt();

            while(!ActivePlayers.contains(Test)){    //if test is dead
                System.out.println("This player is dead. Choose another Player to test!!");
                System.out.println("Choose Player to be tested: ");
                Test=scan.nextInt();
            }

            for(int i=0; i< det.size();i++) {
                while (det.get(i).getID() == Test) {    //if target is a fellow detective
                    System.out.println("You cannot test a detective.");
                    System.out.println("Choose Player to be tested: ");
                    Test=scan.nextInt();
                }
            }

            for (int i = 0; i < maf.size(); i++) {       //if player detected is a mafia
                if(!(dSize>0)){    // if there are no alive detectives
                    Voting_decision = -2;
                    System.out.println("Detectives are dead");
                    break;
                }
                if (maf.get(i).getID() == Test) {
                    System.out.println("This player is a mafia!!");
                    Voting_decision = Test;   //this id will be >=0
                }
                else{
                    Voting_decision = -1;    // if detected id is not a mafia
                }
            }

            //Healer - is false then the healer did not heal the target => dead target
            int saved = Random_Healer(target);
            if(hSize!=0){System.out.println("Healers have chosen someone to heal.");}

            System.out.println("--End of Actions--");

            if(saved == 1){
                System.out.println("No one died");
            }
            else{
                System.out.println("Player "+target+" is dead.");


                for(int c=0; c< det.size();c++) {   //make hp 0 for dead person -- target detective
                    if (det.get(c).getID() == target) {
                        det.get(c).setHP(0);
                        dSize--;
                    }
                }

                for(int c=0; c< heal.size();c++) {   //make hp 0 for dead person  -- target healer
                    if (heal.get(c).getID() == target) {
                        heal.get(c).setHP(0);
                        hSize--;
                    }
                }

                for(int c=0; c< com.size();c++) {   //make hp 0 for dead person -- target commoner
                    if (com.get(c).getID() == target) {
                        com.get(c).setHP(0);
                        cSize--;
                    }
                }

                dead.add(target);
                for(int j=0; j<ActivePlayers.size();j++){
                    if(ActivePlayers.get(j) == target) {ActivePlayers.remove(j);}
                }
            }

            if((mSize/(dSize+ hSize+ cSize) < 1 ) && (mSize>0)) {

                //VOTING DECISION
                if (Voting_decision < 0) {   //detectives did not detect the mafia
                    Voting(User.getID());
                } else if (Voting_decision >= 0) {   //detectives detected the mafia
                    System.out.println("Player " + Voting_decision + " has been voted out!");
                    for (int c = 0; c < maf.size(); c++) {   //mafia voted out so 0 hp
                        if (maf.get(c).getID() == Voting_decision) {
                            maf.get(c).setHP(0);
                        }
                    }
                    dead.add(Voting_decision);
                    mSize--;
                    for (int k = 0; k < ActivePlayers.size(); k++) {
                        if (ActivePlayers.get(k) == Voting_decision) {
                            ActivePlayers.remove(k);
                        }
                    }
                }
            }

        }


        //if user detective dead but other detectives alive
        else{                                  //In case user is removed during voting --> random target selection
            Voting_decision = Random_Detective();

            //Healer - is false then the healer did not heal the target => dead target
            int saved = Random_Healer(target);
            if(hSize!=0){System.out.println("Healers have chosen someone to heal.");}

            System.out.println("--End of Actions--");

            if(saved == 1){
                System.out.println("No one died");
            }
            else{
                System.out.println("Player "+target+" is dead.");
                for(int c=0; c< det.size();c++) {   //make hp 0 for dead person -- target detective
                    if (det.get(c).getID() == target) {
                        det.get(c).setHP(0);
                        dSize--;
                    }
                }

                for(int c=0; c< heal.size();c++) {   //make hp 0 for dead person  -- target healer
                    if (heal.get(c).getID() == target) {
                        heal.get(c).setHP(0);
                        hSize--;
                    }
                }

                for(int c=0; c< com.size();c++) {   //make hp 0 for dead person -- target commoner
                    if (com.get(c).getID() == target) {
                        com.get(c).setHP(0);
                        cSize--;
                    }
                }
                dead.add(target);
                for(int j=0; j<ActivePlayers.size();j++){
                    if(ActivePlayers.get(j) == target) {ActivePlayers.remove(j);}
                }
            }

            if((mSize/(dSize+ hSize+ cSize) < 1 ) && (mSize>0)) {

                //VOTING DECISION
                if (Voting_decision < 0) {   //detectives did not detect the mafia
                    Voting(User.getID());
                } else if (Voting_decision >= 0) {   //detectives detected the mafia
                    System.out.println("Player " + Voting_decision + " has been voted out!");
                    for (int c = 0; c < maf.size(); c++) {   //mafia voted out so 0 hp
                        if (maf.get(c).getID() == Voting_decision) {
                            maf.get(c).setHP(0);
                        }
                    }
                    dead.add(Voting_decision);
                    mSize--;
                    for (int k = 0; k < ActivePlayers.size(); k++) {
                        if (ActivePlayers.get(k) == Voting_decision) {
                            ActivePlayers.remove(k);
                        }
                    }
                }
            }
        }


    }


    //Computerised Mafia
    public static int Random_Mafia(){

            int Target = rand.nextInt(100) % N;

            while (!ActivePlayers.contains(Target)) {   //if target is dead
                Target = rand.nextInt(N);
            }
            for (int i = 0; i < maf.size(); i++) {
                while (maf.get(i).getID() == Target) {    //if target is a fellow mafia
                    Target = rand.nextInt(N);
                }
            }

            int MafHPTotal = 0;
            for (int j = 0; j < maf.size(); j++) {      //calculate mafia's total HP
                MafHPTotal += maf.get(j).getHP();
            }

            for (int i = 0; i < det.size(); i++) {
                if (det.get(i).getID() == Target) {    //if target is a detective
                    for (int b = 0; b < det.size(); b++) {
                        if (det.get(b).getID() == Target) {
                            tD = new Detective(Target);
                            tD.setHP(det.get(b).getHP());
                        }
                    }

                    //Target HP calculations
                    Acquired_Target(tD, MafHPTotal);
                    System.out.println("Mafias have selected a target to kill.");
                }
            }

            for (int i = 0; i < heal.size(); i++) {
                if (heal.get(i).getID() == Target) {    //if target is a healer
                    for (int b = 0; b < heal.size(); b++) {
                        if (heal.get(b).getID() == Target) {
                            tH = new Healer(Target);
                            tH.setHP(heal.get(b).getHP());
                        }
                    }
                    //Target HP calculations
                    Acquired_Target(tH, MafHPTotal);
                    System.out.println("Mafias have selected a target to kill.");
                }
            }

            for (int i = 0; i < com.size(); i++) {
                if (com.get(i).getID() == Target) {    //if target is a commoner
                    for (int b = 0; b < com.size(); b++) {
                        if (com.get(b).getID() == Target) {
                            tC = new Commoner(Target);
                            tC.setHP(com.get(b).getHP());
                        }
                    }
                    //Target HP calculations
                    Acquired_Target(tC, MafHPTotal);
                    System.out.println("Mafias have selected a target to kill.");
                }
            }
            return Target;   // id of Target
    }


    //User Mafia
    public static void GamePlay_Mafia(Mafia User){
        int Target;

        //if user is mafia and user is alive
        if(ActivePlayers.contains(User.getID())){
            System.out.println("Choose target: ");
            Target = scan.nextInt();
        }
        else{                                  //In case user is removed during voting --> random target selection
            int rand_target = rand.nextInt(100);
            Target = rand_target%N;
        }

        int MafHPTotal = 0;
        for(int j=0; j< maf.size();j++){      //calculate mafia's total HP
            MafHPTotal+=maf.get(j).getHP();
        }

        while(!ActivePlayers.contains(Target)){    //if target is dead
            System.out.println("This player is dead. Choose another Player!!");
            Target=rand.nextInt(N);
        }

        for(int i=0; i< maf.size();i++) {
            while (maf.get(i).getID() == Target) {    //if target is a fellow mafia
                System.out.println("You cannot choose a fellow Mafia.");
                Target=rand.nextInt(N);
            }
        }

        for(int i=0; i< det.size();i++) {
            if (det.get(i).getID() == Target) {    //if target is a detective
                for(int b=0;b<det.size();b++) {
                    if (det.get(b).getID() == Target) {
                        tD = new Detective(Target);
                        tD.setHP(det.get(b).getHP());
                    }
                }

                //Target HP calculations
                Acquired_Target(tD, MafHPTotal);

                //Detective - if -1 then voting will take place, else no voting, straight removal of the id in voting_decision
                int voting_decision = Random_Detective();
                if(voting_decision!= -2){System.out.println("Detectives have tested a player.");}


                //Healer - is false then the healer did not heal the target => dead target
                int saved = Random_Healer(Target);
                if(hSize!=0){System.out.println("Healers have chosen someone to heal.");}

                System.out.println("--End of Actions--");

                if(saved == 1){
                    System.out.println("No one died");
                }
                else{
                    System.out.println("Player "+Target+" is dead.");
                    for(int c=0; c< det.size();c++){   //make hp 0 for dead person
                        if(det.get(c).getID() == Target){det.get(c).setHP(0);}
                    }
                    dead.add(Target);
                    dSize--;
                    for(int j=0; j<ActivePlayers.size();j++){
                        if(ActivePlayers.get(j) == Target) {ActivePlayers.remove(j);}
                    }
                }

                if(!(mSize/(dSize+ hSize+ cSize) < 1 ) && !(mSize>0)){
                    break;
                }

                //VOTING DECISION
                if(voting_decision < 0){   //detectives did not detect the mafia
                    Voting(User.getID());
                }
                else if(voting_decision>=0){   //detectives detected the mafia
                    System.out.println("Player "+ voting_decision+ " has been voted out!");
                    for(int c=0; c< maf.size();c++){   //mafia voted out so 0 hp
                        if(maf.get(c).getID() == voting_decision){maf.get(c).setHP(0);}
                    }
                    dead.add(voting_decision);
                    mSize--;
                    for(int k=0; k<ActivePlayers.size();k++){
                        if(ActivePlayers.get(k) == voting_decision){ActivePlayers.remove(k);}
                    }
                }
            }
        }

        for(int i=0; i< heal.size();i++) {
            if (heal.get(i).getID() == Target) {    //if target is a healer
                for(int b=0;b<heal.size();b++) {
                    if (heal.get(b).getID() == Target) {
                        tH = new Healer(Target);
                        tH.setHP(heal.get(b).getHP());
                    }
                }

                //Target HP calculations
                Acquired_Target(tH, MafHPTotal);

                //Detective - if -1 then voting will take place, else no voting, straight removal of the id in voting_decision
                int voting_decision = Random_Detective();
                if(voting_decision!= -2){System.out.println("Detectives have tested a player.");}

                //Healer - is false then the healer did not heal the target => dead target
                int saved = Random_Healer(Target);
                if(hSize!=0){System.out.println("Healers have chosen someone to heal.");}

                System.out.println("--End of Actions--");

                if (saved == 1) {
                    System.out.println("No one died");
                } else {
                    System.out.println("Player " + Target + " is dead.");
                    for(int c=0; c< heal.size();c++){
                        if(heal.get(c).getID() == Target){heal.get(c).setHP(0);}
                    }
                    dead.add(Target);
                    hSize--;
                    for (int j = 0; j < ActivePlayers.size(); j++) {
                        if (ActivePlayers.get(j) == Target) {
                            ActivePlayers.remove(j);
                        }
                    }
                }

                if(!(mSize/(dSize+ hSize+ cSize) < 1 ) && !(mSize>0)){
                    break;
                }
                //VOTING DECISION
                if (voting_decision < 0) {   //detectives did not detect the mafia
                    Voting(User.getID());
                }
                else if(voting_decision>=0){    //detectives detected the mafia
                    System.out.println("Player " + voting_decision + " has been voted out!");
                    for(int c=0; c< maf.size();c++){
                        if(maf.get(c).getID() == voting_decision){maf.get(c).setHP(0);}
                    }
                    dead.add(voting_decision);
                    mSize--;
                    for (int k = 0; k < ActivePlayers.size(); k++) {
                        if (ActivePlayers.get(k) == voting_decision) {
                            ActivePlayers.remove(k);
                        }
                    }
                }

            }
        }


        for(int i=0; i< com.size();i++) {
            if (com.get(i).getID() == Target) {    //if target is a commoner
                for(int b=0;b<com.size();b++) {
                    if (com.get(b).getID() == Target) {
                        tC = new Commoner(Target);
                        tC.setHP(com.get(b).getHP());
                    }
                }

                //Target HP calculations
                Acquired_Target(tC, MafHPTotal);

                //Detective - if -1 then voting will take place, else no voting, straight removal of the id in voting_decision
                int voting_decision = Random_Detective();
                if(voting_decision!= -2){System.out.println("Detectives have tested a player.");}

                //Healer - is false then the healer did not heal the target => dead target
                int saved = Random_Healer(Target);
                if(hSize!=0){System.out.println("Healers have chosen someone to heal.");}

                System.out.println("--End of Actions--");

                if(saved == 1){
                    System.out.println("No one died");
                }
                else{
                    System.out.println("Player "+Target+" is dead.");
                    for(int c=0; c< com.size();c++){
                        if(com.get(c).getID() == Target){com.get(c).setHP(0);}
                    }
                    dead.add(Target);
                    cSize--;
                    for(int j=0; j<ActivePlayers.size();j++){
                        if(ActivePlayers.get(j) == Target) {ActivePlayers.remove(j);}
                    }
                }

                if(!(mSize/(dSize+ hSize+ cSize) < 1 ) && !(mSize>0)){
                    break;
                }

                //VOTING DECISION
                if(voting_decision < 0){   //detectives did not detect the mafia
                    Voting(User.getID());
                }
                else if(voting_decision>=0){    //detectives detected the mafia
                    System.out.println("Player "+ voting_decision+ " has been voted out!");
                    for(int c=0; c< maf.size();c++){
                        if(maf.get(c).getID() == voting_decision){maf.get(c).setHP(0);}
                    }
                    dead.add(voting_decision);
                    mSize--;
                    for(int k=0; k<ActivePlayers.size();k++){
                        if(ActivePlayers.get(k) == voting_decision){ActivePlayers.remove(k);}
                    }
                }

            }
        }
    }


    //When target is a acquired --> following HP things here  ==> helper for mafia
    public static void Acquired_Target(Characters target, int MafTotal){
        int OGTargetHP = target.getHP();    //to save original HP of target
        int activeMaf = 0;
        int divide = -1;

        for(int i=0;i<maf.size();i++){  //to count the no. of active mafias at this time
            if(maf.get(i).getHP()>0){
                activeMaf++;
            }
        }

        //HP change of target
        if(MafTotal>= target.getHP()){
            target.setHP(0);
        }
        else{
            target.setHP(target.getHP()-MafTotal);
        }

        //Damage on Mafias
        for(int i=0; i<maf.size();i++){
            if(activeMaf<=0){
                break;
            }
            if(maf.get(i).getHP() > OGTargetHP/activeMaf){
                maf.get(i).setHP(maf.get(i).getHP() - OGTargetHP/activeMaf);
            }
            else{
                maf.get(i).setHP(maf.get(i).getHP() - OGTargetHP/activeMaf);
                divide = divide*maf.get(i).getHP();
                maf.get(i).setHP(0);
            }
        }

        //damage case where mafia HP<X/Y
        if(divide!= -1){
            int actTemp = 0;  //to get current active mafias after applying initial damage
            for(int i=0;i<maf.size();i++){
                if(maf.get(i).getHP() > 0){
                    actTemp++;
                }
            }

            for(int i=0; i<maf.size();i++){
                if(maf.get(i).getHP() > 0){
                    maf.get(i).setHP(maf.get(i).getHP() - divide/actTemp);
                }
            }
        }
    }


    //Computerised Detective
    public static int Random_Detective(){
        if(dSize!=0) {   //alive detectives exist
            int detect_Random = rand.nextInt(100);
            int detect_ID = detect_Random % N;  //id to be checked
            Set<Integer> det_set = new HashSet<Integer>();

            for(int s=0; s<det.size(); s++){   //add detective ids to hashset
                det_set.add(det.get(s).getID());
            }

            while(det_set.contains(detect_ID) && !ActivePlayers.contains(detect_ID)){  //keep generating random ids till we find one st it is alive
                detect_ID = rand.nextInt(100)%N;
            }

            for (int i = 0; i < maf.size(); i++) {       //if player detected is a mafia
                if (maf.get(i).getID() == detect_ID) {
                    return maf.get(i).getID();   //return mafia id - can be from 0 to N-1
                }
            }

            return -1;   // if not mafia and some other player was checked, return -1
        }
        else{     // if all detectives are dead
            System.out.println("All detectives are dead!");
            return -2;
        }
    }


    //Computerised Healer
    public static int Random_Healer(int Target){
        int ret = 0;
        int heal_Rand = rand.nextInt(100);
        int heal_ID = heal_Rand % N;
        while(!ActivePlayers.contains(heal_ID)) {    //player to be healed must be alive
            heal_ID=rand.nextInt(100)%N;
        }
        if (hSize != 0) {
            for (int i = 0; i < maf.size(); i++) {  //if healer chose a mafia
                if (maf.get(i).getID() == heal_ID) {
                    maf.get(i).setHP(maf.get(i).getHP() + 500);
                }
            }
            for (int i = 0; i < det.size(); i++) {  //if healer chose a detective
                if (det.get(i).getID() == heal_ID) {
                    det.get(i).setHP(det.get(i).getHP() + 500);
                    if (det.get(i).getID() == Target) {
                        ret = 1;
                    }
                }
            }
            for (int i = 0; i < heal.size(); i++) {  //if healer chose a healer
                if (heal.get(i).getID() == heal_ID) {
                    heal.get(i).setHP(heal.get(i).getHP() + 500);
                    if (heal.get(i).getID() == Target) {
                        ret = 1;
                    }
                }
            }
            for (int i = 0; i < com.size(); i++) {  //if healer chose a commoner
                if (com.get(i).getID() == heal_ID) {
                    com.get(i).setHP(com.get(i).getHP() + 500);
                    if (com.get(i).getID() == Target) {
                        ret = 1;
                    }
                }
            }
        }
        else{
            ret = -1;
        }

        if (ret == 1) {
            return 1;
        } else if(ret == -1){
            System.out.println("All healers are dead");
            return -1;
        }
        else{
            return 0;
        }

    }


    //Voting
    public static void Voting(int U_ID){
        int[] votes = new int[ActivePlayers.size()];
        for(int i=0; i< ActivePlayers.size() ;i++){
            if(ActivePlayers.get(i) == U_ID){  //=> user is active or alive
                System.out.println("Select a player to vote out: ");
                int voteOutID = scan.nextInt();

                while(!ActivePlayers.contains(voteOutID)){   //to check if player id entered is alive or dead
                    System.out.println("This player is already dead! Select an alive player: ");
                    voteOutID = scan.nextInt();
                }

                for(int j=0; j<ActivePlayers.size();j++){
                    if(ActivePlayers.get(j)==voteOutID){
                        votes[j]++;
                    }
                }
            }
            else{
                //for(int j=0; j<ActivePlayers.size(); j++){    //for other random voters
                int VO_rand = rand.nextInt(100);
                int VO = VO_rand%N;   //id to be voted for

                while (!ActivePlayers.contains(VO)){    //keeps going till it gets a valid id
                    VO_rand = rand.nextInt(100);
                    VO = VO_rand%N;   //id to be voted for
                }

                //for voting this above random ID generated
                for(int l=0;l<ActivePlayers.size();l++){
                    if(ActivePlayers.get(l) == VO){
                        votes[l]++;
                    }
                }


            }
        }

        int max = 0;
        int maxID = -1;
        int tie = 0;
        for(int i=0; i< votes.length;i++) {
            if (votes[i] > max) {max = votes[i]; maxID = ActivePlayers.get(i);}
            else if (votes[i] == max) {tie = votes[i];}
        }
        if(max>tie){
            System.out.println("Player "+ maxID + " has been voted out.");
            dead.add(maxID);
            for(int a=0; a<maf.size();a++){if(maf.get(a).getID() == maxID){mSize--; maf.get(a).setHP(0);}}
            for(int a=0; a<det.size();a++){if(det.get(a).getID() == maxID){dSize--; det.get(a).setHP(0);}}
            for(int a=0; a<heal.size();a++){if(heal.get(a).getID() == maxID){hSize--; heal.get(a).setHP(0);}}
            for(int a=0; a<com.size();a++){if(com.get(a).getID() == maxID){cSize--; com.get(a).setHP(0);}}

            for(int z=0; z< ActivePlayers.size();z++){if(ActivePlayers.get(z) == maxID){ActivePlayers.remove(z);}}
        }
        else{
            System.out.println("Voting was tied!! Vote again!");
            Voting(U_ID);
        }
    }



    /////////////////GAME MAIN////////////////////////
    public static void main(String[] args){
        int UserCharacChoice;
        int GameRounds = 1;
        int PlayersRemaining;


        System.out.println("WELCOME TO MAFIA!\nEnter Number of Players: \n");
        N = scan.nextInt();
        PlayersRemaining = N;
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

        mSize = maf.size();
        dSize = det.size();
        hSize = heal.size();
        cSize = com.size();

        //get a sorted list of active players
        ActivePlayers = new ArrayList<>();
        for(int z=0; z<assigned.size();z++){
            ActivePlayers.add(assigned.get(z));
        }
        Collections.sort(ActivePlayers);


        switch(UserCharacChoice){
            case 1:    //User chooses mafia
                int rand_M = rand.nextInt(100);
                int UserMafID = rand_M % maf.size();
                User_M = new Mafia(maf.get(UserMafID).getID());
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
                User_D = new Detective(det.get(UserDetID).getID());
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
                User_H = new Healer(heal.get(UserHealID).getID());
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
                User_C = new Commoner(com.get(UserComID).getID());
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
                        User_RM = new Mafia(0);

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
                        User_RD = new Detective(0);

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
                        User_RH = new Healer(0);

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
                        User_RC = new Commoner(0);

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



        ////////////////////////////////////GAME PLAY BEGINS///////////////////////////////////////////////

        while((mSize/(dSize+ hSize+ cSize) < 1 ) && mSize>0){   //for game to end
            System.out.println("---ROUND " + GameRounds + "---");
            System.out.println(ActivePlayers.size() + " Players are remaining: ");
            for (int i = 0; i < ActivePlayers.size(); i++) {
                System.out.println("Player " + ActivePlayers.get(i));
            }
            if (UserCharacChoice == 1) {   //User chooses to be a mafia
                GamePlay_Mafia(User_M);
            } else if ((UserCharacChoice == 5 && User_RM.getID() == 0)) {    //User chooses random and gets mafia
                GamePlay_Mafia(User_RM);
            }
            else if(UserCharacChoice == 2){   //User chooses to be a detective
                GamePlay_Detective(User_D);
            }
            else if((UserCharacChoice == 5 && User_RD.getID() == 0)){    //User chooses random and gets detective
                GamePlay_Detective(User_RD);
            }
            else if(UserCharacChoice == 3){      //User chooses to be healer
                GamePlay_Healer(User_H);
            }
            else if((UserCharacChoice == 5 && User_RH.getID() == 0)){    //User chooses random and gets detective
                GamePlay_Healer(User_RH);
            }
            else if(UserCharacChoice == 4){   //User chooses to be a commoner
                GamePlay_Commoner(User_C);
            }
            else if((UserCharacChoice == 5 && User_RH.getID() == 0)){    //User chooses random and gets detective
                GamePlay_Commoner(User_RC);
            }

            GameRounds++;
            //}
        }

        System.out.println("GAME OVER!!");
        if(maf.size()==0){System.out.println("The Mafias have lost.");}
        else if((maf.size()/(det.size()+ heal.size()+ com.size()) ==1)){System.out.println("The Mafias won!!");}
        for(int i=0; i< maf.size();i++){System.out.println("Player "+ maf.get(i).getID()+ " ");}
        System.out.println("were mafias.");
        for(int i=0; i< det.size(); i++){System.out.println("Player " + det.get(i).getID() + " ");}
        System.out.println("were detectives.");
        for(int i=0; i< heal.size(); i++){System.out.println("Player " + heal.get(i).getID() + " ");}
        System.out.println("were healers.");
        for(int i=0; i< com.size(); i++){System.out.println("Player " + com.get(i).getID() + " ");}
        System.out.println("were commoners.");
        System.out.println("--------END OF GAME---------");
    }

}