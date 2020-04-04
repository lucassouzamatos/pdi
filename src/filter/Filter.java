package filter;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import util.AlertEmitter;
import util.Message;

public class Filter {
    public static void apply(Image image, ImageView imageView) {
        try {
            int width = (int) image.getWidth();
            int height = (int) image.getHeight();
            imageView.setImage(image);
            imageView.setFitHeight(height);
            imageView.setFitWidth(width);
        } catch (Exception e) {
            AlertEmitter.emit(Message.FILTER_ERROR, AlertType.ERROR);
        }
    }
}
