package io.github.tetris.processors;

import com.badlogic.gdx.math.Vector2;

import io.github.tetris.tetromino.TetrominoShape;

public interface ITetrominoMathCalculator {
    Vector2[] calculateInitialDefinitions(TetrominoShape shape);
    Vector2[] calculateLogicalPositions(Vector2 anchorLogicalPosition, Vector2[] definitions);
    Vector2[] calculateClockWiseRotation(Vector2[] definitions);
    Vector2[] calculateCounterClockWiseRotation(Vector2[] definitions);
}
