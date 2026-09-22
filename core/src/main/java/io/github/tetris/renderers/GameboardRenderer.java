package io.github.tetris.renderers;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;

import io.github.tetris.wrappers.AssetIdentifier;
import io.github.tetris.wrappers.TetrisRenderAssets;
import io.github.tetris.gameboard.Cell;
import io.github.tetris.gameboard.Gameboard;
import io.github.tetris.tetromino.TetrominoShape;

public class GameboardRenderer {
    private final SpriteBatch batch;
    private final FitViewport viewport;
    private final TextureRegion gameboardTexture;
    private final Gameboard gameboard;
    private final TetrisRenderAssets tetrisRenderAssets;
    private final int GAMEBOARD_WIDTH;
    private final int GAMEBOARD_HEIGHT;
    private final int GAMEBOARD_CELL_SIZE;
    private final int GAMEBOARD_BORDER_SIZE;
    private final int GAMEBOARD_ROWS;
    private final int GAMEBOARD_COLS;
    private final float GAMEBOARD_ORIGIN_X;
    private final float GAMEBOARD_ORIGIN_Y;
    public GameboardRenderer(SpriteBatch batch, FitViewport viewport, Gameboard gameboard, TetrisRenderAssets tetrisRenderAssets){
        this.batch = batch;
        this.viewport = viewport;
        this.gameboard = gameboard;
        this.tetrisRenderAssets = tetrisRenderAssets;
        this.gameboardTexture = this.tetrisRenderAssets.getRegion(AssetIdentifier.GAMEBOARD);
        this.GAMEBOARD_WIDTH = 470;
        this.GAMEBOARD_HEIGHT = 920;
        this.GAMEBOARD_CELL_SIZE = 45;
        this.GAMEBOARD_BORDER_SIZE = 10;
        this.GAMEBOARD_COLS = 10;
        this.GAMEBOARD_ROWS = 20;
        this.GAMEBOARD_ORIGIN_X = ((viewport.getWorldWidth() - GAMEBOARD_WIDTH) / 2f);
        this.GAMEBOARD_ORIGIN_Y = ((viewport.getWorldHeight() - GAMEBOARD_HEIGHT) / 2f);
    }

    private void drawGameboardFrame(){
        batch.draw(
            gameboardTexture,
            (viewport.getWorldWidth() - GAMEBOARD_WIDTH) / 2f,
            (viewport.getWorldHeight() - GAMEBOARD_HEIGHT) / 2f,
            GAMEBOARD_WIDTH,
            GAMEBOARD_HEIGHT);
    }

    private void renderGameboardLayout(){
        Cell[][] layout = this.gameboard.getLayout();
        for(int row = 0; row < layout.length; row++){
            for(int col = 0; col < layout[row].length; col++){
                Cell cell = layout[row][col];
                if(cell.getContent().isPresent()){
                    TetrominoShape shape = cell.getContent().get();
                    Vector2 visualPosition = getCellVisualPosition(col, row);
                    TextureRegion region = this.tetrisRenderAssets.getRegion(AssetIdentifier.valueOf(shape.name()));
                    batch.draw(region, visualPosition.x, visualPosition.y, GAMEBOARD_CELL_SIZE, GAMEBOARD_CELL_SIZE);
                }
            }
        }
    }

    public Vector2 getCellVisualPosition(int x, int y) {
        float gameboardX = GAMEBOARD_ORIGIN_X + (GAMEBOARD_CELL_SIZE * x) + GAMEBOARD_BORDER_SIZE;
        float gameboardY = (GAMEBOARD_ORIGIN_Y + (GAMEBOARD_CELL_SIZE * ((GAMEBOARD_ROWS -1) - y)) + GAMEBOARD_BORDER_SIZE);
        return new Vector2(gameboardX, gameboardY);
    }

    public void render(){
        this.drawGameboardFrame();
        this.renderGameboardLayout();
    }

    public void dispose(){
    }
}
