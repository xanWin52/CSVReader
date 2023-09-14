import javax.swing.*;
import java.awt.*;

public class GUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Attendance");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,300);
        
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter Text");
        JTextField tf = new JTextField(20);
        JButton done = new JButton("done");
        panel.add(label);
        panel.add(tf);
        panel.add(done);
        frame.getContentPane().add(BorderLayout.SOUTH, panel);

        frame.setVisible(true);
    }
}
