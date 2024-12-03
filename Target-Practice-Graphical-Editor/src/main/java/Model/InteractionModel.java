package Model;

import Interface.IModelListener;
import Interface.SMModelListener;

import java.util.ArrayList;

public class InteractionModel {

    SMStateNode selection = null;
    SMTransitionLink selectionLink = null;
    int selectedButtonIndex = 0;
    double worldWidth, worldHeight;
    double viewWidth, viewHeight;
    public double viewLeft = 0, viewTop = 0;
    private ArrayList<IModelListener> subscribers = new ArrayList<>();
    ArrayList<SMModelListener> smSubscribers = new ArrayList<>();
    ArrayList<SMModelListener> smLinkSubscribers = new ArrayList<>();

    public InteractionModel() {
    }

    public void setSelectedButton(Integer buttonNum) {
        this.selectedButtonIndex = buttonNum;
        this.notifySubscribers();
    }

    private void notifySubscribers() {
        subscribers.forEach(s -> s.iModelChanged());
    }

    private void notifySmSubscribers() {
        smSubscribers.forEach(s -> s.modelChanged());
    }

    public void addSubscriber(IModelListener sub) { // for version 2
        subscribers.add(sub);
    }

    public void addSMSubscriber(SMModelListener sub){
        smSubscribers.add(sub);
    }

    public int getSelectedButtonIndex() {
        return selectedButtonIndex;
    }

    public void setSelection(SMStateNode node) {
        selection = node;
        selectionLink = null;
        notifySmSubscribers();
    }

    public void setSelectionLink(SMTransitionLink link){
        selectionLink = link;
        selection = null;
        notifySmSubscribers();
    }

    public SMTransitionLink getSelectionLink() {
        return selectionLink;
    }

    public SMStateNode getSelection() {
        return this.selection;
    }

    public void unselect() {
        selection = null;
        notifySmSubscribers();
    }

    public void deselectNode() {
        if (selection != null) {
            selection = null;
            notifySmSubscribers();
        }
    }

    public void deSelectEventBox() {
        if (selectionLink != null) {
            selectionLink = null;
            notifySmSubscribers();
        }
    }

    public void setWorldExtents(double newWidth, double newHeight) {
        worldWidth = newWidth;
        worldHeight = newHeight;
    }

    public void moveView(double dX, double dY) {

//        viewLeft -= dX;
//        viewTop -= dY;
//        if (viewLeft < 0) viewLeft = 0;
//        if (viewLeft > (1.0 - viewWidth)) viewLeft = 1.0 - viewWidth;
//        if (viewTop < 0) viewTop = 0;
//        if (viewTop > (1.0 - viewHeight)) viewTop = 1.0 - viewHeight;
        notifySubscribers();
    }

    public boolean checkViewfinderHit(double x, double y) {
        return x >= viewLeft && x <= viewLeft + viewWidth &&
                y >= viewTop && y <= viewTop + viewHeight;
    }

    public void setViewExtents(double newWidth, double newHeight) {
        viewWidth = newWidth;
        viewHeight = newHeight;
        notifySubscribers();
    }
}
