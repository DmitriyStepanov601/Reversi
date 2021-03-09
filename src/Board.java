import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A class that describes the playing field
 * @author Dmitriy Stepanov
 */
public class Board extends JPanel {
    private static JLabel score1;
    private static JLabel score2;
    private static JButton newGame;
    private static JButton undo;

    private static JButton [] cell;
    private static Reversi board;
    private static final ArrayList<Reversi> arrReversi = new ArrayList<>();
    private static int countUndo = 0;
    private static int playerScore = 2;
    private static int pcScore = 2;

    private static Reversi start;
    private static final int rows = 8;
    private static final int cols = 8;
    private static final Color col = Color.green;

    /**
     * Constructor - creating a new playing field
     * @see Board#Board()
     */
    public Board() {
        super(new BorderLayout());    
        setPreferredSize(new Dimension(800,700));
        setLocation(0, 0);
        board = new Reversi();
        start = board;
        arrReversi.add(new Reversi(board));

        JPanel boardPanel = new JPanel(new GridLayout(8, 8));
        cell = new JButton[64];
        int k = 0;

        for (int row = 0; row < rows; row++) {
            for (int colum=0; colum < cols; colum++) {
                cell[k] = new JButton();
                cell[k].setSize(50, 50);
                cell[k].setBackground(Color.GREEN);
                cell[k].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                if (board.gameCells[row][colum].getCh() == 'X') {
                    Image dark = loadImage("/dark.png");
                    cell[k].setIcon(new ImageIcon(dark));
                } else if (board.gameCells[row][colum].getCh() == 'O') {
                    Image light = loadImage("/light.png");
                    cell[k].setIcon(new ImageIcon(light));
                } else if (row == 2 && colum == 4 || row == 3 && colum == 5 ||
                        row == 4 && colum == 2 || row == 5 && colum == 3 ) {
                    Image legal = loadImage("/legalMoveIcon.png");
                    cell[k].setIcon(new ImageIcon(legal));
                }

                cell[k].addActionListener(new Clicker());
                boardPanel.add(cell[k]);
                k++;
            }
        }

        add(boardPanel, BorderLayout.CENTER);
        JPanel scorePanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        scorePanel.setPreferredSize(new Dimension(240,700));

        JLabel dark = new JLabel();
        Image darkimg = loadImage("/dark.png");
        dark.setIcon(new ImageIcon(darkimg));
        JLabel light = new JLabel();
        Image lightimg = loadImage("/light.png");
        light.setIcon(new ImageIcon(lightimg));

        score1 = new JLabel();
        score1.setText("Player : " + playerScore + "  ");
        score1.setFont(new Font("Serif", Font.BOLD, 22));
        score2 = new JLabel();   
        score2.setText("AI : " + pcScore + "  ");
        score2.setFont(new Font("Serif", Font.BOLD, 22));

        JLabel dump = new JLabel(" ");
        JLabel dummy = new JLabel(" ");

        newGame = new JButton();
        Image start = loadImage("/start.png");
        newGame.setIcon(new ImageIcon(start));
        newGame.addActionListener(new Clicker());
        newGame.setContentAreaFilled(false);
        newGame.setBorderPainted(false);

        undo = new JButton();
        Image undoimg = loadImage("/undo.png");
        undo.setIcon(new ImageIcon(undoimg));
        undo.addActionListener(new Clicker());
        undo.setContentAreaFilled(false);
        undo.setBorderPainted(false);

        c.gridx = 0;
        c.gridy = 1;
        scorePanel.add(dark, c);
        c.gridx = 1;
        c.gridy = 1;
        scorePanel.add(score1,c);
        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(0, 0, 0, 0);
        scorePanel.add(light, c);
        c.gridx = 1;
        c.gridy = 2;
        scorePanel.add(score2,c);
        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(0, 0, 0, 0);
        scorePanel.add(dump,c);
        c.gridx = 1;
        c.gridy = 3;
        scorePanel.add(dummy,c);
        c.gridx = 0;
        c.gridy = 4;
        c.insets = new Insets(0, 30, 0, 0);
        scorePanel.add(newGame,c);
        c.gridx = 1;
        c.gridy = 4;
        c.insets = new Insets(0, -10, 0, 0);
        scorePanel.add(undo,c);
        add(scorePanel, BorderLayout.EAST);
    }

    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(Board.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    static class Clicker implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == newGame) {
                board.reset();
                arrReversi.clear();
                arrReversi.add(new Reversi(start));
                int k = 0;

                for (int row = 0; row < rows; row++) {
                    for (int colum = 0; colum < cols; colum++) {
                        cell[k].setIcon(null);
                        if (board.gameCells[row][colum].getCh() == 'X') {
                            Image dark = loadImage("/dark.png");
                            cell[k].setIcon(new ImageIcon(dark));
                        } else if (board.gameCells[row][colum].getCh() == 'O') {
                            Image light = loadImage("/light.png");
                            cell[k].setIcon(new ImageIcon(light));
                        }
                        if (row == 2 && colum == 4 || row == 3 && colum == 5 ||
                        row == 4 && colum == 2 || row == 5 && colum == 3 ) {
                            Image legal = loadImage("/legalMoveIcon.png");
                            cell[k].setIcon(new ImageIcon(legal));
                        }
                        ++k;
                    }
                }

                playerScore = 2; pcScore = 2;
                score1.setText("Player : " + playerScore + "  ");
                score2.setText("AI : " + pcScore + "  ");
            } else if (e.getSource() == undo) {
                countUndo++;
                int y, point;
                char c, x;
                int[] arr = new int[3];
                ArrayList <Integer> arrList = new ArrayList <>();

                if (arrReversi.size() - countUndo - 1 >= 0) {
                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < cols; j++) {
                            c = arrReversi.get(arrReversi.size() - countUndo - 1)
                                    .gameCells[i][j].getCh();
                            x = arrReversi.get(arrReversi.size() - countUndo - 1)
                                    .gameCells[i][j].getCorX();
                            y = arrReversi.get(arrReversi.size() - countUndo - 1)
                                    .gameCells[i][j].getCorY();
                            board.gameCells[i][j].setPosition(x, c, y); 
                        }
                    }

                    int k = 0;
                    for (int row = 0; row < rows; row++) {
                        for (int colum = 0; colum < cols; colum++) {
                            cell[k].setIcon(null);
                            if (board.gameCells[row][colum].getCh() == 'X') {
                                Image dark = loadImage("/dark.png");
                                cell[k].setIcon(new ImageIcon(dark));
                            } else if (board.gameCells[row][colum].getCh() == 'O') {
                                Image light = loadImage("/light.png");
                                cell[k].setIcon(new ImageIcon(light));
                            }
                            ++k;
                        }
                    }

                    board.findLegalMove(arrList);

                    for (int j = 0; j < arrList.size(); j += 2) {
                        Image legal = loadImage("/legalMoveIcon.png");
                        cell[arrList.get(j) * rows + arrList.get(j + 1)].setIcon(new ImageIcon(legal));
                    }

                    board.controlElements(arr);
                    playerScore = arr[0]; pcScore = arr[1];
                    score1.setText("Player : " + playerScore + "  ");
                    score2.setText("AI : " + pcScore + "  ");
                }
            } else {
                for (int i = 0; i < 64; i++) {
                    if (e.getSource() == cell[i]) {
                        int xCor, yCor;
                        int ct, point;
                        int[] arr = new int[3];
                        System.out.println("button : " + i);

                        if (i == 0) {
                            xCor = 0;
                            yCor = 0;
                        } else {
                            yCor = i % 8;
                            xCor = i / 8;
                        }

                        ct = board.play(xCor, yCor);
                        if (ct == 0) {
                            arrReversi.add(new Reversi(board));
                            int k = 0;

                            for (int row = 0; row < rows; row++) {
                                for (int colum=0; colum < cols; colum++) {
                                    if (board.gameCells[row][colum].getCh() == 'X') {
                                        Image dark = loadImage("/dark.png");
                                        cell[k].setIcon(new ImageIcon(dark));
                                    } else if (board.gameCells[row][colum].getCh() == 'O') {
                                        Image light = loadImage("/light.png");
                                        cell[k].setIcon(new ImageIcon(light));
                                    }
                                    k++;
                                }
                            }

                            board.controlElements(arr);
                            playerScore = arr[0]; pcScore = arr[1];
                            score1.setText("Player : " + playerScore + "  ");
                            score2.setText("AI : " + pcScore + "  ");
                        }

                        if (ct == 0 || ct == -1) {
                            board.play();
                            arrReversi.add(new Reversi(board));
                            ArrayList <Integer> arrList = new ArrayList <>();
                            int k = 0;

                            for (int row = 0; row < rows; row++) {
                                for (int colum=0; colum < cols; colum++) {
                                    if (board.gameCells[row][colum].getCh() == 'X') {
                                        Image dark = loadImage("/dark.png");
                                        cell[k].setIcon(new ImageIcon(dark));
                                    } else if (board.gameCells[row][colum].getCh() == 'O') {
                                        Image light = loadImage("/light.png");
                                        cell[k].setIcon(new ImageIcon(light));
                                    } else if (board.gameCells[row][colum].getCh() == '.') {
                                        cell[k].setIcon(null);
                                    }
                                    k++;
                                }
                            }

                            board.findLegalMove(arrList);

                            for (int j = 0; j < arrList.size(); j += 2) {
                                Image legal = loadImage("/legalMoveIcon.png");
                                cell[arrList.get(j) * rows + arrList.get(j + 1)].
                                        setIcon(new ImageIcon(legal));
                            }

                            board.controlElements(arr);
                            playerScore = arr[0]; pcScore = arr[1];
                            score1.setText("Player : " + playerScore + "  ");
                            score2.setText("AI : " + pcScore + "  ");
                        }
                    }
                }

                int st = board.endOfGame();
                if (st == 0) {
                    if (playerScore > pcScore)
                        JOptionPane.showMessageDialog(null,
                                "No legal move!\nPlayer Win!",
                                "Game Over",JOptionPane.PLAIN_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(null,
                                "No legal move!\nAI Win!",
                                "Game Over",JOptionPane.PLAIN_MESSAGE);
                } else if (st == 1 || st == 3) {
                    ImageIcon defeat = new ImageIcon(loadImage("/lose.png"));
                    JOptionPane.showMessageDialog(null,"AI Win!",
                            "Defeat", JOptionPane.INFORMATION_MESSAGE, defeat);
                } else if (st == 2 || st == 4) {
                    ImageIcon win = new ImageIcon(loadImage("/win.png"));
                    JOptionPane.showMessageDialog(null,"Player Win!",
                            "Victory", JOptionPane.INFORMATION_MESSAGE, win);
                }
            }
        }
    }
}