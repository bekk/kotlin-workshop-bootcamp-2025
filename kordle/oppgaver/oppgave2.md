# Oppgave 2: Gjetting og foretningslogikk

Så langt i oppgavene har vi laget en serverapplikasjon som kan hente ut en tilfeldig oppgave.
Dessverre kan vi ende ikke utføre gjett på oppgaven, da vi ikke har laget noen logikk for å håndtere gjetting 😱
Dette er det vi skal gjøre i denne oppgaven!

Som nevnt tidligere, er det viktig at vi holder foretningslogikken samlet på samme sted. I spring
samles slik foretningslogikk i `services`, det vil si klasser som er annotert med `@Service`.
I denne opppgaven skal vi utvide `OppgaveService.kt` for å håndtere gjetting av oppgaver.

I fila [oppgave.kt](../shared/src/main/kotlin/no/bekk/kordle/shared/dto/oppgave.kt) finner du to klasser:

```kotlin
@Serializable
data class GjettOrdRequest(
    val oppgaveId: Int,
    val ordGjett: String
)


@Serializable
data class GjettResponse(
    val oppgaveId: Int,
    val alleBokstavtreff: List<BokstavTreff>
)
```

Oppgave 2.1: Sjekk bokstavgjett

I fila [oppgave.kt](../shared/src/main/kotlin/no/bekk/kordle/shared/dto/oppgave.kt) finner du en klasse:

```kotlin
@Serializable
data class BokstavTreff(
    val plassISekvensen: Int,
    val bokstavGjettet: Char,
    var erBokstavenIOrdet: Boolean,
    val erBokstavenPaaRettsted: Boolean
)
```

Denne klassen representerer et treff på en bokstav i et ord.

Oppgave:

1. Lag en funksjon `sjekkBokstavTreff` i `OppgaveService.kt` som tar inn to parametere:
    - ordIOppgave: String - Dette er ordet som er riktig for oppgaven.
    - ordGjettet: String- Dette er ordet som brukeren har gjettet.
      Funksjonen skal returnere en liste med `BokstavTreff`-objekter som representerer treffene for hver bokstav i
      gjetningen.

OBS:

- Husk spillregelen om at en bokstav kan ha flere treff i et ord, men at en skal bare gi tilbakemelding lik antall
  bokstaver med treff i gjetningen.

<details>
<summary> Løsningsforslag </summary>

```kotlin
private fun sjekkBokstavTreff(
    ordIOppgave: String,
    ordGjettet: String
): List<BokstavTreff> {
    val ordIOppgaveListe: MutableList<Char?> = ordIOppgave.lowercase().map { it }.toMutableList()
    val treff = ordGjettet.lowercase().mapIndexed { index, bokstav ->
        val hit = ordIOppgave[index] == bokstav
        if (hit) {
            ordIOppgaveListe[index] = null // Fjerner bokstaven fra ordet for å unngå dobbelttelling
        }
        BokstavTreff(
            plassISekvensen = index,
            bokstavGjettet = bokstav,
            erBokstavenIOrdet = hit,
            erBokstavenPaaRettsted = hit
        )
    }
    treff.forEachIndexed { index, treff ->
        if (treff.erBokstavenPaaRettsted) return@forEachIndexed
        val hitIndex = ordIOppgave.indexOfFirst { it == treff.bokstavGjettet }
        if (hitIndex != -1) {
            treff.erBokstavenIOrdet = true
            ordIOppgaveListe[hitIndex] = null
        }
    }
    return treff
}
```

</details>

## Oppgave 2.2: Foretningslogikk og databaseinteraksjon

Nå som vi har en funksjon som kan sjekke treff på bokstaver, er det på tide å lage en funksjon som håndterer
interaksjonen med databasen.

Oppgave:

1. Lag en funksjon `hentOppgave` i `OppgaveRepository.kt` som tar inn en `oppgaveId: Int` og returnerer en instans av
   `Oppgave`.
2. Lag en funksjon `gjettOrd` i `OppgaveService.kt` som tar inn to parametre:
    - oppgaveId: Int - Dette er ID-en til oppgaven som skal gjettes på.
    - ordGjettet: String - Dette er ordet som brukeren har gjettet

som henter ut oppgaven fra databasen for den angitte `oppgaveId`en.

Deretter skal funksjonen bruke `sjekkBokstavTreff`-funksjonen for å sjekke treffene på bokstavene i gjetningen
og returnere en liste med `BokstavTreff`-objekter.

<details>
<summary> Løsningsforslag </summary>

Oppgave 1:

```kotlin
    fun hentOppgave(oppgaveId: Int): Oppgave {
    return jdbcTemplate.query(
        """
                |SELECT * FROM OPPGAVE
                |WHERE ID = :id
            """.trimMargin(),
        MapSqlParameterSource(
            mapOf(
                "id" to oppgaveId,
            )
        ),
        DataClassRowMapper(Oppgave::class.java)
    ).first()
}
```

Oppgave 2:

```kotlin
fun gjettOrd(oppgaveId: Int, ordGjettet: String): List<BokstavTreff> {
    val oppgaveGjettetPaa = oppgaveRepository.hentOppgave(oppgaveId)
    val bokstavTreff = sjekkBokstavTreff(
        ordIOppgave = oppgaveGjettetPaa.ord,
        ordGjettet = ordGjettet
    )
    return bokstavTreff
}
```

</details>

## Oppgave 2.3: Endepunkt for gjetting

Nå som vi har en funksjon som kan håndtere gjetting av ord, er det på tide å lage et endepunkt som bruker denne
funksjonaliteten.

I fila [oppgave.kt](../shared/src/main/kotlin/no/bekk/kordle/shared/dto/oppgave.kt) finner du to klasser:

```kotlin
@Serializable
data class GjettOrdRequest(
    val oppgaveId: Int,
    val ordGjett: String
)


@Serializable
data class GjettResponse(
    val oppgaveId: Int,
    val alleBokstavtreff: List<BokstavTreff>
)
```

Disse to klassene representerer henholdsvis en forespørsel om å gjette et ord og svaret på gjettingen.
Representasjonene er serialiserbare, noe som betyr at de kan konverteres til JSON og sendes over nettverket.
I tilfeller sånn som dette, hvor en overfører JSON-objekter i dens `HTTP Message Body`, er det kotyme å bruke opprette
et POST-endepunkt for å håndtere slike
forespørsler.([Les mer her](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Methods/POST))

I Spring Boot kan vi enkelt lage et slikt endepunkt ved å bruke annotasjonen `@PostMapping` i en controller-klasse.
Videre kan vi indikere hvilken objekttype vi forventer å motta i forespørselen sin `HTTP Message Body`  ved å bruke
annotasjonen `@RequestBody`. Les mer om
Requestbody [her](https://docs.spring.io/spring-framework/reference/web/webflux/controller/ann-methods/requestbody.html)

Oppgave:

1. Lag en funksjon `gjettOrd` i `OppgaveController.kt` som tar inn en `GjettOrdRequest` og returnerer en
   `GjettResponse`.

2. Omgjør denne funksjonen til å være et endepunkt med følgende spesifikasjoner:
    - Endepunktet skal ha URLen `/gjettOrd`.
    - Endepunktet skal være et HTTP POST-endepunkt.
    - Endepunktet skal ta imot en `GjettOrdRequest` i `HTTP Message Body`.
    - Endepunktet skal returnere en `GjettResponse` som inneholder resultatet av gjettingen.

<details>
<summary> Løsningsforslag </summary>

Oppgave 1:

```kotlin
 fun gjettOrd(gjettOrdRequest: GjettOrdRequest): GjettResponse {
    val bokstavTreff = oppgaveService.gjettOrd(
        oppgaveId = gjettOrdRequest.oppgaveId,
        ordGjettet = gjettOrdRequest.ordGjett
    )
    val gjettResponse = GjettResponse(
        oppgaveId = gjettOrdRequest.oppgaveId,
        alleBokstavtreff = bokstavTreff
    )
    return gjettResponse
}
```

Oppgave 2:

```kotlin
@PostMapping("/gjettOrd")
fun gjettOrd(@RequestBody gjettOrdRequest: GjettOrdRequest): GjettResponse {
    val bokstavTreff = oppgaveService.gjettOrd(
        oppgaveId = gjettOrdRequest.oppgaveId,
        ordGjettet = gjettOrdRequest.ordGjett
    )
    val gjettResponse = GjettResponse(
        oppgaveId = gjettOrdRequest.oppgaveId,
        alleBokstavtreff = bokstavTreff
    )
    return gjettResponse
}
```

</details>

🧪 Når du er ferdig, kan du kjøre opp frontend-applikasjonen og teste om endepunktet fungerer ved å åpne opp
applikasjonen og se om du kan gjette på ord! 
