package sample;

import filter.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import util.*;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Controller {
    @FXML ImageView imageViewOne;
    @FXML ImageView imageViewTwo;
    @FXML ImageView imageViewThree;

    @FXML Label redLabel;
    @FXML Label greenLabel;
    @FXML Label blueLabel;

    @FXML TextField fieldRed;
    @FXML TextField fieldGreen;
    @FXML TextField fieldBlue;

    @FXML Slider thresholdSlider;
    @FXML Slider thresholdCanny;

    @FXML Slider addSliderOne;
    @FXML Slider addSliderTwo;

    @FXML CheckBox marker;
    @FXML ColorPicker colorMarker;

    private Image imageOne;
    private Image imageTwo;

    @FXML
    public void handleImageOneAction() {
        imageOne = ImageHandler.open(imageViewOne, imageOne);
    }

    @FXML
    public void handleImageTwoAction() {
        imageTwo = ImageHandler.open(imageViewTwo, imageTwo);
    }

    @FXML
    public void mouseHover(MouseEvent mouseEvent) {
        ImageView imageView = (ImageView) mouseEvent.getTarget();
        if (imageView.getImage() != null) {
            setColorStatus(imageView.getImage(), (int) mouseEvent.getX(), (int) mouseEvent.getY());
        }
    }

    @FXML
    public void gray() {
        Filter.apply(GrayScaleFilter.Arithmetic(imageOne), imageViewThree);
    }

    @FXML
    public void noiseX() {
        Filter.apply(NoiseFilter.RemoveNoise(imageOne, Pixel.NEIGHTBOUR_X), imageViewThree);
    }

    @FXML
    public void noise3x3() {
        Filter.apply(NoiseFilter.RemoveNoise(imageOne, Pixel.NEIGHTBOUR3X3), imageViewThree);
    }

    @FXML
    public void noiseCross() {
        Filter.apply(NoiseFilter.RemoveNoise(imageOne, Pixel.NEIGHTBOUR_CROSS), imageViewThree);
    }

    @FXML
    public void add() {
        Filter.apply(AddFilter.Add(imageOne, imageTwo, addSliderOne.getValue(), addSliderTwo.getValue()), imageViewThree);
    }

    private Drag drag = new Drag();

    @FXML
    public void applySobel() {
        Filter.apply(SobelFilter.Handle(imageOne), imageViewThree);
    }

    @FXML
    public void applyDilate() {
        Filter.apply(DilateFilter.Handle(imageViewOne.getImage()), imageViewThree);
    }

    @FXML
    public void applyErode() {
        Filter.apply(ErodeFilter.Handle(imageViewOne.getImage()), imageViewThree);
    }

    @FXML
    public void applyCanny() {
        Filter.apply(CannyFilter.Handle(imageOne, thresholdCanny), imageViewThree);
    }

    @FXML
    public void openHistogramCameraModal(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("video.fxml"));
            Parent root = loader.load();

            stage.setScene(new Scene(root));
            stage.setTitle("Histograma por vídeo");
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.setMaximized(true);
            stage.show();

            HistogramVideoController controller = (HistogramVideoController) loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clickImage(MouseEvent mouseEvent) {
        ImageView imageView = (ImageView) mouseEvent.getTarget();

        if (imageView.getImage() != null) {
            Click click = new Click((int) mouseEvent.getX(), (int) mouseEvent.getY());
            this.drag.setStartPress(click);
        }
    }


    @FXML
    public void releaseImage(MouseEvent mouseEvent) {
        ImageView imageView = (ImageView) mouseEvent.getTarget();

        if (imageView.getImage() != null) {
            Click click = new Click((int) mouseEvent.getX(), (int) mouseEvent.getY());
            this.drag.setFinalPress(click);

            if (marker.isSelected()) {
                ImageMarker.Mark(this.drag, imageView, 5, colorMarker.getValue());
            }
        }
    }

    @FXML
    public void subtract() {
        Filter.apply(SubtractFilter.Subtract(imageOne, imageTwo), imageViewThree);
    }

    @FXML
    public void weightedGray() {
        try {
            int r = Integer.parseInt(fieldRed.getText());
            int g = Integer.parseInt(fieldGreen.getText());
            int b = Integer.parseInt(fieldBlue.getText());

            if ((r + g + b) > 100 || r == 0 || g == 0 || b == 0) {
                AlertEmitter.emit(Message.RGB_SCALE_TOTAL, Alert.AlertType.ERROR);
            } else {
                Filter.apply(GrayScaleFilter.Weighted(imageOne, r, g, b), imageViewThree);
            }
        } catch (Exception e) {
            AlertEmitter.emit(Message.RGB_ERROR, Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void handleThreshold() {
        Filter.apply(ThresholdFilter.Threshold(imageOne, thresholdSlider.getValue() / 255), imageViewThree);
    };

    @FXML
    public void negative() {
        Filter.apply(NegativeFilter.Negative(imageOne), imageViewThree);
    }

    private void setColorStatus(Image image, int x, int y) {
        try {
            Color color = image.getPixelReader().getColor(x-1, y-1);

            redLabel.setText("R: " + (int)(color.getRed()*255));
            greenLabel.setText("G: " + (int)(color.getGreen()*255));
            blueLabel.setText("B: " + (int)(color.getBlue()*255));
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openHistogramModal(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("histogram.fxml"));
            Parent root = loader.load();

            stage.setScene(new Scene(root));
            stage.setTitle("Histograma");
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.setMaximized(true);
            stage.show();

            HistogramController controller = (HistogramController) loader.getController();

            HistogramWriter.setChart(imageViewOne.getImage(), controller.imageOne);
            HistogramWriter.setChart(imageViewTwo.getImage(), controller.imageTwo);
            HistogramWriter.setChart(imageViewThree.getImage(), controller.imageThree);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void equalize() {
        Filter.apply(HistogramWriter.equalize(imageOne, imageViewThree, false), imageViewThree);
    }

    @FXML
    public void equalizeValid() {
        Filter.apply(HistogramWriter.equalize(imageOne, imageViewThree, true), imageViewThree);
    }
}
