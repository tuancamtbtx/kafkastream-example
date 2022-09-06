package vn.dataplatform.transformer.common.utils;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@UtilityClass
public class XRateLimitUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(XRateLimitUtils.class.getName());

    /**
     * Get sleep time before next bulk index
     *
     * @param checkingTs    checking timestamp before index
     * @param lastFlushTs   start timestamp of last flush
     * @param lastFlushSize last flush size
     * @param limitRate     rate limit, request per second
     * @return the time processor should sleep before sleep state
     */
    public static long getShouldSleepTimeBeforeIndex(long checkingTs, long lastFlushTs, int lastFlushSize, int limitRate) {
        return 1000L * lastFlushSize / limitRate - (checkingTs - lastFlushTs);
    }

    /**
     * Get sleep time before next bulk index
     *
     * @param checkingTs    checking timestamp before index
     * @param lastFlushTs   start timestamp of last flush
     * @param lastFlushSize last flush size
     * @param limitRate     rate limit, request per second
     * @return the time processor should sleep before sleep state
     */
    public static void shouldSleep(long checkingTs, long lastFlushTs, int lastFlushSize, int limitRate) throws InterruptedException {
        long sleepInMs = getShouldSleepTimeBeforeIndex(checkingTs, lastFlushTs, lastFlushSize, limitRate);
        try {
            if (sleepInMs > 0) {
                Thread.sleep(sleepInMs);
            }
        } catch (InterruptedException e) {
            LOGGER.error("Error when sleeping when applying rate limit", e);
            throw e;
        }
    }
}
