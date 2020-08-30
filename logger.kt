package dojo.log

enum class Color(val ansi: String) {
    BLACK("\u001B[30m"),
    RED ("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m")
}

/** Logger **/
class Logger(val canUseColor: Boolean = true) {

    private val ANSI_RESET = "\u001B[0m"

    fun log(message: Any?, color: Color, newLine: Boolean = false) {
        if(canUseColor) {
            print("${color.ansi}$message$ANSI_RESET")
        } else {
            print(message)
        }

        if(newLine) print("\n")
    }

    fun log(message: Any?, newLine: Boolean = false) = log(message, Color.WHITE, newLine)

    fun error(exception: Throwable) {
        if(canUseColor) {
            print(Color.RED.ansi)
            exception.printStackTrace()
            print(ANSI_RESET)
        } else {
            exception.printStackTrace()
        }
    }

}