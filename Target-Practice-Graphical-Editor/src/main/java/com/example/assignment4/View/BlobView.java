package com.example.assignment4.View;

import com.example.assignment4.Controller.BlobController;
import com.example.assignment4.Interface.BlobModelListener;
import com.example.assignment4.Interface.IModelListener;
import com.example.assignment4.Model.BlobModel;
import com.example.assignment4.Model.InteractionModel;
import com.example.assignment4.RubberBand;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class BlobView extends StackPane implements BlobModelListener, IModelListener {
    GraphicsContext gc;
    Canvas myCanvas;
    BlobModel model;
    InteractionModel iModel;
    public double width=800, height=800;

    public BlobView() {
        myCanvas = new Canvas(width,height);
        gc = myCanvas.getGraphicsContext2D();
        this.setStyle("-fx-background-color: #acffff");
        this.getChildren().add(myCanvas);
    }

    private void draw() {
        gc.clearRect(0,0,myCanvas.getWidth(),myCanvas.getHeight());

        model.getBlobsMap().forEach((index,blob) -> {

            blob.forEach(b -> {
                if (b == iModel.getSelected()) {
                    gc.setFill(Color.TOMATO);
                } else {
                    gc.setFill(Color.BEIGE);
                }

                if (iModel.getRubberBandSelections() != null){

                iModel.getRubberBandSelections().forEach((num, blobs) -> {
                    if (blobs.contains(b)) {
                        gc.setFill(Color.TOMATO);
                    }else {
                        blobs.forEach(rubberBlob -> {
                            if (rubberBlob.index == b.index && iModel.getRubberBandSelections().containsValue(b)) {
                                gc.setFill(Color.TOMATO);
                            }
                        });
                    }
                });
            }
            gc.fillOval(b.x-b.r,b.y-b.r,b.r*2,b.r*2);
            gc.setFill(Color.BLACK);
            gc.fillText(Integer.toString(b.index),b.x-b.r + b.r,b.y-b.r + b.r);

            });
        });

        model.rubberBandArrayList.forEach(band -> {
            gc.setStroke(Color.GREEN);
            strokeRubberBand(band);
        });

    }

    private void strokeRubberBand(RubberBand band) {

            gc.strokeLine(band.left,band.top,band.left+ band.width,band.top);  // horizontal line with start points
            gc.strokeLine(band.left,band.top,band.left,band.top+band.height); // vertical line with start points
            gc.strokeLine(band.left,band.top+band.height,band.left+ band.width,band.top+band.height);
            gc.strokeLine(band.left + band.width, band.top,band.left + band.width,band.top + band.height);

    }

    public void setModel(BlobModel newModel) {
        model = newModel;
    }

    public void setIModel(InteractionModel newIModel) {
        iModel = newIModel;
    }

    @Override
    public void modelChanged() {
        draw();
    }

    @Override
    public void iModelChanged() {
        draw();
    }

    public void setController(BlobController controller) {

        myCanvas.setOnMousePressed(controller::handlePressed);
        myCanvas.setOnMouseDragged(controller::handleDragged);
        myCanvas.setOnMouseReleased(controller::handleReleased);
    }
}
