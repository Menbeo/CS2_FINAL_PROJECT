import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.URL;

public class Card extends JFrame implements ActionListener {
    //Background
    private JLabel backgroundLabel;
    private JLayeredPane layeredPane;
    private JLabel mainLabel;
    //Button
    // private Jbutton poker;
    // private JButton blackjack;
    // private Jbutton commingsoon;
    private JButton startButton;
    private JButton arrow;

    
    private Card() {
        setTitle("Card Saga");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startScreen();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void startScreen() {
        getContentPane().removeAll(); 
        try {
            URL bgImUrl = getClass().getResource("start_backg.jpg");
            if (bgImUrl != null) {
                ImageIcon backgroundIcon = new ImageIcon(bgImUrl);
                int width = 1505;
                int height = 845;
                setSize(width, height);
                backgroundLabel = new JLabel(backgroundIcon);
                backgroundLabel.setLayout(new BorderLayout());
                setContentPane(backgroundLabel);
                //Button add
                startButton = new JButton();
                styleStart();
                JPanel buttonPanel = new JPanel();
                buttonPanel.setOpaque(false);
                buttonPanel.setLayout(new GridBagLayout());
                buttonPanel.add(startButton);
                backgroundLabel.add(buttonPanel, BorderLayout.SOUTH);
            } else {
                throw new Exception("Background not found");
            }
        } catch (Exception e) {
            System.err.println("Error loading background: " + e.getMessage());
        }
        revalidate();
        repaint();
    }
    private void styleStart() {
        URL startUrl = getClass().getResource("start_button.png");
        if (startUrl != null) {
            ImageIcon start = new ImageIcon(startUrl);
            startButton.setIcon(start);
            startButton.setContentAreaFilled(false);
            startButton.setBorderPainted(false);
            startButton.setFocusPainted(false);
            startButton.addActionListener(this);
        }
    }
    private void guideScreen() {
        getContentPane().removeAll(); 

        URL guidScreenURL = getClass().getResource("guid_screen.png");
        if (guidScreenURL != null) {
            ImageIcon originalIcon = new ImageIcon(guidScreenURL);
            int newWidth = 1550;
            int newHeight = 900;
            Image scaledImage = originalIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(scaledImage);
            JLabel guideLabel = new JLabel(resizedIcon);
            guideLabel.setBounds(0, 0, newWidth, newHeight);
            //Arrrow add
            arrow = new JButton();
            arrowStyle();
            arrow.setBounds(newWidth - 333 - 80, newHeight - 188 - 80, 333, 188);
            layeredPane = new JLayeredPane();
            layeredPane.setPreferredSize(new Dimension(newWidth, newHeight));
            layeredPane.add(guideLabel, Integer.valueOf(0));
            layeredPane.add(arrow, Integer.valueOf(1));
            guideLabel.setLayout(null);
            arrow.setLayout(null);
            guideLabel.setOpaque(false);
            layeredPane.setOpaque(true);
            setContentPane(layeredPane);
            pack();
        } else {
            System.out.println("Guide image not found");
        }
        revalidate();
        repaint();
    }
    private void arrowStyle() {
        URL arrowUrl = getClass().getResource("arrow.png");
        if (arrowUrl != null) {
            ImageIcon arrowIcon = new ImageIcon(arrowUrl);
            arrow.setIcon(arrowIcon);
            arrow.setContentAreaFilled(false);
            arrow.setBorderPainted(false);
            arrow.setFocusPainted(false);
            arrow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            arrow.addActionListener(this);
        } else {
            System.out.println("Arrow image not found");
        }
    }
    private void main_game_screen(){
        getContentPane().removeAll();
        URL main_screen = getClass().getClassLoader().getResource("start_backg.jpg");
        System.out.println("Attempting to load: " + main_screen);
        if(main_screen != null){
            ImageIcon originalIcon = new ImageIcon(main_screen);
            int width = 1550;
            int height = 900;
            Image scaledImage = originalIcon.getImage().getScaledInstance(width,height, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(scaledImage);
            mainLabel = new JLabel(resizedIcon);
            mainLabel.setLayout(new BorderLayout());
            setContentPane(mainLabel);
            // setSize(width,height);
            //Card add
        } else {
           System.out.println("Background not found");
        }
        revalidate();
        repaint();
    }
    //----ACTION HANDLER---
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            System.out.println("Start button clicked");
            guideScreen();
        } else if (e.getSource() == arrow) {
            System.out.println("Arrow button clicked");
            main_game_screen(); 
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Card());
    }
}