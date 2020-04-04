package util;

import javafx.scene.image.*;
import javafx.scene.paint.Color;

public class ImageMarker {
    public static void Mark(Drag drag, ImageView imageView, int stroke, Color currentColor) {
        Image image = imageView.getImage();

        WritableImage writableImage = new WritableImage((int) image.getWidth(), (int) image.getHeight());
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        PixelReader pixelReader = image.getPixelReader();

        int startX = drag.getStartPress().positionX;
        int startY = drag.getStartPress().positionY;
        int finalX = drag.getFinalPress().positionX;
        int finalY = drag.getFinalPress().positionY;

        for (int i = 1; i < (int) image.getWidth(); i++) {
            for (int j = 1; j < (int) image.getHeight(); j++) {
                pixelWriter.setColor(i, j, pixelReader.getColor(i, j));
            }
        }

        if (startX < finalX) {
            for (int i = startX; i <= finalX; i++) {
                markStrokeX(pixelWriter, i, startY, currentColor, finalY, stroke);
            }
        } else {
            for (int i = finalX; i <= startX; i++) {
                markStrokeX(pixelWriter, i, startY, currentColor, finalY, stroke);
            }
        }

        if (startY < finalY) {
            for (int i = startY; i <= finalY; i++) {
                markStrokeY(pixelWriter, i, startX, currentColor, finalX, stroke);
            }
        } else {
            for (int i = finalY; i <= startY; i++) {
                markStrokeY(pixelWriter, i, startX, currentColor, finalX, stroke);
            }
        }

        imageView.setImage(writableImage);
    }

    public static void markStrokeY(PixelWriter pixelWriter, int i, int startX, Color currentColor, int finalX, int stroke) {
        pixelWriter.setColor(startX, i, currentColor);
        pixelWriter.setColor(finalX, i, currentColor);

        for (int j = 0; j <= stroke/2; j++) {
            pixelWriter.setColor(startX + j, i + j, currentColor);
            pixelWriter.setColor(startX + j, i - j, currentColor);
            pixelWriter.setColor(startX - j, i - j, currentColor);
            pixelWriter.setColor(startX - j, i + j, currentColor);

            pixelWriter.setColor(finalX + j, i + j, currentColor);
            pixelWriter.setColor(finalX + j, i - j, currentColor);
            pixelWriter.setColor(finalX - j, i - j, currentColor);
            pixelWriter.setColor(finalX - j, i + j, currentColor);
        }
    }

    public static void markStrokeX(PixelWriter pixelWriter, int i, int startY, Color currentColor, int finalY, int stroke) {
        pixelWriter.setColor(i, startY, currentColor);
        pixelWriter.setColor(i, finalY, currentColor);

        for (int j = 0; j <= stroke/2; j++) {
            pixelWriter.setColor(i + j, startY + j, currentColor);
            pixelWriter.setColor(i - j, startY + j, currentColor);
            pixelWriter.setColor(i - j, startY - j, currentColor);
            pixelWriter.setColor(i + j, startY - j, currentColor);

            pixelWriter.setColor(i + j, finalY + j, currentColor);
            pixelWriter.setColor(i - j, finalY + j, currentColor);
            pixelWriter.setColor(i - j, finalY - j, currentColor);
            pixelWriter.setColor(i + j, finalY - j, currentColor);
        }
    }
}
