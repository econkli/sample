/**
 * Created by Emily on 10/5/15.
 */

import java.util.Random;

public class Student {

    //-----------------------DATA MEMBERS-----------------------\\

    //picked by user
    private String firstName;
    private int wealth;
    private String gender;
    private String[] proArray;
    private String major;
    private String minor;
    private String[] goals;
    private int numGoals;
    private College college;
    private int paycheck;
    private int parentContrib;
    private String home;

    //based on school
    private String[] classes;

    //randomly generated
    private Roommate roomie;

    //meters that change during gameplay
    private int gradeM;
    private int stressM;
    private int sleepDeprivM;
    private int debt;
    private int prestige;
    private String[] friends;
    private int numFriends;
    private int cluelessness;

    //-----------------------CONSTRUCTOR-----------------------\\

    Student() {
        stressM = 0;
        sleepDeprivM = 0;
        debt = 0;
        proArray = new String[6];
        friends = new String[10];
        numFriends = 0;
        goals = new String[10];
        cluelessness = 0;
    }

    //-----------------------METHODS-----------------------\\

    public void setName(String nameIn) {
        firstName = nameIn;
    }

    public String getName() {
        return firstName;
    }

    public int getWealth() { return wealth; }

    public void setWealth(int wealthIn) {

        wealth = wealthIn;
        if (wealth==5)
            home = "private island";
        else if (wealth==4)
            home = "big house in the suburbs";
        else if (wealth==3)
            home = "small house in the suburbs";
        else if (wealth==2)
            home = "townhouse";
        else if (wealth==1)
            home = "small apartment";
    }

    public String getHome() { return home; }

    public void setGender(String genderIn) {
        gender = genderIn;

        if (gender.equals("F")) {
            proArray[0] = "she";
            proArray[1] = "her";
            proArray[2] = "hers";
            proArray[3] = "woman";
            proArray[4] = "herself";
            proArray[5] = "girl";
        } else if (gender.equals("M")) {
            proArray[0] = "he";
            proArray[1] = "his";
            proArray[2] = "his";
            proArray[3] = "man";
            proArray[4] = "himself";
            proArray[5] = "boy";
        } else if (gender.equals("O")) {
            proArray[0] = "they";
            proArray[1] = "them";
            proArray[2] = "theirs";
            proArray[3] = "person";
            proArray[4] = "themself";
            proArray[5] = "person";
        }
    }

    public String getPronoun(int ind) { return proArray[ind]; }

    public void setGrades(int gradeIn) {
        gradeM = gradeIn;
    }

    public void setMajorMinor(String majorIn, String minorIn) {
        major = majorIn;
        minor = minorIn;
    }

    public String getMajor() {
        return major;
    }

    public String getMinor() {
        return minor;
    }

    public void setCollege(int collegeIn) {
        college = new College(collegeIn);

        if (college.getName().equals("")) {
            college = new College(collegeIn);
            prestige+=college.getCollPrestige();
            stressM+=college.getCollStress();
            sleepDeprivM+=college.getCollSD();
        }
        else {
            prestige-=college.getCollPrestige();
            stressM-=college.getCollStress();
            sleepDeprivM-=college.getCollSD();
            college = new College(collegeIn);
            prestige+=college.getCollPrestige();
            stressM+=college.getCollStress();
            sleepDeprivM+=college.getCollSD();
        }
        if (gender.equals("O") && collegeIn==2){
            stressM++;
        }
        classes = college.getClasses();
    }

    public College getCollege(){
        return college;
    }

    public void setJob(int jobIn) {
        if (jobIn == 0)
            paycheck = 0;
        else if (jobIn == 1) {
            paycheck = 480;
            stressM++;
        }
        else if (jobIn == 2) {
            paycheck = 960;
            stressM += 2;
            sleepDeprivM++;
        }
        else if (jobIn == 3) {
            paycheck = 1440;
            stressM += 2;
            sleepDeprivM +=2;
        }
        else if (jobIn == 4)
            paycheck = 3200;
            stressM += 3;
            sleepDeprivM += 3;
    }

    public void setParentContrib(int contribIn) {
        if (contribIn == 0)
            parentContrib = 0;
        else if (contribIn == 1)
            parentContrib = (college.getTuition() + college.getRoomBoard()) / 2;
        else if (contribIn == 2)
            parentContrib = (college.getTuition() + college.getRoomBoard());
    }

    public void setRoommate() {
        Random rand = new Random();
        int rmSelect;
        if (gender.equals("F")) {
            rmSelect = rand.nextInt(3);
        } else if (gender.equals("M")) {
            rmSelect = rand.nextInt(3) + 3;
        } else {
            rmSelect = rand.nextInt(6);
        }

        roomie = new Roommate(rmSelect);
    }

    public String getRmName() {
        return roomie.getRmName();
    }

    public String getRmDesc() {
        return roomie.getRmDesc();
    }

    public void upGrade() {
        if (gradeM < 10)
        gradeM++;
    }

    public void downGrade() {
        if (gradeM > 0)
            gradeM--;
    }

    public float getGrade() {
        return gradeM;
    }

    public void upStress() {
        if (stressM < 10)
            stressM++;
    }

    public void downStress() {
        if (stressM > 0)
            stressM--;
    }

    public int getStress() {
        return stressM;
    }

    public void upSleepDepriv() {
        if (sleepDeprivM < 10) {
            sleepDeprivM++;
        }
    }

    public void downSleepDepriv() {
        if (sleepDeprivM > 0) {
            sleepDeprivM--;
        }
    }

    public int getSleepDepriv() {
        return sleepDeprivM;
    }

    public void adjustDebt() {
        debt += (college.getTuition()/2 + college.getRoomBoard()/2 - (paycheck + parentContrib));
        if (debt < 0)
            debt = 0;
    }

    public int getDebt() {
        return debt;
    }

    public int getPrestige() {
        return prestige;
    }

    public void upPrestige() {
        if (prestige < 10) {
            prestige++;
        }
    }

    public void downPrestige() {
        if (prestige > 0) {
            prestige--;
        }
    }

    public void addFriend(String nameIn) {
        friends[numFriends] = nameIn;
        numFriends++;
    }

    public void addGoal(String goalIn) {
        goals[numGoals] = goalIn;
        numGoals++;
    }

    public String getFriend(){
        return friends[0];
    }

    public String friendToString(){
        String out = "";
        for (int i=0;i<numFriends-1;i++){
            out+=friends[i]+", ";
        }out+=friends[numFriends];
        return out;
    }

    public String goalToString(){
        String out = "";
        if (numGoals > 0){
        for (int i=0;i<numGoals-1;i++){
            out+=goals[i]+", ";
        }out+="and "+goals[numGoals-1];}
        return out;
    }

    public int getNumGoals(){
        return numGoals;
    }

    public void increaseCluelessness() {
        cluelessness++;
    }

    public int getClueless() {
        return cluelessness;
    }
}