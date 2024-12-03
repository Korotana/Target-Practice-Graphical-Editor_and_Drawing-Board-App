package com.example.assignment4.Command;

import com.example.assignment4.Blob;
import com.example.assignment4.Interface.TargetCommand;
import com.example.assignment4.Model.BlobModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MoveCommand implements TargetCommand {
    Blob myBlob;
    BlobModel model;
    double dx, dy;
    DecimalFormat df;
    ArrayList<Blob> collection;

    public MoveCommand(BlobModel newModel, Blob newBox, double newDX, double newDY, ArrayList<Blob> collection) {
        model = newModel;
        myBlob = newBox;
        dx = newDX;
        dy = newDY;
        this.collection = collection;
        df = new DecimalFormat("#.##");
    }

    public void doIt() {
        if (collection != null){
            model.moveMultiBlobs(collection, dx, dy);
        }else {
        model.moveBlob(myBlob, dx, dy);
        }
    }

    public void undo() {
        if (collection != null){
            model.moveMultiBlobs(collection,dx * -1, dy * -1);
        }else {
        model.moveBlob(myBlob, dx * -1, dy * -1);}
    }

    public String toString() {
        return "Move: " + df.format(dx) + "," + df.format(dy);
    }
}