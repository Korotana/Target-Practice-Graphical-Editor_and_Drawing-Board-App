package com.example.assignment4;

public class RubberBand {

    public double left, top, width, height;

    public RubberBand(double newLeft, double newTop, double newWidth, double newHeight) {
        left = newLeft;
        top = newTop;
        width = newWidth;
        height = newHeight;
    }

    public boolean checkHit(double x, double y) {

//        gc.strokeLine(band.left,band.top,band.left,band.top+band.height); // vertical line with start points

        if (width+left <= left) {
            double temp = left;
            left = left + width;
            width = temp - left;
        }
        if (height+top <= top) {
            double temp = top;
            top = top + height;
            height = temp - top;
        }

        return x >= left && x <= left+width && y >= top && y <= top+height;

    }


    public boolean checkAllHit(double x, double y, double r) {
        boolean hit = false;
        for (int i = 0; i < 361; i++) {
            hit = this.checkHit(r*Math.cos(i) + x,r*Math.sin(i) + y);
            if (hit){
                return hit;
            }
        }
        return hit;
    }
}







