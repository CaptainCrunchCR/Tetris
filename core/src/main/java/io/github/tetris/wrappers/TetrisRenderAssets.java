package io.github.tetris.wrappers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ObjectMap;

public class TetrisRenderAssets {
    private final ObjectMap<AssetIdentifier, TextureRegion> gameRegions;

    public TetrisRenderAssets(){
        this.gameRegions = new ObjectMap<>();
    }

    public void putRegion(AssetIdentifier assetIdentifier, TextureRegion textureRegion){
        this.gameRegions.put(assetIdentifier, textureRegion);
    }

    public TextureRegion getRegion(AssetIdentifier assetIdentifier){
        return this.gameRegions.get(assetIdentifier);
    }

    public boolean hasRegion(AssetIdentifier assetIdentifier){
        return this.gameRegions.containsKey(assetIdentifier);
    }
}
