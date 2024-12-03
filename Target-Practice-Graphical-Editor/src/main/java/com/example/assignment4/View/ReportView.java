package com.example.assignment4.View;

import com.example.assignment4.Model.InteractionModel;
import com.example.assignment4.Trial;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;

public class ReportView extends StackPane {
    InteractionModel imodel;
    XYChart.Series series2;
    final ScatterChart<Number,Number> sc;

    public ReportView() {
        final NumberAxis xAxis = new NumberAxis(0, 10, 1);
        final NumberAxis yAxis = new NumberAxis(-100, 500, 100);
        yAxis.setAutoRanging(true);
        xAxis.setAutoRanging(true);
        sc = new ScatterChart<Number,Number>(xAxis,yAxis);
        xAxis.setLabel("ID");
        yAxis.setLabel("MT");
        sc.setTitle("Targeting Performance");

        series2 = new XYChart.Series();
        series2.setName("Trials");

        sc.getData().addAll(series2);
        this.getChildren().add(sc);
    }


    public void setIModel(InteractionModel iModel) {
        this.imodel = iModel;
    }

    public void fillChart(){
        for (Trial t: imodel.getTrials()) {
            series2.getData().add(new XYChart.Data(t.getEndTime(),t.getIndexDiff()));
        }
    }

}
