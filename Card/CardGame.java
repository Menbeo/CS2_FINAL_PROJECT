import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class CardGame extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JPanel startPanel;
    private JPanel modeSelectionPanel;
    private JPanel blackjackPanel;
    private JPanel pokerPanel;
    private JPanel unoPanel;

    // Blackjack variances
    private List<Card> blackjackDeck;
    private List<Card> playerHand;
    private List<Card> dealerHand;
    private JPanel playerCardsPanel;
    private JPanel dealerCardsPanel;
    private JLabel playerScoreLabel;
    private JLabel dealerScoreLabel;
    private JButton hitButton;
    private JButton standButton;
    private JButton blackjackNewGameButton;

    // Poker variances
    private List<Card> pokerDeck;
    private List<Card> playerPokerHand;
    private List<Card> computerPokerHand;
    private JPanel playerPokerCardsPanel;
    private JPanel computerPokerCardsPanel;
    private JButton drawButton;
    private JButton evaluateButton;
    private JButton pokerNewGameButton;
    private JLabel pokerResultLabel;
    //Card background 
    private JFrame startFrame;
    private JLabel backgroundLabel;
    private JButton startButton;
    private JButton arrow;

    public CardGame() {
        startScreen();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        createStartPanel();
        createModeSelectionPanel();
        createBlackjackPanel();
        createPokerPanel();
        createUnoPanel();

        mainPanel.add(startPanel, "start");
        mainPanel.add(modeSelectionPanel, "modeSelection");
        mainPanel.add(blackjackPanel, "blackjack");
        mainPanel.add(pokerPanel, "poker");
        mainPanel.add(unoPanel, "uno");

        cardLayout.show(mainPanel, "start");
        add(mainPanel);
        setVisible(true);
    }

    private void createStartPanel() {
        startPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // Background UI
                GradientPaint gradient = new GradientPaint(
                        0, 0, PRIMARY_COLOR,
                        0, getHeight(), SECONDARY_COLOR);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Decorate cards
                drawDecorationCards(g2d);
            }

            private void drawDecorationCards(Graphics2D g2d) {
                int cardWidth = 100;
                int cardHeight = 150;

                // Top left
                drawCard(g2d, 30, 30, cardWidth, cardHeight, "♠", "A", -15);

                // Top right
                drawCard(g2d, getWidth() - cardWidth - 30, 30, cardWidth, cardHeight, "♥", "K", 15);

                // Bottom left
                drawCard(g2d, 30, getHeight() - cardHeight - 30, cardWidth, cardHeight, "♦", "Q", 15);

                // Bottom right
                drawCard(g2d, getWidth() - cardWidth - 30, getHeight() - cardHeight - 30, cardWidth, cardHeight, "♣", "J", -15);
            }

            private void drawCard(Graphics2D g2d, int x, int y, int width, int height, String suit, String rank, double angle) {
                Color suitColor = suit.equals("♥") || suit.equals("♦") ? ACCENT_COLOR : Color.BLACK;

                g2d.rotate(Math.toRadians(angle), x + width/2, y + height/2);

                // Draw card
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(x, y, width, height, 10, 10);
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(x, y, width, height, 10, 10);

                // Draw the suits
                g2d.setFont(new Font("Arial", Font.BOLD, 20));
                g2d.setColor(suitColor);
                g2d.drawString(rank + suit, x + 10, y + 30);

                g2d.setFont(new Font("Arial", Font.BOLD, 50));
                g2d.drawString(suit, x + width/2 - 15, y + height/2 + 15);

                // Draw the suits upside down
                g2d.setFont(new Font("Arial", Font.BOLD, 20));
                g2d.drawString(rank + suit, x + width - 40, y + height - 15);

                g2d.rotate(Math.toRadians(-angle), x + width/2, y + height/2);
            }
        };

        startPanel.setLayout(new BorderLayout(20, 20));

        JLabel titleLabel = new JLabel("Card Saga", JLabel.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));

        JButton startButton = createStyledButton("Start", e -> cardLayout.show(mainPanel, "modeSelection"));
        startButton.setFont(new Font("Segoe UI", Font.BOLD, 22));
        startButton.setPreferredSize(new Dimension(200, 60));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(startButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 100, 0));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);

        JLabel subtitleLabel = new JLabel("Let's try the classic card game!", JLabel.CENTER);
        subtitleLabel.setFont(REGULAR_FONT);
        subtitleLabel.setForeground(Color.WHITE);

        centerPanel.add(subtitleLabel, BorderLayout.NORTH);

        startPanel.add(titleLabel, BorderLayout.NORTH);
        startPanel.add(centerPanel, BorderLayout.CENTER);
        startPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text, ActionListener listener) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2d.setColor(PRIMARY_COLOR.darker());
                } else if (getModel().isRollover()) {
                    g2d.setColor(PRIMARY_COLOR.brighter());
                } else {
                    g2d.setColor(PRIMARY_COLOR);
                }

                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                g2d.setColor(Color.WHITE);
                g2d.setFont(BUTTON_FONT);

                FontMetrics metrics = g2d.getFontMetrics();
                int x = (getWidth() - metrics.stringWidth(getText())) / 2;
                int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();

                g2d.drawString(getText(), x, y);
            }
        };

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setForeground(Color.WHITE);
        button.setFont(BUTTON_FONT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(listener);

        return button;
    }

    private void createModeSelectionPanel() {
        modeSelectionPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                g2d.setColor(BACKGROUND_COLOR);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Vẽ đường kẻ trang trí
                g2d.setColor(PRIMARY_COLOR);
                g2d.setStroke(new BasicStroke(3));
                g2d.drawLine(50, 80, getWidth() - 50, 80);
            }
        };
        modeSelectionPanel.setLayout(new BorderLayout(20, 20));

        JLabel titleLabel = new JLabel("Select Mode", JLabel.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        JPanel modesPanel = new JPanel(new GridLayout(1, 3, 30, 0));
        modesPanel.setOpaque(false);
        modesPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        modesPanel.add(createGameModeButton("Blackjack", "blackjack", "Test your luck in the 21 score game!"));
        modesPanel.add(createGameModeButton("Poker", "poker", "Placeholder text tại ko biết giới thiệu gì"));
        modesPanel.add(createGameModeButton("Uno", "uno", "Draw 25 cards or give us an applause"));

        JButton backButton = createStyledButton("Back", e -> cardLayout.show(mainPanel, "start"));
        backButton.setPreferredSize(new Dimension(150, 40));

        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backPanel.setOpaque(false);
        backPanel.add(backButton);
        backPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        modeSelectionPanel.add(titleLabel, BorderLayout.NORTH);
        modeSelectionPanel.add(modesPanel, BorderLayout.CENTER);
        modeSelectionPanel.add(backPanel, BorderLayout.SOUTH);
    }

    private JPanel createGameModeButton(String title, String destination, String description) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Vẽ background và border
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.setColor(PRIMARY_COLOR);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

                // Vẽ icon đại diện cho game mode
                drawModeIcon(g2d, title, getWidth() / 2, getHeight() / 3);
            }

            private void drawModeIcon(Graphics2D g2d, String mode, int centerX, int centerY) {
                int iconSize = 80;

                if (mode.equals("Blackjack")) {
                    
                    drawCardForIcon(g2d, centerX - iconSize/4, centerY, iconSize, "A", "♠");
                    drawCardForIcon(g2d, centerX + iconSize/4, centerY, iconSize, "J", "♥");
                } else if (mode.equals("Poker")) {
                
                    for (int i = 0; i < 5; i++) {
                        double angle = -20 + i * 10;
                        int offsetX = (int)(i * iconSize/10);
                        drawCardForIcon(g2d, centerX - iconSize/4 + offsetX, centerY, iconSize/2,
                                String.valueOf(10 + i), i % 2 == 0 ? "♣" : "♦");
                    }
                } else if (mode.equals("Uno")) {
                    // Vẽ lá bài UNO
                    g2d.setColor(ACCENT_COLOR);
                    g2d.fillRoundRect(centerX - iconSize/2, centerY - iconSize/2, iconSize, iconSize, 10, 10);
                    g2d.setColor(Color.WHITE);
                    g2d.setFont(new Font("Arial", Font.BOLD, 30));
                    g2d.drawString("UNO", centerX - 35, centerY + 10);
                }
            }

            private void drawCardForIcon(Graphics2D g2d, int centerX, int centerY, int size, String rank, String suit) {
                int width = size/2;
                int height = size;
                int x = centerX - width/2;
                int y = centerY - height/2;

                Color suitColor = suit.equals("♥") || suit.equals("♦") ? ACCENT_COLOR : Color.BLACK;

                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(x, y, width, height, 10, 10);
                g2d.setColor(Color.BLACK);
                g2d.drawRoundRect(x, y, width, height, 10, 10);

                g2d.setFont(new Font("Arial", Font.BOLD, 16));
                g2d.setColor(suitColor);
                g2d.drawString(rank + suit, x + 5, y + 20);

                g2d.setFont(new Font("Arial", Font.BOLD, 30));
                g2d.drawString(suit, x + width/2 - 10, y + height/2 + 10);
            }
        };

        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(SUBTITLE_FONT);
        titleLabel.setForeground(PRIMARY_COLOR);

        JLabel descLabel = new JLabel("<html><center>" + description + "</center></html>", JLabel.CENTER);
        descLabel.setFont(REGULAR_FONT);
        descLabel.setForeground(TEXT_COLOR);

        JButton playButton = createStyledButton("Play Now", e -> {
            cardLayout.show(mainPanel, destination);
            if (destination.equals("blackjack")) {
                initBlackjackGame();
            } else if (destination.equals("poker")) {
                initPokerGame();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(playButton);

        JPanel infoPanel = new JPanel(new BorderLayout(5, 10));
        infoPanel.setOpaque(false);
        infoPanel.add(titleLabel, BorderLayout.NORTH);
        infoPanel.add(descLabel, BorderLayout.CENTER);

        panel.add(new JPanel() {
            { setOpaque(false); setPreferredSize(new Dimension(0, 120)); }
        }, BorderLayout.NORTH);
        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void createBlackjackPanel() {
        blackjackPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // Vẽ nền giống bàn chơi bài
                g2d.setColor(PRIMARY_COLOR);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Vẽ họa tiết trang trí
                g2d.setColor(new Color(53, 101, 77, 150));
                int patternSize = 25;
                for (int x = 0; x < getWidth(); x += patternSize) {
                    for (int y = 0; y < getHeight(); y += patternSize) {
                        g2d.drawOval(x, y, 5, 5);
                    }
                }
            }
        };
        blackjackPanel.setLayout(new BorderLayout(20, 20));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Blackjack", JLabel.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        headerPanel.add(titleLabel, BorderLayout.CENTER);

        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout(20, 40));
        gamePanel.setOpaque(false);
        gamePanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        // Panel nhà cái
        JPanel dealerSection = new JPanel();
        dealerSection.setLayout(new BorderLayout(10, 10));
        dealerSection.setOpaque(false);

        JLabel dealerLabel = new JLabel("Dealer", JLabel.CENTER);
        dealerLabel.setFont(SUBTITLE_FONT);
        dealerLabel.setForeground(Color.WHITE);

        dealerCardsPanel = new JPanel();
        dealerCardsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
        dealerCardsPanel.setOpaque(false);

        dealerScoreLabel = new JLabel("Score: ?", JLabel.CENTER);
        dealerScoreLabel.setFont(REGULAR_FONT);
        dealerScoreLabel.setForeground(Color.WHITE);
        dealerScoreLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        dealerSection.add(dealerLabel, BorderLayout.NORTH);
        dealerSection.add(dealerCardsPanel, BorderLayout.CENTER);
        dealerSection.add(dealerScoreLabel, BorderLayout.SOUTH);

        // Panel người chơi
        JPanel playerSection = new JPanel();
        playerSection.setLayout(new BorderLayout(10, 10));
        playerSection.setOpaque(false);

        JLabel playerLabel = new JLabel("You", JLabel.CENTER);
        playerLabel.setFont(SUBTITLE_FONT);
        playerLabel.setForeground(Color.WHITE);

        playerCardsPanel = new JPanel();
        playerCardsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
        playerCardsPanel.setOpaque(false);

        playerScoreLabel = new JLabel("Score: 0", JLabel.CENTER);
        playerScoreLabel.setFont(REGULAR_FONT);
        playerScoreLabel.setForeground(Color.WHITE);
        playerScoreLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        playerSection.add(playerLabel, BorderLayout.NORTH);
        playerSection.add(playerCardsPanel, BorderLayout.CENTER);
        playerSection.add(playerScoreLabel, BorderLayout.SOUTH);

        // Thêm một separator ở giữa
        JPanel separatorPanel = new JPanel();
        separatorPanel.setPreferredSize(new Dimension(getWidth(), 2));
        separatorPanel.setBackground(SECONDARY_COLOR);

        gamePanel.add(dealerSection, BorderLayout.NORTH);
        gamePanel.add(separatorPanel, BorderLayout.CENTER);
        gamePanel.add(playerSection, BorderLayout.SOUTH);

        // Panel điều khiển
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        controlPanel.setOpaque(false);
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        hitButton = createGameButton("Draw Another", e -> blackjackHit());
        standButton = createGameButton("Stop", e -> blackjackStand());
        blackjackNewGameButton = createGameButton("New Game", e -> initBlackjackGame());
        JButton backButton = createGameButton("Back To Menu", e -> cardLayout.show(mainPanel, "modeSelection"));

        controlPanel.add(hitButton);
        controlPanel.add(standButton);
        controlPanel.add(blackjackNewGameButton);
        controlPanel.add(backButton);

        blackjackPanel.add(headerPanel, BorderLayout.NORTH);
        blackjackPanel.add(gamePanel, BorderLayout.CENTER);
        blackjackPanel.add(controlPanel, BorderLayout.SOUTH);
    }

    private JButton createGameButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setForeground(TEXT_COLOR);
        button.setBackground(SECONDARY_COLOR);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 2, true),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(listener);

        return button;
    }

    private void createPokerPanel() {
        pokerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // Vẽ nền giống bàn chơi bài
                g2d.setColor(new Color(41, 128, 185));  // Xanh lam đậm cho Poker
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Vẽ họa tiết trang trí
                g2d.setColor(new Color(41, 128, 185, 150));
                int patternSize = 25;
                for (int x = 0; x < getWidth(); x += patternSize) {
                    for (int y = 0; y < getHeight(); y += patternSize) {
                        g2d.drawRect(x, y, 3, 3);
                    }
                }
            }
        };
        pokerPanel.setLayout(new BorderLayout(20, 20));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Poker", JLabel.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        headerPanel.add(titleLabel, BorderLayout.CENTER);

        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout(20, 40));
        gamePanel.setOpaque(false);
        gamePanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        // Panel máy
        JPanel computerSection = new JPanel();
        computerSection.setLayout(new BorderLayout(10, 10));
        computerSection.setOpaque(false);

        JLabel computerLabel = new JLabel("Computer", JLabel.CENTER);
        computerLabel.setFont(SUBTITLE_FONT);
        computerLabel.setForeground(Color.WHITE);

        computerPokerCardsPanel = new JPanel();
        computerPokerCardsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
        computerPokerCardsPanel.setOpaque(false);

        computerSection.add(computerLabel, BorderLayout.NORTH);
        computerSection.add(computerPokerCardsPanel, BorderLayout.CENTER);

        // Result panel
        pokerResultLabel = new JLabel("Draw the cards and evaluate", JLabel.CENTER);
        pokerResultLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        pokerResultLabel.setForeground(Color.WHITE);
        pokerResultLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JPanel resultPanel = new JPanel();
        resultPanel.setOpaque(false);
        resultPanel.add(pokerResultLabel);

        // Panel người chơi
        JPanel playerSection = new JPanel();
        playerSection.setLayout(new BorderLayout(10, 10));
        playerSection.setOpaque(false);

        JLabel playerLabel = new JLabel("You", JLabel.CENTER);
        playerLabel.setFont(SUBTITLE_FONT);
        playerLabel.setForeground(Color.WHITE);

        playerPokerCardsPanel = new JPanel();
        playerPokerCardsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
        playerPokerCardsPanel.setOpaque(false);

        playerSection.add(playerLabel, BorderLayout.NORTH);
        playerSection.add(playerPokerCardsPanel, BorderLayout.CENTER);

        gamePanel.add(computerSection, BorderLayout.NORTH);
        gamePanel.add(resultPanel, BorderLayout.CENTER);
        gamePanel.add(playerSection, BorderLayout.SOUTH);

        // Controlpanel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        controlPanel.setOpaque(false);
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        drawButton = createGameButton("Draw card", e -> pokerDraw());
        drawButton.setBackground(new Color(155, 89, 182));

        evaluateButton = createGameButton("Evaluate", e -> pokerEvaluate());
        evaluateButton.setBackground(new Color(155, 89, 182));

        pokerNewGameButton = createGameButton("New Game", e -> initPokerGame());
        pokerNewGameButton.setBackground(new Color(155, 89, 182));

        JButton backButton = createGameButton("Back to Menu", e -> cardLayout.show(mainPanel, "modeSelection"));
        backButton.setBackground(new Color(155, 89, 182));

        controlPanel.add(drawButton);
        controlPanel.add(evaluateButton);
        controlPanel.add(pokerNewGameButton);
        controlPanel.add(backButton);

        pokerPanel.add(headerPanel, BorderLayout.NORTH);
        pokerPanel.add(gamePanel, BorderLayout.CENTER);
        pokerPanel.add(controlPanel, BorderLayout.SOUTH);
    }

    private void createUnoPanel() {
        unoPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // Vẽ gradient background
                GradientPaint gradient = new GradientPaint(
                        0, 0, ACCENT_COLOR,
                        0, getHeight(), new Color(241, 196, 15)  // Vàng
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Write UNo
                g2d.setFont(new Font("Arial", Font.BOLD, 150));
                g2d.setColor(new Color(255, 255, 255, 50));
                g2d.drawString("UNO", getWidth()/2 - 150, getHeight()/2 + 50);
            }
        };

        JLabel comingSoonLabel = new JLabel("Coming Soon...", JLabel.CENTER);
        comingSoonLabel.setFont(TITLE_FONT);
        comingSoonLabel.setForeground(Color.WHITE);

        JButton backButton = createGameButton("Back to Menu", e -> cardLayout.show(mainPanel, "modeSelection"));
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(ACCENT_COLOR);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));

        unoPanel.add(comingSoonLabel, BorderLayout.CENTER);
        unoPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    // Các phương thức cho Blackjack
    private void initBlackjackGame() {
        blackjackDeck = initDeck();
        Collections.shuffle(blackjackDeck);

        playerHand = new ArrayList<>();
        dealerHand = new ArrayList<>();

        // Phát 2 lá cho người chơi và nhà cái
        playerHand.add(blackjackDeck.remove(0));
        dealerHand.add(blackjackDeck.remove(0));
        playerHand.add(blackjackDeck.remove(0));
        dealerHand.add(blackjackDeck.remove(0));

        updateBlackjackUI();

        hitButton.setEnabled(true);
        standButton.setEnabled(true);
    }

    private void updateBlackjackUI() {
        playerCardsPanel.removeAll();
        dealerCardsPanel.removeAll();

        for (Card card : playerHand) {
            playerCardsPanel.add(createCardLabel(card, true));
        }

        // Dealer's hand = 1 up 1 down
        if (!dealerHand.isEmpty()) {
            dealerCardsPanel.add(createCardLabel(dealerHand.get(0), true));

            for (int i = 1; i < dealerHand.size(); i++) {
                dealerCardsPanel.add(createCardLabel(dealerHand.get(i), !standButton.isEnabled()));
            }
        }

        int playerScore = calculateBlackjackScore(playerHand);
        playerScoreLabel.setText("Score: " + playerScore);

        if (standButton.isEnabled()) {
            dealerScoreLabel.setText("Score: ?");
        } else {
            int dealerScore = calculateBlackjackScore(dealerHand);
            dealerScoreLabel.setText("Score: " + dealerScore);
        }

        playerCardsPanel.revalidate();
        playerCardsPanel.repaint();
        dealerCardsPanel.revalidate();
        dealerCardsPanel.repaint();
    }

    private JLabel createCardLabel(Card card, boolean faceUp) {
        JLabel cardLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (faceUp) {
                    // Lá bài mở
                    drawCard(g2d, card);
                } else {
                    // Lá bài úp
                    drawCardBack(g2d);
                }
            }

            private void drawCard(Graphics2D g2d, Card card) {
                int width = getWidth();
                int height = getHeight();

                // Draw 
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, width, height, 10, 10);
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(0, 0, width, height, 10, 10);

                // Color based on suits
                Color suitColor = card.getSuit().equals("♥") || card.getSuit().equals("♦") ?
                        ACCENT_COLOR : new Color(50, 50, 50);

                // Value
                String rankStr = getRankString(card.getRank());
                g2d.setFont(new Font("Arial", Font.BOLD, 18));
                g2d.setColor(suitColor);
                g2d.drawString(rankStr, 5, 20);
                g2d.setFont(new Font("Arial", Font.BOLD, 22));
                g2d.drawString(card.getSuit(), 5, 40);

                // SUits
                g2d.setFont(new Font("Arial", Font.BOLD, 50));
                FontMetrics fm = g2d.getFontMetrics();
                int suitWidth = fm.stringWidth(card.getSuit());
                g2d.drawString(card.getSuit(), (width - suitWidth) / 2, (height + fm.getAscent()) / 2);

                // Upside down
                g2d.rotate(Math.PI, width, height);
                g2d.setFont(new Font("Arial", Font.BOLD, 18));
                g2d.drawString(rankStr, width - 25, height - 15);
                g2d.setFont(new Font("Arial", Font.BOLD, 22));
                g2d.drawString(card.getSuit(), width - 25, height - 35);
            }

            private void drawCardBack(Graphics2D g2d) {
                int width = getWidth();
                int height = getHeight();

                // Background and border
                g2d.setColor(new Color(65, 105, 225));  // Royal Blue
                g2d.fillRoundRect(0, 0, width, height, 10, 10);
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(0, 0, width, height, 10, 10);

                // Back card
                g2d.setColor(new Color(100, 149, 237));  // Cornflower Blue

                // diagonal lines
                for (int i = 0; i < width; i += 10) {
                    g2d.drawLine(i, 0, i, height);
                }

                // inner borders
                g2d.setColor(Color.WHITE);
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRoundRect(10, 10, width - 20, height - 20, 5, 5);
            }

            private String getRankString(int rank) {
                switch (rank) {
                    case 1: return "A";
                    case 11: return "J";
                    case 12: return "Q";
                    case 13: return "K";
                    default: return String.valueOf(rank);
                }
            }
        };

        cardLabel.setPreferredSize(new Dimension(80, 120));
        cardLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        return cardLabel;
    }

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

    private void blackjackStand() {
        hitButton.setEnabled(false);
        standButton.setEnabled(false);

        // Nhà cái rút bài đến khi >= 17 Score
        int dealerScore = calculateBlackjackScore(dealerHand);
        while (dealerScore < 17) {
            dealerHand.add(blackjackDeck.remove(0));
            dealerScore = calculateBlackjackScore(dealerHand);
        }

        updateBlackjackUI();

        int playerScore = calculateBlackjackScore(playerHand);

        if (dealerScore > 21) {
            JOptionPane.showMessageDialog(this, "Dealer's hand is over 21 Score. You win!", "Result", JOptionPane.INFORMATION_MESSAGE);
        } else if (dealerScore > playerScore) {
            JOptionPane.showMessageDialog(this, "Dealer's hand is " + dealerScore + " Score. Dealer wins!", "Result", JOptionPane.INFORMATION_MESSAGE);
        } else if (playerScore > dealerScore) {
            JOptionPane.showMessageDialog(this, "Your hand is " + playerScore + " Score. You win!", "Result", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Draw by " + playerScore + " Score!", "Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }

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

        // Adjust A score to 11 of 1
        while (score > 21 && aceCount > 0) {
            score -= 10;
            aceCount--;
        }

        return score;
    }

    // Poker control
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

        playerPokerCardsPanel.revalidate();
        playerPokerCardsPanel.repaint();
        computerPokerCardsPanel.revalidate();
        computerPokerCardsPanel.repaint();
    }

    private void pokerDraw() {
        playerPokerHand.clear();
        computerPokerHand.clear();

        //Draw 5 cards
        for (int i = 0; i < 5; i++) {
            playerPokerHand.add(pokerDeck.remove(0));
            computerPokerHand.add(pokerDeck.remove(0));
        }

        updatePokerUI(false);
        drawButton.setEnabled(false);
        evaluateButton.setEnabled(true);
    }

    private void updatePokerUI(boolean showComputerCards) {
        playerPokerCardsPanel.removeAll();
        computerPokerCardsPanel.removeAll();

        for (Card card : playerPokerHand) {
            playerPokerCardsPanel.add(createCardLabel(card, true));
        }

        for (Card card : computerPokerHand) {
            computerPokerCardsPanel.add(createCardLabel(card, showComputerCards));
        }

        playerPokerCardsPanel.revalidate();
        playerPokerCardsPanel.repaint();
        computerPokerCardsPanel.revalidate();
        computerPokerCardsPanel.repaint();
    }
    private void pokerEvaluate() {
        updatePokerUI(true);
        String playerHandType = evaluatePokerHand(playerPokerHand);
        String computerHandType = evaluatePokerHand(computerPokerHand);
        int playerRank = getPokerHandRank(playerHandType);
        int computerRank = getPokerHandRank(computerHandType);
        if (playerRank > computerRank) {
            pokerResultLabel.setText("You win by " + playerHandType + " vs " + computerHandType);
        } else if (computerRank > playerRank) {
            pokerResultLabel.setText("Computer win by " + computerHandType + " vs " + playerHandType);
        } else {
            pokerResultLabel.setText("Draw! Both have " + playerHandType);
        }
        evaluateButton.setEnabled(false);
    }
    private String evaluatePokerHand(List<Card> hand) {
        Collections.sort(hand, (c1, c2) -> c1.getRank() - c2.getRank());
        boolean isFlush = true;
        boolean isStraight = true;
        for (int i = 1; i < hand.size(); i++) {
            if (hand.get(i).getSuit() != hand.get(0).getSuit()) {
                isFlush = false;
                break;
            }
        }
        for (int i = 1; i < hand.size(); i++) {
            if (hand.get(i).getRank() != hand.get(i-1).getRank() + 1) {
                isStraight = false;
                break;
            }
        }
        if (!isStraight) {
            boolean specialStraight = true;
            if (hand.get(0).getRank() == 1 && hand.get(1).getRank() == 2 &&
                    hand.get(2).getRank() == 3 && hand.get(3).getRank() == 4 &&
                    hand.get(4).getRank() == 5) {
                specialStraight = true;
            }
            isStraight = specialStraight;
        }

        if (isFlush && isStraight) {
            return "Straight Flush";
        }
        Map<Integer, Integer> rankCounts = new HashMap<>();
        for (Card card : hand) {
            rankCounts.put(card.getRank(), rankCounts.getOrDefault(card.getRank(), 0) + 1);
        }
        boolean hasThreeOfAKind = false;
        boolean hasPair = false;
        boolean hasTwoPair = false;
        boolean hasFourOfAKind = false;

        for (int count : rankCounts.values()) {
            if (count == 4) {
                hasFourOfAKind = true;
            } else if (count == 3) {
                hasThreeOfAKind = true;
            } else if (count == 2) {
                if (hasPair) {
                    hasTwoPair = true;
                } else {
                    hasPair = true;
                }
            }
        }
        if (hasFourOfAKind) {
            return "Four of A Kind";
        }
        if (hasThreeOfAKind && hasPair) {
            return "Full House";
        }
        if (isFlush) {
            return "Royal";
        }

        if (isStraight) {
            return "Straight";
        }

        if (hasThreeOfAKind) {
            return "Three of A Kind";
        }

        if (hasTwoPair) {
            return "Two Pairs";
        }

        if (hasPair) {
            return "Pair";
        }

        return "High Card";
    }
    private int getPokerHandRank(String handType) {
        switch (handType) {
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
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(CardGame::new);
    }
}
