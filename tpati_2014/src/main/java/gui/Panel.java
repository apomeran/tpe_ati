package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import model.Image;

public class Panel extends JPanel {

	private static final long serialVersionUID = 1L;
	private List<Image> image = new ArrayList<Image>();

	/**
	 * Paints an image in the panel
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (image != null) {
			for (int l = 0; l < image.size(); l++) {

				for (int i = 0; i < image.get(l).getWidth(); i++)
					for (int j = 0; j < image.get(l).getHeight(); j++) {
						g.setColor(new Color(image.get(l).getRGBPixel(i, j)));
						g.drawRect(i + l * 100, j + l * 100, 1, 1);
					}
				this.getTopLevelAncestor().setSize(image.get(l).getWidth() + 7,
						image.get(l).getHeight() + 53);
			}

		}
	}

	/**
	 * Loads an image to the panel
	 * 
	 * @param image
	 */
	public void loadImage(Image image) {
		this.image.add(image);
		((Window) getTopLevelAncestor()).enableTools();
	}

	public boolean setPixel(String xText, String yText, String colorText) {

		int x = 0;
		int y = 0;
		int color = 0;

		try {
			x = Integer.valueOf(xText);
			y = Integer.valueOf(yText);
			color = Integer.valueOf(colorText);
		} catch (NumberFormatException ex) {
			new MessageFrame("Los valores ingresados son incorrectos");
			return false;
		}

		this.image.get(0).setPixel(x, y, Image.ColorChannel.RED, color);
		this.image.get(0).setPixel(x, y, Image.ColorChannel.GREEN, color);
		this.image.get(0).setPixel(x, y, Image.ColorChannel.BLUE, color);

		this.repaint();
		return true;
	}

	public Image getImage() {
		return image.get(0);
	}

	public void resetImages() {
		this.image = new ArrayList<Image>();
	}

}
