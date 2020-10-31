package org.osrs.input.mouse;

public class MTimer {

	private long startTime = 0;
	private long endTime = 0;

	/**
	 * Use for countdowns.
	 * @param timeToRun Countdown time. No effect once timer is finished. Use isDone() to check.
	 */
	public MTimer(long timeToRun) {
		if (timeToRun < 0) {
			timeToRun *= -1;
		}
		startTime = System.currentTimeMillis();
		endTime = startTime + timeToRun;
	}

	/**
	 * Use to measure the passing of time.
	 */
	public MTimer() {
		startTime = System.currentTimeMillis();
		endTime = startTime;
	}

	/**
	 * Add time to a countdown.
	 * @param timeToRun Number of milliseconds to add.
	 */
	public void addTime(long timeToRun) {
		//edit by Apples
		endTime = endTime + timeToRun;
	}

	/**
	 * Creates a new end time.
	 * @param timeToRun Number of milliseconds to end from the current time.
	 */
	//Replaces old addTime method
	public void newEndTime(long timeToRun) {
		endTime = System.currentTimeMillis() + timeToRun;
	}

	/**
	 * Completely reset timer.
	 */
	public void reset() {
		startTime = System.currentTimeMillis();
		endTime = startTime;
	}

	/**
	 * Check if countdown is finished.
	 * @return True if finished, false if still going.
	 */
	public boolean isDone() {
		if (endTime <= System.currentTimeMillis()) {
			return true;
		}
		return false;
	}

	/**
	 * Get time remaining in countdown.
	 * @return Time remaining until countdown is finished.
	 */
	public long getTimeRemaining() {
		return endTime - System.currentTimeMillis();
	}

	/**
	 * Get time passed since timer initialization.
	 * @return Time passed.
	 */
	public long getTimeElapsed() {
		return System.currentTimeMillis() - startTime;
	}

	/**
	 * Formats time in the "HH:MM:SS" format.
	 * @param timeMilliseconds Time to format.
	 * @return Formatted time.
	 */
	public String getFormattedTime(long timeMilliseconds) {
		StringBuilder b = new StringBuilder();
		long runtime = timeMilliseconds;
		long TotalSecs = runtime / 1000;
		long TotalMins = TotalSecs / 60;
		long TotalHours = TotalMins / 60;
		int seconds = (int) TotalSecs % 60;
		int minutes = (int) TotalMins % 60;
		int hours = (int) TotalHours % 60;
		if (hours < 10) {
			b.append("0");
		}
		b.append(hours);
		b.append(" : ");
		if (minutes < 10) {
			b.append("0");
		}
		b.append(minutes);
		b.append(" : ");
		if (seconds < 10) {
			b.append("0");
		}
		b.append(seconds);
		return b.toString();
	}

}
