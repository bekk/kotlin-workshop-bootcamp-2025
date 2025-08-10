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


<details>
<summary> Løsningsforslag for `/users` (GET) </summary>

I `UserController.kt`:

```kotlin
@GetMapping("/users")
fun getUser(@RequestParam("username") username: String): User? {
    return userService.getUserByUsername(username)
}
```

I `UserService.kt`:

```kotlin
@Service
class UserService(
    val userRepository: UserRepository,
    ...
) {
    ...
    fun getUserByUsername(username: String): User? {
        return userRepository.getUserByUsername(username)
    }
    ...
}
```

I `UserRepository.kt`:

```kotlin
fun getUserByUsername(username: String): User? {
    return jdbcTemplate.query(
        """
            SELECT
               *
            FROM
               KordleUser
            WHERE
               Username = :username""".trimIndent(),
        mapOf("username" to username),
        DataClassRowMapper(User::class.java)
    ).singleOrNull()
}
```

</details>

<details>
<summary> Løsningsforslag for `/users` (POST) </summary>

I `UserController.kt`:

```kotlin
@PostMapping("/users")
fun createUser(@RequestBody body: CreateUserRequest): User {
    return userService.createUser(body.username)
}
```

I `UserService.kt`:

```kotlin
fun createUser(username: String): User {
    userRepository.createUser(username)
    return userRepository.getUserByUsername(username)
        ?: throw IllegalStateException("Klarte ikke opprette bruker med navn $username")
}
```

I `UserRepository.kt`:

```kotlin
fun createUser(username: String) {
    val sql = """
        INSERT INTO
            KordleUser (Username)
        VALUES
            (:username);
    """.trimIndent()

    jdbcTemplate.update(
        sql,
        mapOf("username" to username)
    )
}
```

<details>
<summary> Løsningsforslag for `/users/{userId}/stats` (GET) </summary>

I `UserController.kt`:

```kotlin
@GetMapping("/users/{userId}/stats")
fun getUserStats(@PathVariable userId: Int): StatsForUser {
    return userService.statsForUser(userId)
}
```

I `UserService.kt`:

```kotlin
@Service
class UserService(
    val userRepository: UserRepository,
    val userOppgaveResultRepository: UserOppgaveResultRepository,
) {
    ...

    fun statsForUser(userId: Int): StatsForUser {
        val resultater = userOppgaveResultRepository.getResultsByUserId(userId)
        val oppgaveCountByAttemptCount = resultater
            .filter { it.success }
            .groupBy { it.attemptCount }
            .mapValues { it.value.size }
        val amountOfOppgaverFailed = resultater.count { !it.success }
        return StatsForUser(userId, amountOfOppgaverFailed, oppgaveCountByAttemptCount)
    }
}
```

I `UserRepository.kt`:

```kotlin
fun getResultsByUserId(userId: Int): List<UserOppgaveResult> {
    return jdbcTemplate.query(
        """
                SELECT
                   *
                FROM
                   UserOppgaveResult
                WHERE
                   UserId = :userId""".trimIndent(),
        mapOf("userId" to userId),
        DataClassRowMapper(UserOppgaveResult::class.java)
    )
}
```

</details>



<details>
<summary> Løsningsforslag for `/result` (POST) </summary>

I `UserController.kt`:

```kotlin
@PostMapping("/result")
fun registerUserOppgave(
    @RequestBody body: UserOppgaveResult
): StatsForUser {
    return userService.registerResult(body.userId, body.oppgaveId, body.success, body.attemptCount)
}
```

I `UserService.kt`:

```kotlin
fun registerResult(
    userId: Int,
    oppgaveId: Int,
    success: Boolean,
    guesses: Int
): StatsForUser {
    userOppgaveResultRepository.create(userId, oppgaveId, success, guesses)
    return statsForUser(userId)
}
```

I `UserRepository.kt`:

```kotlin
fun create(
    userId: Int,
    oppgaveId: Int,
    success: Boolean,
    guesses: Int
) {
    val sql = """
            INSERT INTO
                UserOppgaveResult (UserId, OppgaveId, Success, AttemptCount)
            VALUES
                (:userId, :oppgaveId, :success, :guesses);
        """.trimIndent()

    jdbcTemplate.update(
        sql,
        mapOf(
            "userId" to userId,
            "oppgaveId" to oppgaveId,
            "success" to success,
            "guesses" to guesses
        )
    )
}
```

</details>

```bash
curl -X POST -H "Content-Type: application/json" -d '{"username": "test"}' http://localhost:8080/users
```

