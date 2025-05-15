
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.URL;
import java.util.jar.JarEntry;

public class Card extends JFrame implements ActionListener{
    private JFrame startFrame;
    private JLabel backgroundLabel;
    private JButton startButton;
    
    private Card(){
        startScreen();
    }
    private void startScreen(){
        startFrame  = new JFrame("Card Saga");
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setLocationRelativeTo(null);

        // Get background image 
        try{
            URL bgImUrl = getClass().getResource("start_backg.jpg");
            if (bgImUrl != null){
                ImageIcon backgroundIcon = new ImageIcon(bgImUrl);
                //set frame size to match
                int width = backgroundIcon.getIconWidth();
                int height = backgroundIcon.getIconHeight();
                startFrame.setSize(width,height);
                backgroundLabel = new JLabel(backgroundIcon);
                backgroundLabel.setLayout(new BorderLayout());
                startFrame.setContentPane(backgroundLabel);
                //Add button start 
                startButton = new JButton();
                styleStart();
                JPanel buttoPanel = new JPanel();
                buttoPanel.setOpaque(false);
                buttoPanel.setLayout(new GridBagLayout());
                buttoPanel.add(startButton);
                //Add to the frame
                backgroundLabel.add(buttoPanel, BorderLayout.SOUTH);
            }else{
                throw new Exception("Background not found");
            }
        } catch (Exception e){
            System.err.println("Error loading");
        }
        startFrame.setVisible(true);
    }
    private void styleStart(){
        URL startUrl = getClass().getResource("start_button.png");
        if(startUrl != null){
            ImageIcon start = new ImageIcon(startUrl);
            startButton.setIcon(start);
            //style 
            startButton.setContentAreaFilled(false); 
            startButton.setBorderPainted(false);
            startButton.setFocusPainted(false);
            startButton.addActionListener(this);
        }
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == startButton){
            //Debuging
            System.out.println("Start button clicked");
            guide_screen();

        }
    }

    private void guide_screen(){
        startFrame.dispose();
        JFrame guidFrame = new JFrame("Guid");
        URL guid_screenURL = getClass().getResource("guid_screen.png");
        if(guid_screenURL != null){
            ImageIcon guidIcon = new ImageIcon(guid_screenURL);
            //Style
            // int width = guidIcon.getIconWidth();
            // int height = guidIcon.getIconHeight();
            // guidFrame.setSize(width, height);
            JLabel guideLabel = new JLabel(guidIcon);
            guidFrame.add(guideLabel, BorderLayout.CENTER);
            guidFrame.pack();
            guidFrame.setLayout(new BorderLayout());
            guidFrame.setContentPane(guideLabel);
            guidFrame.setLocationRelativeTo(null);
        }else{
            System.out.println("Background not found");
        }
            
        guidFrame.setVisible(true);
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new Card());
    }
}