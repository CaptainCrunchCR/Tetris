package io.github.tetris.processors;

import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;

import io.github.tetris.tetromino.TetrominoShape;

public class TetrominoMathCalculator implements ITetrominoMathCalculator {
    @Override
    public Vector2[] calculateInitialDefinitions(TetrominoShape shape){
        Vector2[] definitions = new Vector2[4];
        Arrays.setAll(definitions, definition -> new Vector2());

        switch(shape){
            case T:
                definitions[0].set(-1, 0);
                definitions[1].set(0, 0);
                definitions[2].set(1, 0);
                definitions[3].set(0, 1);
                break;
            case I:
                definitions[0].set(-1, 0);
                definitions[1].set(0, 0);
                definitions[2].set(1, 0);
                definitions[3].set(2, 0);
                break;
            case L:
                definitions[0].set(-1, 0);
                definitions[1].set(0, 0);
                definitions[2].set(1, 0);
                definitions[3].set(1, 1);
                break;
            case J:
                definitions[0].set(-1, 0);
                definitions[1].set(0, 0);
                definitions[2].set(1, 0);
                definitions[3].set(-1, 1);
                break;
            case S:
                definitions[0].set(-1, 0);
                definitions[1].set(0, 0);
                definitions[2].set(0, 1);
                definitions[3].set(1, 1);
                break;
            case Z:
                definitions[0].set(1, 0);
                definitions[1].set(0, 0);
                definitions[2].set(0, 1);
                definitions[3].set(-1, 1);
                break;
            case O:
                definitions[0].set(0, 0);
                definitions[1].set(1, 0);
                definitions[2].set(0, 1);
                definitions[3].set(1, 1);
                break;
        }

        return definitions;
    }

    @Override
    public Vector2[] calculateLogicalPositions(Vector2 anchorLogicalPosition, Vector2[] definitions){
        Vector2[] logicalPositions = new Vector2[4];
        Arrays.setAll(logicalPositions, definition -> new Vector2());
        for(int i = 0; i < logicalPositions.length; i++){
            int positionX = (int) (anchorLogicalPosition.x + definitions[i].x);
            int positionY = (int) (anchorLogicalPosition.y - definitions[i].y);
            logicalPositions[i] = new Vector2(positionX, positionY);
        }
        return logicalPositions;
    }

    @Override
    public Vector2[] calculateClockWiseRotation(Vector2[] definitions){
        for (Vector2 definition : definitions) {
            int newDefinitionX = (int) definition.y;
            int newDefinitionY = Math.negateExact((int) definition.x);
            definition.x = newDefinitionX;
            definition.y = newDefinitionY;
        }
        return definitions;
    }
}
