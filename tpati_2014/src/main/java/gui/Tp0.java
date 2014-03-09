package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import model.Image;

import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.ImageWriteException;

import app.ImageConverter;
import app.ImageLoader;
import app.ImageSaver;

public class Tp0 extends JMenu {

	public JMenuItem saveImage = new JMenuItem("Guardar imagen");
	public JMenuItem getPixel = new JMenuItem(
			"Obtener Informacion sobre un pixel particular");
	public JMenuItem copyImage = new JMenuItem(
			"Copiar una parte de la imagen en otra imagen nueva");
	public JMenuItem RGBtoHSV = new JMenuItem("Pasar de RGB a HSV");

	private static final long serialVersionUID = 1L;

	public Tp0() {
		super("TP 0");
		this.setEnabled(true);
		JMenuItem loadImage = new JMenuItem("Cargar imagen (BMP, PPM, PGM)");
		loadImage.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				JFileChooser selector = new JFileChooser();
				selector.showOpenDialog(Tp0.this);
				File arch = selector.getSelectedFile();

				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				panel.resetImages();

				if (arch != null) {
					Image image = null;

					try {
						image = ImageLoader.loadImage(arch);
					} catch (IllegalArgumentException ex) {
						new MessageFrame(
								"Para cargar una imagen RAW use Cargar imagen Raw");
					} catch (IllegalStateException ex) {
						new MessageFrame(
								"Extension de imagen no soportado. Solo (BMP, PPM, PGM)");
					} catch (ImageReadException ex) {
						new MessageFrame(
								"No se pudo leer correctamente la imagen");
					} catch (IOException ex) {
						new MessageFrame(
								"No se pudo cargar la imagen. Error de I/O");
					}

					if (image != null) {
						// Loads the image to the panel
						panel.loadImage(image);

						// This will repaint the panel with the previous image
						// loaded
						panel.repaint();
					}

				}

			}
		});
		JMenuItem loadRaw = new JMenuItem("Cargar Imagen extension Raw");
		loadRaw.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				JFileChooser selector = new JFileChooser();
				selector.showOpenDialog(Tp0.this);
				File arch = selector.getSelectedFile();

				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				panel.resetImages();
				if (arch != null) {
					JDialog rawParams = new RawImageDialog(panel, arch);
					rawParams.setVisible(true);
				}
			}
		});

		getPixel.setEnabled(false);
		getPixel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				JDialog binaryImage = new GetPixelDialog(
						panel,
						(((Window) getTopLevelAncestor()).getPanel().getImage()));
				binaryImage.setVisible(true);
			}
		});

		copyImage.setEnabled(false);
		copyImage.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO ?!?!?!??!
			}
		});

		RGBtoHSV.setEnabled(false);
		RGBtoHSV.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Image HSVImage = ImageConverter
						.convertRGBImagetoHSVImage((((Window) getTopLevelAncestor())
								.getPanel().getImage()));
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				panel.removeAll();
				panel.loadImage(HSVImage);
				panel.repaint();

			}
		});

		saveImage.setEnabled(false);
		saveImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser selector = new JFileChooser();
				selector.setApproveButtonText("Save");
				selector.showSaveDialog(Tp0.this);

				File arch = selector.getSelectedFile();

				if (arch != null) {
					Image image = (((Window) getTopLevelAncestor()).getPanel()
							.getImage());
					try {
						ImageSaver.saveImage(arch, image);
					} catch (ImageWriteException ex) {
						new MessageFrame("No se pudo guardar la imagen");
					} catch (IOException ex) {
						new MessageFrame("No se pudo guardar la imagen");
					}
				}

			}
		});
		JMenuItem exit = new JMenuItem("Salir");
		exit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JMenuItem degradeBW = new JMenuItem("Degrade de grises");
		degradeBW.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Panel panel = (((Window) getTopLevelAncestor()).getPanel());

				JDialog degrade = new DegradeDialog(panel, false);

				degrade.setVisible(true);

			}
		});

		JMenuItem degradeColor = new JMenuItem("Degrade de colores");
		degradeColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Panel panel = (((Window) getTopLevelAncestor()).getPanel());

				JDialog degrade = new DegradeDialog(panel, true);

				degrade.setVisible(true);

			}
		});

		JMenuItem binaryImageSquare = new JMenuItem(
				"Imagen binaria Cuadrado Blanco");
		binaryImageSquare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Panel panel = (((Window) getTopLevelAncestor()).getPanel());

				JDialog binaryImage = new CreateBinarySquareImageDialog(panel);

				binaryImage.setVisible(true);

			}
		});

		JMenuItem binaryImageCircle = new JMenuItem(
				"Imagen binaria Circulo Blanco");
		binaryImageCircle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Panel panel = (((Window) getTopLevelAncestor()).getPanel());

				JDialog binaryImage = new CreateBinaryCircleImageDialog(panel);
				binaryImage.setVisible(true);

			}
		});

		this.add(loadImage);
		this.add(loadRaw);
		this.add(getPixel);
		this.add(copyImage);
		this.add(saveImage);
		this.add(RGBtoHSV);
		this.add(binaryImageSquare);
		this.add(binaryImageCircle);
		this.add(degradeBW);
		this.add(degradeColor);
		this.add(new JSeparator());
		this.add(exit);

	}
}
