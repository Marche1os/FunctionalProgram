/**
 * Элемент на игровой доске
 */
data class Element(
    val symbol: Char,
) {
    companion object {
        const val EMPTY = '0'
    }
}
