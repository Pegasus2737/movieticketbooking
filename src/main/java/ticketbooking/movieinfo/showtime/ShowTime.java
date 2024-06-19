package ticketbooking.movieinfo.showtime;

/**
 * The ShowTime class represents a specific time for a movie show.
 * It provides methods to get and set the year, month, day, hour, and minute of the show time.
 * It also provides methods to compare show times and check for equality.
 */
public class ShowTime implements Cloneable, Comparable<ShowTime> {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    /**
     * Constructs a ShowTime object with the specified year, month, day, hour, and minute.
     *
     * @param year   the year of the show time
     * @param month  the month of the show time
     * @param day    the day of the show time
     * @param hour   the hour of the show time
     * @param minute the minute of the show time
     */
    public ShowTime(int year, int month, int day, int hour, int minute) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Constructs a ShowTime object from a string representation of the show time.
     * The string should be in the format "year-month-day-hour-minute".
     *
     * @param showTime the string representation of the show time
     */
    public ShowTime(String showTime) {
        String[] time = showTime.split("-");
        this.year = Integer.parseInt(time[0]);
        this.month = Integer.parseInt(time[1]);
        this.day = Integer.parseInt(time[2]);
        this.hour = Integer.parseInt(time[3]);
        this.minute = Integer.parseInt(time[4]);
    }

    /**
     * Returns the year of the show time.
     *
     * @return the year of the show time
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the year of the show time.
     *
     * @param year the year of the show time
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Returns the month of the show time.
     *
     * @return the month of the show time
     */
    public int getMonth() {
        return month;
    }

    /**
     * Sets the month of the show time.
     *
     * @param month the month of the show time
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * Returns the day of the show time.
     *
     * @return the day of the show time
     */
    public int getDay() {
        return day;
    }

    /**
     * Sets the day of the show time.
     *
     * @param day the day of the show time
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * Returns the hour of the show time.
     *
     * @return the hour of the show time
     */
    public int getHour() {
        return hour;
    }

    /**
     * Sets the hour of the show time.
     *
     * @param hour the hour of the show time
     */
    public void setHour(int hour) {
        this.hour = hour;
    }

    /**
     * Returns the minute of the show time.
     *
     * @return the minute of the show time
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Sets the minute of the show time.
     *
     * @param minute the minute of the show time
     */
    public void setMinute(int minute) {
        this.minute = minute;
    }

    /**
     * Returns a string representation of the show time in the format "year-month-day-hour-minute".
     *
     * @return a string representation of the show time
     */
    @Override
    public String toString() {
        return year + "-" + month + "-" + day + "-" + hour + "-" + minute;
    }

    /**
     * Creates and returns a copy of this ShowTime object.
     *
     * @return a clone of this ShowTime object
     */
    @Override
    public ShowTime clone() {
        return new ShowTime(year, month, day, hour, minute);
    }

    /**
     * Compares this ShowTime object with the specified ShowTime object for order.
     * Returns a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     *
     * @param showTime the ShowTime object to be compared
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object
     */
    //@Override
    public int compareTo(ShowTime showTime) {
        if (year != showTime.getYear()) {
            return year - showTime.getYear();
        } else if (month != showTime.getMonth()) {
            return month - showTime.getMonth();
        } else if (day != showTime.getDay()) {
            return day - showTime.getDay();
        } else if (hour != showTime.getHour()) {
            return hour - showTime.getHour();
        } else {
            return minute - showTime.getMinute();
        }
    }

    /**
     * Checks if this ShowTime object is equal to the specified object.
     * Returns true if the objects are equal, false otherwise.
     *
     * @param obj the object to compare with
     * @return true if this ShowTime object is equal to the specified object, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ShowTime) {
            ShowTime showTime = (ShowTime) obj;
            return year == showTime.getYear() && month == showTime.getMonth() && day == showTime.getDay() && hour == showTime.getHour() && minute == showTime.getMinute();
        }
        return false;
    }
}
