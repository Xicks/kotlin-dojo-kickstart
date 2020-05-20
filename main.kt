import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val logger = Logger(!args.contains("--no-color"))

    logger.log("***** INITIATING TESTS *****", Color.CYAN, true)
    val testMethods = TestCases::class.members.filter { method -> method.annotations.any { it is Test } }
    var allPassed = true
    testMethods.forEach {
        logger.log(" -> ${it.name}")
        val start = System.nanoTime()
        var runtime: Double
        try { 
            it.call(TestCases)
            runtime = (System.nanoTime() - start) / 1000000.0
            logger.log("   #   PASSED  ", Color.GREEN)
        } catch(e: Exception) {
            runtime = (System.nanoTime() - start) / 1000000.0
            allPassed = false
            logger.log("   #   FAILED   ", Color.RED, true)
            logger.error(e.cause!!)
        }
        logger.log(" (took $runtime milliseconds)", true)
    }

    if (allPassed) {
        logger.log("***** ALL TESTS HAVE PASSED *****", Color.CYAN, true)
        exitProcess(0)
    } else {
        logger.log("***** THERE ARE TEST FAILURES *****", Color.YELLOW, true)
        exitProcess(1)
    }
}