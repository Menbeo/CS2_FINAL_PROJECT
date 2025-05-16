import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;

public class CardSagaSaga extends JFrame implements ActionListener {
    // Frontend UI components
    private JLabel backgroundLabel;
    private JLayeredPane layeredPane;
    private JLabel mainLabel;
    private JButton startButton;
    private JButton arrow;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    // Blackjack UI components
    private JPanel blackjackPanel;
    private JPanel playerCardsPanel;
    private JPanel dealerCardsPanel;
    private JLabel playerScoreLabel;
    private JLabel dealerScoreLabel;
    private JButton hitButton;
    private JButton standButton;
    private JButton blackjackBackButton;

    // Poker UI components
    private JPanel pokerPanel;
    private JPanel playerPokerCardsPanel;
    private JPanel computerPokerCardsPanel;
    private JLabel pokerResultLabel;
    private JButton drawButton;
    private JButton evaluateButton;
    private JButton assessHandsButton;
    private JButton pokerBackButton;

    // Backend variables
    private List<Card> blackjackDeck;
    private List<Card> playerHand;
    private List<Card> dealerHand;
    private List<Card> pokerDeck;
    private List<Card> playerPokerHand;
    private List<Card> computerPokerHand;

    // Card class (custom ADT)
    static class Card {
        private final String suit;
        private final int rank;

        public Card(String suit, int rank) {
            this.suit = suit;
            this.rank = rank;
        }

        public String getSuit() {
            return suit;
        }

        public int getRank() {
            return rank;
        }

        @Override
        public String toString() {
            String rankStr;
            switch (rank) {
                case 1: rankStr = "A"; break;
                case 11: rankStr = "J"; break;
                case 12: rankStr = "Q"; break;
                case 13: rankStr = "K"; break;
                default: rankStr = String.valueOf(rank);
            }
            return suit + rankStr;
        }
    }

    public CardSagaSaga() {
        setTitle("Card Saga");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        setContentPane(mainPanel);
        startScreen();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Frontend: Start screen
    private void startScreen() {
        JPanel startPanel = new JPanel(new BorderLayout());
        try {
            URL bgImUrl = getClass().getResource("image/start_backg.jpg");
            if (bgImUrl != null) {
                ImageIcon backgroundIcon = new ImageIcon(bgImUrl);
                setSize(1505, 845);
                backgroundLabel = new JLabel(backgroundIcon);
                backgroundLabel.setLayout(new BorderLayout());
                startButton = new JButton();
                styleStart();
                JPanel buttonPanel = new JPanel();
                buttonPanel.setOpaque(false);
                buttonPanel.setLayout(new GridBagLayout());
                buttonPanel.add(startButton);
                backgroundLabel.add(buttonPanel, BorderLayout.SOUTH);
                startPanel.add(backgroundLabel, BorderLayout.CENTER);
            } else {
                System.err.println("Background image not found: start_backg.jpg");
            }
        } catch (Exception e) {
            System.err.println("Error loading background: " + e.getMessage());
        }
        mainPanel.add(startPanel, "start");
        cardLayout.show(mainPanel, "start");
        revalidate();
        repaint();
    }

    // Frontend: Style start button
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

    // Frontend: Guide screen
    private void guideScreen() {
        JPanel guidePanel = new JPanel();
        URL guidScreenURL = getClass().getResource("image/guid_screen.png");
        if (guidScreenURL != null) {
            ImageIcon originalIcon = new ImageIcon(guidScreenURL);
            int newWidth = 1550;
            int newHeight = 900;
            Image scaledImage = originalIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(scaledImage);
            JLabel guideLabel = new JLabel(resizedIcon);
            guideLabel.setBounds(0, 0, newWidth, newHeight);
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
            guidePanel.add(layeredPane);
            setSize(newWidth, newHeight);
        } else {
            System.err.println("Guide image not found: guid_screen.png");
        }
        mainPanel.add(guidePanel, "guide");
        cardLayout.show(mainPanel, "guide");
        revalidate();
        repaint();
    }

    // Frontend: Style arrow button
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

    // Frontend: Main game selection screen (Using card images as clickable labels)
    private void mainGameScreen() {
        JPanel mainGamePanel = new JPanel(new BorderLayout());
        URL mainScreenUrl = getClass().getResource("image/Hehe.jpg");
        if (mainScreenUrl != null) {
            ImageIcon originalIcon = new ImageIcon(mainScreenUrl);
            int width = 1550;
            int height = 900;
            Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(scaledImage);
            mainLabel = new JLabel(resizedIcon);
            mainLabel.setLayout(null); // Use absolute positioning

            // Create a layered pane to hold the cards
            layeredPane = new JLayeredPane();
            layeredPane.setPreferredSize(new Dimension(width, height));
            layeredPane.setBounds(0, 0, width, height);

            // Load card images (scaled to 300x400 for better fit)
            ImageIcon pokerCardIcon = loadCardImage("image/poker.png", 300, 400);
            ImageIcon blackjackCardIcon = loadCardImage("image/blackjack.png", 300, 400);
            ImageIcon comingSoonCardIcon = loadCardImage("image/commingsoon.png", 300, 400);

            // Card dimensions
            int cardWidth = 300; // Scaled size
            int cardHeight = 400;
            int gap = 50; // Space between cards
            int startX = (width - (3 * cardWidth + 2 * gap)) / 2; // Center horizontally
            int startY = (height - cardHeight) / 2; // Center vertically

            // Poker card label
            JLabel pokerCardLabel = new JLabel();
            styleCardLabel(pokerCardLabel, pokerCardIcon, startX, startY, cardWidth, cardHeight, true, () -> createPokerPanel());
            layeredPane.add(pokerCardLabel, Integer.valueOf(1));

            // Blackjack card label
            JLabel blackjackCardLabel = new JLabel();
            styleCardLabel(blackjackCardLabel, blackjackCardIcon, startX + cardWidth + gap, startY, cardWidth, cardHeight, true, () -> createBlackjackPanel());
            layeredPane.add(blackjackCardLabel, Integer.valueOf(1));

            // Uno (Coming Soon) card label
            JLabel unoCardLabel = new JLabel();
            styleCardLabel(unoCardLabel, comingSoonCardIcon, startX + 2 * (cardWidth + gap), startY, cardWidth, cardHeight, false, null);
            layeredPane.add(unoCardLabel, Integer.valueOf(1));

            // Add the title "CHOOSE YOUR GAME"
            JLabel titleLabel = new JLabel("CHOOSE YOUR GAME", SwingConstants.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setBounds(0, 50, width, 50);
            layeredPane.add(titleLabel, Integer.valueOf(2));

            mainLabel.add(layeredPane);
            mainGamePanel.add(mainLabel, BorderLayout.CENTER);
            setSize(width, height);
        } else {
            System.err.println("Main screen background not found: Hehe.jpg");
        }
        mainPanel.add(mainGamePanel, "mainGame");
        cardLayout.show(mainPanel, "mainGame");
        revalidate();
        repaint();
    }

    // Helper method to load card images with scaling
    private ImageIcon loadCardImage(String path, int width, int height) {
        URL cardUrl = getClass().getResource(path);
        if (cardUrl != null) {
            ImageIcon cardIcon = new ImageIcon(cardUrl);
            Image scaledImage = cardIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } else {
            System.err.println("Card image not found: " + path);
            return new ImageIcon(); // Return an empty icon if image not found
        }
    }

    // Helper method to style card labels
    private void styleCardLabel(JLabel label, ImageIcon cardIcon, int x, int y, int width, int height, boolean enabled, Runnable action) {
        label.setIcon(cardIcon);
        label.setBounds(x, y, width, height);
        label.setLayout(null);

        if (enabled) {
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (action != null) {
                        action.run();
                    }
                }
            });
        } else {
            label.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }

    // Helper: Create styled button (used for other screens)
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 102, 204));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        button.setPreferredSize(new Dimension(200, 60));
        button.addActionListener(this);
        return button;
    }

    // Backend: Initialize deck
    private List<Card> initDeck() {
        List<Card> deck = new ArrayList<>();
        String[] suits = {"♠", "♥", "♦", "♣"};
        for (String suit : suits) {
            for (int rank = 1; rank <= 13; rank++) {
                deck.add(new Card(suit, rank));
            }
        }
        return deck;
    }

    // Blackjack: Create UI panel
    private void createBlackjackPanel() {
        blackjackPanel = new JPanel(new BorderLayout());
        URL bgUrl = getClass().getResource("image/Hehe.jpg");
        if (bgUrl != null) {
            ImageIcon bgIcon = new ImageIcon(bgUrl);
            JLabel bgLabel = new JLabel(bgIcon);
            bgLabel.setLayout(new BorderLayout());
            JPanel gamePanel = new JPanel(new GridBagLayout());
            gamePanel.setOpaque(false);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            // Dealer cards panel with overlap
            dealerCardsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, -20, 10)); // Negative hgap for overlap
            dealerCardsPanel.setOpaque(false);
            dealerCardsPanel.setPreferredSize(new Dimension(600, 160)); // Fit multiple cards
            gamePanel.add(dealerCardsPanel, gbc);
            gbc.gridy = 1;
            dealerScoreLabel = new JLabel("Dealer Score: 0", SwingConstants.CENTER);
            dealerScoreLabel.setForeground(Color.WHITE);
            dealerScoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
            gamePanel.add(dealerScoreLabel, gbc);
            gbc.gridy = 2;
            // Player cards panel with overlap
            playerCardsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, -20, 10));
            playerCardsPanel.setOpaque(false);
            playerCardsPanel.setPreferredSize(new Dimension(600, 160));
            gamePanel.add(playerCardsPanel, gbc);
            gbc.gridy = 3;
            playerScoreLabel = new JLabel("Player Score: 0", SwingConstants.CENTER);
            playerScoreLabel.setForeground(Color.WHITE);
            playerScoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
            gamePanel.add(playerScoreLabel, gbc);
            gbc.gridy = 4;
            gbc.gridwidth = 1;
            hitButton = createStyledButton("Hit");
            gamePanel.add(hitButton, gbc);
            gbc.gridx = 1;
            standButton = createStyledButton("Stand");
            gamePanel.add(standButton, gbc);
            gbc.gridx = 0;
            gbc.gridy = 5;
            gbc.gridwidth = 2;
            blackjackBackButton = createStyledButton("Back to Menu");
            gamePanel.add(blackjackBackButton, gbc);
            bgLabel.add(gamePanel, BorderLayout.CENTER);
            blackjackPanel.add(bgLabel, BorderLayout.CENTER);
        }
        mainPanel.add(blackjackPanel, "blackjack");
        initBlackjackGame();
        cardLayout.show(mainPanel, "blackjack");
        revalidate();
        repaint();
    }

    // Blackjack: Initialize game
    private void initBlackjackGame() {
        blackjackDeck = initDeck();
        Collections.shuffle(blackjackDeck);
        playerHand = new ArrayList<>();
        dealerHand = new ArrayList<>();
        playerHand.add(blackjackDeck.remove(0));
        dealerHand.add(blackjackDeck.remove(0));
        playerHand.add(blackjackDeck.remove(0));
        dealerHand.add(blackjackDeck.remove(0));
        hitButton.setEnabled(true);
        standButton.setEnabled(true);
        updateBlackjackUI();
    }

    // Blackjack: Update UI
    private void updateBlackjackUI() {
        playerCardsPanel.removeAll();
        dealerCardsPanel.removeAll();
        for (Card card : playerHand) {
            playerCardsPanel.add(createCardLabel(card, true));
        }
        for (int i = 0; i < dealerHand.size(); i++) {
            dealerCardsPanel.add(createCardLabel(dealerHand.get(i), i == 0));
        }
        int playerScore = calculateBlackjackScore(playerHand);
        playerScoreLabel.setText("Player Score: " + playerScore);
        int dealerScore = calculateBlackjackScore(dealerHand.subList(1, dealerHand.size())); // Hide first card
        dealerScoreLabel.setText("Dealer Score: " + dealerScore);
        playerCardsPanel.revalidate();
        playerCardsPanel.repaint();
        dealerCardsPanel.revalidate();
        dealerCardsPanel.repaint();
    }

    // Blackjack: Player hits
    private void blackjackHit() {
        playerHand.add(blackjackDeck.remove(0));
        updateBlackjackUI();
        int playerScore = calculateBlackjackScore(playerHand);
        if (playerScore > 21) {
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
            JOptionPane.showMessageDialog(this, "Your hand is over 21. Dealer wins!", "Result", JOptionPane.INFORMATION_MESSAGE);
        } else if (playerScore == 21) {
            blackjackStand();
        }
    }

    // Blackjack: Player stands
    private void blackjackStand() {
        hitButton.setEnabled(false);
        standButton.setEnabled(false);
        int dealerScore = calculateBlackjackScore(dealerHand);
        while (dealerScore < 17) {
            dealerHand.add(blackjackDeck.remove(0));
            dealerScore = calculateBlackjackScore(dealerHand);
        }
        dealerCardsPanel.removeAll();
        for (Card card : dealerHand) {
            dealerCardsPanel.add(createCardLabel(card, true));
        }
        dealerScoreLabel.setText("Dealer Score: " + dealerScore);
        dealerCardsPanel.revalidate();
        dealerCardsPanel.repaint();
        int playerScore = calculateBlackjackScore(playerHand);
        String message;
        if (dealerScore > 21) {
            message = "Dealer's hand is over 21. You win!";
        } else if (dealerScore > playerScore) {
            message = "Dealer's hand is " + dealerScore + ". Dealer wins!";
        } else if (playerScore > dealerScore) {
            message = "Your hand is " + playerScore + ". You win!";
        } else {
            message = "Draw by " + playerScore + "!";
        }
        JOptionPane.showMessageDialog(this, message, "Result", JOptionPane.INFORMATION_MESSAGE);
    }

    // Blackjack: Calculate score
    private int calculateBlackjackScore(List<Card> hand) {
        int score = 0;
        int aceCount = 0;
        for (Card card : hand) {
            if (card.getRank() == 1) {
                aceCount++;
                score += 11;
            } else if (card.getRank() >= 10) {
                score += 10;
            } else {
                score += card.getRank();
            }
        }
        while (score > 21 && aceCount > 0) {
            score -= 10;
            aceCount--;
        }
        return score;
    }

    // Poker: Create UI panel
    private void createPokerPanel() {
        pokerPanel = new JPanel(new BorderLayout());
        URL bgUrl = getClass().getResource("image/Hehe.jpg");
        if (bgUrl != null) {
            ImageIcon bgIcon = new ImageIcon(bgUrl);
            JLabel bgLabel = new JLabel(bgIcon);
            bgLabel.setLayout(new BorderLayout());
            JPanel gamePanel = new JPanel(new GridBagLayout());
            gamePanel.setOpaque(false);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            // Computer cards panel with overlap
            computerPokerCardsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, -20, 10));
            computerPokerCardsPanel.setOpaque(false);
            computerPokerCardsPanel.setPreferredSize(new Dimension(600, 160));
            gamePanel.add(computerPokerCardsPanel, gbc);
            gbc.gridy = 1;
            // Player cards panel with overlap
            playerPokerCardsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, -20, 10));
            playerPokerCardsPanel.setOpaque(false);
            playerPokerCardsPanel.setPreferredSize(new Dimension(600, 160));
            gamePanel.add(playerPokerCardsPanel, gbc);
            gbc.gridy = 2;
            pokerResultLabel = new JLabel("Draw cards and evaluate", SwingConstants.CENTER);
            pokerResultLabel.setForeground(Color.WHITE);
            pokerResultLabel.setFont(new Font("Arial", Font.BOLD, 20));
            gamePanel.add(pokerResultLabel, gbc);
            gbc.gridy = 3;
            gbc.gridwidth = 1;
            drawButton = createStyledButton("Draw");
            gamePanel.add(drawButton, gbc);
            gbc.gridx = 1;
            evaluateButton = createStyledButton("Evaluate");
            evaluateButton.setEnabled(false);
            gamePanel.add(evaluateButton, gbc);
            gbc.gridx = 0;
            gbc.gridy = 4;
            assessHandsButton = createStyledButton("Assess Hands");
            assessHandsButton.setEnabled(false);
            gamePanel.add(assessHandsButton, gbc);
            gbc.gridx = 1;
            pokerBackButton = createStyledButton("Back to Menu");
            gamePanel.add(pokerBackButton, gbc);
            bgLabel.add(gamePanel, BorderLayout.CENTER);
            pokerPanel.add(bgLabel, BorderLayout.CENTER);
        }
        mainPanel.add(pokerPanel, "poker");
        initPokerGame();
        cardLayout.show(mainPanel, "poker");
        revalidate();
        repaint();
    }

    // Poker: Initialize game
    private void initPokerGame() {
        pokerDeck = initDeck();
        Collections.shuffle(pokerDeck);
        playerPokerHand = new ArrayList<>();
        computerPokerHand = new ArrayList<>();
        playerPokerCardsPanel.removeAll();
        computerPokerCardsPanel.removeAll();
        pokerResultLabel.setText("Draw cards and evaluate");
        drawButton.setEnabled(true);
        evaluateButton.setEnabled(false);
        assessHandsButton.setEnabled(false);
        playerPokerCardsPanel.revalidate();
        playerPokerCardsPanel.repaint();
        computerPokerCardsPanel.revalidate();
        computerPokerCardsPanel.repaint();
    }

    // Poker: Deal cards
    private void pokerDraw() {
        playerPokerHand.clear();
        computerPokerHand.clear();
        for (int i = 0; i < 5; i++) {
            playerPokerHand.add(pokerDeck.remove(0));
            computerPokerHand.add(pokerDeck.remove(0));
        }
        updatePokerUI(false);
        drawButton.setEnabled(false);
        evaluateButton.setEnabled(true);
        assessHandsButton.setEnabled(true);
    }

    // Poker: Update UI
    private void updatePokerUI(boolean revealComputer) {
        playerPokerCardsPanel.removeAll();
        computerPokerCardsPanel.removeAll();
        for (Card card : playerPokerHand) {
            playerPokerCardsPanel.add(createCardLabel(card, true));
        }
        for (Card card : computerPokerHand) {
            playerPokerCardsPanel.add(createCardLabel(card, revealComputer));
        }
        playerPokerCardsPanel.revalidate();
        playerPokerCardsPanel.repaint();
        computerPokerCardsPanel.revalidate();
        computerPokerCardsPanel.repaint();
    }

    // Poker: Evaluate hands
    private void pokerEvaluate() {
        updatePokerUI(true);
        String playerHandType = evaluatePokerHand(playerPokerHand);
        String computerHandType = evaluatePokerHand(computerPokerHand);
        int playerRank = getPokerHandRank(playerHandType);
        int computerRank = getPokerHandRank(computerHandType);
        if (playerRank > computerRank) {
            pokerResultLabel.setText("You win with " + playerHandType + " vs " + computerHandType);
        } else if (computerRank > playerRank) {
            pokerResultLabel.setText("Computer wins with " + computerHandType + " vs " + playerHandType);
        } else {
            int result = compareHighCards(playerPokerHand, computerPokerHand, playerHandType);
            if (result > 0) {
                pokerResultLabel.setText("You win with higher " + playerHandType);
            } else if (result < 0) {
                pokerResultLabel.setText("Computer wins with higher " + computerHandType);
            } else {
                pokerResultLabel.setText("Draw! Both have equal " + playerHandType);
            }
        }
        evaluateButton.setEnabled(false);
    }

    // Poker: Assess hands
    private void assessPokerHands() {
        updatePokerUI(true);
        String playerHandType = evaluatePokerHand(playerPokerHand);
        String computerHandType = evaluatePokerHand(computerPokerHand);
        StringBuilder assessment = new StringBuilder();
        assessment.append("Hand Assessment:\n\n");
        assessment.append("Your Hand: ").append(playerHandType).append("\n");
        assessment.append("Cards: ");
        for (Card card : playerPokerHand) {
            assessment.append(card.toString()).append(" ");
        }
        assessment.append("\n");
        assessment.append(getHandDetails(playerPokerHand, playerHandType));
        assessment.append("\n\n");
        assessment.append("Computer's Hand: ").append(computerHandType).append("\n");
        assessment.append("Cards: ");
        for (Card card : computerPokerHand) {
            assessment.append(card.toString()).append(" ");
        }
        assessment.append("\n");
        assessment.append(getHandDetails(computerPokerHand, computerHandType));
        assessment.append("\n\n");
        int playerRank = getPokerHandRank(playerHandType);
        int computerRank = getPokerHandRank(computerHandType);
        if (playerRank > computerRank) {
            assessment.append("Your hand is stronger than the computer's hand.");
        } else if (computerRank > playerRank) {
            assessment.append("The computer's hand is stronger than your hand.");
        } else {
            int result = compareHighCards(playerPokerHand, computerPokerHand, playerHandType);
            if (result > 0) {
                assessment.append("Your hand is stronger due to higher cards.");
            } else if (result < 0) {
                assessment.append("The computer's hand is stronger due to higher cards.");
            } else {
                assessment.append("Both hands are of equal strength.");
            }
        }
        JTextArea textArea = new JTextArea(assessment.toString());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        JOptionPane.showMessageDialog(this, scrollPane, "Poker Hand Assessment", JOptionPane.INFORMATION_MESSAGE);
    }

    // Poker: Hand details
    private String getHandDetails(List<Card> hand, String handType) {
        StringBuilder details = new StringBuilder();
        List<Card> sortedHand = new ArrayList<>(hand);
        Collections.sort(sortedHand, (c1, c2) -> {
            int rank1 = c1.getRank() == 1 ? 14 : c1.getRank();
            int rank2 = c2.getRank() == 1 ? 14 : c2.getRank();
            return rank2 - rank1;
        });
        Map<Integer, Integer> rankFreq = getRankFrequencies(hand);
        switch (handType) {
            case "Royal Flush":
                details.append("The highest possible hand: Ten, Jack, Queen, King, and Ace all of the same suit.");
                break;
            case "Straight Flush":
                details.append("Five cards in sequence, all of the same suit.");
                break;
            case "Four of A Kind":
                for (Map.Entry<Integer, Integer> entry : rankFreq.entrySet()) {
                    if (entry.getValue() == 4) {
                        String rankName = getRankName(entry.getKey());
                        details.append("Four ").append(rankName).append("s");
                        break;
                    }
                }
                break;
            case "Full House":
                int threeOfKindRank = 0;
                int pairRank = 0;
                for (Map.Entry<Integer, Integer> entry : rankFreq.entrySet()) {
                    if (entry.getValue() == 3) {
                        threeOfKindRank = entry.getKey();
                    } else if (entry.getValue() == 2) {
                        pairRank = entry.getKey();
                    }
                }
                details.append("Three ").append(getRankName(threeOfKindRank)).append("s and two ")
                        .append(getRankName(pairRank)).append("s");
                break;
            case "Flush":
                details.append("Five cards of the same suit, not in sequence. High card: ")
                        .append(getRankName(convertAceRank(sortedHand.get(0).getRank())));
                break;
            case "Straight":
                int highestRank = convertAceRank(sortedHand.get(0).getRank());
                details.append("Five cards in sequence, not of the same suit. High card: ")
                        .append(getRankName(highestRank));
                break;
            case "Three of A Kind":
                for (Map.Entry<Integer, Integer> entry : rankFreq.entrySet()) {
                    if (entry.getValue() == 3) {
                        String rankName = getRankName(entry.getKey());
                        details.append("Three ").append(rankName).append("s");
                        break;
                    }
                }
                break;
            case "Two Pairs":
                List<Integer> pairRanks = new ArrayList<>();
                for (Map.Entry<Integer, Integer> entry : rankFreq.entrySet()) {
                    if (entry.getValue() == 2) {
                        pairRanks.add(entry.getKey());
                    }
                }
                Collections.sort(pairRanks, Collections.reverseOrder());
                details.append("A pair of ").append(getRankName(pairRanks.get(0)))
                        .append("s and a pair of ").append(getRankName(pairRanks.get(1))).append("s");
                break;
            case "Pair":
                for (Map.Entry<Integer, Integer> entry : rankFreq.entrySet()) {
                    if (entry.getValue() == 2) {
                        String rankName = getRankName(entry.getKey());
                        details.append("A pair of ").append(rankName).append("s");
                        break;
                    }
                }
                break;
            case "High Card":
                int highCard = convertAceRank(sortedHand.get(0).getRank());
                details.append("No matching cards. High card: ").append(getRankName(highCard));
                break;
        }
        return details.toString();
    }

    // Poker: Compare high cards
    private int compareHighCards(List<Card> playerHand, List<Card> computerHand, String handType) {
        Collections.sort(playerHand, (c1, c2) -> c2.getRank() - c1.getRank());
        Collections.sort(computerHand, (c1, c2) -> c2.getRank() - c1.getRank());
        if (handType.equals("Pair") || handType.equals("Two Pairs") ||
                handType.equals("Three of A Kind") || handType.equals("Full House") ||
                handType.equals("Four of A Kind")) {
            Map<Integer, Integer> playerRankFreq = getRankFrequencies(playerHand);
            Map<Integer, Integer> computerRankFreq = getRankFrequencies(computerHand);
            if (handType.equals("Pair")) {
                int playerPairRank = getPairRank(playerRankFreq);
                int computerPairRank = getPairRank(computerRankFreq);
                if (playerPairRank != computerPairRank) {
                    return playerPairRank - computerPairRank;
                }
            }
        }
        for (int i = 0; i < playerHand.size(); i++) {
            int playerRank = convertAceRank(playerHand.get(i).getRank());
            int computerRank = convertAceRank(computerHand.get(i).getRank());
            if (playerRank != computerRank) {
                return playerRank - computerRank;
            }
        }
        return 0;
    }

    // Poker: Convert Ace rank
    private int convertAceRank(int rank) {
        return rank == 1 ? 14 : rank;
    }

    // Poker: Get rank frequencies
    private Map<Integer, Integer> getRankFrequencies(List<Card> hand) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (Card card : hand) {
            int rank = convertAceRank(card.getRank());
            frequencyMap.put(rank, frequencyMap.getOrDefault(rank, 0) + 1);
        }
        return frequencyMap;
    }

    // Poker: Get pair rank
    private int getPairRank(Map<Integer, Integer> rankFreq) {
        for (Map.Entry<Integer, Integer> entry : rankFreq.entrySet()) {
            if (entry.getValue() == 2) {
                return entry.getKey();
            }
        }
        return 0;
    }

    // Poker: Evaluate hand
    private String evaluatePokerHand(List<Card> hand) {
        Collections.sort(hand, (c1, c2) -> {
            int rank1 = c1.getRank() == 1 ? 14 : c1.getRank();
            int rank2 = c2.getRank() == 1 ? 14 : c2.getRank();
            return rank1 - rank2;
        });
        boolean isFlush = isFlush(hand);
        boolean isStraight = isStraight(hand);
        if (isFlush && isStraight) {
            if (hand.get(0).getRank() == 10 && hand.get(1).getRank() == 11 &&
                    hand.get(2).getRank() == 12 && hand.get(3).getRank() == 13 &&
                    hand.get(4).getRank() == 1) {
                return "Royal Flush";
            }
            return "Straight Flush";
        }
        Map<Integer, Integer> rankCounts = new HashMap<>();
        for (Card card : hand) {
            int rank = card.getRank() == 1 ? 14 : card.getRank();
            rankCounts.put(rank, rankCounts.getOrDefault(rank, 0) + 1);
        }
        if (hasFourOfAKind(rankCounts)) {
            return "Four of A Kind";
        }
        if (hasFullHouse(rankCounts)) {
            return "Full House";
        }
        if (isFlush) {
            return "Flush";
        }
        if (isStraight) {
            return "Straight";
        }
        if (hasThreeOfAKind(rankCounts)) {
            return "Three of A Kind";
        }
        if (hasTwoPairs(rankCounts)) {
            return "Two Pairs";
        }
        if (hasPair(rankCounts)) {
            return "Pair";
        }
        return "High Card";
    }

    // Poker: Check Flush
    private boolean isFlush(List<Card> hand) {
        String suit = hand.get(0).getSuit();
        for (Card card : hand) {
            if (!card.getSuit().equals(suit)) {
                return false;
            }
        }
        return true;
    }

    // Poker: Check Straight
    private boolean isStraight(List<Card> hand) {
        List<Card> sortedHand = new ArrayList<>(hand);
        Collections.sort(sortedHand, (c1, c2) -> {
            int rank1 = c1.getRank() == 1 ? 14 : c1.getRank();
            int rank2 = c2.getRank() == 1 ? 14 : c2.getRank();
            return rank1 - rank2;
        });
        if (sortedHand.get(0).getRank() == 2 &&
                sortedHand.get(1).getRank() == 3 &&
                sortedHand.get(2).getRank() == 4 &&
                sortedHand.get(3).getRank() == 5 &&
                sortedHand.get(4).getRank() == 1) {
            return true;
        }
        for (int i = 0; i < sortedHand.size() - 1; i++) {
            int currentRank = sortedHand.get(i).getRank() == 1 ? 14 : sortedHand.get(i).getRank();
            int nextRank = sortedHand.get(i+1).getRank() == 1 ? 14 : sortedHand.get(i+1).getRank();
            if (nextRank - currentRank != 1) {
                return false;
            }
        }
        return true;
    }

    // Poker: Check Four of a Kind
    private boolean hasFourOfAKind(Map<Integer, Integer> rankCounts) {
        return rankCounts.containsValue(4);
    }

    // Poker: Check Full House
    private boolean hasFullHouse(Map<Integer, Integer> rankCounts) {
        return rankCounts.containsValue(3) && rankCounts.containsValue(2);
    }

    // Poker: Check Three of a Kind
    private boolean hasThreeOfAKind(Map<Integer, Integer> rankCounts) {
        return rankCounts.containsValue(3);
    }

    // Poker: Check Two Pairs
    private boolean hasTwoPairs(Map<Integer, Integer> rankCounts) {
        int pairCount = 0;
        for (int count : rankCounts.values()) {
            if (count == 2) {
                pairCount++;
            }
        }
        return pairCount == 2;
    }

    // Poker: Check Pair
    private boolean hasPair(Map<Integer, Integer> rankCounts) {
        return rankCounts.containsValue(2);
    }

    // Poker: Get hand rank
    private int getPokerHandRank(String handType) {
        switch (handType) {
            case "Royal Flush": return 10;
            case "Straight Flush": return 9;
            case "Four of A Kind": return 8;
            case "Full House": return 7;
            case "Flush": return 6;
            case "Straight": return 5;
            case "Three of A Kind": return 4;
            case "Two Pairs": return 3;
            case "Pair": return 2;
            default: return 1; // High Card
        }
    }

    // Poker: Get rank name
    private String getRankName(int rank) {
        if (rank == 14) return "Ace";
        if (rank == 13) return "King";
        if (rank == 12) return "Queen";
        if (rank == 11) return "Jack";
        return String.valueOf(rank);
    }

    // Helper: Create card label with scaling
    private JLabel createCardLabel(Card card, boolean visible) {
        final int CARD_WIDTH = 100; // Standard card size
        final int CARD_HEIGHT = 140;
        if (visible) {
            String cardName = card.toString().replace("♠", "S").replace("♥", "H").replace("♦", "D").replace("♣", "C");
            URL cardUrl = getClass().getResource("image/cards/" + cardName + ".png");
            if (cardUrl != null) {
                ImageIcon cardIcon = new ImageIcon(cardUrl);
                Image scaledImage = cardIcon.getImage().getScaledInstance(CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH);
                return new JLabel(new ImageIcon(scaledImage));
            } else {
                System.err.println("Card image not found: " + cardName + ".png");
                JLabel fallback = new JLabel(card.toString(), SwingConstants.CENTER);
                fallback.setFont(new Font("Arial", Font.BOLD, 12));
                fallback.setForeground(Color.RED);
                fallback.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                fallback.setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
                return fallback;
            }
        } else {
            URL backUrl = getClass().getResource("image/cards/back.png");
            if (backUrl != null) {
                ImageIcon backIcon = new ImageIcon(backUrl);
                Image scaledImage = backIcon.getImage().getScaledInstance(CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH);
                return new JLabel(new ImageIcon(scaledImage));
            } else {
                System.err.println("Card back image not found: back.png");
                JLabel fallback = new JLabel("Hidden", SwingConstants.CENTER);
                fallback.setFont(new Font("Arial", Font.BOLD, 12));
                fallback.setForeground(Color.BLUE);
                fallback.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                fallback.setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
                return fallback;
            }
        }
    }

    // Action handler
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            guideScreen();
        } else if (e.getSource() == arrow) {
            mainGameScreen();
        } else if (e.getSource() == hitButton) {
            blackjackHit();
        } else if (e.getSource() == standButton) {
            blackjackStand();
        } else if (e.getSource() == drawButton) {
            pokerDraw();
        } else if (e.getSource() == evaluateButton) {
            pokerEvaluate();
        } else if (e.getSource() == assessHandsButton) {
            assessPokerHands();
        } else if (e.getSource() == blackjackBackButton || e.getSource() == pokerBackButton) {
            mainGameScreen();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CardSagaSaga());
    }
}