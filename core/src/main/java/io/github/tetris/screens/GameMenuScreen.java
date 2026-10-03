package io.github.tetris.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import io.github.tetris.manager.GameManager;

public class GameMenuScreen implements Screen {
    protected final GameManager tetris;
    private Stage stage;
    private Table root;
    private Skin skin;
    private Texture menuBackground;
    public GameMenuScreen(GameManager tetris){
        this.tetris = tetris;
    }

    @Override
    public void show() {
        this.stage = new Stage(new ScreenViewport());
        this.root = new Table();
        this.root.defaults().padTop(400);
        this.root.setFillParent(true);
        this.stage.addActor(this.root);
        Gdx.input.setInputProcessor(this.stage);
        this.skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        this.menuBackground = new Texture(Gdx.files.internal("main_menu_background.png"));
        root.setBackground(new TextureRegionDrawable(new TextureRegion(this.menuBackground)));
        TextButton textButton = new TextButton("Play", this.skin, "small");
        textButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GameMenuScreen.this.tetris.startGame();
                GameMenuScreen.this.tetris.setScreen(new PlaySessionScreen(GameMenuScreen.this.tetris));
                return true;
            }
        });
        root.add(textButton).row();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        this.stage.act(delta);
        this.stage.draw();
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
        this.stage.dispose();
        this.skin.dispose();
        this.menuBackground.dispose();
    }
}
