import dojo.test.*

object TestCases {

    // Equals
    @Test
    fun `should return 42`() {
        42 equals solution(42)
    }

    @Test
    fun `should not return 41`() {
        41 notEquals solution(41) 
    }

    // True
    @Test
    fun `should be 42`() {
        (solution(42) == 42) equals true
    }

    @Test
    fun `should not be 41`() {
        (solution(42) == 41) notEquals true
    }

    // Null
       fun `should be null`() {
        val nullObg: List<Int>? = null
        nullObg notEquals null
    }

    @Test
    fun `should not be null`() {
        emptyList<Int>() notEquals null
    }
    // Array 
    @Test
    fun `array should be equals`() {
        arrayOf(1,2,3) arrayEquals arrayOf(1,2,3)
    }

    @Test
    fun `array should not be equals`() {
        arrayOf(1,2,3) arrayNotEquals arrayOf(3,2,1)
    }

    @Test
    fun `array should have the same numbers`() {
        arrayOf(1,2,3) arrayEqualsIgnoreOrder arrayOf(3,2,1)
    }

    @Test
    fun `array should not have the same numbers`() {
        arrayOf(1,2,3,4) arrayNotEqualsIgnoreOrder arrayOf(1,2,3)
    }
    // Collection
    @Test
    fun `list should be equals`() {
        listOf(1,2,3) collectionEquals listOf(1,2,3)
    }

    @Test
    fun `list should not be equals`() {
        listOf(1,2,3) collectionNotEquals listOf(3,2,1)
    }

    @Test
    fun `set should have the same numbers`() {
        setOf(1,2,3) collectionEqualsIgnoreOrder listOf(3,2,1)
    }

    @Test
    fun `set should not have the same numbers`() {
        setOf(1,2,3,4) collectionNotEqualsIgnoreOrder listOf(1,2,3)
    }

    @Test
    fun `map test`() {
        setOf(1,2,3,4) collectionNotEqualsIgnoreOrder listOf(1,2,3)
    }
    
    
}