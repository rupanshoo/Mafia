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
    public static int GamePlay_Mafia(Mafia User){
        int Target;

        //if user is mafia and user is alive
        if(ActivePlayers.contains(User.getID())){
            System.out.println("Choose target: ");
            Target = scan.nextInt();
        }
        else{                                  //In case user is removed during voting
            int rand_target = rand.nextInt(100);
            Target = rand_target%N;
        }

        int MafHPTotal = 0;
        for(int j=0; j< maf.size();j++){      //calculate mafia's total HP
            MafHPTotal+=maf.get(j).getHP();
        }

        if(!ActivePlayers.contains(Target)){    //if target is alive or dead
            System.out.println("This player is dead. Choose another Player!!");
            GamePlay_Mafia(User);
        }

        for(int i=0; i< maf.size();i++) {
            if (maf.get(i).getID() == Target) {    //if target is a fellow mafia
                System.out.println("You cannot choose a fellow Mafia.");
                GamePlay_Mafia(User);
            }
        }

        for(int i=0; i< det.size();i++) {
            if (det.get(i).getID() == Target) {    //if target is a detective
                tD = new Detective(Target);


                //Target HP calculations
                Detective_Target(tD, MafHPTotal);

                //Detective - if -1 then voting will take place, else no voting, straight removal of the id in voting_decision
                int voting_decision = Random_Detective();
                System.out.println("Detectives have tested a player.");


                //Healer - is false then the healer did not heal the target => dead target
                boolean saved = Random_Healer(Target);
                System.out.println("Healers have chosen someone to heal.");

                System.out.println("--End of Actions--");

                if(saved == true){
                    System.out.println("No one died");
                }
                else{
                    System.out.println("Player "+Target+" is dead.");
                    dead.add(Target);

                    for(int j=0; j<ActivePlayers.size();j++){
                        if(ActivePlayers.get(j) == Target) {ActivePlayers.remove(j);}
                    }
                }

                //VOTING DECISION
                if(voting_decision == -1){   //detectives did not detect the mafia
                    Voting();
                }
                else{    //detectives detected the mafia
                    System.out.println("Player "+ voting_decision+ " has been voted out!");
                    dead.add(voting_decision);

                    for(int k=0; k<ActivePlayers.size();k++){
                        if(ActivePlayers.get(k) == voting_decision){ActivePlayers.remove(k);}
                    }
                }
                    //t[1] = true;
            }
            return 0;
        }

        for(int i=0; i< heal.size();i++) {
            if (heal.get(i).getID() == Target) {    //if target is a healer
                tH = new Healer(Target);
                System.out.println(tH);
                //t[2] = true;
            }
        }

        for(int i=0; i< com.size();i++) {
            if (com.get(i).getID() == Target) {    //if target is a commoner
                tC = new Commoner(Target);
                System.out.println(tC);
                //t[3] = true;
            }
        }

        return 0;
    }

    //When target is a detective
    public static void Detective_Target(Detective target, int MafTotal){
        int OGTargetHP = target.getHP();
        int activeMaf = 0;
        int divide = -1;

        for(int i=0;i<maf.size();i++){  //to count the no. of active mafias at this time
            if(maf.get(i).getHP()>0){
                activeMaf++;
            }
        }

        //HP change of target
        if(MafTotal>= target.getHP()){
            target.setID(0);
        }
        else{
            target.setHP(target.getHP()-MafTotal);
        }

        //Damage on Mafias
        for(int i=0; i<maf.size();i++){
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
        int detect_Random = rand.nextInt(100);
        int detect_ID = detect_Random%N;  //id to be checked

        for(int i=0; i< det.size();i++) {   //in case we check a fellow detective
            if (det.get(i).getID() == detect_ID) {Random_Detective();}
        }

        if(!ActivePlayers.contains(detect_ID)){Random_Detective();}   //in case player selected by the detective is a dead player

        else{
            for(int i=0; i<maf.size();i++){    //if player detected is a mafia
                if(maf.get(i).getID() == detect_ID){return maf.get(i).getID();}
            }
        }
        return -1;
    }


    //Computerised Healer
    public static boolean Random_Healer(int Target){
        int heal_Rand = rand.nextInt(100);
        int heal_ID = heal_Rand%N;
        if(!ActivePlayers.contains(heal_ID)){
            Random_Healer(Target);
        }
        else{
            for(int i=0; i< maf.size();i++){  //if healer chose a mafia
                if(maf.get(i).getID() == heal_ID){maf.get(i).setHP(maf.get(i).getHP()+500);}
            }
            for(int i=0; i< det.size();i++){  //if healer chose a detective
                if(det.get(i).getID() == heal_ID){
                    det.get(i).setHP(det.get(i).getHP()+500);
                    if(det.get(i).getHP() == Target){
                        return true;
                    }
                }
            }
            for(int i=0; i< heal.size();i++){  //if healer chose a healer
                if(heal.get(i).getID() == heal_ID){
                    heal.get(i).setHP(heal.get(i).getHP()+500);
                    if(heal.get(i).getHP() == Target){
                        return true;
                    }
                }
            }
            for(int i=0; i< com.size();i++){  //if healer chose a commoner
                if(com.get(i).getID() == heal_ID){
                    com.get(i).setHP(com.get(i).getHP()+500);
                    if(com.get(i).getHP() == Target){
                        return true;
                    }
                }
            }
        }
        return false;
    }


    //Voting
    public static void Voting(){
        
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

        while(maf.size()!=0 || (maf.size()/(det.size()+ heal.size()+ com.size()) !=1)){   //for game to end
            System.out.println("---ROUND "+ GameRounds+ "---");
            System.out.println(ActivePlayers.size() + " Players are remaining: ");
            for(int i=0; i< ActivePlayers.size();i++){
                System.out.println("Player " + ActivePlayers.get(i));
            }
            if(UserCharacChoice == 1){
                GamePlay_Mafia(User_M);
            }
            else if((UserCharacChoice == 5 && User_RM.getID()==0)){
                GamePlay_Mafia(User_RM);
            }



            GameRounds++;
        }



    }

}

