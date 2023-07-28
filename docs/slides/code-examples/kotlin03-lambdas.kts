val hello = "Hello!"

hello.let { word ->
    word.uppercase()
}

hello.let {
    it.uppercase()
}
