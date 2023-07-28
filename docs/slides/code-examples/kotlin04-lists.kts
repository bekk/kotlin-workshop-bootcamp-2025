val ages = listOf(3, 5, 10)               // 3, 5, 10

val under10 = ages.filter { it < 10 }     // 3, 5
val older = ages.map { it * 3 }           // 9, 15, 30
val totalAge = ages.sum()                 // 18

val someAge = ages
    .filter { it > 4 }                    // 5, 10
    .map { it * 2 }                       // 10, 20
    .first()                              // 10
