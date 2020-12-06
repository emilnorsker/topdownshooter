package norsker.topdownshooter.model;

public class Input
{
    private int[] moveDirection;
    private int playerDirection;
    private boolean isMouseDown;
    private int playerId;


    public Input() {

    }


    public int[] getMoveDirection() {
        return moveDirection;
    }

    public void setMoveDirection(int[] moveDirection) {
        this.moveDirection = moveDirection;
    }

    public int getPlayerDirection() {
        return playerDirection;
    }

    public void setPlayerDirection(int playerDirection) {
        this.playerDirection = playerDirection;
    }

    public boolean isMouseDown() {
        return isMouseDown;
    }

    public void setMouseDown(boolean mouseDown) {
        isMouseDown = mouseDown;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
