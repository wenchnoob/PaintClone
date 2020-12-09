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
        this.setBackground(Color.WHITE);
        this.setVisible(true);
        this.grandParent = grandParent;
        parent = grandParent.getPanel(Window.CENTER);
        parent.setLayout(new GridLayout(1,1));
        parent.add(this);
        this.setSize(parent.getWidth(), parent.getHeight());
        drawables = new ArrayList<>();
    }

    public String saveString() {
        var sb = new StringBuilder(300);
        drawables.forEach(drawable -> sb.append(drawable + "\n"));
        return sb.toString();
    }

    public void accept(Drawable d) { drawables.add(d);}

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
                drawables.add(new Drawable(e.getX(), e.getY(), 10,10, true, true, Color.WHITE));
            }
            MyCanvas.this.repaint(e.getX(), e.getY(), 11,11);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (state == MyCanvas.FREE) {
                drawables.add(new Drawable(e.getX(), e.getY(), 1,1, true, true, color));
            } else if (state < 0) {
                drawables.add(new Drawable(e.getX(), e.getY(), 10,10, true, true, Color.white));
            }
            MyCanvas.this.repaint(e.getX(), e.getY(), 11,11);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            Drawable rect = null;
            if (getState() == MyCanvas.RECT) {
                if (prevXClick < e.getX()) {
                    if (prevYClick < e.getY()) {
                        rect = new Drawable(prevXClick, prevYClick, e.getX() - prevXClick + 1, e.getY() - prevYClick + 1, false, true, color);
                    } else {
                        rect = new Drawable(prevXClick, e.getY(), e.getX() - prevXClick + 1, prevYClick - e.getY() + 1, false, true, color);
                    }
                } else {
                    if (prevYClick < e.getY()) {
                        rect = new Drawable(e.getX(), prevYClick,  prevXClick - e.getX() + 1, e.getY() - prevYClick + 1, false, true, color);
                    } else {
                        rect = new Drawable(e.getX(),  e.getY(), prevXClick - e.getX() + 1, prevYClick - e.getY() + 1, false, true, color);
                    }
                }
                drawables.add(rect);
            } else if(state == MyCanvas.FRECT) {
                if (prevXClick < e.getX()) {
                    if (prevYClick < e.getY()) {
                        rect = new Drawable(prevXClick, prevYClick, e.getX() - prevXClick, e.getY() - prevYClick, true, true, color);
                    } else {
                        rect = new Drawable(prevXClick, e.getY(), e.getX() - prevXClick, prevYClick - e.getY(), true, true, color);
                    }
                } else {
                    if (prevYClick < e.getY()) {
                        rect = new Drawable(e.getX(), prevYClick,  prevXClick - e.getX(), e.getY() - prevYClick, true, true, color);
                    } else {
                        rect = new Drawable(e.getX(),  e.getY(), prevXClick - e.getX(), prevYClick - e.getY(), true, true, color);
                    }
                }
                drawables.add(rect);
            } else if (state == MyCanvas.LINE) {
                drawables.add(new Drawable(prevXClick, prevYClick, e.getX(), e.getY(), false, false, color));
            } else if (state < 0) {
                drawables.add(new Drawable(prevXClick, prevYClick, e.getX() - prevXClick, e.getY() - prevYClick, true, true, Color.WHITE));
            }

            if (prevXClick < e.getX()) {
                if (prevYClick < e.getY()) {
                    MyCanvas.this.repaint(prevXClick, prevYClick, (e.getX() - prevXClick) + 2, (e.getY() - prevYClick) + 2);
                } else {
                    MyCanvas.this.repaint(prevXClick, e.getY(), (e.getX() - prevXClick) + 2, (prevYClick - e.getY()) + 2);
                }
            } else {
                if (prevYClick < e.getY()) {
                    MyCanvas.this.repaint(e.getX(), prevYClick,  (prevXClick - e.getX()) + 2, (e.getY() - prevYClick) + 2);
                } else {
                    MyCanvas.this.repaint(e.getX(),  e.getY(), (prevXClick - e.getX()) + 2, (prevYClick - e.getY()) + 2);
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            prevXClick = e.getX();
            prevYClick = e.getY();
        }
    }

    public static class Drawable {
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

        public String toString() {
            return x + " " + y + " " + x2 + " " + y2 + " " + fill + " " + isRect + " " + color.getRed() + " " + color.getBlue() + " " + color.getGreen();
        }
    }
}
