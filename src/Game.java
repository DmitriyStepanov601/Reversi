import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * A class that describes the game's graphical interface
 * @author Dmitriy Stepanov
 */
public class Game extends JFrame {

    /**
     * Constructor - creating a new game
     * @see Game#Game()
     */
    public Game() {
        super("Reversi");
        setLayout(new BorderLayout());

        Image windowIcon = null;
        try {
            windowIcon = ImageIO.read(Game.class.getResource("/reversi.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setIconImage(windowIcon);
        JPanel pnlLeft = new Board();
        add(pnlLeft, BorderLayout.CENTER);
        setSize(800, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Game();
    }
}