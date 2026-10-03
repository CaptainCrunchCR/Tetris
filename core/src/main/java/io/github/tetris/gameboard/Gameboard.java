package io.github.tetris.gameboard;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Arrays;

import io.github.tetris.helpers.Holder;
import io.github.tetris.tetromino.ITetromino;

public class Gameboard {
    public Cell[][] getLayout() {
        return layout.clone();
    }

    public void setLayout(Cell[][] layout) {
        this.layout = layout;
    }

    private Cell[][] layout;
    private final int TOTAL_ROWS;
    private final int TOTAL_COLUMNS;
    private final int TOTAL_X_COORDINATES;
    private final int TOTAL_Y_COORDINATES;
    private final Holder<ITetromino> tetrominoHolder;
    private GameboardState state;
    private ArrayList<Integer> completedRows;
    public int getTotalRows() {
        return TOTAL_ROWS;
    }

    public int getTotalColumns() {
        return TOTAL_COLUMNS;
    }

    public int getTotalXCoordinates(){
        return TOTAL_X_COORDINATES;
    }

    public int getTotalYCoordinates(){
        return TOTAL_Y_COORDINATES;
    }

    public Gameboard(Holder<ITetromino> tetrominoHolder) {
        this.TOTAL_ROWS = 20;
        this.TOTAL_COLUMNS = 10;
        this.layout = new Cell[this.TOTAL_ROWS][this.TOTAL_COLUMNS];
        this.TOTAL_X_COORDINATES = 9;
        this.TOTAL_Y_COORDINATES = 19;
        this.state = GameboardState.EMPTY;
        this.tetrominoHolder = tetrominoHolder;
        this.completedRows = new ArrayList<>();
        this.resetCompletedRows();
        this.initGameboard();
    }

    private void initGameboard(){
        for(int row = 0; row < layout.length; row++){
            for(int column = 0; column < layout[row].length; column++){
                this.layout[row][column] = new Cell();
                this.layout[row][column].setEmptyContent();
            }
        }
    }

    public void updateGameboard(){
        this.commitTetromino();
        this.calculateCompletedRows();
        if(!this.completedRows.isEmpty()) {
            this.shiftRowsDown();
        }
        this.resetCompletedRows();
        this.updateLayoutState();
    }

    private void updateLayoutState(){
        int occupiedRows = 0;
        for (Cell[] cells : this.layout) {
            boolean isRowOccupied = false;
            for (Cell cell : cells) {
                if (cell.getContent().isPresent()) {
                    isRowOccupied = true;
                    break;
                }
            }
            if (isRowOccupied) {
                occupiedRows++;
            }
        }

        if(occupiedRows == this.TOTAL_ROWS){
            this.state = GameboardState.FULL;
        }else if(occupiedRows == 0){
            this.state = GameboardState.EMPTY;
        }else{
            this.state = GameboardState.PARTIALLY_FULL;
        }
    }
    private void resetCompletedRows(){
        this.completedRows.clear();
    }

    private void shiftRowsDown(){
        this.completedRows.forEach(completedRow -> {
            for(int row = completedRow; row > 0; row--){
                this.layout[row] = this.layout[row - 1];
            }
        });

        //Clean up gameboard top rows memory addresses to have fresh objects.
        for(int row = 0; row < this.completedRows.size(); row++){
            this.layout[row] = new Cell[this.TOTAL_COLUMNS];
            Arrays.setAll(this.layout[row], cell -> new Cell());
        };
    }

    private void calculateCompletedRows(){
        int row= 0;
        for (; row < this.layout.length; row++) {
            boolean isLineCompleted = false;
            for (int column= 0; column < this.layout[row].length; column++) {
                Cell currentCell = this.layout[row][column];
                if (!(currentCell.getContent().isPresent())) {
                    isLineCompleted = false;
                    break;
                } else {
                    isLineCompleted = true;
                }
            }
            if (isLineCompleted) {
                this.completedRows.add(row);
            }
        }
    }

    private void commitTetromino(){
        Vector2[] tetrominoPositions = this.tetrominoHolder.getValue().getLogicalPositions();
        for(Vector2 position: tetrominoPositions){
            this.layout[(int) position.y][(int) position.x].setContent(this.tetrominoHolder.getValue().getShape());
        }
    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for(int border = 0; border < (layout[0].length * 3); border++){
            str.append('-');
        }
        str.append('\n');
        for (Cell[] cells : this.layout) {
            for (Cell cell : cells) {
                str.append(' ');
                str.append(cell);
                str.append(' ');
            }
            str.append('\n');
        }
        for(int border = 0; border < (layout[0].length * 3); border++){
            str.append('-');
        }

        return str.toString();
    }
}
