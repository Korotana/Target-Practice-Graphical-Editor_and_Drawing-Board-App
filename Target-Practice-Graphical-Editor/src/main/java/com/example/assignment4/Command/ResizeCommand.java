package com.example.assignment4.Command;

import com.example.assignment4.Blob;
import com.example.assignment4.Interface.TargetCommand;
import com.example.assignment4.Model.BlobModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ResizeCommand implements TargetCommand {
    Blob myBlob;
    BlobModel model;
    double dr;
    DecimalFormat df;
    ArrayList<Blob> blobs = new ArrayList<>();

    public ResizeCommand(BlobModel newModel, Blob newBlob, double newR, ArrayList<Blob> blobs) {
        model = newModel;
        myBlob = newBlob;
        dr = newR;
        this.blobs = blobs;
        df = new DecimalFormat("#.##");
    }

    public void doIt() {
        if (blobs != null){
            model.resizeMultiBlobs(blobs,dr);
        }else {
            model.resizeBlob(myBlob, dr);}
    }

    public void undo() {
        if (blobs != null){
            model.resizeMultiBlobs(blobs,dr*-1);
        }else {
            model.resizeBlob(myBlob, dr*-1);}
    }

    public double getDr() {
        return dr;
    }

    public void setDr(double dr) {
        this.dr = dr;
    }

//    public String toString() {
//        return "Move: " + df.format(dx) + "," + df.format(dy);
//    }
}