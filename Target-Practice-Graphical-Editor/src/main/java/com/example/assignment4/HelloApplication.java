package com.example.assignment4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    boolean zPressed = false;
    boolean ctrlPressed = false;
    boolean rPressed = false;
    @Override
    public void start(Stage stage) throws IOException {
        MainUI uiRoot = new MainUI();
//        uiRoot.iModel.addAppSubscriber(uiRoot);
        Scene scene = new Scene(uiRoot);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.SHIFT) uiRoot.controller.isShiftDown = true;
            if (keyEvent.getCode() == KeyCode.DELETE) uiRoot.controller.handleDeleteKeyPressed();
            if (keyEvent.getCode() == KeyCode.Z) zPressed = true;
            if (keyEvent.getCode() == KeyCode.CONTROL) {ctrlPressed = true; uiRoot.controller.setCtrlDown(true);}
            if (keyEvent.getCode() == KeyCode.R) rPressed = true;
            if (keyEvent.getCode() == KeyCode.C) uiRoot.controller.setCDown(true);
            if (keyEvent.getCode() == KeyCode.X) uiRoot.controller.setXDown(true);
            if (keyEvent.getCode() == KeyCode.V) uiRoot.controller.setVDown(true);
            if (keyEvent.getCode() == KeyCode.T) uiRoot.controller.setTDown(true);
            if (keyEvent.getCode() == KeyCode.E) uiRoot.controller.setEDown(true);
            if (zPressed && ctrlPressed) uiRoot.controller.handleUndo();
            if (rPressed && ctrlPressed) uiRoot.controller.handleRedo();
        });
        scene.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.SHIFT) uiRoot.controller.isShiftDown = false;
            if (keyEvent.getCode() == KeyCode.Z) zPressed = false;
            if (keyEvent.getCode() == KeyCode.CONTROL) {ctrlPressed = false; uiRoot.controller.setCtrlDown(false);}
            if (keyEvent.getCode() == KeyCode.R) rPressed = false;
            if (keyEvent.getCode() == KeyCode.C) uiRoot.controller.setCDown(false);
            if (keyEvent.getCode() == KeyCode.X) uiRoot.controller.setXDown(false);
            if (keyEvent.getCode() == KeyCode.V) uiRoot.controller.setVDown(false);
            if (keyEvent.getCode() == KeyCode.T) uiRoot.controller.setTDown(false);
            if (keyEvent.getCode() == KeyCode.E) uiRoot.controller.setEDown(false);
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}