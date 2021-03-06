package org.fortune.commons.core.util;

import org.fortune.commons.core.constants.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {

    /**
     * 一分钟的秒数
     */
    public static final int SECONDS_ONE_MINUTE = 60;

    /**
     * 一秒的毫秒数
     */
    public static final int MILLISECONDS_ONE_SECOND = 1000;

    /**
     * 一小时的秒数
     */
    public static final int SECONDS_ONE_HOUR = 60 * SECONDS_ONE_MINUTE;

    /**
     * 一天的秒数
     */
    public static final int SECONDS_ONE_DAY = 24 * SECONDS_ONE_HOUR;

    /**
     * 数据库存储的时间格式串，如yyyymmdd 或yyyymmddHHMiSS
     */
    public static final int DB_STORE_DATE = 1;

    /**
     * 用连字符-分隔的时间时间格式串，如yyyy-mm-dd 或yyyy-mm-dd HH:Mi:SS
     */
    public static final int HYPHEN_DISPLAY_DATE = 2;

    /**
     * 用连字符.分隔的时间时间格式串，如yyyy.mm.dd 或yyyy.mm.dd HH:Mi:SS
     */
    public static final int DOT_DISPLAY_DATE = 3;

    /**
     * 用中文字符分隔的时间格式串，如yyyy年mm月dd 或yyyy年mm月dd HH:Mi:SS
     */
    public static final int CN_DISPLAY_DATE = 4;
    /**
     * 日期的开始时间戳
     */
    public static final String DB_STORE_DATE_BEGIN = "000000";

    /**
     * 日期的结束时间戳
     */
    public static final String DB_STORE_DATE_END = "235959";

    public static final String DATE_PATTERN_DASH_1 = "MM-dd-yyyy";

    public static final String PATTERN_SHORT_DATE_SLASH = "M/d/yyyy";
    public static final String PATTERN_PERIOD_DATE = "yyyyMM";
    public static final String PATTERN_MONTH_YEAR = "MM/yyyy";
    public static final String PATTERN_YEAR_MONTH = "yyyy/MM";

    public static final String PATTERN_FULL_DATE_SLASH = "MM/dd/yyyy";
    public static final String PATTERN_FULL_DATE_SLASH_1 = "yyyy/MM/dd";
    public static final String PATTERN_FULL_DATE_DASH = "yyyy-MM-dd";
    public static final String PATTERN_FULL_DATE_DOT = "yyyy.MM.dd";

    public static final String PATTERN_FULL_DATE_TIME_24 = "MM/dd/yyyy HH:mm:ss";
    public static final String PATTERN_FULL_DATE_TIME_UNDERSCORE = "yyyyMMdd_HHmmss";
    public static final String DATE_TIME_PATTERN_2 = "yyyy-MM-dd HH:mm:ss";

    private static Set<String> patterns_datetime = new HashSet();
    private static Set<String> patterns_date = new HashSet();

    static {
        patterns_date.add(DateUtil.PATTERN_FULL_DATE_DASH);
        patterns_date.add(DateUtil.PATTERN_FULL_DATE_DOT);
        patterns_date.add(DateUtil.PATTERN_FULL_DATE_SLASH_1);
        patterns_date.add(DateUtil.PATTERN_FULL_DATE_SLASH);
        patterns_date.add(DateUtil.PATTERN_SHORT_DATE_SLASH);
        patterns_date.add(DateUtil.PATTERN_PERIOD_DATE);
        patterns_date.add(DateUtil.PATTERN_MONTH_YEAR);
        patterns_date.add(DateUtil.DATE_PATTERN_DASH_1);
        patterns_datetime.add(DateUtil.DATE_TIME_PATTERN_2);
        patterns_datetime.add(DateUtil.PATTERN_FULL_DATE_TIME_24);
        patterns_datetime.add(DateUtil.PATTERN_FULL_DATE_TIME_UNDERSCORE);

    }

    public static String getCurrentDateTime() {
        return getCurrentDateTime(new SimpleDateFormat(PATTERN_FULL_DATE_TIME_24));
    }

    public static String getCurrentDateTime(SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date());
    }

    public static Date string2Date(String date) {
        if(!StringUtil.hasText(date)) return null;
        Date dateObj;
        Iterator it = patterns_date.iterator();
        if(date.length() > 10) {
            it = patterns_datetime.iterator();
        }
        while (it.hasNext()) {
            String pattern = (String) it.next();
            dateObj = string2Date(date,pattern,false);
            if(dateObj != null) {
                return dateObj;
            }
        }
        return string2Date(date, PATTERN_FULL_DATE_SLASH);
    }

    public static Date string2Date(String originalValue, String format) {
        return string2Date(originalValue, format, true);
    }

    public static Date string2Date(String originalValue, String format, boolean lenient) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(lenient);
        try {
            return dateFormat.parse(originalValue);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String date2String(Date originalValue) {
        return date2String(originalValue, PATTERN_FULL_DATE_SLASH);
    }

    public static String date2String(Date originalValue, String format) {
        if (originalValue == null) return null;

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(originalValue);
    }

    public static Date getDateIgnoreTime(Date date) {
        return string2Date(date2String(date, PATTERN_FULL_DATE_SLASH), PATTERN_FULL_DATE_SLASH);
    }

    public static boolean isValidDate(String dateStr, String format) {
        return isValidDate(dateStr, format, true);
    }

    public static boolean isValidDate(String dateStr, String format, boolean lenient) {
        if (string2Date(dateStr, format, false) == null) return false;

        if (!lenient) {
            Date date = string2Date(dateStr, format);
            String nStr = date2String(date, format);
            if (!dateStr.equals(nStr)) return false;
        }

        return true;
    }

    public static int dayDifference(Date date1, Date date2) {
        int compare = date1.compareTo(date2);

        if (compare == 0) return 0;

        Calendar past = Calendar.getInstance();
        Calendar future = Calendar.getInstance();
        if (compare < 0) {
            past.setTime(date1);
            future.setTime(date2);
        } else {
            past.setTime(date2);
            future.setTime(date1);
        }

        return (int) Math.round((double) (future.getTimeInMillis() - past.getTimeInMillis()) / 8.64E7D);
    }

    public static int dayDifferenceIgnoreTime(Date date1, Date date2) {
        date1 = string2Date(date2String(date1, PATTERN_FULL_DATE_SLASH), PATTERN_FULL_DATE_SLASH);
        date2 = string2Date(date2String(date2, PATTERN_FULL_DATE_SLASH), PATTERN_FULL_DATE_SLASH);
        return dayDifference(date1, date2);
    }

    public static int getYear(Date date) {
        Calendar calendar = getCalendar(date);
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        Calendar calendar = getCalendar(date);
        return calendar.get(Calendar.MONTH);
    }

    public static int getDay(Date date) {
        Calendar calendar = getCalendar(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static Date plusMonths(Date date, int months) {
        Calendar calendar = getCalendar(date);
        calendar.add(Calendar.MONTH, months);

        return calendar.getTime();
    }

    public static Date plusDays(Date date, int days) {
        Calendar calendar = getCalendar(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);

        return calendar.getTime();
    }

    public static Date getSysDate() {
        return getDateIgnoreTime(new Date());
    }

    public static boolean between(Date date, Date startDate, Date endDate) {
        return date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0;
    }

    //compute age through birthDay
    public static int getAge(Date birthDay) {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            } else {
                age--;
            }
        }
        return age;
    }

    public static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 将null字符串转换为空串，方便HTML的显示
     *
     * @param sourceStr 待处理的字符串
     * @return String 处理的的字符串
     */
    public static String toVisualString(String sourceStr) {
        if (sourceStr == null || sourceStr.equals("")) {
            return "";
        } else {
            return sourceStr;
        }
    }

    /**
     * 将数据库存储的日期格式串转换为各种显示的格式
     *
     * @param dateStr    最小6位，最大14位的数据库存储格式时间串如:20041212
     * @param formatType 时间格式的类型{@link #DB_STORE_DATE}
     * @return 格式化的时间串
     */
    public static String toDisplayStr(String dateStr, int formatType) {
        if (formatType < DB_STORE_DATE || formatType > CN_DISPLAY_DATE) {
            throw new IllegalArgumentException("时间格式化类型不是合法的值。");
        }
        if (dateStr == null || dateStr.length() < 6 || dateStr.length() > 14 || formatType == DB_STORE_DATE) {
            return toVisualString(dateStr);
        } else {
            char[] charArr;
            switch (formatType) {
                case HYPHEN_DISPLAY_DATE:
                    charArr = new char[]{'-', '-', ' ', ':', ':'};
                    break;
                case DOT_DISPLAY_DATE:
                    charArr = new char[]{'.', '.', ' ', ':', ':'};
                    break;
                case CN_DISPLAY_DATE:
                    charArr = new char[]{'年', '月', ' ', ':', ':'};
                    break;
                default:
                    charArr = new char[]{'-', '-', ' ', ':', ':'};
            }
            try {
                SimpleDateFormat sdf1;
                SimpleDateFormat sdf2;
                switch (dateStr.length()) {
                    case 6:
                        sdf1 = new SimpleDateFormat("yyyyMM");
                        sdf2 = new SimpleDateFormat("yyyy'" + charArr[0] + "'MM");
                        break;
                    case 8:
                        sdf1 = new SimpleDateFormat("yyyyMMdd");
                        sdf2 = new SimpleDateFormat("yyyy'" + charArr[0] + "'MM'" + charArr[1] + "'dd");
                        break;
                    case 10:
                        sdf1 = new SimpleDateFormat("yyyyMMddHH");
                        sdf2 = new SimpleDateFormat("yyyy'" + charArr[0] + "'MM'" + charArr[1] + "'dd'"
                                + "+charArr[2]" + "'HH");
                        break;
                    case 12:
                        sdf1 = new SimpleDateFormat("yyyyMMddHHmm");
                        sdf2 = new SimpleDateFormat("yyyy'" + charArr[0] + "'MM'" + charArr[1] + "'dd'" + charArr[2]
                                + "'HH'" + charArr[3] + "'mm");
                        break;
                    case 14:
                        sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
                        sdf2 = new SimpleDateFormat("yyyy'" + charArr[0] + "'MM'" + charArr[1] + "'dd'" + charArr[2]
                                + "'HH'" + charArr[3] + "'mm'" + charArr[4] + "'ss");
                        break;
                    default:
                        return dateStr;
                }
                return sdf2.format(sdf1.parse(dateStr));
            } catch (ParseException ex) {
                return dateStr;
            }
        }
    }

    /**
     * 获取最近12个月，经常用于统计图表的X轴(倒序)
     */
    public static String[] getLast12Months(){
        String[] last12Months = new String[12];

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1); //要先+1,才能把本月的算进去
        // 加此行,否则3月重复
        cal.set(Calendar.DATE,1);

        for(int i=11; i>=0; i--){
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)-1); //逐次往前推1个月
            last12Months[11-i] = cal.get(Calendar.YEAR)+ Constants.SLASH +  String.format("%02d", (cal.get(Calendar.MONTH)+1));
        }
        return last12Months;
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.string2Date("2017.09.07", PATTERN_FULL_DATE_DOT));
    }
}
