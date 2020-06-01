package filter;

import javafx.scene.image.Image;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import util.MatHandler;

public class SobelFilter {
    public static Image Handle(Image image) {
        try {
            Mat frame = MatHandler.ByImage(image);

            Mat grayMat = new Mat();
            Mat sobel = new Mat();

            Mat grad_x = new Mat();
            Mat abs_grad_x = new Mat();

            Mat grad_y = new Mat();
            Mat abs_grad_y = new Mat();

            Imgproc.cvtColor(frame, grayMat, Imgproc.COLOR_BGR2GRAY);
            Imgproc.Sobel(grayMat, grad_x, CvType.CV_16S, 1, 0, 3, 1, 0);
            Imgproc.Sobel(grayMat, grad_y, CvType.CV_16S, 0, 1, 3, 1, 0);
            Core.convertScaleAbs(grad_x, abs_grad_x);
            Core.convertScaleAbs(grad_y, abs_grad_y);
            Core.addWeighted(abs_grad_x, 0.5, abs_grad_y, 0.5, 1, sobel);

            return MatHandler.AsImage(sobel);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
