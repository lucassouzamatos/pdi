package util;

public class Drag {
    public Click startPress;
    public Click finalPress;

    public void setStartPress(Click startPress) {
        this.startPress = startPress;
    }

    public void setFinalPress(Click finalPress) {
        this.finalPress = finalPress;
    }

    public Click getStartPress() {
        return startPress;
    }

    public Click getFinalPress() {
        return finalPress;
    }
}
