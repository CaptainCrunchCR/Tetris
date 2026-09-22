package io.github.tetris.controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;


import io.github.tetris.helpers.Holder;
import io.github.tetris.arbiter.GameArbiter;
import io.github.tetris.renderers.TetrominoRenderer;
import io.github.tetris.tetromino.ITetromino;

public class ControlManager {
    private final int RIGHT_KEY = Input.Keys.RIGHT;
    private final int LEFT_KEY = Input.Keys.LEFT;
    private final int SOFT_DOWN_KEY = Input.Keys.DOWN;
    private final int CLOCKWISE_ROTATION_KEY = Input.Keys.UP;
    private final int COUNTER_CLOCKWISE_ROTATION_KEY = Input.Keys.Z;
    private float dasTimer;
    private final float DAS_DELAY;
    private final float DAS_RATE;
    private final Holder<ITetromino> tetrominoHolder;
    private final TetrominoRenderer tetrominoRenderer;
    private final GameArbiter gameArbiter;
    private long soundId;

    public ControlManager(Holder<ITetromino> tetrominoHolder, TetrominoRenderer tetrominoRenderer, GameArbiter gameArbiter){
        this.tetrominoHolder = tetrominoHolder;
        this.tetrominoRenderer = tetrominoRenderer;
        this.gameArbiter = gameArbiter;
        this.dasTimer = 0;
        this.DAS_DELAY = .2f;
        this.DAS_RATE = .05f;
    }

    private void handleInputs(){
        if(Gdx.input.isKeyJustPressed(LEFT_KEY)){
            if(this.gameArbiter.isTetrominoAllowedToPerformAction(ActionIntent.MOVE_LEFT)) {
                moveLeft();
            }
        }
        if(Gdx.input.isKeyJustPressed(RIGHT_KEY)){
            if(this.gameArbiter.isTetrominoAllowedToPerformAction(ActionIntent.MOVE_RIGHT)){
                moveRight();
            }
        }
        if(Gdx.input.isKeyJustPressed(SOFT_DOWN_KEY)){
            if(this.gameArbiter.isTetrominoAllowedToPerformAction(ActionIntent.MOVE_DOWN)) {
                moveDown();
            }
        }
        if(Gdx.input.isKeyJustPressed(CLOCKWISE_ROTATION_KEY)){
            if(this.gameArbiter.isTetrominoAllowedToPerformAction(ActionIntent.ROTATE_CLOCKWISE)) {
                moveClockWise();
            }
        }
        this.tetrominoRenderer.updateTetrominoShape();
    }

    private void moveLeft(){
        this.tetrominoHolder.getValue().setAnchorLogicalPositionX(this.tetrominoHolder.getValue().getAnchorLogicalPositionX() - 1);

    }

    private void moveRight(){
        this.tetrominoHolder.getValue().setAnchorLogicalPositionX(this.tetrominoHolder.getValue().getAnchorLogicalPositionX() + 1);

    }

    private void moveDown(){
        this.tetrominoHolder.getValue().setAnchorLogicalPositionY(this.tetrominoHolder.getValue().getAnchorLogicalPositionY() + 1);
    }

    private void moveClockWise(){
        this.tetrominoHolder.getValue().rotateClockWise();
    }

    public void render(float deltaTime){
        this.handleInputs();
    }

}
