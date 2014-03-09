package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.ColorImage;
import model.Image;

public class GetPixelDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	public GetPixelDialog(final Panel panel, final Image img) {

		setTitle("Obtener Informacion de Pixel");
		setBounds(1, 1, 250, 200);
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width / 3 - getWidth() / 3, size.height / 3
				- getHeight() / 3);
		this.setResizable(false);
		setLayout(null);

		JPanel panelX = new JPanel();
		panelX.setBorder(BorderFactory.createTitledBorder("X"));
		panelX.setBounds(0, 0, 250, 50);

		JPanel panelY = new JPanel();
		panelY.setBorder(BorderFactory.createTitledBorder("Y"));
		panelY.setBounds(0, 50, 250, 50);

		JLabel xValueLabel = new JLabel("X = ");
		final JTextField defaultvaluex = new JTextField("1");
		defaultvaluex.setColumns(3);

		JLabel yValueLabel = new JLabel("Y = ");
		final JTextField defaultvaluey = new JTextField("1");
		defaultvaluey.setColumns(5);

		JButton okButton = new JButton("OK");
		okButton.setSize(250, 40);
		okButton.setBounds(0, 100, 250, 40);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int xCoordinate;
				int yCoordinate;
				try {
					xCoordinate = Integer.valueOf(defaultvaluex.getText()
							.trim());
					yCoordinate = Integer.valueOf(defaultvaluey.getText()
							.trim());

				} catch (NumberFormatException ex) {
					new MessageFrame("Los datos ingresados son invalidos");
					return;
				}

				if (xCoordinate <= 0 || yCoordinate <= 0) {
					new MessageFrame("El pixel debe ser un valor positivo");
					return;
				}

				if (xCoordinate > img.getHeight()
						|| yCoordinate > img.getWidth()) {
					new MessageFrame(
							"El pixel debe ser del tamaño de la imagen!  "
									+ img.getHeight() + "x" + img.getWidth()
									+ " pixels");
					return;
				}

				Color color = ((ColorImage) img).getRGBColor(xCoordinate,
						yCoordinate);
				;
				color.getBlue();
				color.getRed();
				color.getAlpha();
				color.getColorSpace();
				String info = "Verde: " + color.getGreen() + " - Rojo: "
						+ color.getRed() + " - Azul: " + color.getBlue()
						+ " - Alpha: " + color.getAlpha();
				JPanel pixelInfoPanel = new JPanel();
				pixelInfoPanel.setBorder(BorderFactory
						.createTitledBorder("Informacion del pixel "
								+ xCoordinate + ":" + yCoordinate + " => "
								+ info));
				pixelInfoPanel.setBounds(10, 500, 750, 350);
				panel.add(pixelInfoPanel);
				panel.repaint();
				dispose();

			}
		});

		panelX.add(xValueLabel);
		panelX.add(defaultvaluex);

		panelY.add(yValueLabel);
		panelY.add(defaultvaluey);

		this.add(panelX);
		this.add(panelY);
		this.add(okButton);

	};

}
