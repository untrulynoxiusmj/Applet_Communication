import java.util.*;
import java.applet.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

class ColorListener implements ActionListener {
    Applet scApplet;
    Checkbox shape;
    Checkbox color;
    TextField shapeName;
    TextField dimensions;
    TextField rgb;

    ColorListener(Applet scApplet, TextField shapeName, TextField dimensions, Checkbox color, Checkbox shape, TextField rgb){
        this.scApplet  = scApplet;
        this.color = color;
        this.shape = shape;
        this.shapeName = shapeName;
        this.dimensions = dimensions;
        this.rgb = rgb;
    }

    public void actionPerformed(ActionEvent e){
        JButton buttonSource = (JButton) e.getSource();
        String buttonLabel = (String) buttonSource.getText();
        if (scApplet!=null)
        {
            ShapeColorApplet mainApplet = (ShapeColorApplet) scApplet;
            if(buttonLabel == "Stop")
            {
                mainApplet.stop();
            }
            if(buttonLabel == "Start")
            {
                String SN = shapeName.getText();
                String D = dimensions.getText();
                String RGB = rgb.getText();

                if (color.getState() == true){
                    int[] dimensionResults = Arrays.stream(D.split(","))
                    .mapToInt(Integer::parseInt)
                    .toArray();
                    mainApplet.setShapeSize(dimensionResults);
                    mainApplet.shapeName = SN;
                    mainApplet.change = "color";
                }
                else if (shape.getState() == true){
                    int[] rgbResults = Arrays.stream(RGB.split(","))
                    .mapToInt(Integer::parseInt)
                    .toArray();
                    mainApplet.RGBColor = rgbResults.clone();
                    mainApplet.change = "shape";
                }
                mainApplet.start();
            }
        }
    }
}

public class EnterShapeApplet extends JApplet
{
    TextField shapeName;
    TextField dimensions;
    TextField rgb;
    Applet scApplet;
    JButton stopB;
    JButton startB;
    Checkbox color, shape;
    CheckboxGroup chooseTrans;
    JFrame extFrame; 
    JPanel transPanel;
    JPanel mainPanel;
    public void init()
    {
        add(new Label("Inter Applet Communication"));

        scApplet=null;
        scApplet= getAppletContext().getApplet("ShapeColorApplet");

	    extFrame = new JFrame("Control Room");
    	extFrame.setVisible(true);
        extFrame.setSize(500, 500);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5,2));
        mainPanel.setFont(new java.awt.Font("Serif", Font.PLAIN, 20));

        transPanel = new JPanel();
        transPanel.setLayout(new GridLayout(0,2));

        chooseTrans = new CheckboxGroup();
        color = new Checkbox("color", chooseTrans, true);
        shape = new Checkbox("shape", chooseTrans, false);

        shapeName = new TextField("", 20);
        dimensions = new TextField("", 20);
        rgb = new TextField("", 20);

        stopB = new JButton("Stop");
        stopB.addActionListener(new ColorListener(scApplet, shapeName, dimensions, color, shape, rgb));

        startB = new JButton("Start");
        startB.addActionListener(new ColorListener(scApplet, shapeName, dimensions, color, shape, rgb));
        
        mainPanel.add(new Label("Choose type of transition: "));
        transPanel.add(color);
        transPanel.add(shape);
        mainPanel.add(transPanel);

        mainPanel.add(new Label("Enter name of shape: "));
        mainPanel.add(shapeName);

        mainPanel.add(new Label("Enter dimensions: "));
        mainPanel.add(dimensions);

        mainPanel.add(new Label("Enter RGB values: "));
        mainPanel.add(rgb);

        mainPanel.add(stopB);
        mainPanel.add(startB);

        extFrame.add(mainPanel,BorderLayout.CENTER);
    }
}
