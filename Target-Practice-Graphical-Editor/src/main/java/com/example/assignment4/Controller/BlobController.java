package com.example.assignment4.Controller;

import com.example.assignment4.Blob;
import com.example.assignment4.Command.CreateCommand;
import com.example.assignment4.Command.DeleteCommand;
import com.example.assignment4.Command.MoveCommand;
import com.example.assignment4.Command.ResizeCommand;
import com.example.assignment4.Model.BlobModel;
import com.example.assignment4.Model.InteractionModel;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;


public class BlobController {
    BlobModel model;
    InteractionModel iModel;
    double prevX,prevY;
    double dX,dY,dR;
    double dragStartX, dragStartY, dragEndResize, dragEndResizeToMove, dragEndResizeY, dragEndMoveX, dragEndMoveY;
    ResizeCommand rc;
    MoveCommand mc;

    Boolean isCtrlDown = false;
    boolean isCDown = false;
    boolean isVDown = false;
    boolean isXDown = false;
    boolean isTDown = false;
    boolean isEDown = false;
    public boolean isShiftDown = false;

    int undoStackSize = 0;

    ArrayList<Blob> mult = new ArrayList<>();

    enum State {READY,PREPARE_CREATE, DRAGGING}
    State currentState = State.READY;

    public BlobController() {
    }

    public void setModel(BlobModel newModel) {
        model = newModel;
    }

    public void setIModel(InteractionModel newIModel) {
        iModel = newIModel;
    }

    public void handlePressed(MouseEvent event) {
        switch (currentState) {
            case READY -> {
                if (model.hitBlob(event.getX(),event.getY())) {
                    Blob b = model.whichHit(event.getX(),event.getY());
                    if (iModel.getRubberBandSelections() != null) {
                        if (iModel.checkRubberBandHit(b)) {undoStackSize = iModel.getUndoStack().size();}
                        else if (b != null){
                            if (isCtrlDown){
                                if (iModel.getSelected() != null){
                                iModel.getRubberBandSelections().forEach((integer, blobs) -> {
                                    blobs.add(b);
                                });
                            }}
                            else iModel.setSelected(b);}
                    }
                    else{iModel.setSelected(b);}
                    prevX = event.getX();
                    prevY = event.getY();
                    dragStartX = event.getX();
                    dragStartY = event.getY();
                    currentState = State.DRAGGING;
                } else {
                    model.rubberLeft = event.getX();
                    model.rubberTop = event.getY();
                    currentState = State.PREPARE_CREATE;
                }
            }
        }
    }

    public void handleDragged(MouseEvent event) {
        dX = event.getX() - prevX;
        dY = event.getY() - prevY;
        prevX = event.getX();
        prevY = event.getY();
        switch (currentState) {
            case PREPARE_CREATE -> {
                if (isShiftDown) currentState = State.READY;
                model.createRubberBand(event.getX(), event.getY(), false);
            }
            case DRAGGING -> {

                if (isShiftDown) {

                    if (iModel.getRubberBandSelections() != null) {
                        AtomicReference<ArrayList<Blob>> temp = new AtomicReference<>(new ArrayList<>());
                        iModel.getRubberBandSelections().forEach((integer, blobs) -> temp.set(blobs));
                        rc = new ResizeCommand(model, iModel.getSelected(), event.getX(),temp.get());
//                        resizeMult = temp.get();
                        model.resizeMultiBlobs(temp.get(), dX);
                        if (temp.get().get(0).r > 5.1){
                            dR = event.getX() - dragStartX;}}

                    else {
                        model.resizeBlob(iModel.getSelected(), dX);
                        rc = new ResizeCommand(model, iModel.getSelected(), event.getX(), null);
                        if (iModel.getSelected().r > 5.1) {
                        dR = event.getX() - dragStartX;}
                    }

                    dragEndResize = event.getX();
                    dragEndResizeY = event.getY();}

                else {

                    if (iModel.getSelected() != null) model.moveBlob(iModel.getSelected(), dX, dY);
                    else if (iModel.getSelected() == null) {
                        AtomicReference<ArrayList<Blob>> temp = new AtomicReference<>(new ArrayList<>());
                        iModel.getRubberBandSelections().forEach((integer, blobs) -> temp.set(blobs));
                        if (iModel.getRubberBandSelections() != null) {
                            mult = temp.get();
                            model.moveMultiBlobs(temp.get(),dX,dY);}
//                        MoveCommand mc = new MoveCommand(model, iModel.getSelected(), event.getX(), t, temp.get());
//                        iModel.addToUndoStack(mc);
                    }

                    mc = new MoveCommand(model, iModel.getSelected(), event.getX(), event.getY(),null);
                    dragEndMoveX = event.getX();
                    dragEndMoveY = event.getY();

                }

                dragEndResizeToMove = event.getX();

            }
        }
    }

    public void handleReleased(MouseEvent event) {
        switch (currentState) {
            case PREPARE_CREATE -> {
                if (isShiftDown) {
                    CreateCommand cc = new CreateCommand(model,event.getX(),event.getY(), null);
                    cc.doIt();
                    iModel.addToUndoStack(cc);
                    iModel.setSelected(model.whichHit(event.getX(),event.getY()));
                }else {

                    iModel.setRubberBandSelections(model.createRubberBand(event.getX(), event.getY(), true));
                    iModel.unselect();
                }
                currentState = State.READY;
            }


            case DRAGGING -> {
                double totalDX = event.getX() - dragStartX;
                double totalDY = event.getY() - dragStartY;
                if (rc != null) {

                    if (dragEndResize == dragEndResizeToMove && dR != 0) {
                        rc.setDr(dR);
                        iModel.addToUndoStack(rc);
                        rc = null;
                        dR = 0;}

                    else if (dragEndResize != dragEndResizeToMove && Math.abs(dragEndResizeToMove - event.getX()) < 1.1){
                            //When doing resize and shift is released to go into move state
                            System.out.println("DID RESIZE TO MOVE SIMULTANEOUSLY");
                            rc.setDr(dragEndResize - dragStartX);
                            iModel.addToUndoStack(rc);
                            totalDX = event.getX() - dragEndResize;
                            totalDY = event.getY() - dragEndResizeY;
                            MoveCommand mc = new MoveCommand(model, iModel.getSelected(), totalDX, totalDY,mult);
                            iModel.addToUndoStack(mc);}

                    else if (dragEndMoveX != dragEndResizeToMove && Math.abs(dragEndResizeToMove - event.getX()) < 1.1){
                        System.out.println("DID MOVE TO RESIZE SIMULTANEOUSLY");
                        totalDX = dragEndMoveX - dragStartX;
                        totalDY = dragEndMoveY - dragStartY;
                        MoveCommand mc = new MoveCommand(model, iModel.getSelected(), totalDX, totalDY,mult);
                        iModel.addToUndoStack(mc);
                        rc.setDr(dragEndResize-dragEndMoveX);
                        iModel.addToUndoStack(rc);
                    }
                } else {
                        if (iModel.getRubberBandSelections() != null){
                            if (dragStartX == event.getX()){
                                iModel.setSelected(model.whichHit(event.getX(),event.getY()));}
                            else {AtomicReference<ArrayList<Blob>> temp = new AtomicReference<>(new ArrayList<>());
                            iModel.getRubberBandSelections().forEach((integer, blobs) -> temp.set(blobs));
                            MoveCommand mc = new MoveCommand(model, iModel.getSelected(), totalDX, totalDY, temp.get());
                            iModel.addToUndoStack(mc);}
                        }
                        else {
                            if (dragStartX == event.getX() ){
                                if (isCtrlDown){
                                    if (iModel.getSelected() != null){
                                        HashMap<Integer, ArrayList<Blob>> addtoBand = new HashMap<Integer, ArrayList<Blob>>();
                                        ArrayList<Blob> temp = new ArrayList<>();
                                        temp.add(iModel.getSelected());
                                        iModel.unselect();
                                        temp.add(model.whichHit(event.getX(),event.getY()));
                                        addtoBand.put(0,temp);
                                        iModel.setRubberBandSelections(addtoBand);
                                        System.out.println(iModel.getRubberBandSelections());
                                    }
                                    else if (iModel.getRubberBandSelections() != null){
                                    }
                                    }
                            }
                            else {
                            MoveCommand mc = new MoveCommand(model, iModel.getSelected(), totalDX, totalDY,null);
                            iModel.addToUndoStack(mc);}
                        }}
                currentState = State.READY;
            }
        }
    }

    public void handleDeleteKeyPressed() {
        if (iModel.getSelected() != null) {
            DeleteCommand dc = new DeleteCommand(model, iModel.getSelected(),iModel.getSelected().x, iModel.getSelected().y, null);
            dc.doIt();
            iModel.addToUndoStack(dc);
        }

        if (iModel.getRubberBandSelections() != null) {
            AtomicReference<ArrayList<Blob>> temp = new AtomicReference<>(new ArrayList<>());
            iModel.getRubberBandSelections().forEach((integer, blobs) -> temp.set(blobs));
            DeleteCommand dc = new DeleteCommand(model, iModel.getSelected(),0, 0, temp.get());
            dc.doIt();
            iModel.addToUndoStack(dc);
        }
        else System.out.println("NOTHING SELECTED TO DELETE");
    }

    public void handleUndo() {iModel.handleUndo();}

    public void handleRedo() {iModel.handleRedo();}

    public void setCtrlDown(Boolean ctrlDown) {
        isCtrlDown = ctrlDown;
    }

    public void setCDown(boolean CDown) {
        isCDown = CDown;
        if (isCDown && isCtrlDown) {
            iModel.copyToClipboard();
        }
    }

    public void setVDown(boolean VDown) {
        isVDown = VDown;
        if (isVDown && isCtrlDown) {
            ArrayList<Blob> pasteItems = iModel.getClipBoard();
            CreateCommand cc = new CreateCommand(model,0,0,pasteItems);
            cc.doIt();
            iModel.addToUndoStack(cc);
            if (pasteItems.size() == 1) iModel.setSelected(pasteItems.get(0));
            else if (pasteItems.size() > 1) {
                HashMap<Integer,ArrayList<Blob>> selectionsPaste = new HashMap<>();
                selectionsPaste.put(0,pasteItems);
                iModel.setRubberBandSelections(selectionsPaste);
            }
        }
    }

    public void setXDown(boolean XDown) {
        isXDown = XDown;
        if (isXDown && isCtrlDown) {
            ArrayList<Blob> cut = new ArrayList<>();
            if (iModel.getSelected() != null){
                cut.add(iModel.getSelected());
            }
            if (iModel.getRubberBandSelections() != null) {
                iModel.getRubberBandSelections().forEach((integer, blobs) -> {
                    cut.addAll(blobs);
                });
            }
            iModel.copyToClipboard();
            DeleteCommand dc = new DeleteCommand(model,null,0,0,cut);
            dc.doIt();
            iModel.addToUndoStack(dc);
            System.out.println("CUT ITEMS");
        }

    }

    public void setTDown(boolean TDown) {
        isTDown = TDown;
        if (isCtrlDown && isTDown){
            if (!iModel.getMode().equals("trainer")){
                iModel.setMode("trainer");
                iModel.updateCreateOrder(model.getBlobsMap());
                iModel.checkTarget(model.getBlobsMap().entrySet().iterator().next().getValue().get(0),0);
                }
        }
    }

    public void setEDown(boolean EDown) {
        isEDown = EDown;
        if (isEDown && isCtrlDown){
            iModel.setMode("edit");
        }
    }

}

