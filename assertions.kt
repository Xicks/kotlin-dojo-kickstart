package dojo.test

/** Assertion **/
class AssertionError(val expected: Any?, val actual: Any?, val type: AssertionErrorType) : RuntimeException() {
    override val message
        get() = when(type) {
                AssertionErrorType.EQUALS -> "Expected value `$expected` must not be iguals to `$actual`"
                AssertionErrorType.NOT_EQUALS -> "Expected value `$expected` is not equals to `$actual`"
            }
}

enum class AssertionErrorType() {
    EQUALS,
    NOT_EQUALS;
}

/** Assertion methods **/
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

private fun <T: Comparable<T>> checkArrayEquals(array1: Array<T>, array2: Array<T>, checkOrder: Boolean) : Boolean {
    if(array1.size != array2.size) return false
    
    val (arrayToCheck1, arrayToCheck2) = if(checkOrder) array1 to array2 else array1.sortedArray() to array2.sortedArray()

    arrayToCheck1.forEachIndexed { index, obj ->
        if (obj != arrayToCheck2.elementAt(index)) {
            return false
        }
    }

    return true
}

infix fun <T> Any?.equals(other: T) { if(!checkObjectEquals(this, other)) throw AssertionError(this, other, AssertionErrorType.NOT_EQUALS) }
infix fun <T> Any?.notEquals(other: T) { if(checkObjectEquals(this, other)) throw AssertionError(this, other, AssertionErrorType.EQUALS) }

infix fun <T> Collection<T>.collectionEquals(other: Collection<T>) { if(!checkCollectionEquals(this, other, true)) throw AssertionError(this, other, AssertionErrorType.NOT_EQUALS) }
infix fun <T> Collection<T>.collectionNotEquals(other: Collection<T>) { if(checkCollectionEquals(this, other, true)) throw AssertionError(this, other, AssertionErrorType.EQUALS) }

infix fun <T> Collection<T>.collectionEqualsIgnoreOrder(other: Collection<T>) { if(!checkCollectionEquals(this, other, false)) throw AssertionError(this, other, AssertionErrorType.NOT_EQUALS) }
infix fun <T> Collection<T>.collectionNotEqualsIgnoreOrder(other: Collection<T>) { if(checkCollectionEquals(this, other, false)) throw AssertionError(this, other, AssertionErrorType.EQUALS) }

infix fun <T: Comparable<T>> Array<T>.arrayEquals(other: Array<T>) { if(!checkArrayEquals(this, other, true)) throw AssertionError(this, other, AssertionErrorType.NOT_EQUALS) }
infix fun <T: Comparable<T>> Array<T>.arrayNotEquals(other: Array<T>) { if(checkArrayEquals(this, other, true)) throw AssertionError(this, other, AssertionErrorType.EQUALS) }

infix fun <T: Comparable<T>> Array<T>.arrayEqualsIgnoreOrder(other: Array<T>) { if(!checkArrayEquals(this, other, false)) throw AssertionError(this, other, AssertionErrorType.NOT_EQUALS) }
infix fun <T: Comparable<T>> Array<T>.arrayNotEqualsIgnoreOrder(other: Array<T>) { if(checkArrayEquals(this, other, false)) throw AssertionError(this, other, AssertionErrorType.EQUALS) }