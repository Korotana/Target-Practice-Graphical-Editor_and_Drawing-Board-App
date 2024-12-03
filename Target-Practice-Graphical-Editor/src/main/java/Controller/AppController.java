package Controller;

import Model.InteractionModel;
import Model.SMModel;
import Model.SMStateNode;
import Model.SMTransitionLink;
import javafx.scene.input.MouseEvent;

import java.util.Map;

public class AppController {

    InteractionModel iModel;
    SMModel smModel;
    double prevX, prevY, panx, pany;

    private enum State {
        READY, PREPARE_CREATE, DRAGGING
    }

    private State currentState;

    public AppController() {
        currentState = State.READY;
    }

    public void handleButtonClick(Integer buttonNum){
        iModel.setSelectedButton(buttonNum);
    }

    public void setInteractionModel(InteractionModel newIModel) {
        this.iModel = newIModel;
    }

    public void setSmModel(SMModel newSmModel){
        this.smModel = newSmModel;
    }

    public void handlePressed(double normX, double normY, MouseEvent event) {
        prevX = normX;
        prevY = normY;
        panx = normX;
        pany = normY;

        switch (currentState) {
            case READY -> {
                // context: are we on a box?
                boolean eventBoxHit = smModel.checkEventBoxHit(normX,normY);
                Map.Entry<SMStateNode,SMTransitionLink> circularEventBoxLink = smModel.checkCircleEventBox(normX,normY);
                boolean hit = smModel.checkHit(normX, normY);
                if (iModel.getSelectedButtonIndex() == 1){
                    currentState = State.DRAGGING;
                }
                else if (hit) {
                    // side effects:
                    // set selection
                    if (iModel.getSelectedButtonIndex() == 2) {
                        SMStateNode node = smModel.whichBox(normX,normY);
                        smModel.lineX = normX;
                        smModel.lineY = normY;
                        smModel.initialNode = node;
                        currentState = State.DRAGGING;
                    }
                    else {
                        iModel.setSelection(smModel.whichBox(normX, normY));

                    // move to new state
                        currentState = State.DRAGGING;}
                }
                else if(eventBoxHit){
                    if (iModel.getSelectedButtonIndex() == 2) {
                        SMTransitionLink link = smModel.whichEventBox(normX,normY);
                        smModel.lineX = normX;
                        smModel.lineY = normY;
                        smModel.initialLink = link;
                        currentState = State.DRAGGING;
                    }
                    else {
                        SMTransitionLink link = smModel.whichEventBox(normX, normY);
                        smModel.links.remove(link);
                        smModel.links.add(link);
                        iModel.setSelectionLink(link);
                        // move to new state
                        currentState = State.DRAGGING;}
                }else if (circularEventBoxLink != null){
                    smModel.circularLink.remove(circularEventBoxLink.getKey());
                    smModel.circularLink.put(circularEventBoxLink.getKey(),circularEventBoxLink.getValue());
                    iModel.setSelectionLink(circularEventBoxLink.getValue());
                    currentState = State.DRAGGING;
                }
                else {
                    // side effects
                    // none
                    // move to new state
                        currentState = State.PREPARE_CREATE;
                }
            }
        }
    }

    public void handleDragged(double normX, double normY) {
        double dX = normX - prevX;
        double dY = normY - prevY;

        switch (currentState) {
            case DRAGGING -> {
                if (iModel.getSelectedButtonIndex() == 1){
                    iModel.moveView(dX,dY);
                    smModel.pan(dX,dY);
                }
                else if (iModel.getSelectedButtonIndex() == 0){
                    boolean eventBoxHit = smModel.checkEventBoxHit(normX,normY);
                    boolean hit = smModel.checkHit(normX, normY);
                    Map.Entry<SMStateNode,SMTransitionLink> circularEventBoxLink = smModel.checkCircleEventBox(normX,normY);
                    if (hit && iModel.getSelection() != null) {
                        smModel.moveBox(iModel.getSelection(), dX, dY);
                        iModel.deSelectEventBox();
                    }
                    if (eventBoxHit && iModel.getSelectionLink() != null) {
                        smModel.moveEventBox(iModel.getSelectionLink(), dX, dY);
                        iModel.deselectNode();
                    }
                    if (circularEventBoxLink !=null && iModel.getSelectionLink() != null){
                        smModel.moveBox(circularEventBoxLink.getKey(),dX,dY);
                    }
                }
                else {
                    smModel.createLink(prevX, prevY, normX, normY);
                }
            }
            case PREPARE_CREATE -> {
                // context: none
                // side effects:
                // - none
                // move to new state
                currentState = State.READY;
            }
        }
        prevX = normX;
        prevY = normY;
    }

    public void handleReleased(double normX, double normY, MouseEvent event) {
        switch (currentState) {
            case DRAGGING -> {
                boolean hit = smModel.checkHit(normX, normY);
                if (iModel.getSelectedButtonIndex() == 1){
                    currentState = State.READY;
                }
                // move to new state
                else if (hit){
                    if (iModel.getSelectedButtonIndex() == 2){
                        SMTransitionLink linkCreated = smModel.createLink(Double.MAX_VALUE, Double.MAX_VALUE,normX,normY);
                        iModel.setSelectionLink(linkCreated);
                    }
                }
                else {
                    System.out.println("NO LINK CAN BE FORMED");
                }
                currentState = State.READY;
            }
            case PREPARE_CREATE -> {

                boolean eventBoxHit = smModel.checkEventBoxHit(normX,normY);
                if (eventBoxHit) {iModel.setSelectionLink(smModel.whichEventBox(normX,normY));}
                else {smModel.createNode(normX,normY);
                iModel.setSelection(smModel.whichBox(normX, normY));}
                // move to new state
                currentState = State.READY;
            }
        }
    }

    public void handleStateUpdate(String state) {
        smModel.updateState(state, iModel.getSelection());
    }

    public void handleUpdatePressed(String event, String context, String effects) {
        if (iModel.getSelectionLink() != null) smModel.updateLinkEvent(event,context,effects,iModel.getSelectionLink());
        else System.out.println("You need to select an Event Box to Update");
    }

    public void handleDeleteKeyPressed() {
        if (iModel.getSelection() != null) smModel.deleteNode(iModel.getSelection());
        else if (iModel.getSelectionLink() != null) smModel.deleteLink(iModel.getSelectionLink());
        else System.out.println("NOTHING SELECTED TO DELETE");

    }

}
