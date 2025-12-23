import GameEngine.cloneBoard
import GameEngine.symbols
import match.Match
import match.Position
import kotlin.random.Random


//data class Match(
//    val direction: MatchDirection,
//    val row: Int,
//    val col: Int,
//    val length: Int,
//) {
//
//    companion object {
//        fun findMatches(bs: BoardState, patterns: List<Match>): List<Match> {
//            val foundMatches = mutableListOf<Match>()
//            val board = bs.board
//
//            for (pattern in patterns) {
//                // Пропускаем пустые паттерны
//                if (pattern.pattern.isEmpty())
//                    continue
//
//                // В Kotlin диапазоны '..' включают последнее число (аналог <=)
//                for (row in 0..(board.size - pattern.height)) {
//                    for (col in 0..(board.size - pattern.width)) {
//                        val candidateOrigin = Position(row, col)
//                        // Обращение к массиву. Если это 2D массив в Kotlin, используется [row][col]
//                        val firstElement = board.cells[row][col]
//
//                        // Пропускаем пустые элементы
//                        if (firstElement == ' ')
//                            continue
//
//                        var isValid = true
//
//                        // Проверяем все позиции паттерна
//                        for (relPos in pattern.pattern) {
//                            val absRow = row + relPos.row
//                            val absCol = col + relPos.col
//                            val currentCell = board.cells[absRow][absCol]
//
//                            if (currentCell == ' ' || currentCell != firstElement) {
//                                isValid = false
//                                break
//                            }
//                        }
//
//                        if (isValid) {
//                            foundMatches.add(Match(
//                                pattern.name,
//                                candidateOrigin,
//                                pattern.width,
//                                pattern.height,
//                                pattern.pattern
//                            ))
//                        }
//                    }
//                }
//            }
//
//            return foundMatches
//        }
//
////        fun findMatches(board: Board): List<Match> {
////            val matches = mutableListOf<Match>()
////
////            for (row in 0 until board.size) {
////                var startCol = 0
////                for (col in 1 until board.size) {
////                    // Пропускаем пустые ячейки в начале строки
////                    if (board.cells[row][startCol] == Element.EMPTY) {
////                        startCol = col;
////                        continue;
////                    }
////
////                    // Если текущая ячейка пустая, обрываем текущую последовательность
////                    if (board.cells[row][col] == Element.EMPTY) {
////                        addMatchIfValid(matches, row, startCol, col - startCol, MatchDirection.Horizontal);
////                        startCol = col + 1;
////                        continue;
////                    }
////
////                    // Проверяем совпадение символов для непустых ячеек
////                    if (board.cells[row][col] != board.cells[row][startCol]) {
////                        addMatchIfValid(matches, row, startCol, col - startCol, MatchDirection.Horizontal);
////                        startCol = col;
////                    } else if (col == board.size - 1) {
////                        addMatchIfValid(matches, row, startCol, col - startCol + 1, MatchDirection.Horizontal);
////                    }
////                }
////            }
////
////            // Вертикальные комбинации
////            for (col in 0 until board.size) {
////                var startRow = 0;
////                for (row in 1 until board.size) {
////                    // Пропускаем пустые ячейки в начале столбца
////                    if (board.cells[startRow][col] == Element.EMPTY) {
////                        startRow = row;
////                        continue;
////                    }
////
////                    // Если текущая ячейка пустая, обрываем текущую последовательность
////                    if (board.cells[row][col] == Element.EMPTY) {
////                        addMatchIfValid(matches, startRow, col, row - startRow, MatchDirection.Vertical);
////                        startRow = row + 1;
////                        continue;
////                    }
////
////                    // Проверяем совпадение символов для непустых ячеек
////                    if (board.cells[row][col] != board.cells[startRow][col]) {
////                        addMatchIfValid(matches, startRow, col, row - startRow, MatchDirection.Vertical);
////                        startRow = row;
////                    } else if (row == board.size - 1) {
////                        addMatchIfValid(matches, startRow, col, row - startRow + 1, MatchDirection.Vertical);
////                    }
////                }
////            }
////
////            return matches
////        }
//
//        fun removeMatches(
//            currentState: BoardState,
//            matches: List<Match>
//        ): BoardState {
//            if (matches.isEmpty()) {
//                return currentState
//            }
//
//            val board = currentState.copy().board
//
//            matches.forEach { match ->
//                for (position in match.getAbsolutePositions()) {
//                    if (position.row >= 0 && position.row < board.size &&
//                        position.col >= 0 && position.col < board.size) {
//                        board.cells[position.row][position.col] = Element.EMPTY
//                    }
//                }
//            }
//
//            return BoardState(
//                board = board,
//                score = currentState.score
//            )
//        }
//
//        fun fillEmptySpaces(currentState: BoardState): BoardState {
//            if (currentState.board.cells.isEmpty()) {
//                return currentState
//            }
//
//            val newCells = currentState.board.cells.clone()
//
//            for (row in 0 until currentState.board.size) {
//                for (col in 0 until currentState.board.size) {
//                    if (newCells[row][col] == Element.EMPTY) {
//                        newCells[row][col] = symbols[Random.nextInt(symbols.size)]
//                    }
//                }
//            }
//
//            return BoardState(
//                Board(currentState.board.size).apply { cells = newCells },
//                currentState.score
//            )
//        }
//
//        private fun markCellsForRemoval(
//            board: Board,
//            matches: List<Match>,
//        ): Array<CharArray> {
//            val newCells = board.cells.clone()
//            matches.forEach { match ->
//                for (i in 0 until match.length) {
//                    val (row, col) = when (match.direction) {
//                        MatchDirection.Horizontal -> match.row to match.col + i
//                        MatchDirection.Vertical -> match.row + i to match.col
//                    }
//
//                    newCells[row][col] = Element.EMPTY
//                }
//            }
//
//            return newCells
//        }
//
//        fun applyGravity(bds: BoardState): BoardState {
//            val board = cloneBoard(bds.board)
//
//            for (col in 0 until board.size) {
//                val nonEmptyElements = mutableListOf<Char>()
//
//                // собираем непустые элементы сверху вниз
//                for (row in 0 until board.size) {
//                    if (board.cells[row][col] != ' ') {
//                        nonEmptyElements.add(board.cells[row][col])
//                    }
//                }
//
//                val emptyCount = board.size - nonEmptyElements.size
//
//                // сверху — пустые
//                for (row in 0 until emptyCount) {
//                    board.cells[row][col] = Element.EMPTY
//                }
//
//                // снизу — непустые
//                for (row in emptyCount until board.size) {
//                    board.cells[row][col] = nonEmptyElements[row - emptyCount]
//                }
//            }
//
//            return BoardState(board, bds.score)
//        }
//
//        private fun calculateScore(
//            removedCount: Int,
//        ): Int {
//            return removedCount * 10
//        }
//
//        private fun addMatchIfValid(
//            matches: MutableList<Match>,
//            row: Int,
//            col: Int,
//            length: Int,
//            direction: MatchDirection,
//        ) {
//            // Учитываем только комбинации из 3 и более элементов (ТЗ)
//            if (length >= 3) {
//                matches.add(Match(direction, row, col, length))
//            }
//        }
//
//
//    }
//}
