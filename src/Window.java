import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    public static final int LEFT = 1, RIGHT = 2, TOP = 3, BOTTOM = 4, CENTER = 5;
    private final JPanel left;
    private final JPanel right;
    private final JPanel top;
    private final JPanel bottom;
    private final JPanel center;

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

        MyCanvas canvas = new MyCanvas(this);

        ColorPanel colors = new ColorPanel(this, canvas);

        Saver saver = new Saver(this, canvas);

        ControlPanel controls = new ControlPanel(this, canvas, saver);

        Tools tools = new Tools(this, canvas);
    }

    public Insets getInsets() {
        return new Insets(10,10,10,10);
    }

    public JPanel getPanel(int comp) {
        return switch (comp) {
            case LEFT -> left;
            case RIGHT -> right;
            case TOP -> top;
            case BOTTOM -> bottom;
            case CENTER -> center;
            default -> null;
        };
    }

}
