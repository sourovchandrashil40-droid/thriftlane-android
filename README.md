# ThriftLane

A small Android app for browsing second-hand fashion, built with **Kotlin** and **Jetpack Compose**.
I built it to practise the skills a mobile engineer uses day to day on a large marketplace app:
clean architecture, testing at several levels, experimentation, and team workflow.

## Features
- Browse items (title, brand, size, condition, price)
- Search by title or brand
- Favourite items and filter by favourites
- Item detail screen
- Loading, empty and error (with retry) states
- **A/B experiment:** users are bucketed into a *list* or *grid* layout, and an exposure event is tracked

## Tech
| Area | Choice |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose + Material 3, Navigation Compose |
| Architecture | MVVM, unidirectional data flow (`StateFlow` + immutable `UiState`) |
| Async | Kotlin Coroutines |
| Tests | JUnit4, coroutines-test, Paparazzi (snapshots), Compose UI tests |
| CI | GitHub Actions |

## Architecture
```
data/         Item model, ItemRepository (interface) + FakeItemRepository
analytics/    Analytics interface (Logcat implementation)
experiment/   ExperimentService: deterministic bucketing for the layout experiment
ui/list/      ItemListViewModel, ItemListUiState, stateless ItemListScreen + ItemCard
ui/detail/    ItemDetailScreen
```
Screens are **stateless** (state in, callbacks out), so they are easy to test and to snapshot.
The ViewModel depends on interfaces, so tests inject fakes.

## Testing
| Type | Where | Command |
|---|---|---|
| Unit tests | `app/src/test` | `./gradlew testDebugUnitTest` |
| Snapshot tests | `app/src/test` (Paparazzi) | `./gradlew recordPaparazziDebug` then `./gradlew verifyPaparazziDebug` |
| UI tests | `app/src/androidTest` | `./gradlew connectedDebugAndroidTest` (emulator/device) |
| Integration-style | ViewModel + repository wired together with fakes, including error → retry flow | part of unit tests |

## Running the app
1. Open the project in Android Studio (Ladybug or newer) with JDK 17.
2. Let Gradle sync, then run the `app` configuration.

## Working method
- Work is split into small user stories (see [docs/BACKLOG.md](docs/BACKLOG.md)), Scrum-style.
- Each story is developed on a feature branch and merged via a pull request using the PR template.
- CI runs unit tests, lint and a debug build on every PR.

## What I'd do next
Real API with Retrofit/Ktor, image loading with Coil, persisted favourites, dependency injection with Hilt.
