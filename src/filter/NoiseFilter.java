package filter;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import util.Pixel;

public class NoiseFilter {
    public static Image RemoveNoise(Image image, int type) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        PixelReader pixelReader = image.getPixelReader();
        WritableImage writableImage = new WritableImage(width, height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int i = 1; i < width-1; i++) {
            for (int j = 1; j < height-1; j++) {
                Color color = pixelReader.getColor(i,j);
                Pixel pixel = new Pixel(color, i, j, image);
                pixelWriter.setColor(i, j, pixel.getMedian(type, pixel));
            }
        }
        return writableImage;
    }
}