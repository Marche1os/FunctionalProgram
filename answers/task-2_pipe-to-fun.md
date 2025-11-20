```kotlin
 fun initGame(boardSize: Int = 0) =
        BoardState(board = Board(boardSize), score = 0)
            .let(::fillAndCascade)

    private fun fillAndCascade(initialState: BoardState): BoardState =
        initialState
            .let(Match::fillEmptySpaces)
            .let(::processCascade)

    fun processCascade(currentState: BoardState): BoardState =
        Match.findMatches(currentState.board)
            .takeIf { it.isNotEmpty() }
            ?.let { matches ->
                currentState
                    .let { Match.removeMatches(it, matches) }
                    .let(Match::fillEmptySpaces)
                    .let(::processCascade)
            } ?: currentState
```