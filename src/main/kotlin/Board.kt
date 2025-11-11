data class Board(
    val size: Int,
) {
    var cells = Array(size) {
        CharArray(size) { Element.EMPTY }
    }
}
