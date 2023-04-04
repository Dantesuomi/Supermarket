package DataBase;

public class StringHelpers {
    //just to shorten if statement
    public static boolean isNullOrEmpty(String string){
        if(string == null || string.trim().isEmpty()){
            return true;
        }
        return false;
    }
}