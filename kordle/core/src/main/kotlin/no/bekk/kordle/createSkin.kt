package no.bekk.kordle

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import ktx.assets.toInternalFile
import ktx.freetype.freeTypeFontParameters
import ktx.freetype.registerFreeTypeFontLoaders
import ktx.style.label

fun createSkin(firstScreen: FirstScreen): Skin {
    val assetManager = initiateAssetManager()

    assetManager.load(
        "sourceSans30.ttf",
        BitmapFont::class.java,
        freeTypeFontParameters("fonts/source-sans-3/SourceSans3-ExtraBold.ttf") {
            size = 30
            color = Color.WHITE
        }
    )
    assetManager.load(
        "sourceSans24.ttf",
        BitmapFont::class.java,
        freeTypeFontParameters("fonts/source-sans-3/SourceSans3-Bold.ttf") {
            size = 24
            color = Color.WHITE
            characters = FreeTypeFontGenerator.DEFAULT_CHARS + "⌫✓"
        }
    )
    assetManager.finishLoading()

    return Skin("skins/new/KordleNew.json".toInternalFile()).apply {
        add("sourceSans30", assetManager["sourceSans30.ttf", BitmapFont::class.java])
        add("sourceSans24", assetManager["sourceSans24.ttf", BitmapFont::class.java])
        label("small") {
            font = getFont("sourceSans24")
            fontColor = Color.WHITE
        }
        label("large") {
            font = getFont("sourceSans30")
            fontColor = Color.WHITE
        }
    }
}

fun initiateAssetManager(): AssetManager {
    val assetManager = AssetManager()
    // Calling registerFreeTypeFontLoaders is necessary in order to load TTF/OTF files:
    assetManager.registerFreeTypeFontLoaders()
    return assetManager
}
