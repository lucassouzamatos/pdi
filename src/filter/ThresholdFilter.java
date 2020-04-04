package filter;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ThresholdFilter {

    public static Image Threshold(Image image, double threshold) {
        try {
            int width = (int) image.getWidth();
            int height = (int) image.getHeight();

            PixelReader pixelReader = image.getPixelReader();
            WritableImage writableImage = new WritableImage(width, height);
            PixelWriter pixelWriter = writableImage.getPixelWriter();

            for (int i = 1; i < width; i++) {
                for (int j = 1; j < height; j++) {
                    Color oldColor = pixelReader.getColor(i, j);
                    Color currentColor;
                    if (oldColor.getRed()  >= threshold) {
                        currentColor = new Color(1,1, 1, oldColor.getOpacity());
                    } else {
                        currentColor = new Color(0, 0, 0,oldColor.getOpacity());
                    }
                    pixelWriter.setColor(i, j, currentColor);
                }
            }
            return writableImage;

        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}