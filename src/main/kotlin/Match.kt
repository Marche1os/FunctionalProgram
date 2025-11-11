import GameEngine.symbols
import kotlin.random.Random

enum class MatchDirection {
    Horizontal,
    Vertical
}

data class Match(
    val direction: MatchDirection,
    val row: Int,
    val col: Int,
    val length: Int,
) {

    companion object {
        fun findMatches(board: Board): List<Match> {
            val matches = mutableListOf<Match>()

            for (row in 0 until board.size) {
                var startCol = 0
                for (col in 1 until board.size) {
                    // Пропускаем пустые ячейки в начале строки
                    if (board.cells[row][startCol] == Element.EMPTY) {
                        startCol = col;
                        continue;
                    }

                    // Если текущая ячейка пустая, обрываем текущую последовательность
                    if (board.cells[row][col] == Element.EMPTY) {
                        addMatchIfValid(matches, row, startCol, col - startCol, MatchDirection.Horizontal);
                        startCol = col + 1;
                        continue;
                    }

                    // Проверяем совпадение символов для непустых ячеек
                    if (board.cells[row][col] != board.cells[row][startCol]) {
                        addMatchIfValid(matches, row, startCol, col - startCol, MatchDirection.Horizontal);
                        startCol = col;
                    } else if (col == board.size - 1) {
                        addMatchIfValid(matches, row, startCol, col - startCol + 1, MatchDirection.Horizontal);
                    }
                }
            }

            // Вертикальные комбинации
            for (col in 0 until board.size) {
                var startRow = 0;
                for (row in 1 until board.size) {
                    // Пропускаем пустые ячейки в начале столбца
                    if (board.cells[startRow][col] == Element.EMPTY) {
                        startRow = row;
                        continue;
                    }

                    // Если текущая ячейка пустая, обрываем текущую последовательность
                    if (board.cells[row][col] == Element.EMPTY) {
                        addMatchIfValid(matches, startRow, col, row - startRow, MatchDirection.Vertical);
                        startRow = row + 1;
                        continue;
                    }

                    // Проверяем совпадение символов для непустых ячеек
                    if (board.cells[row][col] != board.cells[startRow][col]) {
                        addMatchIfValid(matches, startRow, col, row - startRow, MatchDirection.Vertical);
                        startRow = row;
                    } else if (row == board.size - 1) {
                        addMatchIfValid(matches, startRow, col, row - startRow + 1, MatchDirection.Vertical);
                    }
                }
            }

            return matches
        }

        fun removeMatches(
            currentState: BoardState,
            matches: List<Match>
        ): BoardState {
            if (matches.isEmpty()) {
                return currentState
            }

            val markedCells = markCellsForRemoval(currentState.board, matches)
            val gravityAppliedCells = applyGravity(markedCells, currentState.board.size)

            val removedCount = matches.sumOf { match -> match.length }
            val newScore = currentState.score + calculateScore(removedCount)

            return BoardState(
                Board(currentState.board.size).apply { cells = gravityAppliedCells },
                newScore,
            )
        }

        fun fillEmptySpaces(currentState: BoardState): BoardState {
            if (currentState.board.cells.isEmpty()) {
                return currentState
            }

            val newCells = currentState.board.cells.clone()

            for (row in 0 until currentState.board.size) {
                for (col in 0 until currentState.board.size) {
                    if (newCells[row][col] == Element.EMPTY) {
                        newCells[row][col] = symbols[Random.nextInt(symbols.size)]
                    }
                }
            }

            return BoardState(
                Board(currentState.board.size).apply { cells = newCells },
                currentState.score
            )
        }

        private fun markCellsForRemoval(
            board: Board,
            matches: List<Match>,
        ): Array<CharArray> {
            val newCells = board.cells.clone()
            matches.forEach { match ->
                for (i in 0 until match.length) {
                    val (row, col) = when (match.direction) {
                        MatchDirection.Horizontal -> match.row to match.col + i
                        MatchDirection.Vertical -> match.row + i to match.col
                    }

                    newCells[row][col] = Element.EMPTY
                }
            }

            return newCells
        }

        private fun applyGravity(
            cells: Array<CharArray>,
            size: Int
        ): Array<CharArray> {
            val newCells = Array(size) { CharArray(size) }

            for (row in 0 until size) {
                for (col in 0 until size) {
                    newCells[row][col] = Element.EMPTY
                }
            }

            for (col in 0 until size) {
                var newRow = size - 1

                for (row in size - 1 downTo 0) {
                    if (cells[row][col] != Element.EMPTY) {
                        newCells[newRow][col] = cells[row][col]
                        newRow--
                    }
                }
            }

            return newCells;
        }

        private fun calculateScore(
            removedCount: Int,
        ): Int {
            return removedCount * 10
        }

        private fun addMatchIfValid(
            matches: MutableList<Match>,
            row: Int,
            col: Int,
            length: Int,
            direction: MatchDirection,
        ) {
            // Учитываем только комбинации из 3 и более элементов (ТЗ)
            if (length >= 3) {
                matches.add(Match(direction, row, col, length))
            }
        }
    }
}
