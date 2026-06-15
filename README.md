# Tandem Community Challenge

An Android application that displays a paginated list of Tandem language learning community members, built as part of the Tandem hiring process.

## Features

- Community member list fetched from the Tandem API
- NEW badge for members with no references yet
- Like/unlike toggle per member, persisted across app relaunches
- Smooth infinite scroll

## Architecture

The app follows Clean Architecture principles, split into three layers:

- **domain** — pure Kotlin models, repository interface, and use cases. No Android or library dependencies. This is the core of the app.
- **data** — Retrofit API client, Room database, PagingSource, and the concrete repository implementation. Knows how to fetch and store data.
- **ui** — ViewModel and Compose UI. Observes data from the domain layer and renders it. Never talks to the data layer directly.

Dependencies point inward — data and ui both depend on domain, but domain depends on nothing. This makes the business logic independently testable.

## How to Run

1. Clone the repository
2. Open in Android Studio (latest stable)
3. Let Gradle sync complete
4. Run on an emulator or physical device (API 26+)
