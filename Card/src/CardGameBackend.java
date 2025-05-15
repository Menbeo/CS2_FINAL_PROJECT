// CardGameBackend.java
// Contains all backend logic from CardGame.java, excluding UI-related code
import java.util.*;

// Main class for backend logic of Card Saga (Blackjack and Poker)
public class CardGameBackend {
    // Blackjack variables
    private List<Card> blackjackDeck;
    private List<Card> playerHand;
    private List<Card> dealerHand;

    // Poker variables
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

    // Initialize a standard 52-card deck
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

    // Blackjack: Initialize a new game
    private void initBlackjackGame() {
        blackjackDeck = initDeck();
        Collections.shuffle(blackjackDeck);
        playerHand = new ArrayList<>();
        dealerHand = new ArrayList<>();
        playerHand.add(blackjackDeck.remove(0));
        dealerHand.add(blackjackDeck.remove(0));
        playerHand.add(blackjackDeck.remove(0));
        dealerHand.add(blackjackDeck.remove(0));
        // Note: UI-related button enabling (hitButton, standButton) omitted
        // updateBlackjackUI() omitted (UI method)
    }

    // Blackjack: Player draws a card
    private void blackjackHit() {
        playerHand.add(blackjackDeck.remove(0));
        // updateBlackjackUI() omitted (UI method)
        int playerScore = calculateBlackjackScore(playerHand);
        if (playerScore > 21) {
            // Note: UI-related button disabling (hitButton, standButton) omitted
            // JOptionPane.showMessageDialog(this, "Your hand is over 21. Dealer wins!", "Result", JOptionPane.INFORMATION_MESSAGE) omitted (UI)
        } else if (playerScore == 21) {
            blackjackStand();
        }
    }

    // Blackjack: Player ends turn, dealer plays
    private void blackjackStand() {
        // Note: UI-related button disabling (hitButton, standButton) omitted
        int dealerScore = calculateBlackjackScore(dealerHand);
        while (dealerScore < 17) {
            dealerHand.add(blackjackDeck.remove(0));
            dealerScore = calculateBlackjackScore(dealerHand);
        }
        // updateBlackjackUI() omitted (UI method)
        int playerScore = calculateBlackjackScore(playerHand);
        if (dealerScore > 21) {
            // JOptionPane.showMessageDialog(this, "Dealer's hand is over 21 Score. You win!", "Result", JOptionPane.INFORMATION_MESSAGE) omitted (UI)
        } else if (dealerScore > playerScore) {
            // JOptionPane.showMessageDialog(this, "Dealer's hand is " + dealerScore + " Score. Dealer wins!", "Result", JOptionPane.INFORMATION_MESSAGE) omitted (UI)
        } else if (playerScore > dealerScore) {
            // JOptionPane.showMessageDialog(this, "Your hand is " + playerScore + " Score. You win!", "Result", JOptionPane.INFORMATION_MESSAGE) omitted (UI)
        } else {
            // JOptionPane.showMessageDialog(this, "Draw by " + playerScore + " Score!", "Result", JOptionPane.INFORMATION_MESSAGE) omitted (UI)
        }
    }

    // Blackjack: Calculate hand score
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

    // Poker: Initialize a new game
    private void initPokerGame() {
        pokerDeck = initDeck();
        Collections.shuffle(pokerDeck);
        playerPokerHand = new ArrayList<>();
        computerPokerHand = new ArrayList<>();
        // Note: UI-related panel clearing and label updates omitted
        // drawButton, evaluateButton, assessHandsButton enabling/disabling omitted (UI)
        // playerPokerCardsPanel, computerPokerCardsPanel updates omitted (UI)
    }

    // Poker: Deal five cards to player and computer
    private void pokerDraw() {
        playerPokerHand.clear();
        computerPokerHand.clear();
        for (int i = 0; i < 5; i++) {
            playerPokerHand.add(pokerDeck.remove(0));
            computerPokerHand.add(pokerDeck.remove(0));
        }
        // updatePokerUI(false) omitted (UI method)
        // Note: UI-related button enabling/disabling (drawButton, evaluateButton, assessHandsButton) omitted
    }

    // Poker: Evaluate and compare hands
    private void pokerEvaluate() {
        // updatePokerUI(true) omitted (UI method)
        String playerHandType = evaluatePokerHand(playerPokerHand);
        String computerHandType = evaluatePokerHand(computerPokerHand);
        int playerRank = getPokerHandRank(playerHandType);
        int computerRank = getPokerHandRank(computerHandType);
        if (playerRank > computerRank) {
            // pokerResultLabel.setText("You win with " + playerHandType + " vs " + computerHandType) omitted (UI)
        } else if (computerRank > playerRank) {
            // pokerResultLabel.setText("Computer wins with " + computerHandType + " vs " + playerHandType) omitted (UI)
        } else {
            int result = compareHighCards(playerPokerHand, computerPokerHand, playerHandType);
            if (result > 0) {
                // pokerResultLabel.setText("You win with higher " + playerHandType) omitted (UI)
            } else if (result < 0) {
                // pokerResultLabel.setText("Computer wins with higher " + computerHandType) omitted (UI)
            } else {
                // pokerResultLabel.setText("Draw! Both have equal " + playerHandType) omitted (UI)
            }
        }
        // Note: UI-related button disabling (evaluateButton) omitted
    }

    // Poker: Detailed hand assessment
    private void assessPokerHands() {
        // updatePokerUI(true) omitted (UI method)
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
        // Note: UI-related dialog display (JTextArea, JScrollPane, JOptionPane) omitted
    }

    // Poker: Generate detailed hand description
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

    // Poker: Compare hands for tie-breaking
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

    // Poker: Convert Ace rank for high-card comparisons
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

    // Poker: Get rank of a pair
    private int getPairRank(Map<Integer, Integer> rankFreq) {
        for (Map.Entry<Integer, Integer> entry : rankFreq.entrySet()) {
            if (entry.getValue() == 2) {
                return entry.getKey();
            }
        }
        return 0;
    }

    // Poker: Evaluate hand type
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

    // Poker: Check for Flush
    private boolean isFlush(List<Card> hand) {
        String suit = hand.get(0).getSuit();
        for (Card card : hand) {
            if (!card.getSuit().equals(suit)) {
                return false;
            }
        }
        return true;
    }

    // Poker: Check for Straight
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

    // Poker: Check for Four of a Kind
    private boolean hasFourOfAKind(Map<Integer, Integer> rankCounts) {
        return rankCounts.containsValue(4);
    }

    // Poker: Check for Full House
    private boolean hasFullHouse(Map<Integer, Integer> rankCounts) {
        return rankCounts.containsValue(3) && rankCounts.containsValue(2);
    }

    // Poker: Check for Three of a Kind
    private boolean hasThreeOfAKind(Map<Integer, Integer> rankCounts) {
        return rankCounts.containsValue(3);
    }

    // Poker: Check for Two Pairs
    private boolean hasTwoPairs(Map<Integer, Integer> rankCounts) {
        int pairCount = 0;
        for (int count : rankCounts.values()) {
            if (count == 2) {
                pairCount++;
            }
        }
        return pairCount == 2;
    }

    // Poker: Check for Pair
    private boolean hasPair(Map<Integer, Integer> rankCounts) {
        return rankCounts.containsValue(2);
    }

    // Poker: Get numerical rank of hand type
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

    // Poker: Get rank name for display
    private String getRankName(int rank) {
        if (rank == 14) return "Ace";
        if (rank == 13) return "King";
        if (rank == 12) return "Queen";
        if (rank == 11) return "Jack";
        return String.valueOf(rank);
    }
}