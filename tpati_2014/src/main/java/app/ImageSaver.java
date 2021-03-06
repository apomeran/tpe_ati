package app;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import model.Image;

import org.apache.sanselan.ImageFormat;
import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.Sanselan;

public class ImageSaver {

	public static void saveImage(File arch, Image image)
			throws ImageWriteException, IOException {

		String[] cadena = (arch.getName()).split("\\.");
		String extension = cadena[cadena.length - 1];

		BufferedImage bi = null;
		ImageFormat format = null;

		if (!extension.equals("raw")) {
			bi = new BufferedImage(image.getWidth(), image.getHeight(),
					ColorUtilities.toBufferedImageType(image.getType()));
			format = ColorUtilities.toSanselanImageFormat(image
					.getImageFormat());
		} else {
			throw new UnsupportedOperationException(
					"No soporta guardar en formato RAW");
		}

		ColorUtilities.populateEmptyBufferedImage(bi, image);

		Sanselan.writeImage(bi, arch, format, null);

	}

}
