package com.mtv.app.movie.common.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

// Formatter untuk format DB
private val dbFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

// Formatter ISO 8601 (2026-01-11T16:20:00+07:00)
private val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.US)

// ----------------------------
// String DB → Date
// ----------------------------
fun String.toDateDb(): Date? = try {
    dbFormat.parse(this)
} catch (_: Exception) {
    null
}

// ----------------------------
// Date → String DB
// ----------------------------
fun Date.toDbString(): String = dbFormat.format(this)

// ----------------------------
// ISO → Date
// ----------------------------
fun String.toDateIso(): Date? = try {
    isoFormat.parse(this)
} catch (_: Exception) {
    null
}

// ----------------------------
// ISO → DB format
// ----------------------------
fun String.isoToDb(): String? = this.toDateIso()?.let { dbFormat.format(it) }

// ----------------------------
// NOW → DB format
// ----------------------------
fun nowDb(): String = dbFormat.format(Date())

// ----------------------------
// NOW → ISO format
// ----------------------------
fun nowIso(): String = isoFormat.format(Date())


// ----------------------------
// NOW → ISO format
// ----------------------------
@RequiresApi(Build.VERSION_CODES.O)
fun today(): LocalDate = LocalDate.now()

fun stringToTimestamp(date: String): Timestamp {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val parsedDate = sdf.parse(date) ?: Date()
    return Timestamp(parsedDate)
}
