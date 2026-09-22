package io.github.tetris.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.tetris.manager.GameManager;

public class GameScreen implements Screen {
    private final GameManager tetris;
    private final Texture backgroundTexture;
    public GameScreen(GameManager tetris){
        this.tetris = tetris;
        this.backgroundTexture = new Texture(Gdx.files.internal("game_background.png"));
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        tetris.viewport.apply();
        tetris.batch.setProjectionMatrix(tetris.viewport.getCamera().combined);
        tetris.batch.begin();
        tetris.batch.draw(this.backgroundTexture, 0, 0, tetris.viewport.getWorldWidth(), tetris.viewport.getWorldHeight());
        tetris.gameboardRenderer.render();
        tetris.tetrominoRenderer.render();
        tetris.controlManager.render(delta);
        tetris.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        tetris.viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }


    @Override
    public void dispose() {
        backgroundTexture.dispose();
    }
}
