package io.github.tetris.arbiter;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import io.github.tetris.controls.ActionIntent;
import io.github.tetris.gameboard.Gameboard;
import io.github.tetris.helpers.Holder;
import io.github.tetris.processors.ITetrominoMathCalculator;
import io.github.tetris.processors.TetrominoMathCalculator;
import io.github.tetris.tetromino.ITetromino;

public class GameArbiter {
    private final Holder<ITetromino> tetrominoHolder;
    private final Gameboard gameboard;
    private final ITetrominoMathCalculator projector;

    public GameArbiter(Gameboard gameboard, Holder<ITetromino> tetrominoHolder){
        this.gameboard = gameboard;
        this.tetrominoHolder = tetrominoHolder;
        this.projector = new TetrominoMathCalculator();
    }


    public boolean isTetrominoAllowedToPerformAction(ActionIntent actionIntent){
        Vector2[] tetrominoPiecePositions = this.tetrominoHolder.getValue().getLogicalPositions();
        return !(isWallCollisionDetectedOnAction(tetrominoPiecePositions, actionIntent)) && !(isPieceCollisionDetectedOnAction(tetrominoPiecePositions, actionIntent));
    }
    private boolean isWallCollisionDetectedOnAction(Vector2[] tetrominoPiecePositions, ActionIntent actionIntent) {
        boolean isWallCollisionDetected = false;
        for (Vector2 piecePosition : tetrominoPiecePositions) {
            switch (actionIntent) {
                case ROTATE_COUNTER_CLOCKWISE:
                case ROTATE_CLOCKWISE:
                    isWallCollisionDetected = piecePosition.x == 0 || piecePosition.x == this.gameboard.getTotalXCoordinates() || piecePosition.y <= 4;
                    break;
                case MOVE_LEFT:
                    isWallCollisionDetected = piecePosition.x - 1 < 0;
                    break;
                case MOVE_RIGHT:
                    isWallCollisionDetected = piecePosition.x + 1 >= this.gameboard.getTotalColumns();
                    break;
                case MOVE_DOWN:
                    isWallCollisionDetected = !(piecePosition.y + 1 < this.gameboard.getTotalRows());
                    break;
            }
            if(isWallCollisionDetected){
                break;
            }
        }
        return isWallCollisionDetected;
    }
    private boolean isPieceCollisionDetectedOnAction(Vector2[] tetrominoPiecePositions, ActionIntent actionIntent){
        boolean isPieceCollisionDetected = false;
        for(Vector2 piecePosition: tetrominoPiecePositions){
            switch (actionIntent) {
                case ROTATE_COUNTER_CLOCKWISE:
                case ROTATE_CLOCKWISE:
                    isPieceCollisionDetected = this.gameboard.getLayout()[(int) piecePosition.y][(int) piecePosition.x - 1].getContent().isPresent() ||
                        this.gameboard.getLayout()[(int) piecePosition.y][(int) piecePosition.x + 1].getContent().isPresent();
                    break;
                case MOVE_LEFT:
                    isPieceCollisionDetected = this.gameboard.getLayout()[(int) piecePosition.y][(int) piecePosition.x - 1].getContent().isPresent();
                    break;
                case MOVE_RIGHT:
                    isPieceCollisionDetected = this.gameboard.getLayout()[(int) piecePosition.y][(int) piecePosition.x + 1].getContent().isPresent();
                    break;
                case MOVE_DOWN:
                    isPieceCollisionDetected = this.gameboard.getLayout()[(int) piecePosition.y + 1][(int) piecePosition.x].getContent().isPresent();
                    break;
            }

            if(isPieceCollisionDetected){
                break;
            }
        }

        return isPieceCollisionDetected;
    }
}
