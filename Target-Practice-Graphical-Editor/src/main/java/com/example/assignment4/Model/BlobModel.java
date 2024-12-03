package com.example.assignment4.Model;

import com.example.assignment4.Blob;
import com.example.assignment4.Interface.BlobModelListener;
import com.example.assignment4.RubberBand;
import com.example.assignment4.View.BlobView;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class BlobModel {
    private final List<BlobModelListener> subscribers;
    private final LinkedHashMap<Integer,List<Blob>> blobsMap;
    public ArrayList<RubberBand> rubberBandArrayList;
    public double rubberTop, rubberLeft, rubberWidth, rubberHeight;

    public BlobModel() {
        subscribers = new ArrayList<>();
        blobsMap = new LinkedHashMap<>();
        rubberBandArrayList = new ArrayList<>();
    }

    public void addBlob(Blob b) {
        ArrayList<Blob> blob = new ArrayList<Blob>();
        blob.add(b);
        blobsMap.put(b.index, blob);
        notifySubscribers();
    }

    public Blob createBlob(double x, double y){
        Blob b = new Blob(x,y);

        ArrayList<Blob> blob = new ArrayList<Blob>();
        blob.add(b);
        if (!blobsMap.isEmpty()){
            AtomicInteger lastIndex = new AtomicInteger();
            blobsMap.forEach((index, bloblist) -> {
                for (Blob circle: bloblist) {
                    if (circle.index > lastIndex.get()) lastIndex.set(circle.index);
                }
            });
            b.index = lastIndex.get()+1;
        }
        blobsMap.put(b.index, blob);
        notifySubscribers();
        return b;
    }

    public void deleteBlob(Blob b) {
        AtomicInteger temp = new AtomicInteger();
        blobsMap.forEach((i,blob) -> {
            blob.remove(b);
            if (blob.size() == 0) temp.set(0);
        } );

        notifySubscribers();
    }

    public void moveBlob(Blob b, double dx, double dy) {
        b.move(dx,dy);
        notifySubscribers();
    }

    public void resizeBlob(Blob b,double nr){
        if (b.r > 5) b.r += nr;
        if (b.r < 5){b.r = 5.1;
            System.out.println("CANNOT DECREASE MORE THAN THAT");}
        notifySubscribers();
    }

    public void deleteMultipleBlob(ArrayList<Blob> rubberBandSelections) {
        rubberBandSelections.forEach(blob -> {
            deleteBlob(blob);
        });
    }

    public void addSubscriber(BlobView sub) {
        subscribers.add(sub);
    }

    private void notifySubscribers() {
        subscribers.forEach(s -> s.modelChanged());
    }

    public boolean hitBlob(double x, double y) {
        AtomicBoolean hit = new AtomicBoolean(false);

        blobsMap.forEach((index,blob) -> {
            for (Blob b : blob) {
                if (b.contains(x,y)) hit.set(true);
            }
        });
        return hit.get();
    }

    Blob hitBlob;
    public Blob whichHit(double x, double y) {
        blobsMap.forEach((index,blob) -> {
            for (Blob b : blob) {
                if (b.contains(x,y)) hitBlob = b;
            }
        });
        return hitBlob;
    }

    Integer selectionsNum = 1;
    public HashMap<Integer, ArrayList<Blob>> createRubberBand(double w, double h, boolean selectionComplete) {
        HashMap<Integer,ArrayList<Blob>> selections = new HashMap<>();
        RubberBand band = new RubberBand(rubberLeft, rubberTop, w-rubberLeft, h-rubberTop);
        rubberBandArrayList.add(band);
        if (selectionComplete){
            ArrayList<Blob> selectionList = new ArrayList<>();
            blobsMap.forEach((index,blobs) -> {
                blobs.forEach(b -> {
                    if (band.checkAllHit(b.x, b.y,b.r)) {
                        selectionList.add(b);
                    }
                });
            });
            if (selectionList.size() > 0) selections.put(selectionsNum,selectionList);selectionsNum+=1;
        }
        notifySubscribers();
        rubberBandArrayList.remove(band);
        return selections;
    }

    public HashMap<Integer, List<Blob>> getBlobsMap() {
        return blobsMap;
    }

    public void pasteItems(ArrayList<Blob> paste) {

        for (Blob blob: paste) {
            if (blobsMap.containsKey(blob.index))
            {
                blobsMap.get(blob.index).add(blob);
                notifySubscribers();
            }

            else this.addBlob(blob);
        }
    }

    public void cutItems(ArrayList<Blob> cutItems) {
        for (Blob b: cutItems) {
            if (blobsMap.get(b.index).contains(b)){
                if (blobsMap.get(b.index).size() == 1){
                    blobsMap.remove(b.index);
                }else {
                    blobsMap.get(b.index).remove(b);
                }
            }
        }
        notifySubscribers();
    }

    public void moveMultiBlobs(ArrayList<Blob> collection, double dx, double dy) {

        collection.forEach(blob -> {
            moveBlob(blob,dx,dy);
        });
    }

    public void resizeMultiBlobs(ArrayList<Blob> blobs, double dr) {
        blobs.forEach(blob -> {
            resizeBlob(blob,dr);
        });
    }
}
