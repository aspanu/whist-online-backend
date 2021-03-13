package service

import com.aspanu.whistOnline.service.ScoreHelper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ScoreHelperTest {
    private val scoreHelper = ScoreHelper()

    @Test
    fun testCorrectScoring() {
        assertEquals(5, scoreHelper.calculateScore(0,0))
        assertEquals(6, scoreHelper.calculateScore(1,0))
        assertEquals(8, scoreHelper.calculateScore(2,0))
        assertEquals(11, scoreHelper.calculateScore(3,0))
        assertEquals(15, scoreHelper.calculateScore(4,0))
        assertEquals(20, scoreHelper.calculateScore(5,0))
        assertEquals(26, scoreHelper.calculateScore(6,0))
        assertEquals(33, scoreHelper.calculateScore(7,0))
        assertEquals(41, scoreHelper.calculateScore(8,0))
    }

    @Test
    fun testIncorrectScoring() {
        assertEquals(-1, scoreHelper.calculateScore(0,1))
        assertEquals(-3, scoreHelper.calculateScore(0,2))
        assertEquals(-6, scoreHelper.calculateScore(0,3))
        assertEquals(-10, scoreHelper.calculateScore(0,4))
        assertEquals(-15, scoreHelper.calculateScore(0,5))
        assertEquals(-21, scoreHelper.calculateScore(0,6))
        assertEquals(-28, scoreHelper.calculateScore(0,7))
        assertEquals(-36, scoreHelper.calculateScore(0,8))
    }
}