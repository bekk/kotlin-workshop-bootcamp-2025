# Lage ditt første spill med LibGDX og kotlin

Du kan finne en presentasjonen som hører til workshopen under [docs](docs).

Denne workshopen er delt inn i to deler: den første delen gir deg en generell introduksjon til noen viktige konsepter i Kotlin, før vi skal lage et spill i del to! Dersom du har vært borti Kotlin før, må du gjerne hoppe over del en.

Og ikke glem, bruk coachene og kollegaene dine aktivt! Vi er her for å hjelpe 🚀

## Variabler

Kotlin har to nøkkelord for å definere variabler: `val` og `var`. 
`val` brukes for å definere en variabel som ikke kan endres etter at den er opprettet, altså en konstant. 
`var` brukes for å definere en variabel som kan endres.

Oppgaver:

Åpne filen i introduction som heter [Variabler.kt](src/main/kotlin/no/bekk/introduction/Variabler.kt).

1. Prøv å kjøre `main`-funksjonen. Hva får du ut i konsollen?
2. Bytt ut `var` med `val` på linje 4, og kjør `main`-funksjonen igjen. Hva skjer nå? Hvorfor er dette ikke lov?
3. Skriv om koden slik at du fortsatt får samme utskrift som i del 1, uten å bruke `var` på linje 4.

<details><summary> Løsningsforslag 🤠 </summary>

1. Utskriften i konsollen skriver ut :
```kotlin
Verdien til tall1 er: 1
Verdien til tall1 pluss 1 er: 2
Verdien til tall1 pluss 1 delt på to er: 1
```

2. Koden kompilerer ikke lengre og en får en feilmelding som sier at `val` ikke kan endres. Dette er fordi `val` kan ikke defineres på ny etter at den har blitt definert.
3. Løsningsforslag:
```kotlin
val tall1 = 1
println("Verdien til tall1 er: $tall1")
val tall2 = tall1 + 1
println("Verdien til tall1 pluss 1 er: $tall2")
val tall3 = tall2 / 2
println("Verdien til tall1 pluss 1 delt på to er: $tall3")
```

</details>

## Funksjoner

Funksjoner i kotlin defineres med `fun`-nøkkelordet, og kan ha parametere og returverdier.

```kotlin
fun add(a: Int, b: Int): Int {
    return a + b
}
```

Man kan også gi et parameter en defaultverdi ved å skrive `= <verdi>` etter typen som dette: `a: Int = 0`.

Oppgaver:

Åpne filen i introduction som heter [Funksjoner.kt](src/main/kotlin/no/bekk/introduction/Funksjoner.kt)

1. Lag en funksjon som heter `add` som tar to heltall som parametere og returnerer summen av dem.
2. Lag en ny funksjon som heter `addWithDefault` som tar to heltall som parametere, men det andre parameteret skal ha en defaultverdi på 0. Denne funksjonen skal returnere summen av de to tallene.
3. Skriv om funksjonen `addWithDefault` slik at du ikke trenger å bruke `return`-nøkkelordet eller krøllparenteser. Dette kan du gjøre ved å bruke en [single-expression function](https://kotlinlang.org/docs/functions.html#single-expression-functions).

<details><summary> Løsningsforslag 🤠 </summary>

Løsningsforslag til oppgave 1:

```kotlin
fun add(a: Int, b: Int): Int {
    return a + b
}
```

Løsningsforslag til oppgave 2:

```kotlin
fun addWithDefault(a: Int, b: Int = 0): Int {
  return a + b
}
```

Løsningsforslag til oppgave 3:

```kotlin
fun addWithDefault(a: Int, b: Int = 0) = a + b
```

</details>

## Nullabillity
I Kotlin er det en viktig forskjell på typer som kan være `null` og typer som ikke kan være `null`.
Typer som kan være `null` har et `?`-tegn etter typen, f.eks. `String?` er en type som kan være `null`, mens `String` er en type som ikke kan være `null`.
Dette er en viktig forskjell fra Java, hvor alle typer kan være `null` med mindre de er primitive typer som `Int`, `Boolean`, etc.

I kotlin så må en eksplisitt håndtere `null`-verdier, og det finnes flere måter å gjøre dette på:
1. **Null-sjekk**: Du kan sjekke om en verdi er `null` ved å bruke `if`-setninger.
   ```kotlin
   val navn: String? = null
   if (navn != null) {
       println(navn.length)
   }
   ```
2. **Safe call operator**: Du kan bruke `?.` for å kalle en metode på en verdi som kan være `null`. Hvis verdien er `null`, vil hele uttrykket returnere `null` uten å kaste en feil.
   ```kotlin
    val navn: String? = null
    println(navn?.length) // Dette vil skrive ut null uten å kaste en feil
    ```

3. **Elvis-operator**: Du kan bruke `?:` for å gi en defaultverdi hvis verdien er `null`.
    ```kotlin
    val navn: String? = null
    println(navn?.length ?: 0) // Dette vil skrive ut 0 hvis navn er null
    ```

4. **!!-operator**: Du kan bruke `!!` for å kaste en `NullPointerException` hvis verdien er `null`. Dette bør brukes med forsiktighet, da det kan føre til krasj i programmet.
    ```kotlin
    val navn: String? = null
    println(navn!!.length) // Dette vil kaste en NullPointerException hvis navn er null
    ```

Oppgaver: 

Åpne filen i introduction som heter [Nullability.kt](src/main/kotlin/no/bekk/introduction/Nullabillity.kt).

1. Kjør `main`-funksjonen. Hva skjer og hvorfor?
2. Kommenter ut kodelinje 9 og bytt ut typen på parameteret `streng` fra `String?` til `String`. Hva skjer når du kjører `main`-funksjonen nå? Hvorfor er det slik?
3. Skriv en funksjon som tar inn et parameter av typen `String?` og returnerer lengden dersom strengen ikke er null. Dersom den er null, skal funksjonen returnere `null`.
4. Skriv en funksjon som tar inn et parameter av typen `String?` og returnerer lengden dersom strengen ikke er null. Dersom den er null, skal funksjonen returnere tallet 0.

<details><summary> Løsningsforslag 🤠 </summary>

Oppgave 1:
Når du kjører `main`-funksjonen, vil den kaste en `NullPointerException` fordi vi prøver å kalle `length` på en `null`-verdi.


Oppgave 2:
Når du kommenterer ut kodelinje 9 og endrer typen på parameteret `streng` fra `String?` til `String`, vil koden kompilere og skrive ut 
```kotlin
Lengden på streng1 er: 23
```

Oppgave 3:
```kotlin
fun skrivUtStringlengde(streng: String?): Int? {
    return streng?.length
}
```


Oppgave 4:
```kotlin
fun skrivUtStringlengde(streng: String?): Int? {
  return streng?.length ?: 0
}
```

</details>


## Klasser
I Kotlin defineres klasser med `class`-nøkkelordet. En klasse kan ha egenskaper (properties) og metoder (functions).
Egenskaper defineres med `val` eller `var`, og metoder defineres med `fun`. Her er et eksempel på en klasse:

```kotlin
class Person(val name: String, var age: Int) {
    fun introduce() {
      println(
        "Hei, jeg heter $name og er $age år gammel")
    }
}
```

Oppgaver:

Åpne filen i introduction som heter [Klasser.kt](src/main/kotlin/no/bekk/introduction/Klasser.kt).

1. Lag en klasse som heter `Coach` som har følgende egenskaper:
   - `navn`: String
   - `aarIBekk`: Int
   - `favorittaktivitet`: String
2. Lag en funksjon i `Coach`-klassen som heter `skrivUtInfo` som skriver ut navnet, antall år i Bekk og hva favorittaktivitet er deres favorittaktivitet.


<details><summary> Løsningsforslag 🤠 </summary>

Oppgave 1:
```kotlin
class Coach(
    val navn: String,
    val aarIBekk: Int,
    val favorittaktivitet: String,
)
```

Oppgave 2:
```kotlin
class Coach(
    val navn: String,
    val aarIBekk: Int,
    val favorittaktivitet: String,
){
    fun skrivUtInfo(){
        println("Navn: $navn, År i Bekk: $aarIBekk, Favorittaktivitet: $favorittaktivitet")
    }
}
```

</details>

## Dataklasser

En `data class` er en klasse kun ment til å representere data og har en del funksjonalitet som er nyttig for å jobbe med data.
Når du definerer en dataklasse får du en del funksjonalitet gratis, som f.eks. `toString`, `equals`, `hashCode` og `copy`.
Dette gjør at sammenligning av objekter blir enklere, og at du kan lage kopier av objekter med endrede verdier uten å måtte skrive mye kode selv.

Oppgaver:

Åpne filen i introduction som heter [DataClass.kt](src/main/kotlin/no/bekk/introduction/DataClass.kt). Her ligger det en klasse som heter `Konsulent`, og en main funksjon.

1. Kjør main funksjonen, og se hva som skjer.
2. Gjør `Konsulent` om til en `data class` og kjør main funksjonen igjen. Hvilke to forskjeller ser du ser du i konsollutskriften? Hvorfor er det slik? 

<details><summary> Løsningsforslag 🤠 </summary>
I konsollutskriften ser vi to endringer:

1. Vi får en fin utskrift av objektet vårt, i stedet for at det bare står `Konsulent@<hashkode>`.
2. Vi får `true` når vi sammenligner to konsulenter med samme navn, i stedet for `false`.

Endringen i objektutskriften er fordi `Konsulent` alle vanlige klasser (`class`) har en default implementasjon av `toString` som skriver ut klassenavnet og en hashkode.
Dette fører til at utskriften av `println(konsulent)` blir noe sånt som `Konsulent@6d06d69c`. Instanser av `data class` derimot, 
har en implementasjon av `toString` som skriver ut alle feltene i klassen, slik at utskriften blir noe sånt som `Konsulent(name=Patrick)`.

Endringen i sammenligningen er fordi vanlige klasser (`class`) har en default implementasjon av `==` (eller `equals` som det heter) 
som bare sammenligner referansene til objektene, altså om de er det samme objektet i minnet.
`data class` derimot, har en implementasjon av `equals` som sammenligner innholdet i objektene, altså om de har samme verdi for alle feltene.
Dette fører til at når Konsulent er en `data class` så får vi `true` dersom vi sammenligner to objekter med samme navn.

Se mer: https://kotlinlang.org/docs/data-classes.html

</details>

## Mutable vs Immutable

I Kotlin er man ofte opptatt av `mutability` og `immutability`, eller "muterbarhet" og "ikke-muterbarhet". 
Disse konseptene referer til hvorvidt dataen i et objekt kan endres etter at det objektet har blitt opprettet.
En stor fordel med å gjøre ting `immutable` er at koden kan bli mer lesbar og lettere å debugge. Eksempler på hvordan dette kommer frem er:

1. Ett ikke-muterbart objekt du bruker kan du alltids være sikker på at ikke kommer til å endre seg og kan heller ikke bli endret noe annet sted i koden. Dette reduserer utilsiktede utfall da det er vanskeligere å påvirke annen funksjonalitet ved "uhell" dersom den funksjonaliteten også bruker samme data.  
2. En kan alltids spore verdier tilbake til der de ble opprettet. Dette gjør det lettere å forstå hvor i koden en verdi ble opprettet og hvor den har blitt brukt og hvor den skal brukes videre.

I standardbiblioteket til Kotlin skiller man på datastrukturer som er muterbare og de som ikke er det. F.eks:
det finnes både `List<T>` og `MutableList<T>`. Begge disse typene er lister, men typen `List` er ikke-muterbar og lar deg dermed ikke legge til eller fjerrne
elementer fra lista. Typen `MutableList<T>` lar deg bruke funksjonene `add` og `remove` for å legge til og fjerne elementer fra lista.

Dersom en har et objekt som en ønsker å endre på, så er det vanlig å lage en kopi av objektet og deretter utføre endringene en ønsker å gjøre. F.eks.

```kotlin 
val tall = listOf(1,2,3)
val flereTall = tall + 4 // Dette lager en kopi av lista `tall` og legger til 4 i den kopierte lista, uten å endre på `tall`.

println(tall) // -> [1, 2, 3]
println(flereTall) // -> [1, 2, 3, 4]
```

Egendefinerte dataklasser har også støtte for dette gjennom `copy`-funksjonen som genereres automatisk når du definerer en `data class`. 
Denne gir deg muligheten til å lage en kopi av et objekt med noen av feltene endret, mens de andre feltene forblir uendret.

```kotlin
val patrick = Person(name = "Patrick", age = 27)

val eldrePatrick = patrick.copy(age = 28) // Dette lager en kopi av `patrick` som er 28 år gammel, men beholder navnet "Patrick".

```
Oppgaver:

Åpne filen i introduction som heter [Mutability.kt](src/main/kotlin/no/bekk/introduction/Mutability.kt)

**Oppgave 1:**

1. Kommenter inn linjen med `patrick.name`, og undersøk feilen du får. Hvorfor er ikke dette lov? 
2. Hvordan kan du opprette et nytt person-objekt med samme verdi for `age` men med et annet navn?"

**Oppgave 2:**

Din oppgave er å få skrevet ut en liste med tallene fra lista `viktigeTall` men med tallet 4 lagt på slutten.
Dette skal da gjøres uten å endre på hvordan funksjonen `funkSjonalitetSomIkkeLikerTalletFire` fungerer.

1. Kommenter inn linja `skrivUtTallListeMedFire(viktigeTall)` og kjør koden. Hvorfor kræsjer koden? 
2. Skriv om koden i funksjonen `skrivUtTallListeMedFire` slik at du ikke endrer verdiene i lista `viktigeTall` men fortsatt får skrevet ut lista med tallet 4 på slutten.


<details><summary>Løsningsforslag til oppgave 1 🤠</summary>

Linja `patrick.name = "Sondre"` er ikke lov fordi `name` er definert som en ikke-muterbar verdi med nøkkelordet `val` og kan dermed ikke endres etter at objektet er opprettet.

Løsningsforslag:
```kotlin
val patrick = Person("Patrick", 27)
val sondre = patrick.copy(name = "Sondre")

println(sondre) // -> Person(name=Sondre, age=27)
```

</details>

<details><summary>Løsningsforslag til oppgave 2 🤠</summary>

Koden kræsjer fordi vi legger til tallet 4 i den muterbare lista `viktigeTall` i funksjonen `skrivUtTallListeMedFire`.
Dette fører til at koden kræsjer i funksjonen `funkSjonalitetSomIkkeLikerTalletFire`, da den funksjonen ikke krever at lista ikke kan ikkeholde tallet 4. 

Løsningsforslag:

```kotlin
fun skrivUtTallListeMedFire(viktigeTall: MutableList<Int>) {
  val viktigeTallMedFire = viktigeTall + 4 // Lager en kopi av lista uten å endre på den originale lista.
  println(viktigeTallMedFire) // -> [1, 2, 3, 4]
}
```

</details>

## Higher Order Functions og Lamdafunksjoner

Kotlin har støtte og legger veldig til rette for bruk av funksjoner som kalles `Higher Order Functions`.
Det høres kanskje litt stort ut, men en `Higher Order Function` er bare en funksjon tar en eller flere funksjoner som argumenter, 
eller returnerer en funksjon som resultat. Ett eksempel på en slik funksjon er `map`-funksjonen som finnes på lister i Kotlin:

```kotlin
fun doble(tall: Int): Int {
  return tall * 2
}

val tallListe = listOf(1, 2, 3)
val dobledeTall = tallListe.map ({tall -> doble(tall) }) // -> [2, 4, 6]
```

Her ser man hvordan `map`-funksjonen tar inn funksjonen `doble`, og bruker den til å transformere hvert element i listen.

For lesbarhetens skyld pleier en ofte å kombinere slike `higher order functions` med funksjoner uten navn, bedre kjent som`anonyme`- funksjoner eller `lambda`-funksjoner.

Vi kan f.eks. skrive om koden over til å bruke en `lambda` i stedet for å definere en egen funksjon:

```kotlin
val tallListe = listOf(1, 2, 3)
val dobledeTall = tallListe.map ({ tall -> tall * 2 }) // -> [2, 4, 6]
```

Standardbiblioteket til Kotlin bruker lambda ganske mye, og språket har en del syntaktisk støtte for å gjøre det enklere å bruke.
F.eks. En trenger ikke å navngi argumentene i en lambda og en kan referere til dem direkte ved å bruke `it` som et standardnavn istedenfor.

```kotlin
listOf(1, 2, 3).map({ tall ->  tall * 2 }) // [2, 4, 6]
listOf(1, 2, 3).map({ it * 2 }) // [2, 4, 6]
```

Og hvis en lambda er det siste argumentet til en funksjon kan en også droppe bruken av paranteser.

```kotlin
listOf(1, 2, 3).map { it * 2 } // [2, 4, 6]
```

Store deler av det vi gjør som utviklere er å hente data, manipulere den og deretter bruke den videre i applikasjonene våre.
I Kotlin finnes det mange "higher order functions" som gjør dette veldig mye lettere enn i Java. Andre eksempler inkluderer da

`filter`, for filtrering:
```kotlin
listOf(1, 2, 3).filter { it != 2 } // [1, 3]
```

`reduce`, for å slå sammen elementer i en liste basert på en funksjon:
```kotlin
listOf(1, 2, 3).reduce { tall1, tall2 -> tall1 + tall2 } // Plusser sammen alle tallene i lista, og returnerer 6 (Ekvivalent til sum)
```

`partition`, for å dele en liste i to basert på en betingelse:
```kotlin
val (tallUnderTo, tallMedToEllerMer) = listOf(1, 2, 3).partition { it < 2 }
println(tallUnderTo) // [1]
println(tallMedToEllerMer) // [2, 3]
```

`any`, for å sjekke om minst ett element i en liste oppfyller en betingelse:
```kotlin
val inneholderTalletTo = listOf(1, 2, 3).any { it == 2 }
println(inneHolderTalletTo) // true
```

Kraften i bruken av slike `higher order functions` og lambda-funksjoner er at de lar deg skrive kode som bryter ned oppgaver i mindre biter og dermed er lettere å lese.

F.eks. vi for å sjekke om en liste inneholder et positivt tall hvor dens kvadrat er større enn 20
 
```kotlin
listOf(-5,-2, 0, 2, 5)
  .filter { it > 0 } // Filtrer ut negative tall -> [2, 5]
  .map { it * it } // Kvadrer tallene -> [4, 25]}
  .any { it > 20 } // Sjekk om noen av tallene er større enn 20 -> true
```

Oppgave:

Åpne filen som heter [HighOrderFunction.kt](src/main/kotlin/no/bekk/introduction/HighOrderFunction.kt):

1. Bruk `coacher2023`-listen, og bruk lambdafunksjon(er) for å finne ut hvor mange år alle coachene i 2023 har jobbet i Bekk.
2. Bruk `coacher2023`-listen, og lag en liste for coachene som er i teknologi-avdelingen.
3. Bruk `coacher2023`-listen, og skriv kode for å lage en kopi av lista hvor Johan er i BMC-avdelingen og Ragnhild er i Design-avdelingen.

Bruk main-funksjonen til å sjekke at du får riktig resultat.

<details><summary> Løsningsforslag 🤠 </summary>

```kotlin
val antallAarIBekk = coacher2023.map { it.aarIBekk }.reduce { aarIBekk1, aarIBekk2 -> aarIBekk1 + aarIBekk2 }

val teknologiCoacher = coacher2023.filter { it.avdeling == Avdeling.TEKNOLOGI }

val endredeCoacher = coacher2023.map { coach ->
    when (coach.name) {
        "Johan" -> coach.copy(avdeling = Avdeling.BMC)
        "Ragnhild" -> coach.copy(avdeling = Avdeling.DESIGN)
        else -> coach
    }
}
```

Du kan lese mer om high order functions i Kotlin [her](https://kotlinlang.org/docs/lambdas.html)
Her bruker vi `when`-uttrykket, som ikke ble dekket i presentasjonen. Det kan du lese mer om [her](https://kotlinlang.org/docs/control-flow.html#when-expression).
</details>

## Extension Functions

Noen ganger har vi behov for spesialtilpasset funksjonalitet på en klasse som vi ikke har tilgang til å endre, f.eks. de innebygde klassene `Int` eller `String`.
Da kan du skrive en spesiell type funksjon som heter "Extension Functions".

Funksjonen kan skrives på følgende måte:

```kotlin
fun <Klasse>.<funksjonsnavn>(<argumenter>): <return-type> {
    // Gjør noe
}
```

For å refere til instansen av klassen bruker vi `this`. Ett eksempel på en slik funksjon er:

```kotlin
fun List<Int>.doble(): List<Int> = this.map { it * 2 }

listOf(1,2,3).doble() // -> [2, 4, 6]
```

Dersom en kombinerer extension functions med `higher order functions` og lambda-funksjoner kan en få veldig lesbar kode.
Ta f.eks. det tidligere eksempelet med å sjekke om en liste inneholder et positivt tall hvor kvadratet er større enn 20:


```kotlin
listOf(-5,-2, 0, 2, 5)
  .filter { it > 0 } // Filtrer ut negative tall -> [2, 5]
  .map { it * it } // Kvadrer tallene -> [4, 25]}
  .any { it > 20 } // Sjekk om noen av tallene er større enn 20 -> true
```

Dette kan skrives om til:

```kotlin
val List<Int>.fjernNegativeVerdier(): List<Int> = this.filter { it > 0 }
val List<Int>.kvadrerVerdier(): List<Int> = this.map { it * it }
val List<Int>.sjekkOmListaHarEnVerdiOver(verdi: Int): List<Int> = this.any { it > verdi }

listOf(-5,-2, 0, 2, 5)
  .fjernNegativeVerdier()
  .kvadrerVerdier()
  .sjekkOmListaHarEnVerdiOver(20) // -> true
```

Oppgave:
Oppgavene ligger i fila [ExtensionFunctions.kt](src/main/kotlin/no/bekk/introduction/ExtensionFunctions.kt).

1. Lag en extension function for `List<BootcampCoach>` som returnerer bare Coacher fra en avdeling.
2. Lag en extension function for `List<BootcampCoach>` som skriver ut navn, antall år i Bekk og avdeling for alle bootcampcoachene. 
3. Lag en extension function for `List<BootcampCoach>` som returnerer totalt antall år i Bekk for alle coachene i listen.
4. Lag en extension function for `List<BootcampCoach>` som øker antall år i Bekk for alle coachene i listen med et gitt antall år.


<details>
<summary> Løsningsforslag 🤠 </summary>

```kotlin
// Oppgave 1
fun List<BootcampCoach>.fraAvdeling(avdeling: Avdeling): List<BootcampCoach> {
  return this.filter { it.avdeling == avdeling }
}

// Oppgave 2
fun List<BootcampCoach>.skrivUtInfo() {
  this.forEach { coach ->
    println("${coach.name} er i avdeling ${coach.avdeling} og har jobbet ${coach.aarIBekk} år i Bekk")
  }
}

// Oppgave 3
fun List<BootcampCoach>.totaltAntallAarIBekk(): Int {
  return this.map { it.aarIBekk }.reduce { total, aar -> total + aar }
}

// Oppgave 4
fun List<BootcampCoach>.leggTilAar(aar: Int): List<BootcampCoach> {
  return this.map { coach ->
    coach.copy(
      aarIBekk = coach.aarIBekk + aar
    )
  }
}
```

Du kan lese mer om extension functions i [den offisielle Kotlin-dokumentasjonen](https://kotlinlang.org/docs/extensions.html).

</details>

# Lage ditt første spill med LibGDX og Kotlin

Gratulerer med vel overstått introduksjon til Kotlin! 🎉

Nå skal vi lage et spill! Du skal styre en firkant på skjermen. Firkanten skal
unngå andre firkanter som faller ned fra toppen av skjermen. Målet med oppgaven er å gjøre
deg litt kjent med et par viktige konsepter som du kan ta med deg inn i de litt mer kreative
oppgavene.

![Animasjon av spillet](docs/images/game-animation.gif)

Skjelettet av koden er allerede skrevet - og består i hovedsak av tomme metoder som
det er opp til deg å implementere i denne delen av workshopen. Vi tar det stegvis, og når
alle metodene er implementert ender man opp med et ferdig spill.

Koden vi skal jobbe finner du i filen [Main.kt](src/main/kotlin/no/bekk/game/Main.kt).

Løsningsforslag ligger i fila [Solution.kt](src/main/kotlin/no/bekk/game/solution/Solution.kt), men bruk den med omhu!  
Vær obs på at den inneholder løsningen på alle oppgavene, så ikke ødelegg moroa for deg selv.

## 1. Tegne en figur (spilleren) på skjermen.

Det første vi skal gjøre er å tegne noe på skjermen, og vi starter med selve spilleren
Start med å implementere metoden `drawPlayer` slik at den tegner spillere som en firkant
på skjermen. Du kan fritt velge farge du ønsker å bruke.

Spilleren er definert som et `Rectangle` som ligger i variabelen `this.player`. Du kan
endre start posisjon og størrelse på spilleren ved å endre verdiene denne variabelen
initialiseres med.

Du kan bruke hjelpemetoden `drawRectangle` for å tegne et rektangel på skjermen.

## 2. Styre spilleren med tastaturet

Det neste vi skal gjøre er å sørge for at man kan styre spilleren med tastaturet.
Posisjonen til spilleren er definert som en `Vector2` på `player` variabelen.
Du kan velge å enten endre manipulere X og Y verdiene på denne direkte eller å bruke
hjelpemetoden `Rectangle.move` for å flytte spilleren. Variabelen `delta` som man får
inn som argument er tiden siden forrige update, og kan brukes for å sørge for at man
får gjevn bevegelse uavhengig av update-rate. Dette kan man gjøre via å bruke den som
en faktor: `val moveDistance = movementSpeed * delta`.

For å sjekke tastatur-input kan man bruke funksjonen `Gdx.input.isKeyPressed`. F.eks.
kan man se om man holder nede `PIL OPP` med `Gdx.input.isKeyPressed(Input.Keys.UP)`.

Når man har fått spilleren til å bevege seg rundt på skjermen kan man legge til at
spilleren ikke skal få lov til å bevege seg utenfor skjermen. Bredde og høyde på skjermen
han man hente fra `EngineConfig.VIEWPORT_HEIGHT` og `EngineConfig.VIEWPORT_WIDTH`.

Posisjonen til spilleren er posisjonen til nedre venstre hjørne av rektangelet, og
det kan være fint å ta høyde for størrelsen på rektangelet når man skal holde spilleren
innenfor skjermen.

## 3. Lage bokser som spilleren må unngå

For at det skal bli et spill må det noe mer gameplay på plass. Så her er tanken at vi
skal ha noen bokser på starter på toppen av skjermen og "faller" nedover, og så er målet
å unngå å bli truffet av disse. For å få dette på plass må følgende ting implementeres:

- `shouldSpawnNewBlocks`: Denne metoden skal returnere true om det skal lages flere blokker
  for spilleren å unngå.
- `spawnNewBlock` Her skal den lage ny blokk(er). De nye blokkene skal ha en posisjon på
  toppen av skjermen, og et tilfeldig X-koordinat. Den nye blokken skal legges i listen
  `blocksToDodge`.
- `drawAllBlocksToDodge`: Denne skal tegne alle blokkene som ligger i `this.blocksToDodge`.
  Dette kan gjøres ganske likt som tegning av spilleren. Men bruk gjerne en annen farge.
- `handleMoveBlocks`: Her flytter man blokkene nedover på skjermen. I starten er det greit
  å bare flytte de med en konstant fart.

## 4. Kollisjonstesting

Om du starter main-metoden skal man når ting er implementert korrekt se at det faller ned noen
blokker fra toppen av skjermen. Men om styrer figuren din inn i en av de vil du se at det ikke
skjer noen ting. Det er som og blokkene ikke er der. Så det neste vi må få på plass er en
enkel kollisjonstest.

Om man ser ser i `update` metoden har den en sjekk på `playerIsColliding`. Hvis denne returnerer
true så kaller den `onGameLost()` som resetter spillet. Så målet nå er å implementere en sjekk i
`playerIsColliding` som sjekker om spilleren har kollidert med en av blokkene. `Rectangle.isCollidingWith`
kan brukes for å sjekke om 2 rektangler overlapper.

## 5. Ytelsesforbedring

Slik spillet er implementert nå vil det gjevnlig legges til nye blokker i listen `blocksToDodge`,
men de fjernes aldri. Over tid vil dette påvirke ytelsen både i form av at den bruker mer og mer minne
men også at den må gjøre operasjoner på fler og fler elementer som ikke lenger er relevante. Vi trenger
derfor en måte å rydde opp.

I `update`-metoden så kaller vi `removeBlocksOutOfBounds()`. Tanken er at denne skal fjerne alle blokker
som er utenfor skjermen, og derfor ikke lenger er relevante for spillet.

## 6. Ekstra features?

Vi kan nå gå videre med å legge til litt fler kule features i spillet. Her er det bare å bruke kreativiteten. Men under
kommer det noen forslag til ting man kan legge til. Se gjerne på ting i `examples`-mappen for inspirasjon til flere ting
man kan gjøre.

#### Vise gi en score til spilleren basert på hvor bra de gjorde det?

Implementere en måte å gi en score til spilleren som vises når de taper. F.eks. kan scored være hvor mange sekunder man
klarte seg. F.eks. kan denne vises midt på skjermen etter man tapte og så må man trykke på en knapp for å starte på nytt.

#### Øke vanskelighetsgraden over tid

En vanlig ting i slike spill er at vanskelighetsgraden øker over tid. Her er det mange ting man kan vurdere, som f.eks.
at man det kommer fler og fler blokker over tid. Eller at de beveger seg raskere. Kanskje de etterhvert også har ulik
størrelse og fart?

#### Bytte ut firkantene med bilder

Firkanter kan være litt kjedelig. Hva om man bytter ut firkantene med noen kule bilder i stedet? Her kan man se på
eksempelkoden [MovingGraphicModule.kt](src/main/kotlin/no/bekk/game/examples/MovingGraphicModule.kt) for hvordan
man kan tegne grafikk på skjermen.

## 7. Åpen oppgave

Til slutt har vi en litt åpen oppgave, hvor du kan lage ditt helt eget spill. Bygg på det du lærte
fra de andre oppgavene og se om du klarer å lage ett lite spill fra scratch. Ta gjerne kontakt med
coachene om du sitter fast eller trenger hjelp.

Forslag kan være å se på å lage noe som gamle klassikere som Pong eller Breakout. Eventuelt
kanskje man vil forsøke seg på en egen Flappy Bird?

For å komme i gang kan du starte med å lage en fil som inneholder koden under.

```kotlin
fun main() {
  Lwjgl3Application(AppRunner { MyGame() }, config)
}

class MyGame: AppModule {
  override fun update(delta: Float) {
  }

  override fun draw(delta: Float) {
  }
}
```
