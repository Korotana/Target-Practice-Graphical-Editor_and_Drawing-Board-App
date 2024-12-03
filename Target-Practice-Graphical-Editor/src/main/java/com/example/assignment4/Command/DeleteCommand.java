package com.example.assignment4.Command;

import com.example.assignment4.Blob;
import com.example.assignment4.Interface.TargetCommand;
import com.example.assignment4.Model.BlobModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DeleteCommand implements TargetCommand {
    Blob myBlob;
    BlobModel model;
    double blobX, blobY;
    DecimalFormat df;
    ArrayList<Blob> cutItems;

    public DeleteCommand(BlobModel newModel, Blob newB, double newX, double newY, ArrayList<Blob> cut) {
        model = newModel;
        myBlob = newB;
        blobX = newX;
        blobY = newY;
        this.cutItems = cut;
        df = new DecimalFormat("#.##");
    }

    public void doIt() {
        if (this.cutItems != null) {
            model.cutItems(this.cutItems);
        }else {
            model.deleteBlob(myBlob);}
    }

    public void undo() {
        if (this.cutItems!= null){
            model.pasteItems(cutItems);
        }
        else model.addBlob(myBlob);
    }

    public String toString() {
        return "Create: " + df.format(blobX) + ", " + df.format(blobY);
    }
}
