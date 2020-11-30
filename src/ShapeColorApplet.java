import java.applet.*;
import java.awt.*;
import java.util.*;

public class ShapeColorApplet extends Applet {

	String shapeName = "";
	int[] ShapeSize;
	int transitioning = 0;
	int[] RGBColor;
	int turn = 1;
	String change = "";
	int fixedX = 50;
	int fixedY = 50;
	int[] fixedRectangle = {30, 30, 100, 200};
	int [][] fixedTriangle = {
		{ 30, 30, 200 }, { 30, 150, 100 }
	};
	int[] fixedCircle = {70, 30, 100, 100};
	int sleepTime = 2000;

	private void delay() {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException err) {
			err.printStackTrace();
			super.showStatus("Please enter valid values");
		}
	}

	public int randomColorRangeNumber(){
		int min = 1;
		int max = 255;
		return (int)(Math.random() * (max - min + 1) + min);
	}

	public Color randomColor(){
		return new Color(randomColorRangeNumber(), randomColorRangeNumber(), randomColorRangeNumber());
	}

	public void setShapeSize(int[] shape) {
		ShapeSize = shape.clone();
	}

	public int[] getShapeSize() {
		return ShapeSize;
	}

	public void init() {
		stop();
	}

	public void start() {
		if (change.equals("color")) {
			super.showStatus("Shape: " + shapeName);
		} else if (change.equals("shape")) {
			super.showStatus("Color: " + Arrays.toString(RGBColor));
		}
		if (transitioning == 1) {
			return;
		}
		transitioning = 1;
		turn = 1;
		new Thread() {
			public void run() {
				while (transitioning == 1) {
					repaint();
					delay();
				}
			}
		}.start();
	}

	public void stop() {
		transitioning = 0;
		super.showStatus("Transition stopped");
	}

	public void paint(Graphics g) {
		if (change.equals("color")) {
			changeColor(g);
		} else if (change.equals("shape")) {
			changeShape(g);
		} else {
			transitioning = 0;
		}
	}

	public void changeColor(Graphics g) {
		g.setColor(randomColor());
		if (shapeName.equals("rectangle")) {
			int length = getShapeSize()[0], breadth = getShapeSize()[1];
			g.fillRect(fixedX, fixedY, length, breadth);
		} else if (shapeName.equals("triangle")) {
			int a = getShapeSize()[0];
			int thirdY = (int)(fixedY + a * 1.732 / 2);
			int[] nXpts = {
				fixedX, fixedX + a, fixedX + a / 2
			};
			int[] nYpts = {
				fixedY, fixedY, thirdY
			};
			g.fillPolygon(nXpts, nYpts, 3);
		} else if (shapeName.equals("circle")) {
			int radiusRelative = 2*getShapeSize()[0];
			g.fillOval(fixedX, fixedY, radiusRelative, radiusRelative);
		} else {
			super.showStatus("Not a valid shape");
			return;
		}
	}

	public void changeShape(Graphics g) {
		g.setColor(new Color(RGBColor[0], RGBColor[1], RGBColor[2]));
		if (turn == 1) {
			g.fillRect(fixedRectangle[0], fixedRectangle[1], fixedRectangle[2], fixedRectangle[3]);
			turn = 2;
		} else if (turn == 2) {
			int[] nXpts = fixedTriangle[0];
			int[] nYpts = fixedTriangle[1];
			g.fillPolygon(nXpts, nYpts, 3);
			turn = 3;
		} else if (turn == 3) {
			g.fillOval(fixedCircle[0], fixedCircle[1], fixedCircle[2], fixedCircle[3]);
			turn = 1;
		}
	}

}