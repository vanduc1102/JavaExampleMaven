package my.example.javaexamplemaven;

/**
 *
 * @author nvduc
 */
public class UnknowStringJava {
    public static void main(String args[]){
        String exportFolder= "";
        String fileName = "";
        String suffix = "({suffix})";
        System.out.println("  ==============================> : "+ (float) 2/3);
        int index = 0;
        String finalFileName = exportFolder + fileName + suffix.replace("{suffix}", index + "") + ".pdf";
        System.out.println("finalFileName : "+finalFileName);
    }
}
