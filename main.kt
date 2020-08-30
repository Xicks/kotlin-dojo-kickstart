import dojo.log.Logger
import dojo.log.Color
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val logger = Logger(!args.contains("--no-color"))

    logger.log("***** INITIATING TESTS *****", Color.CYAN, true)

    val testMethods = TestCases::class.members.filter { method -> method.annotations.any { it is Test } }

    val allPassed = testMethods.all { method ->
        logger.log(" -> ${method.name}")
        val start = System.nanoTime()

        val passed = try { 
            method.call(TestCases)
            logger.log("   #   PASSED  ", Color.GREEN)
            true
        } catch(e: Exception) {
            logger.log("   #   FAILED   ", Color.RED, true)
            logger.error(e.cause!!)
            false
        }

        val runtime = (System.nanoTime() - start) / 1000000.0
        logger.log(" (took $runtime milliseconds)", true)

        passed
    }

    if (allPassed) {
        logger.log("***** ALL TESTS HAVE PASSED *****", Color.CYAN, true)
        exitProcess(0)
    } else {
        logger.log("***** THERE ARE TEST FAILURES *****", Color.YELLOW, true)
        exitProcess(1)
    }
}

annotation class Test