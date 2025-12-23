import match.Match
import match.Position

object LevelGenerator {
    
    fun generateLevelMatches(/* level: Int */): List<Match> {
        // Горизонтальная комбинация: три в ряд
        val horizontalMatch = Match(
            name = "Горизонтальная комбинация три-в-ряд",
            origin = Position(0, 0),
            width = 3,
            height = 1,
            // Используем listOf для создания неизменяемого списка
            rawPattern = listOf(
                Position(0, 0),
                Position(0, 1),
                Position(0, 2)
            )
        )

        // Вертикальная комбинация: три в ряд
        val verticalMatch = Match(
            name = "Вертикальная комбинация три-в-ряд",
            origin = Position(0, 0),
            width = 1,
            height = 3,
            rawPattern = listOf(
                Position(0, 0),
                Position(1, 0),
                Position(2, 0)
            )
        )

        return listOf(horizontalMatch, verticalMatch)
    }
}