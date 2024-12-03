package com.example.assignment4.Controller;

import com.example.assignment4.Blob;
import com.example.assignment4.Model.BlobModel;
import com.example.assignment4.Model.InteractionModel;
import javafx.scene.input.MouseEvent;

public class TrainerController {

    BlobModel model;
    InteractionModel imodel;

    public TrainerController() {
    }

    public void setModel(BlobModel model) {
        this.model = model;
    }

    public void setIModel(InteractionModel iModel) {
        this.imodel = iModel;
    }

    public boolean handleReleased(MouseEvent mouseEvent, Integer trialNum) {
        Blob hit = model.whichHit(mouseEvent.getX(),mouseEvent.getY());
//        imodel.updateCreateOrder();
        return imodel.checkTarget(hit,trialNum);
    }
}
