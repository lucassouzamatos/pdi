package filter;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import util.ImageReader;

public class SubtractFilter {
    public static Image Subtract(Image imageOne, Image imageTwo) {
        try {
            int wSmaller = ImageReader.getSmallerWidth(imageOne, imageTwo);
            int hSmaller = ImageReader.getSmallerHeight(imageOne, imageTwo);

            WritableImage writableImage = new WritableImage(wSmaller, hSmaller);
            PixelWriter pixelWriter = writableImage.getPixelWriter();

            PixelReader pixelReaderOne = imageOne.getPixelReader();
            PixelReader pixelReaderTwo = imageTwo.getPixelReader();

            Color colorOne;
            Color colorTwo;

            double r;
            double g;
            double b;

            for (int i = 0; i < wSmaller; i++) {
                for (int j = 0; j < hSmaller; j++) {
                    colorOne = pixelReaderOne.getColor(i, j);
                    colorTwo = pixelReaderTwo.getColor(i, j);

                    r = colorOne.getRed() - colorTwo.getRed();
                    g = colorOne.getGreen() - colorTwo.getGreen();
                    b = colorOne.getBlue() - colorTwo.getBlue();

                    r = r < 0 ? 0 : r;
                    g = g < 0 ? 0 : g;
                    b = b < 0 ? 0 : b;

                    Color newColor = new Color(r, g, b, 1);
                    pixelWriter.setColor(i, j, newColor);
                }
            }

            return writableImage;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
