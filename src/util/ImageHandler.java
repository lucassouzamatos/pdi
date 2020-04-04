package util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;

public class ImageHandler {
    public static Image open(ImageView imageView, Image image) {
        File file = select();
        if (file != null) {
            image = new Image(file.toURI().toString());
            imageView.setImage(image);
            imageView.setFitHeight(image.getHeight());
            imageView.setFitWidth(image.getWidth());
        }
        return image;
    }

    public static File select() {
        FileChooser fileChooser = new FileChooser();
        return fileChooser.showOpenDialog(null);
    }
}
