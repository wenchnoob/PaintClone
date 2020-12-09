import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ControlPanel implements ActionListener {
    private List<JButton> buttons;
    private JButton draw, erase, save, open;

    private Window grandParent;
    private JPanel parent;
    private MyCanvas canvas;
    private Saver saver;

    public ControlPanel(Window grandParent, MyCanvas canvas, Saver saver) {
        this.grandParent = grandParent;
        parent = this.grandParent.getPanel(Window.BOTTOM);
        this.canvas = canvas;
        this.saver = saver;


        buttons = new ArrayList<>();
        draw = new JButton("Draw");
        draw.addActionListener(e -> canvas.setState(Math.abs(canvas.getState())));

        erase = new JButton("Erase");
        erase.addActionListener(e -> canvas.setState(-Math.abs(canvas.getState())));

        // To be actually implemented
        save = new JButton("Save");
        save.addActionListener(e -> saver.save());

        open = new JButton("Open");
        open.addActionListener(e -> saver.open());

        buttons.add(draw);
        buttons.add(erase);
        buttons.add(open);
        buttons.add(save);

        int cols = buttons.size();

        parent.setLayout(new GridLayout(1, cols, 5, 0));
        buttons.forEach(button -> parent.add(button));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        e.getActionCommand();
    }
}
