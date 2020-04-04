package util;

public class Click {
    public int positionX;
    public int positionY;

    public Click(int x, int y) {
        this
            .setPositionX(x)
            .setPositionY(y);
    }

    public Click setPositionX(int positionX) {
        this.positionX = positionX;
        return this;
    }

    public Click setPositionY(int positionY) {
        this.positionY = positionY;
        return this;
    }
}