import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.Font;

class DrawPanel extends JPanel implements MouseListener {
    private ArrayList<Card> deck;
    private ArrayList<Card> hand;

    private boolean gameOver = false;

    // Rectangle object represents ....... a rectangle.
    private Rectangle button;
    private Rectangle button1;

    public DrawPanel() {
        button = new Rectangle(167, 300, 160, 26);
        button1 = new Rectangle(360, 10, 160, 26);
        this.addMouseListener(this);
        deck = Card.buildDeck();
        hand = Card.buildHand(deck);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 140;
        int y = 10;
        for (int i = 0; i < hand.size(); i++) {
            if (i % 3 == 0 && i != 0) {
                y = y + 100;
                x = 140;
            }
            Card c = hand.get(i);
            if (c.getHighlight()) {
                // draw the border rectangle around the card
                g.drawRect(x, y, c.getImage().getWidth(), c.getImage().getHeight());
            }

            // establish the location of the rectangle "hitbox"
            c.setRectangleLocation(x, y);

            g.drawImage(c.getImage(), x, y, null);
            x = x + c.getImage().getWidth() + 10;
        }

        // drawing the bottom button
        // with my favorite font (not comic sans)
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.drawString("PLAY AGAIN", 180, 320);
        g.drawRect((int)button.getX(), (int)button.getY(), (int)button.getWidth(), (int)button.getHeight());
        g.drawString("REPLACE CARDS", 363, 30);
        g.drawRect((int)button1.getX(), (int)button1.getY(), (int)button1.getWidth(), (int)button1.getHeight());
        g.drawString("Cards left: " + deck.size(), 0, 525);
        if (deck.size() == 0){
            g.drawString("YOU WIN",200,400);
            gameOver = true;
        }
        
        boolean eleven = false;
        boolean jack = false;
        boolean queen = false;
        boolean king = false;
        for (int i = 0; i < hand.size();i++){
            if (hand.get(i).getValue() == 11) {
                jack = true;
            }
            if (hand.get(i).getValue() == 12) {
                queen = true;
            }
            if (hand.get(i).getValue() == 13) {
                king = true;
            }
            for (int j = i + 1; j < hand.size(); j++){
                if (hand.get(i).getValue() + hand.get(j).getValue() == 11){
                    eleven = true;
                }
            }
        }
        if (jack && queen && king) {
            eleven = true;
        }
        if (!eleven){
            g.drawString("No available moves! GAME OVER!",0,400);
            gameOver = true;
        }
    }

    public void mousePressed(MouseEvent e) {

        Point clicked = e.getPoint();

        // left click
        if (e.getButton() == 1) {
            // if "clicked" is inside the button rectangle
            // aka --> did you click the button?
            if (button.contains(clicked)) {
                deck = Card.buildDeck();
                hand = Card.buildHand(deck);
                gameOver = false;
            }
        }
        if(!gameOver){
            if (button1.contains(clicked)) {
                ArrayList<Integer> cards = new ArrayList<Integer>();
                for (int i = 0; i < hand.size(); i++) {
                        if (hand.get(i).getHighlight()) {
                            cards.add(i);
                        }
                        int sum = 0;
                        if (cards.size() == 2){
                            for (int j = 0; j < cards.size(); j++){
                                sum += hand.get(cards.get(j)).getValue();
                            }
                            if (sum == 11){
                                for (int j = 0; j < cards.size(); j++){
                                    hand.get(cards.get(j)).flipHighlight();
                                    hand.get(cards.get(j)).replaceCard(deck);
                                }
                            }
                        }
                        if (cards.size() == 3){
                            boolean jack = false;
                            boolean queen = false;
                            boolean king = false;
                            for (int j = 0; j < cards.size(); j++){
                                int test = hand.get(cards.get(j)).getValue();
                                if (test == 11) {
                                    jack = true;
                                }
                                if (test == 12) {
                                    queen = true;
                                }
                                if (test == 13) {
                                    king = true;
                                }
                                }
                            if (jack && queen && king){
                                for (int j = 0; j < cards.size(); j++){
                                    hand.get(cards.get(j)).flipHighlight();
                                    hand.get(cards.get(j)).replaceCard(deck);
                                }
                            }
                        }
                    }
                }
                

            // go through each card
            // check if any of them were clicked on
            // if it was clicked, flip the card
            /* for (int i = 0; i < hand.size(); i++) {
                Rectangle box = hand.get(i).getCardBox();
                if (box.contains(clicked)) {
                    hand.get(i).flipCard();
                }
            } */
        // right click
        if (e.getButton() == 1 || e.getButton() == 3) {
            for (int i = 0; i < hand.size(); i++) {
                Rectangle box = hand.get(i).getCardBox();
                if (box.contains(clicked)) {
                    if (hand.get(i).getHighlight()) {
                        hand.get(i).flipHighlight();
                    }
                    else {
                        hand.get(i).flipHighlight();
                    }
                }
            }
        }
    }
    }

    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
}