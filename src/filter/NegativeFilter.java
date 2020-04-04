package filter;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class NegativeFilter {

	public static Image Negative(Image image) {
		try {
			int width = (int) image.getWidth();
			int height = (int) image.getHeight();

			PixelReader pixelReader = image.getPixelReader();
			WritableImage writableImage = new WritableImage(width, height);
			PixelWriter pixelWriter = writableImage.getPixelWriter();

			for (int i = 1; i < width; i++) {
				for (int j = 1; j < height; j++) {
					Color oldColor = pixelReader.getColor(i, j);
					double currentRed = 1 - oldColor.getRed();
					double currentGreen = 1 - oldColor.getGreen();
					double currentBlue = 1 - oldColor.getBlue();
					Color currentColor = new Color(currentRed, currentGreen, currentBlue, oldColor.getOpacity());
					pixelWriter.setColor(i, j, currentColor);
				}
			}
			return writableImage;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
