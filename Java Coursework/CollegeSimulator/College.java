/**
 * Created by Emily on 10/5/15.
 */
public class College {
    private String desc;
    private String name;
    private int iden;
    private int numStudents;
    private String type;
    private int tuition;
    private int roomBoard;
    private int gradRate;
    private int collPrestige;
    private int collStress;
    private int collSD;
    private String[] classes;

    public College (int collegeSelect){
        if (collegeSelect==0){
            iden = 0;
            desc = "If swankiness if your thing, you can't beat Ritzpaulroy! This university is the iviest of the Ivy League, and has been around for" +
                    "at least six hundred years. There are a huge variety of classes here, although variety among students may be lacking - you'll" +
                    "find the majority to be distinctly upper class. Ritzpaulroy is known for being one of the more expensive schools, and attending" +
                    "may hurt your wallet, but the prestige will surely open doors for you later in life. Gotta love the good ol' gold and gold!" +
                    " +2 prestige, +2 stress, +1 sleep deprivation.";

            name = "Ritzpaulroy Filibuster University";
            numStudents = 21000;
            tuition = 51000;
            type = "Private Ivy League";
            roomBoard = 15000;
            gradRate = 90;
            collPrestige = 2;
            collStress = 2;
            collSD = 1;
            classes = new String[]{"Wines and Golf","Intro to Agriculture","Poetry for the Pretentious"};
        }

        else if (collegeSelect==1){
            iden = 1;
            desc = "Nestled into a shear, unforgiving cliff face, Iliad is a picturesque liberal arts college. Not too big and not too small, this " +
                    "college is most well-known for its music and animal communication classes, but nevertheless has a wide variety of majors. Iliad " +
                    "offers a decent education, if you can brave the pricetag and notoriously cold winters. +1 prestige, +1 stress, +1 sleep deprivation.";

            name = "Iliad College";
            numStudents = 6000;
            tuition = 41000;
            type = "Private Liberal Arts";
            roomBoard = 15000;
            gradRate = 75;
            collPrestige = 1;
            collStress = 1;
            collSD = 1;
            classes = new String[]{"Animal Communication 101","Intro to Photography: Looking at Cameras","Eating Local: The Only Ethical Choice"};
        }
        else if (collegeSelect==2){
            iden = 2;
            desc = "Freedom University: Training champions for Christ for 1971. If you've always wished your classes could be more like evangelical TV, Freedom " +
                    "University is for you! Here, you will be free from such moral woes as evolutionary theory, gay people, and pre-marital sex. This is also " +
                    "a less expensive option for private liberal arts education. +1 prestige, +1 stress, +1 sleep deprivation. +1 stress if you identify with a " +
                    "gender outside the binary.";

            name = "Freedom University";
            numStudents = 14000;
            tuition = 22000;
            type = "Private Liberal Arts";
            roomBoard = 10000;
            gradRate = 50;
            collPrestige = 1;
            collStress = 1;
            collSD = 1;
            classes = new String[]{"Bible: Selected Passages","Unlearning High School Biology","Psych 101"};
        }

        else if (collegeSelect==3){
            iden = 3;
            desc = "Don't quite have the budget for a private school? Fear not: SUNY Corple has you covered. Located in rustic Corple, it's one of the " +
                    "nicer state schools. Particularly well-known for its education, physical therapy, physical education, educational physics, and therapy " +
                    "education programs, this college offers a good education without dumping you into an inescapable pit of debt. +0 prestige, +1 stress, +1 sleep deprivation.";

            name = "SUNY Corple";
            numStudents = 6000;
            tuition = 6000;
            type = "State School";
            roomBoard = 5000;
            gradRate = 45;
            collPrestige = 0;
            collStress = 1;
            collSD = 1;
            classes = new String[]{"Educational Physics","Physical Education","Psych 101"};
        }
        else if (collegeSelect == 4){
            iden = 4;
            desc = "Tomply Community College - or TC2, as it is affectionately known - provides a low-cost, flexible option to higher education. Expect it to " +
                    "be very similar, if not identical, to the popular television show 'Community'. This school is particularly useful for those trying to obtain " +
                    "cheaper transfer credits, or for students who prefer night or online class options. +0 prestige, +0 stress, +0 sleep deprivation.";

            name = "TC2";
            numStudents = 5000;
            tuition = 5000;
            type = "Community College";
            roomBoard = 5000;
            gradRate = 45;
            collPrestige = 0;
            collStress = 1;
            collSD = 1;
            classes = new String[]{"Psych 101","The Transfer Process","Aura-Reading for Beginners"};
        }
        else if (collegeSelect == -1){
            name = "Sorry, not a valid college.";
        }
    }

    public int getIden(){ return iden; }
    public int getTuition(){ return tuition; }
    public int getRoomBoard(){ return roomBoard; }
    public String getName(){ return name; }
    public int getCollPrestige(){ return collPrestige; }
    public int getCollStress(){ return collStress; }
    public int getCollSD(){ return collSD; }
    public String[] getClasses() {return classes; }
    public String classesToString() { return classes[0]+", "+classes[1]+", and "+classes[2]; }
    public String toString(){
        return desc +"\n"+"\n"+"Name: "+name+"\n"+"Type: "+type+"\nNumber of Students: "+numStudents+"\n"+"Tuiton: "+tuition+"\n"+"Room & Board: "+
                roomBoard+"\n"+"Graduation Rate: "+gradRate+"\n";
    }
    public String moreOptions() {
        return "Want to hear more about any colleges? (Ritzpaulroy Filibuster University, Iliad College, Freedom University, " +
                "SUNY Corple, and TC2)? Or have you made a choice?";
    }

}
