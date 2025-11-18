import kotlin.system.exitProcess

object GameEngine {
    val symbols = charArrayOf(
        'A',
        'B',
        'C',
        'D',
        'E',
        'F',
    )

    fun initGame(
        boardSize: Int = 0
    ) = BoardState(
        board = Board(boardSize),
        score = 0
    )
        .let(Match::fillEmptySpaces)
        .let(::processCascade)

    fun processCascade(currentState: BoardState): BoardState =
        Match.findMatches(currentState.board)
            .takeIf { matches -> matches.isNotEmpty() }
            ?.let { matches ->
                currentState
                    .let { currentState -> Match.removeMatches(currentState, matches) }
                    .let { currentState -> Match.fillEmptySpaces(currentState) }
                    .let { currentState -> processCascade(currentState) }
            } ?: currentState

    fun draw(board: Board) {
        println("  0 1 2 3 4 5 6 7")

        for (i in 0 until 8) {
            print("$i ")
            for (j in 0 until 8) {
                print(board.cells[i][j] + " ")
            }
            println()
        }
        println()
    }

    fun readMove(bs: BoardState): BoardState {
        println(">")

        val input = readln()
        if (input == "q")
            exitProcess(0)

        val board = cloneBoard(bs.board)
        val coords = input.split(' ')

        val x = coords[1].toInt()
        val y = coords[0].toInt()
        val x1 = coords[3].toInt()
        val y1 = coords[2].toInt()
        val element = board.cells[x][y]

        board.cells[x][y] = board.cells[x1][y1]
        board.cells[x1][y1] = element

        return BoardState(board, bs.score)
    }

    fun cloneBoard(board: Board): Board {
        val b = Board(board.size)

        for (row in 0 until board.size) {
            for (col in 0 until board.size) {
                b.cells[row][col] = board.cells[row][col]
            }
        }

        return b
    }
}