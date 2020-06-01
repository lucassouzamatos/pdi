package filter;

import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import util.MatHandler;

public class CannyFilter {
    public static Image Handle(Image image, Slider slider) {
        try {
            Mat grayImage = new Mat();
            Mat detectedEdges = new Mat();

            Mat frame = MatHandler.ByImage(image);

            Imgproc.cvtColor(frame, grayImage, Imgproc.COLOR_BGR2GRAY);
            Imgproc.blur(grayImage, detectedEdges, new Size(3, 3));

            Imgproc.Canny(detectedEdges, detectedEdges, slider.getValue(), slider.getValue() * 3);

            return MatHandler.AsImage(detectedEdges);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
