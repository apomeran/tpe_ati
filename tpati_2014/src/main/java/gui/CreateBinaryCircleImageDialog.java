package gui;

import app.ImageCreator;
import model.Image;

public class CreateBinaryCircleImageDialog extends CreateImageDialog {

	private static final long serialVersionUID = 1L;

	public CreateBinaryCircleImageDialog(Panel panel) {
		super(panel);
	}

	@Override
	protected Image createBinaryImage(int height, int width) {
		return ImageCreator.createBinaryCircleImage(height, width);
	}

}
