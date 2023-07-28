fun hello() {
    println("Hello")
}

fun hello(name: String) {
    println("Hello $name")
}

fun doubleAge(age: Int): Int {
    return age * 2
}

fun doubleAge(age: Int): Int = age * 2