# 🎬 Movie App — Android (Modular • Compose • Firestore)

Movie App adalah aplikasi Android berbasis Jetpack Compose dengan arsitektur modular, dirancang untuk pengembangan jangka panjang yang scalable, maintainable, dan siap untuk integrasi authentication berbasis OAuth.

Project ini menggunakan MVVM sebagai arsitektur utama, StateFlow untuk state management, Hilt untuk dependency injection, serta Firestore sebagai primary data source melalui repository layer.

---

## Highlights

- Jetpack Compose + Material 3
- Modular Architecture
- MVVM + StateFlow
- Firestore via Repository
- Kotlin Coroutines
- Hilt Dependency Injection
- OAuth-ready (Firebase Auth)

---

## Architecture

Project ini menerapkan Unidirectional Data Flow dengan struktur sebagai berikut:

Compose UI  
↓  
ViewModel (StateFlow<UiState>)  
↓  
Repository  
↓  
Firestore DataSource  

UI tidak memiliki akses langsung ke Firestore dan hanya berinteraksi dengan ViewModel.

---

## Module Structure
.
├── app
├── common
├── data
├── domain
├── feature
└── nav

---

## Module Responsibility

### app
Berisi entry point aplikasi dan konfigurasi global:
- MainActivity
- BaseApplication
- App-level dependency injection
- Firebase initialization
- Root navigation host

### common
Berisi komponen yang digunakan lintas module:
- Constants
- Extensions
- Base UI dan Base State
- Utility helpers
- Shared UI components

### domain
Berisi business logic murni:
- Domain models
- Business rules
- Tidak memiliki dependency ke Android framework

### data
Berisi implementasi data layer:
- Firestore DataSource
- DTO ↔ Domain mapping
- Repository implementation
- Suspend function dan Flow-based access

### feature
Berisi feature UI berbasis Compose:
- presentation (ViewModel)
- ui (Compose Screen)
- contract (UiState / Event)
- route (Navigation route)
- Feature-level dependency injection

### nav
Berisi navigasi terpusat:
- App destinations
- Bottom navigation
- NavController extensions
- Navigation helper

---

## Tech Stack

| Category | Technology |
|--------|------------|
| Language | Kotlin |
| Build System | AGP |
| UI | Jetpack Compose + Material 3 |
| Architecture | MVVM |
| State Management | StateFlow |
| Async | Kotlin Coroutines |
| Dependency Injection | Hilt |
| Navigation | Navigation Compose |
| Backend | Firebase Firestore |
| Authentication | Firebase Auth (OAuth-ready) |

Tech stack bersifat LOCKED untuk menjaga konsistensi project.

---

## Firestore Data Rules

Akses Firestore wajib mengikuti aturan berikut:

Allowed:
- Firestore hanya diakses melalui Repository / DataSource
- Menggunakan suspend + await() atau callbackFlow
- DTO wajib di-map ke Domain / UI model
- Siap menerima userId ketika Authentication aktif

Forbidden:
- UI mengakses Firestore secara langsung
- Mengekspos DocumentSnapshot ke UI
- Mengirim Firestore object mentah ke UI

---

## State Management Rules

- ViewModel wajib mengekspos StateFlow<UiState>
- Loading dan Error sudah ditangani di Base State
- UI hanya melakukan observe terhadap state
- Tidak memecah state yang sudah ada

---

## Source Structure (Simplified)
com.mtv.app.movie
├── app
├── config
├── di
├── nav
├── common
├── data
├── domain
└── feature

Folder build, cache, dan output tidak termasuk dalam repository.

---

## Testing (Planned)

- ViewModel unit testing
- Repository testing menggunakan fake datasource
- UI state validation

---

## Future Improvements

- Firebase Auth (OAuth)
- userId-based Firestore rules
- Offline caching
- Dynamic feature module
- Snapshot testing untuk Compose

---

## Notes

- Menggunakan Gradle Version Catalog
- Modular dan scalable by design
- Cocok untuk project Android skala menengah hingga besar

---

## Author

Dedy Wijaya  
Android Developer
