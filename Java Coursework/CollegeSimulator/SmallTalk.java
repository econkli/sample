import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Emily on 12/6/15.
 */
public class SmallTalk {
    private boolean timesUp; //whether or not the game is over
    private int canW;
    private int canH;
    private int newNum;
    private int timer;
    private boolean ifBoring;
    private int weird1;
    private int weird2;
    private int weird3;
    private int boring1;
    private int boring2;
    private int boring3;
    private String[] pFiles;
    private String[] pNames;

    /**
     * constructor for SmallTalk class
     */
    public SmallTalk() {
        //initializes canvas size
        canW = 500;
        canH = 500;

        //starts timer at 60 seconds
        timer = 60;

        //draws canvas
        StdDraw.setCanvasSize(canW,canH);
        StdDraw.setXscale(0, 10);
        StdDraw.setYscale(0, 10);

        //puts students on canvas
        Random rand = new Random();
        int pic1 = rand.nextInt(6)+1;
        String file1 = "student"+Integer.toString(pic1)+".jpg";
        int pic2 = rand.nextInt(6)+1;
        String file2 = "student"+Integer.toString(pic2)+".jpg";
        int pic3 = rand.nextInt(6)+1;
        String file3 = "student"+Integer.toString(pic3)+".jpg";

        String[] files = {file1,file2,file3};
        String[] genders = new String[3];
        String[] names = new String[3];

        for (int i=0;i<files.length;i++) {
            if (files[i].contains("1") || files[i].contains("4") || files[i].contains("5") || files[i].contains("6"))
                genders[i]="M";
            else
                genders[i] = "F";
        }

        //draws students
        StdDraw.picture(5,9,"studentPics/"+file1,3,3);
        StdDraw.picture(2,5,"studentPics/"+file2,3,3);
        StdDraw.picture(8,5,"studentPics/"+file3,3,3);

        //gets names
        for (int i=0;i<files.length;i++){
            if (genders[i].equals("M")){
                String[] mNames = {"Nick", "Andrew", "Taylor", "Zach"};
                names[i] = mNames[rand.nextInt(names.length)+1];
            }
            else{
                String[] fNames = {"Emily","Holly", "Bronwyn", "Kim", "Samantha", "Maddy", "Jill", "Megan"};
                names[i] = fNames[rand.nextInt(names.length)+1];
            }
        }

        //draws names
        StdDraw.setPenColor(Color.black);
        StdDraw.text(5, 7, names[0]);
        StdDraw.text(2, 3, names[1]);
        StdDraw.text(8, 3, names[2]);

        //draws starting scores
        StdDraw.setPenColor(Color.blue);
        StdDraw.text(5, 6.5, "Boring: 0");
        StdDraw.text(2, 2.5, "Boring: 0");
        StdDraw.text(8, 2.5, "Boring: 0");
        StdDraw.setPenColor(Color.red);
        StdDraw.text(2, 2, "Weird: 0");
        StdDraw.text(5, 6, "Weird: 0");
        StdDraw.text(8, 2, "Weird: 0");

        //draws dice
        StdDraw.picture(5,1,"studentPics/dice.png",1,1);

        //draws timer
        StdDraw.setPenColor(Color.black);
        StdDraw.text(9,10, "Timer: 60");

        //saves arrays to public variables
        pFiles = files;
        pNames = names;
    }

    /**
     * clears and redraws grid
     */
    public void update() {
        StdDraw.clear(); //clears contents of grid

        //redraws students
        StdDraw.picture(5,9,"studentPics/"+pFiles[0],3,3);
        StdDraw.picture(2,5,"studentPics/"+pFiles[1],3,3);
        StdDraw.picture(8,5,"studentPics/"+pFiles[2],3,3);

        //redraws names
        StdDraw.setPenColor(Color.black);
        StdDraw.text(5, 7, pNames[0]);
        StdDraw.text(2, 3, pNames[1]);
        StdDraw.text(8, 3, pNames[2]);

        //redraws timer
        StdDraw.text(9,10, "Timer:"+timer);

        //redraws dice
        StdDraw.picture(5,1,"studentPics/dice.png",1,1);

        //redraws scores
        StdDraw.setPenColor(Color.blue);
        StdDraw.text(5, 6.5, "Boring: "+boring1);
        StdDraw.text(2, 2.5, "Boring: "+boring2);
        StdDraw.text(8, 2.5, "Boring: "+boring3);
        StdDraw.setPenColor(Color.red);
        StdDraw.text(5, 6, "Weird: "+weird1);
        StdDraw.text(2, 2, "Weird: "+weird2);
        StdDraw.text(8, 2, "Weird: "+weird3);
    }

    /**
     * runs the game itself inside a while loop
     * gets input from user
     */
    public ArrayList<String> gamePlay() {
        System.out.println("here");
        ifBoring = true;
        Random rand = new Random();

        boring1 = 0;
        boring2 = 0;
        boring3 = 0;
        weird1 = 0;
        weird2 = 0;
        weird3 = 0;
        newNum = 0;

        //starts the clock
        Timer myClock = new Timer();

        //while time is not up, runs the game
        while (timer>0){

            //if user clicks on die and number has not been rolled
            if (StdDraw.isKeyPressed(32) && newNum==0) {
                try {
                    //rolls dice
                    newNum = rand.nextInt(12) + 1;

                    //prints number
                    if (rand.nextInt(2)==1) {
                        StdDraw.setPenColor(Color.blue);
                        ifBoring = true;
                    }
                    else {
                        StdDraw.setPenColor(Color.red);
                        ifBoring = false;
                    }
                    StdDraw.text(5, 2, Integer.toString(newNum));
                }
                 catch (ArrayIndexOutOfBoundsException e) {}
            }

            //increase student 1's score

            if (StdDraw.isKeyPressed(50) && newNum!=0) {
                System.out.println("student1");
                try {
                    if (ifBoring)
                        boring1 += newNum;
                    else
                        weird1 += newNum;
                    newNum=0;
                    update();
                } catch (ArrayIndexOutOfBoundsException e) {}
            }

            //increase student 2's score
            if (StdDraw.isKeyPressed(49) && newNum!=0) {
                System.out.println("student2");
                try {
                    if (ifBoring)
                        boring2 += newNum;
                    else
                        weird2 += newNum;
                    newNum=0;
                    update();
                } catch (ArrayIndexOutOfBoundsException e) {}
            }

            //increase student 3's score
            if (StdDraw.isKeyPressed(51) && newNum!=0) {
                System.out.println("student3");
                try {
                    if (ifBoring)
                        boring3 += newNum;
                    else
                        weird3 += newNum;
                    newNum=0;
                    update();
                } catch (ArrayIndexOutOfBoundsException e) {}
            }
            timer = 60 - (int)myClock.elapsedTime();
        }
        //clears screen + returns list of friends when finished
        StdDraw.clear();
        StdDraw.text(5,5,"Time's up!");
        ArrayList friends = new ArrayList();
        if (Math.abs(boring1-weird1)<5){
            friends.add(pNames[0]);
        }
        if (Math.abs(boring2-weird2)<5){
            friends.add(pNames[1]);
        }
        if (Math.abs(boring3-weird3)<5){
            friends.add(pNames[2]);
        }
        return friends;
    }

    public void requestFocus(){
        StdDraw.frame.requestFocusInWindow();
    }

    /**
     * test main function for Grid class
     */
    public static void main(String[] args) {
        SmallTalk myGame = new SmallTalk();
        myGame.gamePlay();
    }
}
