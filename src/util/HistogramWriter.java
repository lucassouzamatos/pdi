package util;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.image.*;
import javafx.scene.paint.Color;

public class HistogramWriter {

    final static int RED = 1;
    final static int GREEN = 2;
    final static int BLUE = 3;
    final static int ALL = 4;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void setChart(Image image, BarChart<String, Number> chart) {
        if (image != null) {
            int[] histogram = getHistogram(image, ALL);

            XYChart.Series value = new XYChart.Series();

            for (int i = 0; i < histogram.length; i++) {
                value.getData().add(new XYChart.Data(String.valueOf(i), histogram[i]));
            }

            chart.getData().add(value);
        }
    }

    private static int[] getHistogram(Image image, int channel) {
        int[] length = new int[256];
        PixelReader pixelReader = image.getPixelReader();

        int w = (int) image.getWidth();
        int h = (int) image.getHeight();

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {

                switch (channel) {
                    case RED:
                        length[(int)(pixelReader.getColor(i,j).getRed()*255)]++;
                        break;

                    case GREEN:
                        length[(int)(pixelReader.getColor(i,j).getGreen()*255)]++;
                        break;

                    case BLUE:
                        length[(int)(pixelReader.getColor(i,j).getBlue()*255)]++;
                        break;

                    case ALL:
                        length[(int)(pixelReader.getColor(i,j).getRed()*255)]++;
                        length[(int)(pixelReader.getColor(i,j).getGreen()*255)]++;
                        length[(int)(pixelReader.getColor(i,j).getBlue()*255)]++;
                        break;
                }


            }
        }
        return length;
    }

    public static Image equalize(Image image, ImageView imageView, boolean onlyValid) {
        try {
            int w = (int) image.getWidth();
            int h = (int) image.getHeight();

            WritableImage writableImage = new WritableImage(w, h);
            PixelWriter pixelWriter = writableImage.getPixelWriter();
            PixelReader pixelReader = image.getPixelReader();

            int[] r = getHistogram(image, RED);
            int[] g = getHistogram(image, GREEN);
            int[] b = getHistogram(image, BLUE);

            int[] rAccumulated = getHistogramAccumulated(r);
            int[] gAccumulated = getHistogramAccumulated(g);
            int[] bAccumulated = getHistogramAccumulated(b);

            int rValid = getTotalValidColors(r);
            int gValid = getTotalValidColors(g);
            int bValid = getTotalValidColors(b);

            double minR = min(r);
            double minG = min(g);
            double minB = min(b);

            if (!onlyValid) {
                rValid = 255;
                gValid = 255;
                bValid = 255;

                minR = 0;
                minG = 0;
                minB = 0;
            }

            double n = w*h;

            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    Color oldColor = pixelReader.getColor(i, j);

                    double rAccumulatedPixel = rAccumulated[(int) (oldColor.getRed()*255)];
                    double gAccumulatedPixel = gAccumulated[(int) (oldColor.getGreen()*255)];
                    double bAccumulatedPixel = bAccumulated[(int) (oldColor.getBlue()*255)];

                    double rCurrentPixel = ((rValid-1)/n) * rAccumulatedPixel;
                    double gCurrentPixel = ((gValid-1)/n) * gAccumulatedPixel;
                    double bCurrentPixel = ((bValid-1)/n) * bAccumulatedPixel;

                    double currentR = (minR+rCurrentPixel)/255;
                    double currentG = (minG+gCurrentPixel)/255;
                    double currentB = (minB+bCurrentPixel)/255;

                    Color currentColor = new Color(currentR, currentG, currentB, oldColor.getOpacity());
                    pixelWriter.setColor(i, j, currentColor);
                }
            }

            return writableImage;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static int[] getHistogramAccumulated(int[] histogram) {
        int[] accumulated = new int [256];
        int count = 0;

        for (int i = 0; i < histogram.length; i++) {
            accumulated[i] += (count += histogram[i]);
        }

        return accumulated;
    }

    private static int getTotalValidColors(int[] total) {
        int count = 0;

        for (int i = 0; i < total.length; i++) {
            if (total[i] > 0) {
                count++;
            }
        }

        return count;
    }

    private static int min(int[] histogram) {
        for (int i = 0; i < histogram.length; i++) {
            if (histogram[i] > 0) {
                return i;
            }
        }

        return 0;
    }
}
