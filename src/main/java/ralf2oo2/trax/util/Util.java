package ralf2oo2.trax.util;

public class Util {
    public static String removeExtension(String fileName){
        int dotIndex = fileName.lastIndexOf('.');
        System.out.println(fileName + " " + dotIndex);
        return (dotIndex <= 0) ? fileName : fileName.substring(0, dotIndex);
    }
}
