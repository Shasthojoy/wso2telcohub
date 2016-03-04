package lk.dialog.utils;

public class NumberGenerator {
    
    private static int currentNo = 0;
    
    /**
     * 
     */
    public NumberGenerator() {
    }
    
    /**
     * 
     * @return
     */
    public static int next() {
        if(currentNo==Integer.MAX_VALUE) {
            currentNo = 0;
        }
        return currentNo++;
    }
    
    public static int current() {
        return currentNo;
    }
}
