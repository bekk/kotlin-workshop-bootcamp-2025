fun hello(name: String, age: Int) {
    val greeting = if (age > 30) "Hello" else "Yo"
    println("$greeting $name")
}

hello("Vegard", 33)               // "Hello Vegard"
hello(name = "Vegard", age = 25)  // "Yo Vegard"