package io.github.tetris.renderers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import io.github.tetris.helpers.Holder;
import io.github.tetris.wrappers.AssetIdentifier;
import io.github.tetris.wrappers.TetrisRenderAssets;
import io.github.tetris.tetromino.ITetromino;

public class TetrominoRenderer {
    private final SpriteBatch batch;
    private final int TETROMINO_PIECE_SIZE = 45;
    private final GameboardRenderer gameboardRenderer;
    private final Holder<ITetromino> tetrominoHolder;
    private final TetrisRenderAssets tetrisRenderAssets;
    private final Sprite[] tetrominoShape;
    public TetrominoRenderer(SpriteBatch batch, TetrisRenderAssets tetrisRenderAssets, Holder<ITetromino> tetrominoHolder, GameboardRenderer gameboardRenderer){
        this.batch = batch;
        this.tetrominoHolder = tetrominoHolder;
        this.tetrisRenderAssets = tetrisRenderAssets;
        this.gameboardRenderer = gameboardRenderer;
        this.tetrominoShape = new Sprite[4];
        this.updateTetrominoShape();
    }

    /**
     * Updates the Tetromino full shape using a collection of Sprites (tetromino pieces) based in the same TetrominoRegion.
     * Each tetromino piece position is calculated translating the tetromino anchor logical coordinate (gameboard cell coordinate)
     * into visual coordinates that get aligned properly into the gameboard. Then, the calculation is followed by the tetromino
     * relative alignment to its definition multiplied by the tetromino piece size.
     * */
    public void updateTetrominoShape(){
        Vector2[] definitions = this.tetrominoHolder.getValue().getDefinitions();
        Vector2 visualPosition = this.gameboardRenderer.getCellVisualPosition(this.tetrominoHolder.getValue().getAnchorLogicalPositionX(), this.tetrominoHolder.getValue().getAnchorLogicalPositionY());
        for(int i = 0; i < definitions.length; i++){
            TextureRegion textureRegion = this.tetrisRenderAssets.getRegion(AssetIdentifier.valueOf(this.tetrominoHolder.getValue().getShape().name()));
            Sprite tetrominoPiece = new Sprite(textureRegion);
            tetrominoPiece.setPosition(visualPosition.x + ((definitions[i].x * TETROMINO_PIECE_SIZE)), visualPosition.y + (definitions[i].y * TETROMINO_PIECE_SIZE));
            this.tetrominoShape[i] = tetrominoPiece;
        }
    }
    public void render(){
        for(Sprite piece: tetrominoShape){
            piece.draw(batch);
        }
    }
}
