package model;

import java.awt.Color;
import java.awt.image.BufferedImage;

import app.ImageConverter;

public class HSVImage implements Image, Cloneable {

	private ImageType type;
	private ImageFormat format;
	private Channel hue;
	private Channel saturation;
	private Channel value;

	public HSVImage(int height, int width, ImageFormat format, ImageType type) {
		if (format == null) {
			throw new IllegalArgumentException("ImageFormat can't be null");
		}

		// Initialize a channel for each RGB color
		this.hue = new Channel(width, height);
		this.saturation = new Channel(width, height);
		this.value = new Channel(width, height);
		this.format = format;
		this.type = type;
	}

	public HSVImage(BufferedImage bi, ImageFormat format, ImageType type) {
		this(bi.getHeight(), bi.getWidth(), format, Image.ImageType.HSV);
		for (int x = 0; x < bi.getWidth(); x++) {
			for (int y = 0; y < bi.getHeight(); y++) {
				Color c = new Color(bi.getRGB(x, y));
				double[] hsv_valuearray = ImageConverter.RGBtoHSV(c.getRed(),
						c.getGreen(), c.getBlue());
				hue.setPixel(x, y, hsv_valuearray[0]);
				saturation.setPixel(x, y, hsv_valuearray[1]);
				value.setPixel(x, y, hsv_valuearray[2]);
			}
		}

	}

	public HSVImage(Image image, ImageFormat format, ImageType type) {
		this(image.getHeight(), image.getWidth(), format, Image.ImageType.HSV);
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				Color c = new Color(image.getRGBPixel(x, y));
				double[] hsv_valuearray = ImageConverter.RGBtoHSV(c.getRed(),
						c.getGreen(), c.getBlue());
				hue.setPixel(x, y, hsv_valuearray[0]);
				saturation.setPixel(x, y, hsv_valuearray[1]);
				value.setPixel(x, y, hsv_valuearray[2]);
			}
		}

	}

	public void setPixel(int x, int y, ColorChannel channel, double color) {

		if (!hue.validPixel(x, y)) {
			throw new IllegalArgumentException("Invalid pixels on setPixel HSV");
		}

		if (channel == ColorChannel.HUE) {
			hue.setPixel(x, y, color);
			return;
		}
		if (channel == ColorChannel.SATURATION) {
			saturation.setPixel(x, y, color);
			return;
		}
		if (channel == ColorChannel.VALUE) {
			value.setPixel(x, y, color);
			return;
		}
		throw new IllegalStateException();
	}

	public HSVImage getSingleChannelImage(ColorChannel c) {
		HSVImage hsvImage = new HSVImage(this.getHeight(), this.getWidth(),
				this.getImageFormat(), this.getType());
		for (int x = 0; x < this.getWidth(); x++) {
			for (int y = 0; y < this.getHeight(); y++) {
				if (c == ColorChannel.HUE) {
					hsvImage.hue.setPixel(x, y, this.getRGBPixel(x, y));
					hsvImage.saturation.setPixel(x, y, 0);
					hsvImage.value.setPixel(x, y, 1);
				}
				if (c == ColorChannel.SATURATION) {
					hsvImage.hue.setPixel(x, y, 0);
					hsvImage.saturation.setPixel(x, y, this.getRGBPixel(x, y));
					hsvImage.value.setPixel(x, y, 1);

				}
				if (c == ColorChannel.VALUE) {
					hsvImage.hue.setPixel(x, y, 0);
					hsvImage.saturation.setPixel(x, y, 0);
					hsvImage.value.setPixel(x, y, this.getRGBPixel(x, y));

				}
			}
		}
		return hsvImage;
	}

	public int getRGBPixel(int x, int y) {
		int hue = this.hue.truncatePixel(getPixelFromChannel(x, y,
				ColorChannel.HUE));
		int saturation = this.saturation.truncatePixel(getPixelFromChannel(x,
				y, ColorChannel.SATURATION));
		int value = this.value.truncatePixel(getPixelFromChannel(x, y,
				ColorChannel.VALUE));
		return Color.HSBtoRGB(hue, saturation, value);
	}

	public Color getRGBColor(int x, int y) {
		return null; // BANNED //BANNED //TODO
	}

	public double getPixelFromChannel(int x, int y, ColorChannel channel) {
		if (channel == ColorChannel.HUE) {
			return hue.getPixel(x, y);
		}
		if (channel == ColorChannel.SATURATION) {
			return saturation.getPixel(x, y);
		}
		if (channel == ColorChannel.VALUE) {
			return value.getPixel(x, y);
		}
		throw new IllegalStateException();
	}

	public int getHeight() {
		return hue.getHeight();
	}

	public int getWidth() {
		return hue.getWidth();
	}

	public ImageType getType() {
		return type;
	}

	public ImageFormat getImageFormat() {
		return format;
	}

	public void setRGBPixel(int x, int y, int rgb) {
		return; // BANNED //TODO

	}

}
