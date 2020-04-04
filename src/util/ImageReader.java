package util;

import javafx.scene.image.Image;

public class ImageReader {
    public static int getSmallerWidth(Image imageOne, Image imageTwo) {
        int wOne = (int) imageOne.getWidth();
        int wTwo = (int) imageTwo.getWidth();
        return Math.min(wOne, wTwo);
    }

    public static int getSmallerHeight(Image imageOne, Image imageTwo) {
        int hOne = (int) imageOne.getHeight();
        int hTwo = (int) imageTwo.getHeight();
        return Math.min(hOne, hTwo);
    }
}
