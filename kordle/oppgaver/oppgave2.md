# Oppgave 2: Gjetting og foretningslogikk

Så langt i oppgavene har vi laget en serverapplikasjon som kan hente ut en tilfeldig oppgave.🎉
Dessverre kan vi ikke gjette på oppgaven enda, da det ikke eksisterer logikk for dette 😅
Heldigvis har du kommet til redningen! 🚀

Som nevnt tidligere, er det viktig at vi holder foretningslogikken samlet på samme sted. I spring
samles slik foretningslogikk i `services`, det vil si klasser som er annotert med `@Service`.
I denne opppgaven skal vi utvide `OppgaveService.kt` til å kunne håndtere gjetting på ord!

## Oppgave 2.1: Sjekk bokstavgjett

Før vi kan begynne å gjette på ord, må vi lage en funksjon som kan ta inn et gjettet ord fra klienten og sammenligne det
med det riktige ordet.
Basert på denne sammenligningen må vi kunne gi en tilbakemelding på:

- Hvilke bokstaver eksisterer i ordet og er plassert på riktig plass
- Hvilke bokstaver eksisterer i ordet, men er ikke plassert på riktig plass
- Hvilke bokstaver ikke eksisterer i ordet.

I fila [oppgave.kt](../server/src/main/kotlin/no/bekk/kordle/server/domain/oppgave.kt) finner du denne denne klassen som
skal representere dette:

```kotlin
data class BokstavTreff(
    val plassISekvensen: Int,
    val bokstavGjettet: Char,
    var erBokstavenIOrdet: Boolean,
    val erBokstavenPaaRettsted: Boolean
) {
    ...
}
```

Oppgave:

1. Kommenter inn koden i `bokstavtreffUtils` i fila
   her [bokstavtreffUtils.kt](../server/src/main/kotlin/no/bekk/kordle/server/utils/bokstavtreffUtils.kt).

2. Fyll ut funksjonen `finnEksakteBokstavTreff`, som er en funksjon som tar inn to parametre:
    - ordIOppgave: String - Dette er ordet som er riktig for oppgaven.
    - ordGjettet: String - Dette er ordet som brukeren har gjettet.
      Funksjonen skal returnere en liste med `BokstavTreff`-objekter hvor bokstaven eksisterer i ordet og er plassert på
      rett posisjon.

3. Fyll ut funksjonen `finnEksakteBokstavTreff`, som er en funksjon som tar inn to parametre:
    - ordIOppgave: String - Dette er ordet som er riktig for oppgaven.
    - ordGjettet: String - Dette er ordet som brukeren har gjettet.
      Funksjonen skal returnere en liste med `BokstavTreff`-objekter hvor bokstaven eksisterer i ordet men er plassert
      på feil sted

<details>
<summary> Løsningsforslag </summary>

Oppgave 1:

```kotlin
fun finnEksakteBokstavTreff(
    ordIOppgave: String,
    ordGjettet: String,
): List<BokstavTreff> {
    val gjettetOrdIndex: Map<Int, Char> = ordGjettet
        .lowercase()
        .mapIndexed { index, bokstav -> index to bokstav }
        .toMap()

    val eksakteTreffForBokstav = gjettetOrdIndex
        .filter { (index, bokstav) ->
            bokstav == ordIOppgave[index]
        }.map { (index, bokstav) ->
            BokstavTreff(
                plassISekvensen = index,
                bokstavGjettet = bokstav,
                erBokstavenIOrdet = true,
                erBokstavenPaaRettsted = true
            )
        }
    return eksakteTreffForBokstav
}
```

Oppgave 2:

```kotlin
fun finnDelvisBokstavTreff(
    ordIOppgave: String,
    ordGjettet: String,
): List<BokstavTreff> {
    val gjettetOrdIndex: Map<Int, Char> = ordGjettet
        .lowercase()
        .mapIndexed { index, bokstav -> index to bokstav }
        .toMap()

    val delvisTreffForBokstav = gjettetOrdIndex
        .filter { (index, bokstav) ->
            bokstav != ordIOppgave[index] && ordIOppgave.contains(bokstav)
        }.map { (index, bokstav) ->
            BokstavTreff(
                plassISekvensen = index,
                bokstavGjettet = bokstav,
                erBokstavenIOrdet = true,
                erBokstavenPaaRettsted = false
            )
        }
    return delvisTreffForBokstav
}
```

</details>

## Oppgave 2.2: Foretningslogikk og databaseinteraksjon

Nå som vi har en funksjon som kan sjekke treff på bokstaver, er det på tide å lage en funksjon som håndterer
interaksjonen med databasen.

Oppgave:

1. Lag en SQL-spørring som henter ut en oppgave basert på dens ID.
   Du kan gjerne teste spørringen i `Query Console` først.
2. Lag en funksjon `hentOppgave` i `OppgaveRepository.kt` som tar inn en `oppgaveId: Int`, bruker SQL-spørringen fra
   forrige steg og returnerer en instans av
   `Oppgave`.
3. Lag en funksjon `gjettOrd` i `OppgaveService.kt` som tar inn to parametre:
    - oppgaveId: Int - Dette er ID-en til oppgaven som skal gjettes på.
    - ordGjettet: String - Dette er ordet som brukeren har gjettet

som henter ut oppgaven fra databasen for den angitte `oppgaveId`en.
Deretter skal funksjonen bruke `sjekkBokstavTreff`-funksjonen for å sjekke treffene på bokstavene i gjetningen
og returnere en liste med `BokstavTreff`-objekter.

<details>
<summary> Løsningsforslag </summary>

Oppgave 1:

```sql
SELECT *
FROM OPPGAVE
WHERE ID = :id
```

Oppgave 2:

```kotlin
fun hentOppgave(oppgaveId: Int): Oppgave {
    return jdbcTemplate.query(
        """
        SELECT *
        FROM OPPGAVE
        WHERE ID = :id
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
   `GjettResponse`. Husk å konvertere den returnerte listen med `BokstavTreff`-objekter til `BokstavTreffDTO`-objekter
   med `bokstavTreff.map { it.tilBokstavTreffDTO() }`.

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
        alleBokstavtreff = bokstavTreff.map { it.tilBokstavTreffDTO() }
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
        alleBokstavtreff = bokstavTreff.map { it.tilBokstavTreffDTO() }
    )
    return gjettResponse
}
```

</details>

🧪 Når du er ferdig, kan du kjøre opp frontend-applikasjonen og teste om endepunktet fungerer ved å åpne opp
applikasjonen og se om du kan gjette på ord! 
