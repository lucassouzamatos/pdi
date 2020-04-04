package filter;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import util.ImageReader;

public class AddFilter {
    public static Image Add(Image imageOne, Image imageTwo, double weightOne, double weightTwo) {
        try {
            System.out.println(weightOne);
            System.out.println(weightTwo);
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

                    r = ((colorOne.getRed() * weightOne) + (colorTwo.getRed() * weightTwo)) / (weightOne + weightTwo);
                    g = ((colorOne.getGreen() * weightOne) + (colorTwo.getGreen() * weightTwo)) / (weightOne + weightTwo);
                    b = ((colorOne.getBlue() * weightOne) + (colorTwo.getBlue() * weightTwo)) / (weightOne + weightTwo);

                    pixelWriter.setColor(i, j, new Color(r, g, b, (colorOne.getOpacity() + colorTwo.getOpacity()) / 2));
                }
            }
            return writableImage;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
