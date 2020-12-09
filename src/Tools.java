import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Tools {

    public Tools(Window grandParent, MyCanvas canvas) {
        JPanel parent = grandParent.getPanel(Window.RIGHT);

        List<JButton> buttons = new ArrayList<>();

        JButton freeform = new JButton("FreeForm");
        freeform.addActionListener(e -> canvas.setState(MyCanvas.FREE));

        JButton line = new JButton("Line");
        line.addActionListener(e -> canvas.setState(MyCanvas.LINE));

        JButton rectangle = new JButton("Rectangle");
        rectangle.addActionListener(e -> canvas.setState(MyCanvas.RECT));

        JButton frectangle = new JButton("Filled Rectangle");
        frectangle.addActionListener(e -> canvas.setState(MyCanvas.FRECT));


        buttons.add(freeform);
        buttons.add(line);
        buttons.add(rectangle);
        buttons.add(frectangle);
        int rows = buttons.size();

        parent.setLayout(new GridLayout(rows,1, 0, 5));

        buttons.forEach(parent::add);
    }
}
