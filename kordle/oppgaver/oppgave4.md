# Oppgave 4: Users og scoreboard

Nå er det på tide å slippe deg litt mer løs! I denne oppgaven skal du implementere et enkelt bruker- og poengsystem for
Kordle.
Fremfor å gå trinn for trinn gjennom oppgavene, vil vi gi deg en oversikt over formatet på endepunktene som frontenden
forventer og la deg bestemme selv hvordan du vil implementere det.

## Hjelpeinformasjon

Du har helt sikkert lagt merke til at databasen inneholder en tabell for brukere (`USERS`) og en for
oppgave-resultater (`USEROPPGAVERESULT`).
Tabellen `USERS` inneholder informasjon om brukerne, mens `USEROPPGAVERESULT` lagrer resultatene for hver bruker på hver
oppgave.

Videre har en også en fil som inneholder DTO-er for brukere og oppgave-resultater
i [her](../shared/src/main/kotlin/no/bekk/kordle/shared/dto/user.kt).

## Endepunkter som må implementeres

### `/users` (GET)

Forventet funksjonalitet: : Henter en bruker basert på et brukernavn. Hvis brukeren eksisterer, retureres en instans av
`User`, ellers returneres `null`.

#### Api-spesifikasjon

Query-parametre:

- `username`: Brukernavnet til brukeren som skal hentes. Hvis brukeren ikke eksisterer, returneres `null`.

Returtype: `User?`

Query-parametre i spring kan leses mer
om [her](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-methods/requestparam.html)

### `/users` (POST)

Forventet funksjonalitet: : Registrerer en ny bruker med et gitt brukernavn.

#### Api-spesifikasjon

HTTP message body:

- En instans av `CreateUserRequest`

Returtype: `User`

### `/users/{userId}/stats` (GET)

Forventet funksjonalitet: : Henter statistikk for en bruker basert på et brukernavn.

#### Api-spesifikasjon

Path-parameters:

- `userId`: Brukerens ID som skal hentes.

Returtype: `StatsForUser`

Path parameters i spring kan leses mer
om [her](https://www.baeldung.com/spring-pathvariable)

### `/result` (POST)

Forventet funksjonalitet: : Registrerer resultatet av en oppgave for en bruker og returnerer statistikk for brukeren.

#### Api-spesifikasjon

HTTP message body:

- En instans av `UserOppgaveResult`

Returtype: `StatsForUser`

### `/hentFasit` (POST)

Forventet funksjonalitet: : Henter fasitordet for en oppgave basert på oppgave-IDen.

#### Api-spesifikasjon

HTTP message body:

- En instans av `HentFasitRequest`

Returtype: `String`

```kotlin
    @GetMapping("/users")
fun getOrCreateUser(@RequestParam("username") username: String): User? {
    return userService.getUserByUsername(username)
}

@PostMapping("/users")
fun createUser(@RequestBody body: CreateUserRequest): User {
    return userService.createUser(body.username)
}

@PostMapping("/result")
fun registerUserOppgave(
    @RequestBody body: UserOppgaveResult
): StatsForUser {
    return userService.registerResult(body.userId, body.oppgaveId, body.success, body.attemptCount)
}

@GetMapping("/users/{userId}/stats")
fun getUserStats(@PathVariable userId: Int): StatsForUser {
    return userService.statsForUser(userId)
}

@PostMapping("/hentFasit")
fun hentFasit(@RequestBody hentFasitRequest: HentFasitRequest): ResponseEntity<*> {
    try {
        val fasitOrd = userService.hentFasitOrd(hentFasitRequest.oppgaveId)
        return ResponseEntity.ok().body(fasitOrd)

    } catch (exception: RuntimeException) {
        val statusKodeSomSkalReturneres = when (exception) {
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }
        return ResponseEntity
            .status(statusKodeSomSkalReturneres)
            .body(exception.message)
    }
}
```

```bash
curl -X POST -H "Content-Type: application/json" -d '{"username": "test"}' http://localhost:8080/users
```

