package com.vidots.utils;

public class StringUtils {
    /***
     *
     * @param nth 查找第nth个出现的索引
     * @param parent
     * @param sub
     * @return
     */
    public static int indexOfNth(int nth, String parent, String sub) {
        if (nth < 1) {
            return -1;
        }
        int times = 0;
        int result = -1;
        while (times < nth) {
            times++;
            if ((result = parent.indexOf(sub, result + 1)) == -1) {
                return -1;
            }
        }
        return result;
    }
}
