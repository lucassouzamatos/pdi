package util;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

import java.util.Arrays;

public class Pixel {

    public double r;
    public double g;
    public double b;
    public int x;
    public int y;
    public Image image;

    public static int NEIGHTBOUR3X3 = 1;
    public static int NEIGHTBOUR_CROSS = 2;
    public static int NEIGHTBOUR_X = 3;

    public Pixel(Color color, int x, int y, Image image) {
        this.r = color.getRed();
        this.g = color.getGreen();
        this.b = color.getBlue();
        this.x = x;
        this.y = y;
        this.image = image;
    }

    public Color getMedian(int type, Pixel pixel) {
        Pixel pixels[] = null;
        PixelReader pixelReader = this.image.getPixelReader();

        if (type == NEIGHTBOUR3X3)
            pixels = this.findNeighbor3X3(pixel);
        if (type == NEIGHTBOUR_CROSS)
            pixels = this.findNeighborCross(pixel);
        if (type == NEIGHTBOUR_X)
            pixels = this.findNeighborX(pixel);

        double colorsR[] = new double[pixels.length];
        double colorsG[] = new double[pixels.length];
        double colorsB[] = new double[pixels.length];

        for (int i = 0; i < pixels.length; i++) {
            colorsR[i] = pixelReader.getColor(pixels[i].x, pixels[i].y).getRed();
            colorsG[i] = pixelReader.getColor(pixels[i].x, pixels[i].y).getGreen();
            colorsB[i] = pixelReader.getColor(pixels[i].x, pixels[i].y).getBlue();
        }

        Arrays.sort(colorsR);
        Arrays.sort(colorsG);
        Arrays.sort(colorsB);

        int position = Math.round(pixels.length / 2);

        return new Color(
                colorsR[position],
                colorsG[position],
                colorsB[position],
                pixelReader.getColor(this.x, this.y).getOpacity()
        );
    }

    private Pixel[] findNeighborX(Pixel pixel) {
        Pixel[] neighbor = new Pixel[5];
        PixelReader pixelReader = this.image.getPixelReader();

        Color color = pixelReader.getColor(pixel.x-1, pixel.y+1);
        neighbor[0] = new Pixel(color, pixel.x-1, pixel.y+1, this.image);

        color = pixelReader.getColor(pixel.x+1, pixel.y-1);
        neighbor[1] = new Pixel(color, pixel.x+1, pixel.y-1, this.image);

        color = pixelReader.getColor(pixel.x-1, pixel.y-1);
        neighbor[2] = new Pixel(color, pixel.x-1, pixel.y-1, this.image);

        color = pixelReader.getColor(pixel.x+1, pixel.y+1);
        neighbor[3] = new Pixel(color, pixel.x+1, pixel.y+1, this.image);

        neighbor[4] = pixel;

        return neighbor;
    }

    private Pixel[] findNeighbor3X3(Pixel pixel) {
        Pixel[] neighbor = new Pixel[9];
        PixelReader pixelReader = this.image.getPixelReader();

        Color color = pixelReader.getColor(pixel.x-1, pixel.y+1);
        neighbor[0] = new Pixel(color, pixel.x-1, pixel.y+1, this.image);

        color = pixelReader.getColor(pixel.x+1, pixel.y-1);
        neighbor[1] = new Pixel(color,pixel.x+1, pixel.y-1, this.image);

        color = pixelReader.getColor(pixel.x-1, pixel.y-1);
        neighbor[2] = new Pixel(color, pixel.x-1, pixel.y-1, this.image);

        color = pixelReader.getColor(pixel.x+1, pixel.y+1);
        neighbor[3] = new Pixel(color, pixel.x+1, pixel.y+1, this.image);

        color = pixelReader.getColor(pixel.x+1, pixel.y);
        neighbor[4] = new Pixel(color, pixel.x+1, pixel.y, this.image);

        color = pixelReader.getColor(pixel.x-1, pixel.y);
        neighbor[5] = new Pixel(color, pixel.x-1, pixel.y, this.image);

        color = pixelReader.getColor(pixel.x, pixel.y+1);
        neighbor[6] = new Pixel(color, pixel.x, pixel.y+1, this.image);

        color = pixelReader.getColor(pixel.x, pixel.y-1);
        neighbor[7] = new Pixel(color, pixel.x, pixel.y-1, this.image);

        neighbor[8] = pixel;

        return neighbor;
    }

    private Pixel[] findNeighborCross(Pixel pixel) {
        Pixel[] neighbor = new Pixel[5];
        PixelReader pixelReader = this.image.getPixelReader();

        Color color = pixelReader.getColor(pixel.x+1, pixel.y);
        neighbor[0] = new Pixel(color, pixel.x+1, pixel.y, this.image);

        color = pixelReader.getColor(pixel.x-1, pixel.y);
        neighbor[1] = new Pixel(color, pixel.x-1, pixel.y, this.image);

        color = pixelReader.getColor(pixel.x, pixel.y+1);
        neighbor[2] = new Pixel(color, pixel.x, pixel.y+1, this.image);

        color = pixelReader.getColor(pixel.x, pixel.y-1);
        neighbor[3] = new Pixel(color, pixel.x, pixel.y-1, this.image);

        neighbor[4] = pixel;

        return neighbor;

    }
}