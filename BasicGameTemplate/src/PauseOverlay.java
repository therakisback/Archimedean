
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import util.GameIO;



public class PauseOverlay {

    private static final GameIO io = GameIO.getInstance();

    public static int levelPopup(JFrame frame, int id1, int id2, boolean active) {

        // There is some funk regarding how ActionListeners can change values.
        // They cannot return, and thus we have to store the value, but it refuses to store in an int
        // Its a scope issue I believe, and this fixes it, which is good enough for me.
        int choice[] = {-1};

        JDialog dialog = new JDialog(frame, "Choose an upgrade!", JDialog.ModalityType.APPLICATION_MODAL);

        dialog.setLayout(new BorderLayout(10, 10));

        JLabel button1Text;
        JLabel button2Text;
        if (active) {
            button1Text = new JLabel(String.format("<html>%s<br>%s<br>Press %c to active the ability!</html>" ,
                                        io.getActiveTitle(id1), io.getActiveDescription(id1), io.getKeyByID(id1)));
            button2Text = new JLabel(String.format("<html>%s<br>%s<br>Press %c to active the ability!</html>" ,
                                        io.getActiveTitle(id2), io.getActiveDescription(id2), io.getKeyByID(id2)));
        } else {
            button1Text = new JLabel(String.format("<html>%s<br>%s</html>", io.getPassiveTitle(id1), io.getPassiveDescription(id1)));
            button2Text = new JLabel(String.format("<html>%s<br>%s</html>", io.getPassiveTitle(id2), io.getPassiveDescription(id2)));
        }

        JButton btn1 = new JButton();
        JButton btn2 = new JButton();
        btn1.setLayout(new BorderLayout());
        btn2.setLayout(new BorderLayout());
        btn1.addActionListener(e -> {choice[0] = id1; dialog.dispose();});
        btn2.addActionListener(e -> {choice[0] = id2; dialog.dispose();});
        btn1.add(button1Text);
        btn2.add(button2Text);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Choose an Upgrade to your player!"), BorderLayout.CENTER);
        JPanel panel2 = new JPanel();
        panel2.add(btn1);
        panel2.add(btn2);
        dialog.add(panel2);
        // This prevents bugs where the player presses "X" and then gets stuck
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);   
        dialog.setUndecorated(true);

        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
        

        return choice[0];
    }

    public static boolean lose(JFrame frame) {
        JDialog dialog = new JDialog(frame, "", JDialog.ModalityType.APPLICATION_MODAL);
        boolean[] choice = {false};

        JLabel button1Text = new JLabel("Play again");
        JLabel button2Text = new JLabel("Quit game");

        JButton btn1 = new JButton();
        JButton btn2 = new JButton();

        btn1.setLayout(new BorderLayout());
        btn2.setLayout(new BorderLayout());
        btn1.addActionListener(e -> {choice[0] = true; dialog.dispose();});
        btn2.addActionListener(e -> {System.exit(0);});
        btn1.add(button1Text);
        btn2.add(button2Text);

        JPanel panel = new JPanel();
        panel.add(new JLabel("You Lost! Play again?"), BorderLayout.CENTER);
        dialog.add(panel);
        JPanel panel2 = new JPanel();
        panel2.add(btn1);
        panel2.add(btn2);
        dialog.add(panel2, BorderLayout.SOUTH);
        // This prevents bugs where the player presses "X" and then gets stuck
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);   
        dialog.setUndecorated(true);
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
        
        return choice[0];
    }
    
    public static boolean win(JFrame frame) {
        JDialog dialog = new JDialog(frame, "", JDialog.ModalityType.APPLICATION_MODAL);
        boolean[] choice = {false};

        JLabel button1Text = new JLabel("Play again");
        JLabel button2Text = new JLabel("Quit game");

        JButton btn1 = new JButton();
        JButton btn2 = new JButton();

        btn1.setLayout(new BorderLayout());
        btn2.setLayout(new BorderLayout());
        btn1.addActionListener(e -> {choice[0] = true; dialog.dispose();});
        btn2.addActionListener(e -> {System.exit(0);});
        btn1.add(button1Text);
        btn2.add(button2Text);

        JPanel panel = new JPanel();
        panel.add(new JLabel("You Won! Play again?"), BorderLayout.CENTER);
        dialog.add(panel);
        JPanel panel2 = new JPanel();
        panel2.add(btn1);
        panel2.add(btn2);
        dialog.add(panel2, BorderLayout.SOUTH);
        // This prevents bugs where the player presses "X" and then gets stuck
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);   
        dialog.setUndecorated(true);
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
        
        return choice[0];
    }
}
