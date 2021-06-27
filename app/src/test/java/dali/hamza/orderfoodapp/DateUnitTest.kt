package dali.hamza.orderfoodapp

import dali.hamza.core.common.DateManager
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DateUnitTest {
    @Test
    fun currentCET() {
        val currentDiffCET = DateManager.getTimeCET()
        assertEquals(currentDiffCET,1)
    }
}