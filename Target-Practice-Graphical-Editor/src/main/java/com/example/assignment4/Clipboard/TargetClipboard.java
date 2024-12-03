package com.example.assignment4.Clipboard;


import com.example.assignment4.Blob;

import java.util.ArrayList;
import java.util.HashMap;

public class TargetClipboard {

//    HashMap<Integer, ArrayList<Blob>> blobMapClipBoard;
    ArrayList<Blob> blobs;

    public TargetClipboard() {
        this.blobs = new ArrayList<>();
    }

    public void copy(ArrayList<Blob> selected){
        blobs.clear();
        selected.forEach(blob -> {
            Blob copyBlob = new Blob(blob.x,blob.y);
            copyBlob.r = blob.r;
            copyBlob.index = blob.index;
            blobs.add(copyBlob);});
    }

    public ArrayList<Blob> getBlobs() {
        ArrayList<Blob> copy = new ArrayList<>();
        blobs.forEach(blob -> {
            Blob copyBlob = new Blob(blob.x,blob.y);
            copyBlob.r = blob.r;
            copyBlob.index = blob.index;
            copy.add(copyBlob);});

        return copy;
    }
}
