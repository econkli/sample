import java.awt.*;

/**
 * Created by Emily on 10/5/15.
 */

public class Game {

    /**
     * Launches the application
     */
    public static void main(String[] args) {
        Window game = new Window();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Window frame = new Window();
                    frame.setVisible(true);
                    frame.intro1();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}