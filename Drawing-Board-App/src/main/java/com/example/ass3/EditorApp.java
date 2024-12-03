package com.example.ass3;

import Views.MainUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;

public class EditorApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        MainUI main = new MainUI();
        Scene scene = new Scene(main, 1000, 700);
        stage.setTitle("State Machine Editor");
        stage.setScene(scene);
        stage.show();

        scene.setOnKeyPressed(e -> {if (e.getCode().equals(KeyCode.DELETE)) main.appController.handleDeleteKeyPressed();});

    }

    public static void main(String[] args) {
        launch();
    }
}