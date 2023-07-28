package no.bekk.game.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator

fun fontWithSize(size: Int): BitmapFont {
    val fontGenerator = FreeTypeFontGenerator(Gdx.files.internal("font/JetBrainsMono-Regular.ttf"))
    val font = fontGenerator.generateFont(
        FreeTypeFontGenerator.FreeTypeFontParameter().also { it.size = size }
    ).also {
        it.region.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }
    return font
}