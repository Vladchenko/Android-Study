package com.example.vladislav.androidstudy.kotlin.sometasks.stopwatch

private val timeBuilder = StringBuilder(12)
/**
 * Formats elapsed time (in milliseconds) to `"MM:SS.mmm"` (minutes:seconds:milliseconds).
 *
 * Format:
 *   • **MM**: minutes (2 digits, zero-padded)
 *   • **SS**: seconds (2 digits, zero-padded)
 *   • **mmm**: milliseconds (3 digits, zero-padded)
 *
 * Example:
 *   • `123_456L` → `"02:03.456"` (2 min 3 sec 456 ms)
 *   • `987_654L` → `"16:26.500"` (16 min 26 sec 500 ms)
 *
 * ⚠️ **Implementation notes**:
 *   • Uses a shared [StringBuilder] (`timeBuilder`) for performance (avoids allocations).
 *   • **Not thread-safe** — intended for single-threaded use inside the timer coroutine.
 *
 * @param timeInMillis elapsed time in milliseconds (must be ≥ 0).
 * @return formatted string in `"MM:SS.mmm"` format.
 */
fun Long.formatTime(): String {
    val minutes = this / 60000
    val seconds = (this % 60000) / 1000
    val ms = this % 1000

    timeBuilder.clear()
    timeBuilder.append(if (minutes < 10) "0$minutes" else minutes.toString())
    timeBuilder.append(':')
    timeBuilder.append(if (seconds < 10) "0$seconds" else seconds.toString())
    timeBuilder.append('.')
    when {
        ms < 10 -> timeBuilder.append("00$ms")
        ms < 100 -> timeBuilder.append("0$ms")
        else -> timeBuilder.append(ms.toString())
    }
    return timeBuilder.toString()
}