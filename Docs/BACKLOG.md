# Backlog & Sprints

This project was built the way I'd work in a Scrum team: small user stories,
each delivered in its own branch and pull request, with a short retrospective per sprint.

## Sprint 1 – Foundations
- [x] As a user, I can see a list of items with price, brand, size and condition
- [x] As a user, I can see a loading state and an error state with retry
- [x] As a developer, I have unit tests for the ViewModel

## Sprint 2 – Interaction
- [x] As a user, I can search by title or brand
- [x] As a user, I can mark items as favourites and filter by them
- [x] As a user, I can open an item to see its details

## Sprint 3 – Experimentation & quality
- [x] As a product owner, I can A/B test list vs grid layout and see exposure events
- [x] As a developer, I have snapshot tests and Compose UI tests
- [x] As a developer, CI runs tests and lint on every pull request

## Next
- [ ] Load items from a real API (Retrofit / Ktor) and add image loading (Coil)
- [ ] Persist favourites with Room or DataStore
- [ ] Add dependency injection (Hilt)

## Retrospective notes
- **Went well:** a stateless screen composable made UI and snapshot tests simple.
- **To improve:** wire up a real backend earlier to catch edge cases in loading/error handling.
