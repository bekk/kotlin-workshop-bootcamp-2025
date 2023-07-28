fun Int.isEven() = this.mod(2) == 0
fun Int.isOdd() = this.mod(2) == 1

println("7 er partall? ${7.isEven()}")
println("7 er oddetall? ${7.isOdd()}")