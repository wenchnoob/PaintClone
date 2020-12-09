import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class MyCanvas extends Canvas {
    Window grandParent;
    JPanel parent;
    List<Drawable> drawables;
    Color color = Color.BLACK;

    public static final int FREE = 1, LINE = 2, RECT = 3, FRECT = 4;
    private int state = 1;

    public MyCanvas(Window grandParent) {
        addMouseListener(new MYMouseAdapter());
        addMouseMotionListener(new MYMouseAdapter());
        this.setVisible(true);
        this.grandParent = grandParent;
        parent = grandParent.getPanel(Window.CENTER);
        parent.setLayout(new GridLayout(1,1));
        parent.add(this);
        this.setSize(parent.getWidth(), parent.getHeight());
        drawables = new ArrayList<>();
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return  color;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(color);
        drawables.forEach(drawable ->  {
            int[] specs = drawable.specs();
            g.setColor(drawable.color());
            if(drawable.isRect()) {
                if(drawable.isFilled()) {
                    g.fillRect(specs[0], specs[1], specs[2], specs[3]);
                } else {
                    g.drawRect(specs[0], specs[1], specs[2], specs[3]);
                }
            } else {
                    g.drawLine(specs[0], specs[1], specs[2], specs[3]);
            }});
    }

    private class MYMouseAdapter extends MouseAdapter {
        int prevXClick = 0, prevYClick = 0;

        @Override
        public void mouseClicked(MouseEvent e) {
            if (state == MyCanvas.FREE) {
                drawables.add(new Drawable(e.getX(), e.getY(), 1,1, true, true, color));
            } else if (state < 0) {
                drawables.add(new Drawable(e.getX(), e.getY(), 10,10, true, true, color));
            }
            MyCanvas.this.repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (state == MyCanvas.FREE) {
                drawables.add(new Drawable(e.getX(), e.getY(), 1,1, true, true, color));
            }
            MyCanvas.this.repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (getState() == MyCanvas.RECT) {
                drawables.add(new Drawable(prevXClick, prevYClick, e.getX() - prevXClick, e.getY() - prevYClick, false, true, color));
            } else if(state == MyCanvas.FRECT) {
                drawables.add(new Drawable(prevXClick, prevYClick, e.getX() - prevXClick, e.getY() - prevYClick, true, true, color));
            } else if (state == MyCanvas.LINE) {
                drawables.add(new Drawable(prevXClick, prevYClick, e.getX(), e.getY(), false, false, color));
            }
            MyCanvas.this.repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            prevXClick = e.getX();
            prevYClick = e.getY();
        }
    }

    private class Drawable {
        private int x, y, x2, y2;
        private boolean fill, isRect;
        private Color color;

        public Drawable(int x, int y, int x2, int y2, boolean fill, boolean isRect , Color color) {
            this.x = x;
            this.y = y;
            this.x2 = x2;
            this.y2 = y2;
            this.fill = fill;
            this.isRect = isRect;
            this.color = color;
        }

        public boolean isRect() { return isRect; }
        public boolean isFilled() { return fill; }
        public int[] specs() { return new int[]{x, y, x2, y2}; }
        public Color color() { return color; }
    }
}
