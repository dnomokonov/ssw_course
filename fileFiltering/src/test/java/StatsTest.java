import org.example.Stats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StatsTest {
    private Stats stats;

    @BeforeEach
    void setUp() {
        stats = new Stats();
    }

    @Test
    @DisplayName("testUpdateStatsInteger")
    void testUpdateStatsInteger() {
        stats.UpdateStatsInteger(10);
        stats.UpdateStatsInteger(20);
        stats.UpdateStatsInteger(5);

        assertEquals(3, stats.getCountIntegers());
        assertEquals(5, stats.getMinIntegers());
        assertEquals(20, stats.getMaxIntegers());
        assertEquals(35, stats.getSumIntegers());
        assertEquals(11.67, stats.getAverageIntegers(), 0.01);
    }

    @Test
    @DisplayName("testUpdateStatsFloats")
    void testUpdateStatsFloats() {
        stats.UpdateStatsFloat(1.5);
        stats.UpdateStatsFloat(3.5);
        stats.UpdateStatsFloat(1.6);

        assertEquals(3, stats.getCountFloats());
        assertEquals(1.5, stats.getMinFloats());
        assertEquals(3.5, stats.getMaxFloats());
        assertEquals(6.6, stats.getSumFloats());
        assertEquals(2.2, stats.getAverageFloats(), 0.01);
    }

    @Test
    @DisplayName("testUpdateStatsString")
    void testUpdateStatsString() {
        stats.UpdateStatsString("filtering");
        stats.UpdateStatsString("yandexMusic");
        stats.UpdateStatsString("goofy");

        assertEquals(3, stats.getCountString());
        assertEquals(5, stats.getMinStringLength());
        assertEquals(11, stats.getMaxStringLength());
    }

}
