import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.Font;

class DrawPanel extends JPanel implements MouseListener {

    private ArrayList<Card> hand;

    // Rectangle object represents ....... a rectangle.
    private Rectangle button;

    public DrawPanel() {
        button = new Rectangle(167, 300, 160, 26);
        this.addMouseListener(this);
        hand = Card.buildHand();
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
        g.drawString("GET NEW CARDS", 170, 320);
        g.drawRect((int)button.getX(), (int)button.getY(), (int)button.getWidth(), (int)button.getHeight());
    }

    public void mousePressed(MouseEvent e) {

        Point clicked = e.getPoint();

        // left click
        if (e.getButton() == 1) {
            // if "clicked" is inside the button rectangle
            // aka --> did you click the button?
            if (button.contains(clicked)) {
                hand = Card.buildHand();
            }

            // go through each card
            // check if any of them were clicked on
            // if it was clicked, flip the card
            for (int i = 0; i < hand.size(); i++) {
                Rectangle box = hand.get(i).getCardBox();
                if (box.contains(clicked)) {
                    hand.get(i).flipCard();
                }
            }
        }
        // right click
        if (e.getButton() == 3) {
            for (int i = 0; i < hand.size(); i++) {
                Rectangle box = hand.get(i).getCardBox();
                if (box.contains(clicked)) {
                    hand.get(i).flipHighlight(i);
                    if (hand.get(i).getTest()[i]% 2 ==0){
                        hand.get(i).replaceCard();
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