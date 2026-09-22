package io.github.tetris.gameboard;

import java.util.Objects;
import java.util.Optional;

import io.github.tetris.tetromino.TetrominoShape;

public class Cell {
    public Optional<TetrominoShape> getContent() {
        return Optional.ofNullable(this.content);
    }

    public void setContent(TetrominoShape tetrominoShape) {
        Objects.requireNonNull(tetrominoShape, "Tetromino Shape cannot be null. Call setContentWithoutTetrominoShape() instead.");
        this.content = tetrominoShape;
    }

    public void setEmptyContent(){
        this.content = null;
    }

    private TetrominoShape content;

    public Cell(){
    }

    @Override
    public String toString() {
        return this.content == null ? "X" : this.content.name();
    }
}
