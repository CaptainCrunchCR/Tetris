package io.github.tetris.manager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;

import io.github.tetris.arbiter.GameArbiter;
import io.github.tetris.arbiter.GameState;
import io.github.tetris.arbiter.IClearedLinesSubscriber;
import io.github.tetris.controls.ControlManager;
import io.github.tetris.controls.ActionIntent;
import io.github.tetris.gameboard.Gameboard;
import io.github.tetris.helpers.Holder;
import io.github.tetris.renderers.GameboardRenderer;
import io.github.tetris.renderers.TetrominoRenderer;
import io.github.tetris.screens.GameScreen;
import io.github.tetris.tetromino.ITetromino;
import io.github.tetris.tetromino.Tetromino;
import io.github.tetris.tetromino.TetrominoShape;
import io.github.tetris.wrappers.AssetIdentifier;
import io.github.tetris.wrappers.TetrisRenderAssets;

public class GameManager extends Game implements IClearedLinesSubscriber {
    /***
     * Gravity determines the fall speed of tetrominoes.
     * This property is going to cause impact on render multiplying its value by delta time.
     */
    public float gravity;
    public int level;
    public Gameboard gameboard;
    public GameboardRenderer gameboardRenderer;
    public GameArbiter gameArbiter;
    public ControlManager controlManager;
    public TetrominoRenderer tetrominoRenderer;
    private final Holder<ITetromino> tetrominoHolder;
    public float tetrominoTimer = 0;
    public final float WORLD_WIDTH;
    public final float WORLD_HEIGHT;
    public FitViewport viewport;
    public OrthographicCamera camera;
    public BitmapFont mainFont;
    public SpriteBatch batch;
    public Music easyLevelThemeMusic;
    public Music hardLevelThemeMusic;
    public AssetManager assetManager;
    public final TetrisRenderAssets tetrisRenderAssets;
    private GameState gameState;


    public GameManager (){
        gravity = 1f;
        level = 1;
        WORLD_WIDTH = 1920;
        WORLD_HEIGHT = 1080;
        this.tetrominoHolder = new Holder<>(null);
        this.gameboard = new Gameboard(this.tetrominoHolder);
        this.gameArbiter = new GameArbiter(this.gameboard, this.tetrominoHolder);
        this.gameArbiter.subscribeClearedLinesSubscriber(this);
        this.tetrisRenderAssets = new TetrisRenderAssets();
        this.gameState = GameState.NOT_PLAYING;
        startGame();
    }

    private void startGame(){
        this.gameState = GameState.STARTING_GAME;
        this.gameArbiter.updateGameState(this.gameState);
        manageGameSession();
    }

    private void manageGameSession(){
        if(this.gameState == GameState.STARTING_GAME) {
            this.buildNewTetromino();
            this.gameState = GameState.PLAYING;
        }
        else if(this.gameState == GameState.PLAYING){
            if(this.gameArbiter.isTetrominoAllowedToPerformAction(ActionIntent.MOVE_DOWN)) {
                this.fallTetromino();
            }else{
                this.lockTetromino();
                this.buildNewTetromino();
            }
        }
    }
    private void buildNewTetromino(){
        TetrominoShape randomShape = this.selectRandomTetrominoShape();
        this.spawnNewTetromino(randomShape);
        Vector2 tetrominoAnchorSpawnPosition = this.selectTetrominoAnchorSpawnPosition(randomShape);
        this.tetrominoHolder.getValue().setAnchorLogicalPosition((int) tetrominoAnchorSpawnPosition.x, (int) tetrominoAnchorSpawnPosition.y);
    }
    private void spawnNewTetromino(TetrominoShape shape){
        this.tetrominoHolder.setValue(null);
        this.tetrominoHolder.setValue(new Tetromino(shape));
    }
    private TetrominoShape selectRandomTetrominoShape(){
        int randomTetrominoNumber = (int) (Math.random() * TetrominoShape.values().length);
        return TetrominoShape.values()[randomTetrominoNumber];
    }
    private Vector2 selectTetrominoAnchorSpawnPosition(TetrominoShape tetrominoShape){
        int x = 0, y = 0;
        switch(tetrominoShape){
            case S:
            case L:
            case T:
            case O:
            case J:
            case Z:
                x = 4;
                y= 1;
                break;
            case I:
                x = 4;
                break;
        }
        return new Vector2(x, y);
    }

    private void fallTetromino() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        this.tetrominoTimer += deltaTime;
        if (this.tetrominoTimer >= this.gravity) {
            this.tetrominoHolder.getValue().setAnchorLogicalPositionY(this.tetrominoHolder.getValue().getAnchorLogicalPositionY() + 1);
            this.tetrominoRenderer.updateTetrominoShape();
            this.tetrominoTimer = 0;
        }
    }

    private void lockTetromino(){
        this.gameboard.updateLayout();
        this.tetrominoHolder.setValue(null);
        this.buildNewTetromino();
    }

    @Override
    public void create() {
        this.assetManager = new AssetManager();
        this.assetManager.load("image_packs/tetris.atlas", TextureAtlas.class);
        this.assetManager.finishLoading();

        TextureAtlas tetrisAtlas = this.assetManager.get("image_packs/tetris.atlas", TextureAtlas.class);
        this.tetrisRenderAssets.putRegion(AssetIdentifier.GAMEBOARD, tetrisAtlas.findRegion("Gameboard"));
        this.tetrisRenderAssets.putRegion(AssetIdentifier.O, tetrisAtlas.findRegion("OShape"));
        this.tetrisRenderAssets.putRegion(AssetIdentifier.S, tetrisAtlas.findRegion("SShape"));
        this.tetrisRenderAssets.putRegion(AssetIdentifier.Z, tetrisAtlas.findRegion("ZShape"));
        this.tetrisRenderAssets.putRegion(AssetIdentifier.L, tetrisAtlas.findRegion("LShape"));
        this.tetrisRenderAssets.putRegion(AssetIdentifier.J, tetrisAtlas.findRegion("JShape"));
        this.tetrisRenderAssets.putRegion(AssetIdentifier.T, tetrisAtlas.findRegion("TShape"));
        this.tetrisRenderAssets.putRegion(AssetIdentifier.I, tetrisAtlas.findRegion("IShape"));

        this.hardLevelThemeMusic = Gdx.audio.newMusic(Gdx.files.internal("hard_level_music.mp3"));
        this.easyLevelThemeMusic = Gdx.audio.newMusic(Gdx.files.internal("easy_level_music.mp3"));

        /*
         * Game music configurations
         */
        this.easyLevelThemeMusic.setLooping(true);
        this.easyLevelThemeMusic.setVolume(1.5f);
        this.easyLevelThemeMusic.play();

        this.batch = new SpriteBatch();
        /*
         * Orthographic camera is used by default.
         */
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        this.viewport = new FitViewport(1920, 1080, camera);
        this.mainFont = new BitmapFont();
        this.setScreen(new GameScreen(this));

        this.gameboardRenderer = new GameboardRenderer(batch, viewport, this.gameboard, this.tetrisRenderAssets);
        this.tetrominoRenderer = new TetrominoRenderer(batch, this.tetrisRenderAssets, this.tetrominoHolder, this.gameboardRenderer);
        this.controlManager = new ControlManager(this.tetrominoHolder, this.tetrominoRenderer, this.gameArbiter);
    }
    @Override
    public void render() {
        super.render();
        this.manageGameSession();
    }

    @Override
    public void dispose() {
        batch.dispose();
        mainFont.dispose();
        easyLevelThemeMusic.dispose();
        hardLevelThemeMusic.dispose();
        if(this.assetManager != null){
            this.assetManager.dispose();
        }
    }

    @Override
    public void onClearedLines() {

    }
}
