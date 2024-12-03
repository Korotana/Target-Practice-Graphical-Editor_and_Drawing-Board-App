package Model;

import javafx.scene.control.Label;

public class SMTransitionLink extends SmItem{

    public double startX, startY, endX, endY;
    public double left, top, width, height;
    public String eventLabel, contextLabel, effectsLabel;
    public String sideEffects = "No SideEffects", context = "No Context", event = "No Event";

    public SMTransitionLink( double newStartX, double newStartY, double newendX, double newendY) {
            eventLabel = "Event";
            contextLabel = "Context";
            effectsLabel = "SideEffects";
            startX = newStartX;
            startY = newStartY;
            endX = newendX;
            endY = newendY;

            left = (newStartX+newendX)/2;
            top = (newStartY+newendY)/2;
            width = 0.3;
            height = 0.25;
        }

        public boolean checkHit(double x, double y) {
            return x >= left-0.1 && x <= left+width-0.1 && y >= top-0.1 && y <= top+height-0.1;
        }

        public boolean checkCircleHit(double x, double y){
            return x >= startX && x <= startX+endX && y >= startY && y <= startY+endY;
        }

        public void moveLineEnd(double dX, double dY) {
            endX += dX;
            endY += dY;
        }

    public void moveLineStart(double dX, double dY) {
            startX += dX;
            startY += dY;
    }

    public void move(double dX, double dY) {
        left += dX;
        top += dY;
    }

}



