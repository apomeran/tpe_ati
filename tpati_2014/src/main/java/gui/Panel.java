package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import model.Image;

public class Panel extends JPanel implements MouseMotionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private List<Image> image = new ArrayList<Image>();
	Rectangle selection;
	Point anchor;

	public Panel() {
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	/**
	 * Paints an image in the panel
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (selection != null) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.draw(selection);
		}
		if (image != null) {
			for (int l = 0; l < image.size(); l++) {

				for (int i = 0; i < image.get(l).getWidth(); i++)
					for (int j = 0; j < image.get(l).getHeight(); j++) {
						g.setColor(new Color(image.get(l).getRGBPixel(i, j)));
						g.drawRect(i, j + l * image.get(l).getHeight(), 1, 1);
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

	public void mousePressed(MouseEvent e) {
		anchor = e.getPoint();

		selection = new Rectangle(anchor);
		Image img = image.get(0);

		for (double i = selection.getMinX(); i < selection.getMaxX(); i++) {
			for (double j = selection.getMinY(); j < selection.getMaxY(); j++) {
				this.getComponentAt(anchor);
				img.getRGBPixel((int) i, (int) j);
			}
		}
	}

	public void mouseDragged(MouseEvent e) {
		selection.setBounds((int) Math.min(anchor.x, e.getX()),
				(int) Math.min(anchor.y, e.getY()),
				(int) Math.abs(e.getX() - anchor.x),
				(int) Math.abs(e.getY() - anchor.y));
		repaint();
	}

	public void mouseReleased(MouseEvent e) {

		selection = null;
		repaint();
	}

	// unused
	public void mouseMoved(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

}
