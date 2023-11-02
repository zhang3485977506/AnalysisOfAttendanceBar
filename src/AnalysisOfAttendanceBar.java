import javax.swing.*;
import java.io.*;


public class AnalysisOfAttendanceBar {
    public static void main(String[] args) {
        Run();
    }
    public static void Run() {



        //初始化配置
        String s = "2311=N8-A304\n" +
                "2312=N8-A305\n" +
                "2313=N8-A404\n" +
                "2314=N8-A405\n";



        File path = new File("Data");
        path.mkdir();
        File path1 = new File("Data\\OutAttendance");
        path1.mkdir();
        File path2 = new File("Data\\OutFourCsv");
        path2.mkdir();
        File file = new File("Data\\Night_Disposition.properties");

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write(s);
            fileWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        new ComponentMenu().init();


    }

    public static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFolder(file);
                } else {
                    file.delete();
                }
            }
        }
        folder.delete();
    }
}