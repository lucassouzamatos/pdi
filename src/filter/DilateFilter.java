package filter;

import javafx.scene.image.Image;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import util.MatHandler;

public class DilateFilter {
    public static Image Handle(Image image) {
        try {
            Mat frame = MatHandler.ByImage(image);
            Mat dilate = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(8, 8));

            Imgproc.morphologyEx(frame, frame, Imgproc.MORPH_OPEN, dilate);
            Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGRA2BGR);
            return MatHandler.AsImage(frame);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
