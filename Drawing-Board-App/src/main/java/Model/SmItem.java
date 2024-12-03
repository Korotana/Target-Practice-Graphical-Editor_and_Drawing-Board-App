package Model;

public abstract class SmItem {

    public SmItem() {}

    abstract public void move(double dx, double dy);

    abstract public boolean checkHit(double dx, double dy);

}
