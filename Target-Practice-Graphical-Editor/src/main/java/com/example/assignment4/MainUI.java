package com.example.assignment4;

import com.example.assignment4.Controller.BlobController;
import com.example.assignment4.Controller.TrainerController;
import com.example.assignment4.Interface.AppModelListener;
import com.example.assignment4.Model.BlobModel;
import com.example.assignment4.Model.InteractionModel;
import com.example.assignment4.View.BlobView;
import com.example.assignment4.View.ReportView;
import com.example.assignment4.View.TrainerView;
import javafx.scene.layout.StackPane;

public class MainUI extends StackPane implements AppModelListener {
    public BlobController controller;
    TrainerController trainerController;
    TrainerView trainerView;
    ReportView reportView;
    BlobView blobView;
    InteractionModel iModel;


    public MainUI() {

        BlobModel model = new BlobModel();
        controller = new BlobController();
        trainerController = new TrainerController();
        trainerView = new TrainerView();
        blobView = new BlobView();
        iModel = new InteractionModel();

        controller.setModel(model);
        blobView.setModel(model);
        controller.setIModel(iModel);
        blobView.setIModel(iModel);
        model.addSubscriber(blobView);
        iModel.addSubscriber(blobView);
        iModel.addAppSubscriber(this);
        iModel.addAppSubscriber(trainerView);

        trainerController.setModel(model);
        trainerController.setIModel(iModel);
        trainerView.setModel(model);
        trainerView.setIModel(iModel);

        trainerView.setController(trainerController);
        blobView.setController(controller);

        reportView = new ReportView();
        reportView.setIModel(iModel);

        this.getChildren().add(blobView);
    }

    @Override
    public void viewChanged(String view) {
        System.out.println(view);
        if (iModel.getMode().equals("trainer")) {
            this.getChildren().clear();
            this.getChildren().addAll(trainerView);
        }else if (iModel.getMode().equals("edit")){
            this.getChildren().clear();
            this.getChildren().addAll(blobView);
        }else {
            this.getChildren().clear();
            reportView.fillChart();
            this.getChildren().addAll(reportView);
        }

    }
}
