package Model;

public class SMStateNode extends SmItem {

    public double left, top, width, height;
    public String state = "Default";
    public SMStateNode(double newLeft, double newTop, double newWidth, double newHeight) {
        left = newLeft;
        top = newTop;
        width = newWidth;
        height = newHeight;
    }

    public boolean checkHit(double x, double y) {
        return x >= left && x <= left+width && y >= top && y <= top+height;
    }

    public void move(double dX, double dY) {
        left += dX;
        top += dY;
    }


}

