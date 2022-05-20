import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class MainWindow extends JFrame {

    private JPanel contentPane = null;
    private GameBoard gameBoard = null;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainWindow frame = new MainWindow();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public MainWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1269, 966);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        NewGame();
        
        JButton resetGameButton = new JButton("reset game");
        resetGameButton.setBounds(1030, 11, 213, 23);
        contentPane.add(resetGameButton);
        System.out.println();
    }
    
    private void NewGame() {
        gameBoard = new GameBoard();
        //this is the mouse listener for the board
        gameBoard.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                gameBoard.HandleMouseClick(e.getX(),e.getY());
            }
        });
        gameBoard.setBounds(10, 11, 796, 700);
        gameBoard.DrawBoard();
        contentPane.add(gameBoard);
    }
}
