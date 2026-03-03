
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import util.GameIO;



public class PauseOverlay {

    private static final GameIO io = GameIO.getInstance();

    public static int popup(JFrame frame, int id1, int id2, boolean active) {

        // There is some funk regarding how ActionListeners can change values.
        // They cannot return, and thus we have to store the value, but it refuses to store in an int
        // Its a scope issue I believe, and this fixes it, which is good enough for me.
        int choice[] = {-1};

        JDialog dialog = new JDialog(frame, "Choose an upgrade!", JDialog.ModalityType.APPLICATION_MODAL);

        dialog.setLayout(new BorderLayout(10, 10));
        dialog.add(new JLabel("Choose an Upgrade to your player!"), BorderLayout.CENTER);

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
        panel.add(btn1);
        panel.add(btn2);
        dialog.add(panel);
        // This prevents bugs where the player presses "X" and then gets stuck
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);   
        dialog.setUndecorated(true);

        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
        

        return choice[0];
    }
}
