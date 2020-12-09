import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ColorPanel {
    private List<JButton> buttons;
    private JButton blue, red, black, yellow, cyan, pink, gray;

    private Window grandParent;
    private JPanel parent;
    private MyCanvas canvas;

    ColorPanel(Window grandParent, MyCanvas canvas) {
        buttons = new ArrayList<>();

        this.grandParent = grandParent;
        parent = this.grandParent.getPanel(Window.LEFT);
        this.canvas = canvas;

        black = new JButton("Black");
        black.addActionListener(e -> canvas.setColor(Color.BLACK));

        blue = new JButton("Blue");
        blue.addActionListener(e -> canvas.setColor(Color.BLUE));

        red = new JButton("Red");
        red.addActionListener(e -> canvas.setColor(Color.RED));

        yellow = new JButton("Yellow");
        yellow.addActionListener(e -> canvas.setColor(Color.YELLOW));

        cyan = new JButton("Cyan");
        cyan.addActionListener(e -> canvas.setColor(Color.CYAN));

        pink = new JButton("Pink");
        pink.addActionListener(e -> canvas.setColor(Color.PINK));

        gray = new JButton("Gray");
        gray.addActionListener(e -> canvas.setColor(Color.GRAY));

        buttons.add(black);
        buttons.add(blue);
        buttons.add(red);
        buttons.add(yellow);
        buttons.add(cyan);
        buttons.add(pink);
        buttons.add(gray);

        int rows = buttons.size();

        parent.setLayout(new GridLayout(rows, 2, 1, 5));
        buttons.forEach(button -> parent.add(button));
    }
}
