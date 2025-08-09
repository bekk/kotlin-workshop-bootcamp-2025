# Oppgave 3: HTTP Statuskoder og validering

Per nå returnerer vi alltid en av to HTTP statuskoder:
# - 200 OK hvis gjetningen er korrekt
# - 500 Internal Server Error hvis noe går galt

Dette er ikke ideelt, da det ikke gir frontenden informasjon om hva som gikk galt. 
Gjettet brukeren et ord som ikke finnes? Eller sendte brukeren inn en ID som ikke eksisterer?
La oss forbedre dette ved å bruke flere HTTP statuskoder for å gi mer presis informasjon om hva som skjedde!

[Les mer om HTTP statuskoder her](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status).

## Oppgave 3.1: Response entities og gyldige ord
Titt litt på funksjonen `gjettOrd` i `OppgaveController.kt`.
Hvis alt går bra ved kjøring av den funksjonen returnerer den en instans av `GjettResponse`. 
Det som skjer litt bak scenen er at Spring Boot automatisk antar at dersom en funksjon kjører uten feil, så responsen fra serveren ha HTTP statuskoden 200 OK.

For å kunne manipulere HTTP statuskoden som returneres, kan vi bruke `ResponseEntity`-klassen fra Spring boot som kan leses mer om [her](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-methods/responseentity.html).
Fremfor å la Spring Boot håndtere HTTP statuskoden automatisk, kan vi opprette en `ResponseEntity` selv og sette statuskoden manuelt.

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
        alleBokstavtreff = bokstavTreff
    )
    return ResponseEntity
        .ok()
        .body(gjettResponse)
}
```

I denne oppgaven ønsker vi å returnere en 400 Bad Request statuskode dersom brukeren gjetter et ord som ikke finnes i vår liste over gyldige ord.
`OppgaveController.kt` drar inn en annen service `OrdValidatorService` som kan brukes til å sjekke om et ord er gyldig.

Oppgaver:
1. Endre `gjettOrd`-funksjonen i `OppgaveController.kt` på følgende måte: 
   - Hvis `ordGjettet` ikke er et gyldig ord, skal funksjonen returnere en `ResponseEntity` med statuskoden 400 Bad Request og en passende feilmelding.
   - Hvis det gjettede ordet er korrekt, skal funksjonen returnere en `ResponseEntity` med statuskoden 200 OK og `GjettResponse`-objektet.

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
        oppgaveId = gjettOrdRequest.oppgaveId,
        ordGjettet = gjettOrdRequest.ordGjett
    )
    val gjettResponse = GjettResponse(
        oppgaveId = gjettOrdRequest.oppgaveId,
        alleBokstavtreff = bokstavTreff
    )
    return ResponseEntity
        .ok()
        .body(gjettResponse)
}
```
</details>

