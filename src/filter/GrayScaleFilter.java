package filter;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class GrayScaleFilter {
    public static Image Arithmetic(Image image) {
        try {
            int w = (int) image.getWidth();
            int h = (int) image.getHeight();

            PixelReader pixelReader = image.getPixelReader();
            WritableImage writableImage = new WritableImage(w, h);
            PixelWriter pixelWriter = writableImage.getPixelWriter();

            for (int i = 1; i < w; i++) {
                for (int j = 1; j < h; j++) {
                    Color oldColor = pixelReader.getColor(i, j);
                    double media = (oldColor.getRed() + oldColor.getGreen() + oldColor.getBlue()) / 3;
                    Color currentColor = new Color(media, media, media, oldColor.getOpacity());
                    pixelWriter.setColor(i, j, currentColor);
                }
            }
            return writableImage;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Image Weighted(Image image, int r, int g, int b) {
        try {
            int w = (int) image.getWidth();
            int h = (int) image.getHeight();
            PixelReader pixelReader = image.getPixelReader();
            WritableImage writableImage = new WritableImage(w, h);
            PixelWriter pixelWriter = writableImage.getPixelWriter();

            for (int i = 1; i < w; i++) {
                for (int j = 1; j < h; j++) {
                    Color oldColor = pixelReader.getColor(i, j);
                    double media = ((oldColor.getRed() * r) + (oldColor.getGreen() * g) + (oldColor.getBlue() * b)) / 100;
                    Color currentColor = new Color(media, media, media, oldColor.getOpacity());
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
