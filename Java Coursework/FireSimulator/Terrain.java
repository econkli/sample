import java.awt.Color;
/**
 * Adapted by Emily Conklin on 3/31/15.
 * Terrain works with FireSimulator - creates an instance of a forest simulation grid
 * Holds information on the state of each square in the grid
 */

public class Terrain {
    private int W;  // Width of Grid
    private int H;  // Height of Grid
    private int[][] state; //0 - empty, 1 - burning, 2- forest
    private int[][] nextState; //0 - empty, 1 - burning, 2- forest

    /**
     * constructor for Terrain Class
     * @param width int
     * @param height int
     */
    public Terrain (int width, int height){
        this.W = width;
        this.H = height;

        this.state = new int[W][H];     //current state for each square
        this.nextState = new int[W][H]; //next state for each square

        //goes through grid, sets state of each square to forest
        for (int i = 0; i<W; i++){
            for (int j=0; j <H; j++){
                this.state[i][j] = 2;
                this.nextState[i][j] = 2;
            }
        }

        //draws canvas and updates
        StdDraw.setCanvasSize(500,500);
        StdDraw.setXscale(0, W);
        StdDraw.setYscale(0, H);
        this.update();
    }

    public int getState(int i, int j){
        return this.state[i][j];
    }

    /**
     * updates terrain by swapping next state to current state and redrawing
     */
    public void update(){
        //goes through each square in grid
        for (int i = 0; i<W; i++){
            for (int j=0; j <H; j++){
                //sets each state to next state
                this.state[i][j] = this.nextState[i][j];
                //sets empty squares to black
                Color c = new Color(30,30,30);
                //sets burning squares to red
                if (this.state[i][j] == 1){ c = new Color(255,30,30);}
                //sets forested squares to green
                else if (this.state[i][j] == 2){ c = new Color(30,200,30);}
                StdDraw.setPenColor(c);
                //draws each square at same xy coordinates
                StdDraw.filledSquare(i+.45, j+.45, 0.45);
            }
        }
    }

    /**
     * Sets a certain square to empty
     * @param i int
     * @param j int
     */
	public void setEmpty(int i,int j){
        this.nextState[i][j]=0;
    }

    /**
     * Sets a certain square to burning
     * @param i int
     * @param j int
     */
    public void setFire(int i,int j){
        this.nextState[i][j]=1;
    }

    /**
     * Sample main method that tests Terrain Class -
     * @param args
     */
    public static void main(String[] args) {
        //Terrain t = new Terrain(10,10);
        //t.setEmpty(4,5);
        //t.setFire(9,2);
        //t.update();
    }
}