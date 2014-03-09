package gui;

import app.ImageCreator;
import model.Image;

public class CreateBinarySquareImageDialog extends CreateImageDialog {

	private static final long serialVersionUID = 1L;

	public CreateBinarySquareImageDialog(Panel panel) {
		super(panel);
	}

	@Override
	protected Image createBinaryImage(int height, int width) {
		return ImageCreator.createBinarySquareImage(height, width);
	}

}
