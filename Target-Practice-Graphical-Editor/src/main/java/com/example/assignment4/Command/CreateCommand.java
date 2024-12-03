package com.example.assignment4.Command;

import com.example.assignment4.Blob;
import com.example.assignment4.Interface.TargetCommand;
import com.example.assignment4.Model.BlobModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CreateCommand implements TargetCommand {
    public Blob myBlob;
    BlobModel model;
    double blobX, blobY;
    DecimalFormat df;


    ArrayList<Blob> paste;

    public CreateCommand(BlobModel newModel, double newX, double newY, ArrayList<Blob> paste) {
        model = newModel;
        myBlob = null;
        blobX = newX;
        blobY = newY;
        this.paste = paste;
        df = new DecimalFormat("#.##");
    }

    public void doIt() {
        if (paste != null){
            model.pasteItems(paste);
        }
        else {
        if (myBlob == null) {
            myBlob = model.createBlob(blobX, blobY);
        } else {
            model.addBlob(myBlob);
        }
        }
    }

    public void undo() {
        if (paste != null) {model.cutItems(paste);}
        else model.deleteBlob(myBlob);
    }


    public ArrayList<Blob> getPaste() {
        return paste;
    }

    public String toString() {
        return "Create: " + df.format(blobX) + ", " + df.format(blobY);
    }
}
