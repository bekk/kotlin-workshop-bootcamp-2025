fun createNewSpriteMutable(xPos: Float, yPos: Float) {
    sprites.add(Sprite(xPos, yPos))
}

fun createNewSpriteMutable(xPos: Float, yPos: Float) {
    val mutableSprites = sprites.toMutableList()
    mutableSprites.add(Sprite(xPos, yPos))
    sprites = mutableSprites
}

fun createNewSpriteMutable(xPos: Float, yPos: Float) {
    sprites = sprites + Sprite(xPos, yPos)
}
