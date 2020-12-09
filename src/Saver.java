import javax.naming.spi.DirectoryManager;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Scanner;
import java.util.stream.Stream;

public class Saver {

    File f = new File("");
    Window parent;
    MyCanvas canvas;

    public Saver(Window parent, MyCanvas canvas) {
        this.parent = parent;
        this.canvas = canvas;
    }

    public void save() {
        File save = new File(saveDialog() + ".wrench");
        try { save.createNewFile(); } catch (IOException e) {}
        try(OutputStream out = new FileOutputStream(save)) {
            out.write(canvas.saveString().getBytes());
        } catch (IOException e) {}
    }

    public void open() {
        File open = openDialog();
        if (open.getAbsolutePath().endsWith(".wrench")) {
            try(var fin = new FileInputStream(open); var scan = new Scanner(fin)) {
                scan.useDelimiter("\n");
                scan.tokens().forEach(token ->  {
                    var content = token.split(" ");
                    canvas.accept(new MyCanvas.Drawable(Integer.valueOf(content[0]), Integer.valueOf(content[1]),
                            Integer.valueOf(content[2]), Integer.valueOf(content[3]),
                            Boolean.valueOf(content[4]), Boolean.valueOf(content[5]),
                            new Color(Integer.valueOf(content[6]), Integer.valueOf(content[7]), Integer.valueOf(content[8]))));
                });
            } catch (IOException e) { }
        } else {
            System.out.println("InvalidFilePath");
        }
        canvas.repaint();
    }

    private File openDialog() {
        JFileChooser fc = new JFileChooser();
        fc.showSaveDialog(parent);
        return fc.getSelectedFile();
    }

    private String saveDialog() {
        JFileChooser fc = new JFileChooser();
        int s = fc.showSaveDialog(parent);
        return fc.getSelectedFile().getPath();
    }


}
