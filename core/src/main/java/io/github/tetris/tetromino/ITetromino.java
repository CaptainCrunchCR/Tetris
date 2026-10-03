package io.github.tetris.tetromino;

import com.badlogic.gdx.math.Vector2;

public interface ITetromino {
    Vector2[] getDefinitions();
    Vector2[] getLogicalPositions();
    Vector2 getAnchorLogicalPosition();
    void setAnchorLogicalPosition(int x, int y);
    int getAnchorLogicalPositionX();
    void setAnchorLogicalPositionX(int x);
    int getAnchorLogicalPositionY();
    void setAnchorLogicalPositionY(int y);
    void setOrientation(TetrominoOrientation orientation);
    TetrominoOrientation getOrientation();
    TetrominoShape getShape();
    void rotateClockWise();
    void rotateCounterClockWise();
}
