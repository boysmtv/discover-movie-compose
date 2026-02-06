package com.mtv.app.movie.feature.utils

import com.mtv.app.movie.data.model.movie.BelongsToCollection
import com.mtv.app.movie.data.model.movie.GenreItem
import com.mtv.app.movie.data.model.movie.MovieDetailResponse
import com.mtv.app.movie.data.model.movie.MovieVideoItem
import com.mtv.app.movie.data.model.movie.MovieVideoResponse
import com.mtv.app.movie.data.model.movie.MoviesItemResponse
import com.mtv.app.movie.data.model.movie.MoviesResponse
import com.mtv.app.movie.data.model.movie.ProductionCompany
import com.mtv.app.movie.data.model.movie.ProductionCountry
import com.mtv.app.movie.data.model.movie.SpokenLanguage

val previewMovieDetail = MovieDetailResponse(
    adult = false,
    backdropPath = "/xDMIl84Qo5Tsu62c9DGWhmPI67A.jpg",
    belongsToCollection = null,
    budget = 100_000_000,
    genres = listOf(
        GenreItem(1, "Drama"),
        GenreItem(2, "Action")
    ),
    homepage = "https://example.com",
    id = 1,
    imdbId = "tt1234567",
    originalLanguage = "en",
    originalTitle = "The Last of Us",
    overview = "Twenty years after modern civilization has been destroyed...",
    popularity = 999.9,
    posterPath = "/uKvVjHNqB5VmOrdxqAt2F7J78ED.jpg",
    productionCompanies = emptyList(),
    productionCountries = emptyList(),
    releaseDate = "2023-01-15",
    revenue = 500_000_000,
    runtime = 120,
    spokenLanguages = emptyList(),
    status = "Released",
    tagline = "Hope survives.",
    title = "The Last of Us",
    video = false,
    voteAverage = 8.9,
    voteCount = 12345
)

val previewVideoResponse = MovieVideoResponse(
    movieId = 12345,
    results = listOf(
        MovieVideoItem(
            iso6391 = "en",
            iso31661 = "US",
            name = "Official Trailer 1",
            key = "dummyKey1",
            site = "YouTube",
            size = 1080,
            type = "Trailer",
            official = true,
            publishedAt = "2026-01-20T10:00:00Z",
            id = "vid_1"
        ),
        MovieVideoItem(
            iso6391 = "en",
            iso31661 = "US",
            name = "Teaser",
            key = "dummyKey2",
            site = "YouTube",
            size = 720,
            type = "Teaser",
            official = true,
            publishedAt = "2026-01-18T08:00:00Z",
            id = "vid_2"
        ),
        MovieVideoItem(
            iso6391 = "en",
            iso31661 = "US",
            name = "Behind the Scenes",
            key = "dummyKey3",
            site = "YouTube",
            size = 480,
            type = "Featurette",
            official = true,
            publishedAt = "2026-01-15T12:00:00Z",
            id = "vid_3"
        )
    )
)

val mockMovieDetailResponseList = listOf(
    MovieDetailResponse(
        adult = false,
        backdropPath = "/backdrop_movie_1.jpg",
        belongsToCollection = BelongsToCollection(
            id = 1001,
            name = "Kotlin Saga Collection",
            posterPath = "/collection_poster.jpg",
            backdropPath = "/collection_backdrop.jpg"
        ),
        budget = 150_000_000,
        genres = listOf(
            GenreItem(id = 28, name = "Action"),
            GenreItem(id = 12, name = "Adventure")
        ),
        homepage = "https://example.com/movie1",
        id = 101,
        imdbId = "tt1234567",
        originalLanguage = "en",
        originalTitle = "Kotlin Saga",
        overview = "An epic adventure about mastering Kotlin in Android development.",
        popularity = 98.7,
        posterPath = "/poster_movie_1.jpg",
        productionCompanies = listOf(
            ProductionCompany(
                id = 1,
                logoPath = "/logo_company_1.png",
                name = "JetBrains Studio",
                originCountry = "US"
            )
        ),
        productionCountries = listOf(
            ProductionCountry(
                iso31661 = "US",
                name = "United States of America"
            )
        ),
        releaseDate = "2024-06-01",
        revenue = 450_000_000,
        runtime = 128,
        spokenLanguages = listOf(
            SpokenLanguage(
                englishName = "English",
                iso6391 = "en",
                name = "English"
            )
        ),
        status = "Released",
        tagline = "Write once. Run everywhere.",
        title = "Kotlin Saga",
        video = false,
        voteAverage = 8.9,
        voteCount = 12_450
    ),

    MovieDetailResponse(
        adult = false,
        backdropPath = "/backdrop_movie_2.jpg",
        belongsToCollection = null,
        budget = 80_000_000,
        genres = listOf(
            GenreItem(id = 16, name = "Animation"),
            GenreItem(id = 35, name = "Comedy")
        ),
        homepage = null,
        id = 102,
        imdbId = "tt7654321",
        originalLanguage = "en",
        originalTitle = "Compose World",
        overview = "A colorful journey into Jetpack Compose and modern UI.",
        popularity = 76.4,
        posterPath = "/poster_movie_2.jpg",
        productionCompanies = listOf(
            ProductionCompany(
                id = 2,
                logoPath = null,
                name = "Compose Animation Studio",
                originCountry = "JP"
            )
        ),
        productionCountries = listOf(
            ProductionCountry(
                iso31661 = "JP",
                name = "Japan"
            )
        ),
        releaseDate = "2023-11-20",
        revenue = 210_000_000,
        runtime = 95,
        spokenLanguages = listOf(
            SpokenLanguage(
                englishName = "Japanese",
                iso6391 = "ja",
                name = "日本語"
            ),
            SpokenLanguage(
                englishName = "English",
                iso6391 = "en",
                name = "English"
            )
        ),
        status = "Released",
        tagline = "UI has never been this fun.",
        title = "Compose World",
        video = false,
        voteAverage = 8.1,
        voteCount = 8_230
    ),

    MovieDetailResponse(
        adult = false,
        backdropPath = "/backdrop_movie_2.jpg",
        belongsToCollection = null,
        budget = 80_000_000,
        genres = listOf(
            GenreItem(id = 16, name = "Animation"),
            GenreItem(id = 35, name = "Comedy")
        ),
        homepage = null,
        id = 103,
        imdbId = "tt7654321",
        originalLanguage = "en",
        originalTitle = "Compose World",
        overview = "A colorful journey into Jetpack Compose and modern UI.",
        popularity = 76.4,
        posterPath = "/poster_movie_2.jpg",
        productionCompanies = listOf(
            ProductionCompany(
                id = 2,
                logoPath = null,
                name = "Compose Animation Studio",
                originCountry = "JP"
            )
        ),
        productionCountries = listOf(
            ProductionCountry(
                iso31661 = "JP",
                name = "Japan"
            )
        ),
        releaseDate = "2023-11-20",
        revenue = 210_000_000,
        runtime = 95,
        spokenLanguages = listOf(
            SpokenLanguage(
                englishName = "Japanese",
                iso6391 = "ja",
                name = "日本語"
            ),
            SpokenLanguage(
                englishName = "English",
                iso6391 = "en",
                name = "English"
            )
        ),
        status = "Released",
        tagline = "UI has never been this fun.",
        title = "Compose World",
        video = false,
        voteAverage = 8.1,
        voteCount = 8_230
    )
)

val mockMoviesResponse = MoviesResponse(
    page = 1,
    totalPages = 55,
    totalResults = 1099,
    results = listOf(
        MoviesItemResponse(
            adult = false,
            backdropPath = "/sK3z0Naed3H1Wuh7a21YRVMxYqt.jpg",
            genreIds = listOf(9648, 53),
            id = 1368166,
            originalLanguage = "en",
            originalTitle = "The Housemaid",
            overview = "Trying to escape her past, Millie Calloway accepts a job as a live-in housemaid...",
            popularity = 167.0359,
            posterPath = "/cWsBscZzwu5brg9YjNkGewRUvJX.jpg",
            releaseDate = "2025-12-18",
            title = "The Housemaid",
            video = false,
            voteAverage = 7.232,
            voteCount = 321
        ),
        MoviesItemResponse(
            adult = false,
            backdropPath = "/mOeBBD49M72vCEXzgA1dS2MwGno.jpg",
            genreIds = listOf(27, 53),
            id = 1228246,
            originalLanguage = "en",
            originalTitle = "Five Nights at Freddy's 2",
            overview = "One year since the supernatural nightmare at Freddy Fazbear's Pizza...",
            popularity = 125.4234,
            posterPath = "/udAxQEORq2I5wxI97N2TEqdhzBE.jpg",
            releaseDate = "2025-12-03",
            title = "Five Nights at Freddy's 2",
            video = false,
            voteAverage = 6.8,
            voteCount = 571
        ),
        MoviesItemResponse(
            adult = false,
            backdropPath = "/tn95fumbkVAgXjOuw9CQOg0aYQz.jpg",
            genreIds = listOf(28, 80, 53),
            id = 1419406,
            originalLanguage = "zh",
            originalTitle = "捕风追影",
            overview = "Macau Police brings the tracking expert police officer out of retirement...",
            popularity = 103.6128,
            posterPath = "/e0RU6KpdnrqFxDKlI3NOqN8nHL6.jpg",
            releaseDate = "2025-08-16",
            title = "The Shadow's Edge",
            video = false,
            voteAverage = 6.911,
            voteCount = 241
        )
    )
)

val mockSearchMovies = listOf(
    MoviesItemResponse(
        id = 1,
        title = "Fight Club",
        releaseDate = "2024-10-17",
        overview = "A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy.",
        posterPath = "/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg",
        voteAverage = 8.7,
        voteCount = 12000,
        popularity = 180.0
    ),
    MoviesItemResponse(
        id = 3,
        title = "Florence Fight Club",
        releaseDate = "2024-10-17",
        overview = "Intertwined stories from the gladiator/athletes participating in the Calcio Storico Fiorentino yearly championship.",
        posterPath = null,
        voteAverage = 5.7,
        voteCount = 12000,
        popularity = 180.0
    ),
    MoviesItemResponse(
        id = 2,
        title = "Zombie Fight Club",
        releaseDate = "2024-10-17",
        overview = "It's the end of the century at a corner of the city in a building riddled with crime - Everyone in the building has turned into zombies.",
        posterPath = "/u8u3KVq0qfJYmNDsaTVOXy4So6f.jpg",
        voteAverage = 4.7,
        voteCount = 12000,
        popularity = 180.0
    ),
    MoviesItemResponse(
        id = 3,
        title = "Florence Fight Club",
        releaseDate = "2024-10-17",
        overview = "Intertwined stories from the gladiator/athletes participating in the Calcio Storico Fiorentino yearly championship.",
        posterPath = null,
        voteAverage = 5.7,
        voteCount = 12000,
        popularity = 180.0
    ),
    MoviesItemResponse(
        id = 2,
        title = "Zombie Fight Club",
        releaseDate = "2024-10-17",
        overview = "It's the end of the century at a corner of the city in a building riddled with crime - Everyone in the building has turned into zombies.",
        posterPath = "/u8u3KVq0qfJYmNDsaTVOXy4So6f.jpg",
        voteAverage = 4.7,
        voteCount = 12000,
        popularity = 180.0
    ),
)
