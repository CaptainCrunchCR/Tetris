package io.github.tetris.packers;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import java.io.IOException;

public class TetrisPacker {
    public static void main (String[] args) throws IOException {
        Settings settings = new Settings();
        settings.minHeight = 16;
        settings.minWidth = 16;
        settings.maxHeight = 2048;
        settings.maxWidth = 2048;
        settings.alphaThreshold = 0;
        settings.filterMin = Texture.TextureFilter.Nearest;
        settings.filterMag = Texture.TextureFilter.Nearest;
        settings.paddingX = 2;
        settings.paddingY = 2;
        settings.wrapX = Texture.TextureWrap.ClampToEdge;
        settings.wrapY = Texture.TextureWrap.ClampToEdge;
        settings.scale = new float[]{1};
        settings.scaleSuffix = new String[]{""};
        settings.scaleResampling = new TexturePacker.Resampling[]{TexturePacker.Resampling.bicubic};
        settings.edgePadding = true;
        settings.bleed = true;
        settings.pot = true;
        settings.alias = true;
        settings.ignoreBlankImages = true;
        settings.limitMemory = true;
        TexturePacker.process(settings, "images", "assets/image_packs", "tetris");
    }
}
