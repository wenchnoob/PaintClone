import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    public static final int LEFT = 1, RIGHT = 2, TOP = 3, BOTTOM = 4, CENTER = 5;
    private JPanel left, right, top, bottom, center;

    private MyCanvas canvas;
    private ColorPanel colors;
    private ControlPanel controls;
    private Tools tools;

    public Window(int width, int height, String name) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);
        this.setTitle(name);
        this.setLayout(new BorderLayout());
        this.setVisible(true);

        this.setBackground(Color.GRAY);

        left = new JPanel() {
            public Insets getInsets() { return new Insets(10, 10, 10, 10);}
        };
        right = new JPanel() {
            public Insets getInsets() { return new Insets(10, 10, 10, 10);}
        };
        top = new JPanel() {
            public Insets getInsets() { return new Insets(10, 10, 10, 10);}
        };
        bottom = new JPanel() {
            public Insets getInsets() { return new Insets(10, 10, 10, 10);}
        };
        center = new JPanel();

        this.add(left, BorderLayout.WEST);
        this.add(right, BorderLayout.EAST);
        this.add(top, BorderLayout.NORTH);
        this.add(bottom, BorderLayout.SOUTH);
        this.add(center, BorderLayout.CENTER);

        canvas = new MyCanvas(this);

        colors = new ColorPanel(this, canvas);

        controls = new ControlPanel(this, canvas);

        tools = new Tools(this, canvas);
    }

    public Insets getInsets() {
        return new Insets(10,10,10,10);
    }

    public JPanel getPanel(int comp) {
        switch(comp) {
            case LEFT:
                return left;
            case RIGHT:
                return right;
            case TOP:
                return top;
            case BOTTOM:
                return bottom;
            case CENTER:
                return center;
        }
        return null;
    }

}
