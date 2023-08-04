# Lage ditt første spill med LibGDX og kotlin

Du kan finne en presentasjonen som hører til workshopen under [docs](docs).

Denne workshopen er delt inn i to deler: den første delen gir deg en generell introduksjon til noen viktige konsepter i Kotlin, før vi skal lage et spill i del to! Dersom du har vært borti Kotlin før, må du gjerne hoppe over del en.

Og ikke glem, bruk coachene og kollegaene dine aktivt! Vi er her for å hjelpe 🚀

## Data classes

En `data class` er en klasse kun ment til å holde på data.
Når du definerer en dataklasse får du en del funksjonalitet gratis, som f.eks. `toString`, `equals`, `hashCode` og `copy`.

Oppgave:

Åpne filen i introduction som heter [DataClass.kt](src/main/kotlin/no/bekk/introduction/DataClass.kt). Her ligger det en klasse som heter `Konsulent`, og en main funksjon.

1. Kjør main funksjonen, og se hva som skjer.
1. Gjør `Konsulent` om til en `data class` og kjør main funksjonen igjen. Hva skjer nå, og hvorfor?

<details><summary> Løsningsforslag 🤠 </summary>
Nå fungerer `println` mye bedre, og vi får en fin utskrift av objektet vårt.
   Det er fordi default implementasjonen for `toString` i `Any` (som alle klasser arver fra) er å skrive ut klassenavnet og en hashkode.
   Når vi gjør `Konsulent` til en `data class` får vi en implementasjon av `toString` som skriver ut alle feltene i klassen.

`==` gjør nå en strukturell sammenligning, og vi får `true` når vi sammenligner to konsulenter med samme navn.
For en `class` gjør `==` bare en referanse-sammenligning, og vi får `false` når vi sammenligner to separate objekter selv om de har samme innhold.

Se mer: https://kotlinlang.org/docs/data-classes.html

</details>

## Mutable vs Immutable

I Kotlin er man ofte opptatt av `mutability` og `immutability`, eller "muterbarhet" og "ikke-muterbarhet", som referer til hvorvidt dataen kan endres etter at den er opprettet.
Fordelen med å gjøre så mye som mulig `immutable` er at koden ofte blir mer lesbar og lettere å debugge, fordi
man alltid kan resonnere rundt verdien til en variabel utifra hvordan den ble opprettet, uten å tenke på om den har blitt endret av koden senere.
Når man jobber med ikke-muterbar data er måten man gjør oppdateringer på å bruke operasjoner som lager en kopi av dataen med de ønskede endringen.

Oppgavene løses i fila [Mutability.kt](src/main/kotlin/no/bekk/introduction/Mutability.kt)

**Oppgave 1:**

1. Kommenter inn linjen med `sondre.name`, og undersøk feilen du får. Bruk så `copy` for å lage et nytt
   objekt
2. Bruk så `copy` for å lage et nytt person-objekt med ditt navn.

**Oppgave 2:**

I standardbiblioteket til Kotlin skiller man på datastrukturer som er muterbare og de som ikke er det. F.eks:
finnes det både `List<T>` og `MutableList<T>`. Begge disse er generiske lister, men `List` implementerer ikke funksjoner som `add` og `remove`.

1. Kommenter inn linjen under `numbers`, og fiks feilen.


<details><summary>Løsningsforslag til oppgave 1 🤠</summary>

```kotlin
val sondre = Person("Sondre")
val gaute = sondre.copy(name = "Gaute")

println(gaute) // -> Person(name=Gaute, age=26)
```

</details>

<details><summary>Løsningsforslag til oppgave 2 🤠</summary>

```kotlin
val numbers = mutableListOf(1, 2, 3)
numbers.add(4)
```

Det å bruke ikke-muterbare lister blir enklere når
man er komfortabel med "Higher Order Functions" som vi skal se på senere i workshopen.

</details>

## Functions

Funksjoner i kotlin defineres med `fun`-nøkkelordet, og kan ha parametere og returverdier.

```kotlin
fun add(a: Int, b: Int): Int {
    return a + b
}
```

Man kan også gi et parameter en defaultverdi ved å skrive `= <verdi>` etter typen som dette: `a: Int = 0`.

Oppgave:

Åpne [Funksjoner.kt](src/main/kotlin/no/bekk/introduction/Funksjoner.kt) og legg til en funksjon på `BekkAnsatt`-klassen som printer "Hallo, name" + valgfri suffix. Suffixen skal ha en defaultverdi.

<details><summary> Løsningsforslag 🤠 </summary>

```kotlin
fun greet(suffix: String = "!") {
    println("Hallo, $name $suffix")
}
```

_Løsningen bruker [`string templates`](https://kotlinlang.org/docs/java-to-kotlin-idioms-strings.html#concatenate-strings) for å sette sammen meldingen uten å bruke `+`_

</details>

## Lambda og bruken av `it`

Kotlin støtter såkalte `anonyme funksjoner` (også kjent som `lambda`).
Dette er funksjoner som ikke har noe navn og som er nyttige når de skal sendes som argumenter til andre funksjoner.
Et eksempel er `map` funksjonen til lister. Den tar en funksjon som argument og bruker den til å endre alle elementene i listen.

```kotlin
listOf(1, 2, 3).map({ number -> number * 2 }) // [2,4,6]
```

Standardbiblioteket til Kotlin bruker lambda ganske mye, og språket har en del syntaktisk støtte for å gjøre det enklere å bruke.
Hvis en lambda kun har ett argument kan man droppe å gi argumentet et navn og referere til det som `it` inne i lambdaen.

```kotlin
listOf(1, 2, 3).map({ it * 2 }) // [2,4,6]
```

Og hvis en lambda er det siste argumentet til en funksjon kan den plasseres utenfor parentesene.

```kotlin
listOf(1, 2, 3).map { it * 2 } // [2,4,6]
```

## Higher Order Functions

Store deler av det vi gjør som utviklere er å hente data, manipulere den og deretter bruke den videre i applikasjonene våre.
I Kotlin finnes det mange "higher order functions" som gjør dette veldig mye lettere enn i Java.
Blant annet har vi funksjoner for å endre lister (map) eller filtrerer bort ting vi ikke trenger (filter).

``` kotlin
listeMedBekkKonsulenter.filter { it.name != "Ingrid" }
```

Oppgave:

Åpne filen som heter [HighOrderFunction.kt](src/main/kotlin/no/bekk/introduction/HighOrderFunction.kt):

1. Gå igjennom `coacher2023`-listen, finn navnene Johan og Ragnhild, og lag en ny liste hvor Johan er i BMC og Ragnhild er i design. Kall listen "realCoacher2023".
1. Bruk den nye listen, og lag en egen liste "teknologiCoacher" for coachene som er i teknologi-avdelingen.
1. Bruk `coacher2023`-listen, og summer opp hvor mange år de har jobbet til sammen.

Bruk main-funksjonen til å sjekke at du får riktig resultat.

<details><summary> Løsningsforslag 🤠 </summary>

``` kotlin
val realCoacher2023 = coacher2023.map {
    when (it.name) {
        "Johan" -> it.copy(avdeling = Avdeling.BMC)
        "Ragnhild" -> it.copy(avdeling = Avdeling.DESIGN)
        else -> it
    }
}

val teknologiCoacher = realCoacher2023.filter {
    it.avdeling == Avdeling.TEKNOLOGI
}

coacher2023.sumOf { it.yearsInBekk }
```

Du kan lese mer om high order functions i Kotlin [her](https://kotlinlang.org/docs/lambdas.html)
Her bruker vi `when`-uttrykket, som ikke ble dekket i presentasjonen. Det kan du lese mer om [her](https://kotlinlang.org/docs/control-flow.html#when-expression).
</details>

## Extension Functions

Noen ganger har vi behov for spesialtilpasset funksjonalitet på en klasse som vi ikke har tilgang til å endre, f.eks. de innebygde klassene `Int` eller `String`.
Da kan du skrive en spesiell type funksjon som heter "Extension Functions".

Funksjonen kan skrives på følgende måte:

``` kotlin
fun <Klasse>.<funksjonsnavn>(<argumenter>): <return-type> {
    // Gjør noe
}
```

For å refere til instansen av klassen bruker vi `this`.

```kotlin
// Kjedelig:
fun double(x: Int) {
    return x * 2
}

val four = double(2)

// Wow, kult 🤩
fun Int.triple() {
  return this * 3
}

val six = 2.triple()
```

Extension functions kan også gjøre det mer leselig ved at man kan "chaine" funksjonskallene.

```kotlin
val unreadableMess = square(triple(increase(double(2))))

val ahhMuchBetter = 2
    .double()
    .increase()
    .triple()
    .square()
```

Dette kan bli spesielt nyttig når man håndterer null-verdier.

```kotlin
// 🤮
val konsulentMaybe: Konsulent? = getKonsulent()
val konsulentInfo = if (konsulentMaybe) getInfo(konsulentMaybe) else null

// 😍
val konsulentInfo = getKonsulent()?.getInfo()
```

Oppgave:
Oppgavene ligger i fila [ExtensionFunctions.kt](src/main/kotlin/no/bekk/introduction/ExtensionFunctions.kt).

<details>
<summary> Løsningsforslag 🤠 </summary>

```kotlin
// Oppgave 1
fun BootcampCoach.getInfo(): String {
    return "${this.name} er i avdeling ${this.avdeling} og har jobbet ${this.yearsInBekk} år i Bekk"
}

// Oppgave 2
fun BootcampCoach.hasWorkedLongerThan(otherCoach: BootcampCoach): Boolean {
    return this.yearsInBekk > otherCoach.yearsInBekk
}

// Oppgave 3
fun BootcampCoach.addYears(years: Int): BootcampCoach {
    return BootcampCoach(this.name, this.avdeling, this.yearsInBekk + years)
}

fun BootcampCoach.withAvdeling(avdeling: Avdeling): BootcampCoach {
    return BootcampCoach(this.name, avdeling, this.yearsInBekk)
}
// i main:
print(ingrid
    .addYears(1)
    .withAvdeling(Avdeling.BMC)
    .getInfo()
)
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
hjelpemetoden `Rectangle.move` for å flytte spilleren. Veriabelen `delta` som man får
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
