fun main() {
    var boardState = GameEngine.initGame()
    while (true) {
        GameEngine.draw(boardState.board)
        boardState = GameEngine.readMove(boardState)
    }
}