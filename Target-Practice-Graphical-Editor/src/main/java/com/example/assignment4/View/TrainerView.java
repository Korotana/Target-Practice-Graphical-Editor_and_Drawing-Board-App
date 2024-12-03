package com.example.assignment4.View;

import com.example.assignment4.Blob;
import com.example.assignment4.Controller.TrainerController;
import com.example.assignment4.Interface.AppModelListener;
import com.example.assignment4.Model.BlobModel;
import com.example.assignment4.Model.InteractionModel;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class TrainerView extends StackPane implements AppModelListener {

    BlobModel model;
    InteractionModel imodel;
    Integer trialNum = 0;
    Blob current;

    GraphicsContext gc;
    Canvas myCanvas;

    public double width=800, height=800;

    public TrainerView() {
        myCanvas = new Canvas(width,height);
        gc = myCanvas.getGraphicsContext2D();
        this.setStyle("-fx-background-color: #f1d92c");
        this.getChildren().add(myCanvas);
    }

    public void draw(Blob b){

        gc.clearRect(0,0,myCanvas.getWidth(),myCanvas.getHeight());
        gc.setFill(Color.RED);
        gc.fillOval(b.x-b.r,b.y-b.r,b.r*2,b.r*2);

    }

    public void setModel(BlobModel model) {
        this.model = model;
    }

    public void setIModel(InteractionModel iModel) {
        this.imodel = iModel;
    }

    @Override
    public void viewChanged(String draw) {
        if (imodel.getMode().equals("trainer")){
            if (draw.equals("draw")){
                draw(imodel.getTrials().get(trialNum).getBlob());
                trialNum++;
            }else if (draw.equals("viewchanged")){
                trialNum = 0;
//                draw(imodel.getTrials().get(trialNum).getBlob());
            }
        }
    }

    public void setController(TrainerController trainerController) {
        myCanvas.setOnMouseReleased(mouseEvent -> {
            if (trainerController.handleReleased(mouseEvent,trialNum)){
//                trialNum++;
            }
        });
    }
}
