package util;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

public class HistogramWriter {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void setChart(Image image, BarChart<String, Number> chart) {
        if (image != null) {
            int[] histogram = getHistogram(image);

            XYChart.Series value = new XYChart.Series();

            for (int i = 0; i < histogram.length; i++) {
                value.getData().add(new XYChart.Data(String.valueOf(i), histogram[i]));
            }

            chart.getData().add(value);
        }
    }

    private static int[] getHistogram(Image image) {
        int[] length = new int[256];
        PixelReader pixelReader = image.getPixelReader();

        int w = (int) image.getWidth();
        int h = (int) image.getHeight();

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                length[(int)(pixelReader.getColor(i,j).getRed()*255)]++;
                length[(int)(pixelReader.getColor(i,j).getGreen()*255)]++;
                length[(int)(pixelReader.getColor(i,j).getBlue()*255)]++;
            }
        }

        return length;
    }
}
