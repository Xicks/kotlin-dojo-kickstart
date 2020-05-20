enum class AssertionErrorType() {
    EQUALS,
    NOT_EQUALS;
}

private fun <T> checkObjectEquals(obj1: T, obj2: T) = obj1 == obj2
private fun <T> checkCollectionEquals(collection1: Collection<T>, collection2: Collection<T>, checkOrder: Boolean) : Boolean {
    if(collection1.size != collection2.size) return false
    if(checkOrder) {
        collection1.forEachIndexed { index, obj ->
            if (obj != collection2.elementAt(index)) {
                return false
            }
        }
    } else {
        return collection1.containsAll(collection2)
    }
    return true;
}

private fun <T: Comparable<in T>> checkArrayEquals(array1: Array<T>, array2: Array<T>, checkOrder: Boolean) : Boolean {
    if(array1.size != array2.size) return false
    
    val arrayToCheck1 = if(checkOrder) array1 else array1.sortedArray()
    if(!checkOrder) {
        array1.sort()
        array2.sort()
    }

    array1.forEachIndexed { index, obj ->
        if (obj != array2.elementAt(index)) {
            return false
        }
    }

    return true
}

class AssertionError(val expected: Any?, val actual: Any?, val type: AssertionErrorType) : RuntimeException() {
    override val message
        get() = when(type) {
                AssertionErrorType.EQUALS -> "Expected value `$expected` must not be iguals to `$actual`"
                AssertionErrorType.NOT_EQUALS -> "Expected value `$expected` is not equals to `$actual`"
            }
}

infix fun <T> Any?.equals(other: T) { if(!checkObjectEquals(this, other)) throw AssertionError(this, other, AssertionErrorType.NOT_EQUALS) }
infix fun <T> Any?.notEquals(other: T) { if(checkObjectEquals(this, other)) throw AssertionError(this, other, AssertionErrorType.EQUALS) }

infix fun <T> Collection<T>.collectionEquals(other: Collection<T>) { if(!checkCollectionEquals(this, other, true)) throw AssertionError(this, other, AssertionErrorType.NOT_EQUALS) }
infix fun <T> Collection<T>.collectionNotEquals(other: Collection<T>) { if(checkCollectionEquals(this, other, true)) throw AssertionError(this, other, AssertionErrorType.EQUALS) }

infix fun <T> Collection<T>.collectionEqualsIgnoreOrder(other: Collection<T>) { if(!checkCollectionEquals(this, other, false)) throw AssertionError(this, other, AssertionErrorType.NOT_EQUALS) }
infix fun <T> Collection<T>.collectionNotEqualsIgnoreOrder(other: Collection<T>) { if(checkCollectionEquals(this, other, false)) throw AssertionError(this, other, AssertionErrorType.EQUALS) }

infix fun <T: Comparable<in T>> Array<T>.arrayEquals(other: Array<T>) { if(!checkArrayEquals(this, other, true)) throw AssertionError(this, other, AssertionErrorType.NOT_EQUALS) }
infix fun <T: Comparable<in T>> Array<T>.arrayNotEquals(other: Array<T>) { if(checkArrayEquals(this, other, true)) throw AssertionError(this, other, AssertionErrorType.EQUALS) }

infix fun <T: Comparable<in T>> Array<T>.arrayEqualsIgnoreOrder(other: Array<T>) { if(!checkArrayEquals(this, other, false)) throw AssertionError(this, other, AssertionErrorType.NOT_EQUALS) }
infix fun <T: Comparable<in T>> Array<T>.arrayNotEqualsIgnoreOrder(other: Array<T>) { if(checkArrayEquals(this, other, false)) throw AssertionError(this, other, AssertionErrorType.EQUALS) }

annotation class Test

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
