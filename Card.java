import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.URL;
import java.nio.file.Path;

public class Card extends JFrame implements ActionListener {
    //Background
    private JLabel backgroundLabel;
    private JLayeredPane layeredPane;
    private JLabel mainLabel;
    //Button
    private JButton poker;
    private JButton blackjack;
    private JButton commingsoon;
    private JButton startButton;
    private JButton arrow;

    
    private Card() {
        setTitle("Card Saga");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startScreen();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    //---Start Screen--- 
    private void startScreen() {
        getContentPane().removeAll(); 
        try {
            URL bgImUrl = getClass().getResource("image/start_backg.jpg");
            if (bgImUrl != null) {
                ImageIcon backgroundIcon = new ImageIcon(bgImUrl);
                int newWidth = 1550;
                int newHeight = 900;
                setSize(newWidth, newHeight);
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
                System.err.println("Background image not found: start_backg.jpg");
                throw new Exception("Background not found");
            }
        } catch (Exception e) {
            System.err.println("Error loading background: " + e.getMessage());
        }
        revalidate();
        repaint();
    }
    
    private void styleStart() {
        URL startUrl = getClass().getResource("image/start_button.png");
        if (startUrl != null) {
            ImageIcon start = new ImageIcon(startUrl);
            startButton.setIcon(start);
            startButton.setContentAreaFilled(false);
            startButton.setBorderPainted(false);
            startButton.setFocusPainted(false);
            startButton.addActionListener(this);
        } else {
            System.err.println("Start button image not found: start_button.png");
        }
    }
    //Guide Screen
    private void guideScreen() {
        getContentPane().removeAll(); 
        URL guidScreenURL = getClass().getResource("image/guid_screen.png");
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
            System.err.println("Guide image not found: guid_screen.png");
        }
        revalidate();
        repaint();
    }
    
    private void arrowStyle() {
        URL arrowUrl = getClass().getResource("image/arrow.png");
        if (arrowUrl != null) {
            ImageIcon arrowIcon = new ImageIcon(arrowUrl);
            arrow.setIcon(arrowIcon);
            arrow.setContentAreaFilled(false);
            arrow.setBorderPainted(false);
            arrow.setFocusPainted(false);
            arrow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            arrow.addActionListener(this);
        } else {
            System.err.println("Arrow image not found: arrow.png");
        }
    }
    
    private void main_game_screen(){
        getContentPane().removeAll();
        URL mainScreenUrl = getClass().getResource("image/mainscreen.jpg");
        if (mainScreenUrl != null) {
            int newWidth = 1580, newHeight = 900;
            ImageIcon resizedIcon = new ImageIcon(new ImageIcon(mainScreenUrl).getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));

            mainLabel = new JLabel(resizedIcon);
            mainLabel.setBounds(0, 0, newWidth, newHeight);
            mainLabel.setOpaque(false);

            poker = new JButton();
            blackjack = new JButton();
            commingsoon = new JButton();
            cardStyle();

            int sectionWidth = newWidth / 3, buttonWidth = 300, buttonHeight = 500, buttonY = 10;
            poker.setBounds((sectionWidth - buttonWidth) / 2, buttonY, buttonWidth, buttonHeight);
            blackjack.setBounds(sectionWidth + (sectionWidth - buttonWidth) / 2, buttonY, buttonWidth, buttonHeight);
            commingsoon.setBounds(2 * sectionWidth + (sectionWidth - buttonWidth) / 2, buttonY, buttonWidth, buttonHeight);

            layeredPane = new JLayeredPane();
            layeredPane.setPreferredSize(new Dimension(newWidth, newHeight));
            layeredPane.add(mainLabel, 0);
            layeredPane.add(poker, 1);
            layeredPane.add(blackjack, 2);
            layeredPane.add(commingsoon, 3);
            layeredPane.setOpaque(false);
            setContentPane(layeredPane);
        }else{
            System.err.println("Main screen background not found");
        }
        revalidate();
        repaint();
    }
private void cardStyle() {
    try {
        // Poker card
        URL pokerUrl = getClass().getResource("image/poker.png");
        System.out.println("Attempting to load poker: " + pokerUrl);
        
        if (pokerUrl != null) {
            ImageIcon pokerIcon = new ImageIcon(pokerUrl);
            poker.setIcon(pokerIcon);
            poker.setContentAreaFilled(false);
            poker.setBorderPainted(false);
            poker.setFocusPainted(false);
            poker.addActionListener(this);
            poker.setToolTipText("Let's play Poker");
        } else {
            System.err.println("Poker image not found: image/poker.png");
            poker.setText("Poker");  // Fallback text if image not found
        }
        
        // Blackjack card
        URL blackjackUrl = getClass().getResource("image/blackjack.png");
        System.out.println("Attempting to load blackjack: " + blackjackUrl);
        
        if (blackjackUrl != null) {
            ImageIcon blackjackIcon = new ImageIcon(blackjackUrl);
            blackjack.setIcon(blackjackIcon);
            blackjack.setContentAreaFilled(false);
            blackjack.setBorderPainted(false);
            blackjack.setFocusPainted(false);
            blackjack.addActionListener(this);
            blackjack.setToolTipText("Let's play Blackjack");
        } else {
            System.err.println("Blackjack image not found: image/blackjack.png");
            blackjack.setText("Blackjack");  // Fallback text if image not found
        }
        
        // Coming soon card - FIXED: removed leading slash
        URL comingsoonUrl = getClass().getResource("image/commingsoon.png");
        System.out.println("Attempting to load coming soon: " + comingsoonUrl);
        
        if (comingsoonUrl != null) {
            ImageIcon comingsoonIcon = new ImageIcon(comingsoonUrl);
            commingsoon.setIcon(comingsoonIcon);
            commingsoon.setContentAreaFilled(false);
            commingsoon.setBorderPainted(false);
            commingsoon.setFocusPainted(false);
            commingsoon.addActionListener(this);
            commingsoon.setToolTipText("Coming Soon Please Wait");
        } else {
            System.err.println("Coming soon image not found: image/commingsoon.png");
            commingsoon.setText("Coming Soon");  // Fallback text if image not found
        }
    } catch (Exception e) {
        System.err.println("Error in cardStyle: " + e.getMessage());
        e.printStackTrace();
    }
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