/**
 * Created by Emily Conklin on 5/3/15.
 * Timer is used as part of the Minesweeper game
 * Calculates elapsed time in milliseconds to save as a high score
 * Runs with StdDraw.java, Grid.java, Minesweeper.java, Square.java
 */
public class Timer {
    public final long start;

    /**
     * constructor for Timer class
     * gets current system time when game starts
     */
    public Timer(){
        start = System.currentTimeMillis();
    }

    /**
     * calculates elapsed time
     * @return time in seconds
     */
    public double elapsedTime(){
        long now = System.currentTimeMillis();
        double elapsed = ((double)(now-start))/1000;
        return elapsed;
    }
}
