fun multiplyAllNumberImperativ(factor: Float, numbers: List<Float>): List<Float> {
    val newNumbers = MutableList<Float>
    for(val number in numbers) {
        newNumbers.add(number * factor)
    }
    return newNumbers
}

fun multiplyAllNumberFunctionally(factor: Float, numbers: List<Float>) =
    numbers.map { it * factor }