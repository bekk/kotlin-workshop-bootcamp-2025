# Oppgave 3: HTTP Statuskoder og validering

Per nå returnerer vi alltid en av to HTTP statuskoder:

- 200 OK hvis gjetningen er korrekt
- 500 Internal Server Error hvis noe går galt

Dette er ikke ideelt, da det ikke gir frontenden informasjon om hva som gikk galt.
Gjettet brukeren et ord som ikke finnes? Eller sendte brukeren inn en ID som ikke eksisterer?
La oss forbedre dette ved å bruke flere HTTP statuskoder for å gi mer presis informasjon om hva som skjedde!

[Les mer om HTTP statuskoder her](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status).

## Oppgave 3.1: Response entities og gyldige ord

Titt litt på funksjonen `gjettOrd` i `OppgaveController.kt`.
Hvis alt går bra ved kjøring av den funksjonen returnerer den en instans av `GjettResponse`.
Det som skjer litt bak scenen er at Spring Boot automatisk antar at dersom en funksjon kjører uten feil, så responsen
fra serveren ha HTTP statuskoden 200 OK.
Dersom en funksjon kaster en feil, vil Spring Boot returnere en HTTP statuskode 500 Internal Server Error.

For å kunne manipulere HTTP statuskoden som returneres, kan vi bruke `ResponseEntity`-klassen fra Spring boot som kan
leses mer
om [her](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-methods/responseentity.html).

Fremfor å la Spring Boot håndtere HTTP statuskoden automatisk, kan vi opprette en `ResponseEntity` selv og sette
statuskoden manuelt.

En ekvivalent måte å skrive vår nåværende `gjettOrd`-funksjon på med `ResponseEntity` kunne ha sett slik ut:

```kotlin
    @PostMapping("/gjettOrd")
fun gjettOrd(@RequestBody gjettOrdRequest: GjettOrdRequest): ResponseEntity<*> {
    val bokstavTreff = oppgaveService.gjettOrd(
        oppgaveId = gjettOrdRequest.oppgaveId,
        ordGjettet = gjettOrdRequest.ordGjett
    )
    val gjettResponse = GjettResponse(
        oppgaveId = gjettOrdRequest.oppgaveId,
        alleBokstavtreff = bokstavTreff.map { it.tilBokstavTreffDTO() }
    )
    return ResponseEntity
        .ok()
        .body(gjettResponse)
}
```

I dette eksempelet har vi byttet ut return-typen fra `GjettResponse` til `ResponseEntity<*>`, og vi bruker
`ResponseEntity.ok().body(gjettResponse)` for å returnere en `ResponseEntity` med statuskoden 200 OK og
`GjettResponse`-objektet som body.

I denne oppgaven ønsker vi å returnere en 400 Bad Request statuskode dersom brukeren gjetter et ord som ikke finnes i
vår liste over gyldige ord. I klassen `OrdValidatorService` (finnes
her [her](../server/src/main/kotlin/no/bekk/kordle/server/service/OrdValidatorService.kt))
finnes det allerede funksjonalitet for å validere om et ord er gyldig.

Oppgaver:

1. Sett inn `OrdValidatorService` som et parameter i `OppgaveController` og endre `gjettOrd`-funksjonen i
   `OppgaveController.kt` på følgende måte:
    - Hvis `ordGjettet` ikke er et gyldig ord, skal funksjonen returnere en `ResponseEntity` med statuskoden 400 Bad
      Request og en passende feilmelding.
    - Hvis det gjettede ordet er korrekt, skal funksjonen returnere en `ResponseEntity` med statuskoden 200 OK og
      `GjettResponse`-objektet.

Du kan validere at denne funksjonaliteten fungerer som forventet ved å kjøre følgende kommandoer:

Denne skal returnere 200 OK:

```bash
curl -X POST -H "Content-Type: application/json" -d '{"oppgaveId": 1, "ordGjett": "test"}' http://localhost:8080/gjettOrd -w "\nHTTP Status: %{http_code}\n"
```

Denne skal returnere en 400 Bad Request med en passende feilmelding:

```bash
curl -X POST -H "Content-Type: application/json" -d '{"oppgaveId": 1, "ordGjett": "testtt"}' http://localhost:8080/gjettOrd -w "\nHTTP Status: %{http_code}\n"
```

<details>
<summary> Løsningsforslag </summary>

```kotlin
@PostMapping("/gjettOrd")
fun gjettOrd(@RequestBody gjettOrdRequest: GjettOrdRequest): ResponseEntity<*> {
    val gjettetOrd = gjettOrdRequest.ordGjett
    val oppgaveId = gjettOrdRequest.oppgaveId
    val erGjettetOrdGyldig = ordValidatorService.isValid(gjettetOrd)
    if (!erGjettetOrdGyldig) {
        return ResponseEntity
            .badRequest()
            .body("Ordet '${gjettetOrd}' er ikke i ordlista.")
    }
    val bokstavTreff = oppgaveService.gjettOrd(
        oppgaveId = oppgaveId,
        ordGjettet = gjettetOrd
    )
    val gjettResponse = GjettResponse(
        oppgaveId = oppgaveId,
        alleBokstavtreff = bokstavTreff.map { it.tilBokstavTreffDTO() }
    )
    return ResponseEntity
        .ok()
        .body(gjettResponse)
}
```

</details>

## Oppgave 3.2: Custom exceptions og HTTP statuskoder

Validering er en viktig del av enhver applikasjon, og det er ofte nyttig å kunne håndtere feil på en strukturert måte.
Slik validering blir ofte utført på forettninglogikknivået (service-laget), da det kan eksistere domene-spesifikke
regler som må overholdes.
La oss utvide vår applikasjon til å håndtere slike domene-spesifikke feil ved å bruke custom exceptions.
Et eksempel på en slik exception kan være `GjettetErIkkeIOrdlistaException` i
fila [exceptions.kt](../server/src/main/kotlin/no/bekk/kordle/server/exceptions/exceptions.kt),
som kan kastes dersom et ord som ikke er i ordlista blir gjettet.

1. Flytt valideringslogikken for å sjekke om et ord eksisterer fra `OppgaveController.kt` til `gjettOrd` i
   `OppgaveService.kt`.
    - Hvis et ord ikke er gyldig, skal `OppgaveService` kaste en `GjettetErIkkeIOrdlistaException`.
    - Hvis gjettingen er korrekt, skal funksjonen returnere en `List<BokstavTreff>`.

2. Håndter den kastede feilen `GjettetErIkkeIOrdlistaException` i `OppgaveController.kt` ved hjelp av en `try-catch`
   -block (kan leses om [her](https://kotlinlang.org/docs/exceptions.html)).
    - Når denne exceptionen kastes, skal controlleren returnere en `ResponseEntity` med statuskoden 400 Bad Request og
      en passende feilmelding.

Når du føler deg ferdig, kan du teste at endepunktet fungerer som forventet ved å kjøre følgende kommando i terminalen:

```bash
curl -X POST -H "Content-Type: application/json" -d '{"oppgaveId": 1, "ordGjett": "testtt"}' -s http://localhost:8080/gjettOrd 
```

Her skal du få tilbake feilmeldingen du skrev for `GjettetErIkkeIOrdlistaException`.

3. Legg til ekstra validering i `OppgaveService.kt` for å sjekke om det gjettede ordet er korrekt lengde for oppgaven.
    - Hvis lengden ikke er korrekt  `OppgaveService` kaste en custom exception `GjettetHarUgyldigLengdeException`.
    - Håndter denne exceptionen i `OppgaveController.kt` på samme måte som for `GjettetErIkkeIOrdlistaException

Du kan validere at denne funksjonaliteten fungerer som forventet ved å kjøre følgende kommando i terminalen:

```bash
curl -X POST -H "Content-Type: application/json" -d '{"oppgaveId": 2, "ordGjett": "test"}' http://localhost:8080/gjettOrd
```

Her skal du få tilbake feilmeldingen du skrev for `GjettetHarUgyldigLengdeException`.

<details>
<summary> Løsningsforslag </summary>

Oppgave 1:

I oppgaveService på toppen

```kotlin
class OppgaveService(
    val oppgaveRepository: OppgaveRepository,
    private val ordValidatorService: OrdValidatorService
) {... }
```

I `gjettOrd`-funksjonen i `OppgaveService.kt`:

```kotlin
fun gjettOrd(oppgaveId: Int, ordGjettet: String): List<BokstavTreff> {
    if (!ordValidatorService.isValid(ordGjettet)) {
        throw GjettetErIkkeIOrdlistaException("Ordet '${ordGjettet}' er ikke i ordlista.")
    }
    val oppgaveGjettetPaa = oppgaveRepository.hentOppgave(oppgaveId)
    val bokstavTreff = sjekkBokstavTreff(
        ordIOppgave = oppgaveGjettetPaa.ord,
        ordGjettet = ordGjettet
    )
    return bokstavTreff
}
```

Oppgave 2:

I `gjettOrd`-funksjonen i `OppgaveController.kt`:

```kotlin
@PostMapping("/gjettOrd")
fun gjettOrd(@RequestBody gjettOrdRequest: GjettOrdRequest): ResponseEntity<*> {
    try {
        val bokstavTreff = oppgaveService.gjettOrd(
            oppgaveId = gjettOrdRequest.oppgaveId,
            ordGjettet = gjettOrdRequest.ordGjett
        )
        val gjettResponse = GjettResponse(
            oppgaveId = gjettOrdRequest.oppgaveId,
            alleBokstavtreff = bokstavTreff.map { it.tilBokstavTreffDTO() }
        )
        return ResponseEntity.ok().body(gjettResponse)

    } catch (exception: RuntimeException) {
        val statusKodeSomSkalReturneres = when (exception) {
            is GjettetErIkkeIOrdlistaException -> HttpStatus.BAD_REQUEST
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }
        return ResponseEntity
            .status(statusKodeSomSkalReturneres)
            .body(exception.message)
    }
}
```

Oppgave 3:

I `gjettOrd`-funksjonen i `OppgaveService.kt`:

```kotlin
fun gjettOrd(oppgaveId: Int, ordGjettet: String): List<BokstavTreff> {
    if (!ordValidatorService.isValid(ordGjettet)) {
        throw GjettetErIkkeIOrdlistaException("Ordet '${ordGjettet}' er ikke i ordlista.")
    }
    val oppgaveGjettetPaa = oppgaveRepository.hentOppgave(oppgaveId)
    if (ordGjettet.length != oppgaveGjettetPaa.ord.length) {
        throw GjettetHarUgyldigLengdeException("Gjettet '${ordGjettet}' er feil lengde for oppgaven. Oppgaven har lengde ${oppgaveGjettetPaa.ord.length} tegn.")
    }
    val bokstavTreff = sjekkBokstavTreff(
        ordIOppgave = oppgaveGjettetPaa.ord,
        ordGjettet = ordGjettet
    )
    return bokstavTreff
}
```

I `gjettOrd`-funksjonen i `OppgaveController.kt`:

```kotlin
@PostMapping("/gjettOrd")
fun gjettOrd(@RequestBody gjettOrdRequest: GjettOrdRequest): ResponseEntity<*> {
    try {
        val bokstavTreff = oppgaveService.gjettOrd(
            oppgaveId = gjettOrdRequest.oppgaveId,
            ordGjettet = gjettOrdRequest.ordGjett
        )
        val gjettResponse = GjettResponse(
            oppgaveId = gjettOrdRequest.oppgaveId,
            alleBokstavtreff = bokstavTreff.map { it.tilBokstavTreffDTO() }
        )
        return ResponseEntity.ok().body(gjettResponse)

    } catch (exception: RuntimeException) {
        val statusKodeSomSkalReturneres = when (exception) {
            is GjettetErIkkeIOrdlistaException -> HttpStatus.BAD_REQUEST
            is GjettetHarUgyldigLengdeException -> HttpStatus.BAD_REQUEST
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }
        return ResponseEntity
            .status(statusKodeSomSkalReturneres)
            .body(exception.message)
    }
}
```

</details>
