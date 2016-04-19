/**
 * Created by Emily Conklin on 3/31/15.
 * FireSimulator simulates the spread of a fire across a forest
 * Given user-specific height, width, and probability of the fire spreading
 *
 */
public class FireSimulator {
    private double probability; //probability of next square catching
    private int width; //width of grid
    private int height; //height of grid
    private Terrain myForest; //instance of Terrain class
    private String windDir; //wind direction

    /**
     * constructor for FireSimulator Class
     * @param W int
     * @param H int
     * @param p double
     */
    public FireSimulator(int W, int H, double p, String w){
        this.width = W; //grid width
        this.height = H; //grid height
        this.probability = p; //probability of catching
        this.windDir = w; //windDir

        //constructs new terrain with given width and height
        this.myForest = new Terrain(this.width,this.height);
    }

    /**
     * sets a random location on fire
     */
    public void lightMatch(){
        int randX = (int)(Math.random()*this.width); //randomized x-coord
        int randY = (int)(Math.random()*this.height);   //randomized y-coord
        this.myForest.setFire(randX,randY); //sets fire at this random location
        this.myForest.update();
    }

    /**
     * tests to see whether a fire will spread based on given probability
     */
    public boolean willItSpread(double windCond){
        double randNum = Math.random(); //random number between 0 and 1

        //tests to see if random number is less than the probability
        //increases probability if wind is blowing
        System.out.println(windCond);
        if (randNum<=(windCond*this.probability)){ return true; }
        else return false;
    }

    /**
     * for each burning square, tests to see if its neighbors will catch fire
     * @param i int
     * @param j int
     */
    public void neighborBurn(int i, int j){

        //takes in i and j as xy coordinates
        //tests square to south
        if (i-1>=0) {
            double windCond = 1;
            //gets current state of neighbor to south
            int Sstate = myForest.getState(i-1,j);
            if (Sstate == 2){  //if southern neighbor is forest
                if (windDir == "N"){
                    windCond = 1.5;
                }
                boolean check = willItSpread(windCond);    //use probability to determine fate
                if (check){ myForest.setFire(i - 1, j); }
            }
        }
        //tests square to east
        if (j+1<this.height){
            double windCond = 1;
            int Estate = myForest.getState(i,j+1);
            if (Estate == 2){   //if eastern neighbor is forest
                if (windDir == "W"){
                    windCond = 1.5;
                }
                boolean check = willItSpread(windCond);    //use probability to determine fate
                if (check){ myForest.setFire(i,j+1); }
            }
        }
        //tests square to north
        if (i+1<this.width) {
            double windCond = 1;
            int Nstate = myForest.getState(i + 1, j);
            if (Nstate == 2){   //if northern neighbor is forest
                if (windDir == "S"){
                    windCond = 1.5;
                }
                boolean check = willItSpread(windCond);    //use probability to determine fate
                if (check){ myForest.setFire(i+1,j); }
            }
        }
        //tests square to west
        if (j-1>=0) {
            double windCond = 1;
            int Wstate = myForest.getState(i, j - 1);
            if (Wstate == 2){   //if western neighbor is forest
                if (windDir == "E"){
                    windCond = 1.5;
                }
                boolean check = willItSpread(windCond);    //use probability to determine fate
                if (check){ myForest.setFire(i,j-1); }
            }
        }
    }

    /**
     * progresses each step of the fire
     */
    public void oneStepBurn(){
        //goes through each square in the grid
        for (int i = 0; i<this.width; i++){
            for (int j=0; j <this.height; j++){
                int state = myForest.getState(i,j);   //gets state for each square
                if (state==1) {    //if square burning
                    neighborBurn(i,j);  //test to see if neighbors will burn
                    myForest.setEmpty(i,j);    //set burning square to empty
                }
            }
        }
    myForest.update(); //update terrain with new states
    }

    /**
     * runs fire for each step until fire is burned out
     */
    public void spread() {
        boolean condition = true;
        int count=0;
        int zeroCount=0;
        double percent;

        //runs while the fire is still burning
        while (condition) {
            condition = false;
            oneStepBurn();  //burns forest for one step
            count++;
            zeroCount=0;

            //goes through each square in the grid
            for (int i = 0; i<this.width; i++){
                for (int j=0; j <this.height; j++) {
                    int state = myForest.getState(i,j);
                    //if any square is on fire, keeps while loop going
                    if (state==1){ condition = true; }
                    //adds to running total of burned forest
                    else if (state==0){ zeroCount++; }
                }
            }
            //pause for one second at each step
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
            }
        }
        //after fire has stopped, gets percentage of forest burned
        percent = zeroCount/((double)(this.width*this.height))*100;
        //prints out stats about the fire
        System.out.println("Fire burned out in "+count+" time steps.");
        System.out.println(percent+ "% of forest burned.");
    }

    /**
     * test method for FireSimulator
     */
    public static void main(String[] args) {
        //set up fire
        //width, height, probability of catching, wind direction
        FireSimulator thisGirlIsOnFire = new FireSimulator(10,10,0.4,"N");
        //start and spread fire
        thisGirlIsOnFire.lightMatch();
        thisGirlIsOnFire.spread();
    }
}