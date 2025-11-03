data class Board(
    val size: Int,
) {
    val cells = Array(size) {
        CharArray(size) { EMPTY }
    }
}
