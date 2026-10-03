package io.github.tetris.tetromino;

import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;

import io.github.tetris.processors.ITetrominoMathCalculator;
import io.github.tetris.processors.TetrominoMathCalculator;

public class Tetromino implements ITetromino {

    private Vector2 anchorLogicalPosition;
    private Vector2[] definitions;
    private TetrominoOrientation orientation;
    private TetrominoShape shape;
    private Vector2[] logicalPositions;
    private ITetrominoMathCalculator tetrominoMathCalculator;
    public Tetromino(TetrominoShape shape, Vector2 anchorLogicalPosition) {
        this.shape = shape;
        this.anchorLogicalPosition = anchorLogicalPosition;
        this.tetrominoMathCalculator = new TetrominoMathCalculator();
        this.definitions = new Vector2[4];
        this.logicalPositions = new Vector2[4];
        Arrays.setAll(this.definitions, definition -> new Vector2());
        Arrays.setAll(this.logicalPositions, definition -> new Vector2());
        this.definitions = this.tetrominoMathCalculator.calculateInitialDefinitions(shape);
        this.logicalPositions = this.tetrominoMathCalculator.calculateLogicalPositions(this.anchorLogicalPosition, this.definitions);
        this.orientation = TetrominoOrientation.ZERO;
    }

    public Tetromino(TetrominoShape shape) {
        this.shape = shape;
        this.anchorLogicalPosition = new Vector2(0, 0);
        this.tetrominoMathCalculator = new TetrominoMathCalculator();
        this.definitions = new Vector2[4];
        this.logicalPositions = new Vector2[4];
        Arrays.setAll(this.definitions, definition -> new Vector2());
        Arrays.setAll(this.logicalPositions, definition -> new Vector2());
        this.definitions = this.tetrominoMathCalculator.calculateInitialDefinitions(shape);
        this.logicalPositions = this.tetrominoMathCalculator.calculateLogicalPositions(this.anchorLogicalPosition, this.definitions);
        this.orientation = TetrominoOrientation.ZERO;
    }

    @Override
    public int getAnchorLogicalPositionX() {
        return (int) this.anchorLogicalPosition.x;
    }

    @Override
    public void setAnchorLogicalPositionX(int x) {
        this.anchorLogicalPosition.x = x;
        this.calculateLogicalPositions();
    }

    @Override
    public int getAnchorLogicalPositionY() {
        return (int) this.anchorLogicalPosition.y;
    }

    @Override
    public void setAnchorLogicalPositionY(int y) {
        this.anchorLogicalPosition.y = y;
        this.calculateLogicalPositions();
    }

    @Override
    public Vector2 getAnchorLogicalPosition() {
        return this.anchorLogicalPosition;
    }

    @Override
    public void setAnchorLogicalPosition(int x, int y) {
        this.anchorLogicalPosition = new Vector2(x, y);
        this.calculateLogicalPositions();
    }

    @Override
    public Vector2[] getDefinitions() {
        return definitions;
    }

    @Override
    public Vector2[] getLogicalPositions() {
        return this.logicalPositions.clone();
    }

    private void calculateLogicalPositions() {
        this.logicalPositions = tetrominoMathCalculator.calculateLogicalPositions(this.anchorLogicalPosition, this.definitions);
    }

    @Override
    public TetrominoOrientation getOrientation() {
        return orientation;
    }

    @Override
    public TetrominoShape getShape() {
        return shape;
    }

    @Override
    public void setOrientation(TetrominoOrientation orientation) {
        this.orientation = orientation;
    }

    @Override
    public void rotateClockWise() {
        this.definitions = tetrominoMathCalculator.calculateClockWiseRotation(this.definitions);
        this.calculateLogicalPositions();
    }

    @Override
    public void rotateCounterClockWise() {
        this.definitions = tetrominoMathCalculator.calculateCounterClockWiseRotation(this.definitions);
        this.calculateLogicalPositions();
    }
}
