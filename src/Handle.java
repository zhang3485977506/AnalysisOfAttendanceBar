import org.w3c.dom.ls.LSOutput;

import javax.xml.crypto.Data;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class Handle {
    static Calendar time = Calendar.getInstance();

    public static void run() {

        ArrayList<AttendanceBar> attendanceBars = GetBar();
        HashSet<String> peoples = new HashSet<>();

        for (AttendanceBar bar : attendanceBars) {
            String[] other = bar.other.replace(",", " ").split("\\+");
            String People = other[1];
            peoples.add(People);
        }

        System.out.println(peoples);

        BufferedWriter allbufferedWriter = null;
        try {
            File file = new File("Data\\OutAttendance\\" + "0_总考勤条" + ".csv");
            boolean delete = file.delete();
            System.out.println(delete);
            File newfile = new File("Data\\OutAttendance\\" + "0_总考勤条" + ".csv");

            allbufferedWriter = new BufferedWriter(new FileWriter(newfile, true));
            allbufferedWriter.write("时间,班级,课程名称,节次,教室,抽查人,教师,应到,实到,请假人数,缺勤人数,缺勤名单\n");
            allbufferedWriter.write("时间,班级,课程名称,节次,教室,抽查人,教师,应到,实到,请假人数,缺勤人数,缺勤名单\n");


            int o=1;
            for (String people : peoples) {

                StringBuilder stringBuilder = new StringBuilder();

                for (AttendanceBar bar : attendanceBars) {
                    String[] other = bar.other.replace(",", " ").split("\\+");
                    String People = other[1];
                    if (People.equals(people)) {
                        String className = bar.className;
                        String courseName = bar.courseName;
                        String NumberOf_Course = other[0];
                        String classroom = bar.classroom;

                        if(NumberOf_Course.equals("早自习")||NumberOf_Course.equals("晚自习"))
                        {
                            stringBuilder.append(bar.month + "月" + bar.day + "日,").append(className + ",").append(courseName + ",").append(NumberOf_Course + ",").append(classroom + ",").append(People + ",").append("无教师,,,,,,\n");
                        }else{
                            stringBuilder.append(bar.month + "月" + bar.day + "日,").append(className + ",").append(courseName + ",").append(NumberOf_Course + ",").append(classroom + ",").append(People + ",").append(",,,,,,\n");
                        }

                    }
                }

                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("Data\\OutAttendance\\" +o+"_"+ people + "_考勤条" + ".csv")));
                o++;
                bufferedWriter.write("时间,班级,课程名称,节次,教室,抽查人,教师,应到,实到,请假人数,缺勤人数,缺勤名单\n");
                bufferedWriter.write("时间,班级,课程名称,节次,教室,抽查人,教师,应到,实到,请假人数,缺勤人数,缺勤名单\n");
                bufferedWriter.write(String.valueOf(stringBuilder));
                bufferedWriter.close();

                allbufferedWriter.write(",,,,,,,,,,,,\n");
                allbufferedWriter.write(String.valueOf(stringBuilder));


            }

            allbufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public static ArrayList<AttendanceBar> GetBar() {

        ArrayList<AttendanceBar> bars = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(new File("Data\\AttendanceAssignment_Day.txt"));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s = null;
            String first_time = bufferedReader.readLine();

            int year_first = Integer.parseInt(first_time.split("年")[0]);
            int month_first = Integer.parseInt(first_time.split("年")[1].split("月")[0]);
            int day_first = Integer.parseInt(first_time.split("年")[1].split("月")[1].split("日")[0]);
            //月份 是从0开始
            time.set(year_first, month_first - 1, day_first);

            int year0 = time.get(Calendar.YEAR);
            int month0 = time.get(Calendar.MONTH) + 1;
            int date0 = time.get(Calendar.DATE);
            System.out.println("初始时间（周一）：" + year0 + "年" + month0 + "月" + date0 + "日");
            System.out.println();
            bufferedReader.readLine();

            while ((s = bufferedReader.readLine()) != null) {

                if (!(s.equals("周二\t\t\t\t") || s.equals("周三\t\t\t\t") || s.equals("周四\t\t\t\t") || s.equals("周五\t\t\t\t"))) {
                    System.out.println("111");
                    if (!s.equals("周一\t\t\t\t")) {
                        int now_month = time.get(Calendar.MONTH) + 1;
                        int now_date = time.get(Calendar.DATE);
                        String[] information = s.split("\t");
                        AttendanceBar attendanceBar = new AttendanceBar(String.valueOf(now_month), String.valueOf(now_date), information[0], information[1], information[2] + "+" + information[4], information[3]);
                        bars.add(attendanceBar);

                    }

                } else {
                    time.add(Calendar.DATE, 1);//时间加一
                }
            }

            FileReader fileReaderNight = new FileReader(new File("Data\\AttendanceAssignment_Night.txt"));
            BufferedReader br = new BufferedReader(fileReaderNight);
            String s1 = null;
            br.readLine();
            br.readLine();


            String[] night = new String[4];
            int count = 1;
            while ((s1 = br.readLine()) != null) {

                String[] split = s1.split("\t");
                String p1 = split[1];
                String p2 = split[2];
                String p3 = split[3];
                String p4 = split[4];

                if (split[0].equals("星期一、星期四")) {
                    night[0] = p1 + "," + p2 + "," + p3 + "," + p4;
                    night[3] = p1 + "," + p2 + "," + p3 + "," + p4;
                } else {
                    night[count] = p1 + "," + p2 + "," + p3 + "," + p4;
                    count++;
                }

            }

            //时间归位
            time.add(Calendar.DATE, -4);

            //读取配置
            Properties properties = new Properties();
            properties.load(new InputStreamReader(new FileInputStream(new File("Data\\Night_Disposition.properties"))));

            String firstKey = (String) properties.keys().nextElement();//获取第一个键
            Integer first = Integer.valueOf(firstKey) - 3;

            for (int i = 0; i < night.length; i++) {

                String peopleName = night[i];
                String[] split = peopleName.split(",");

                for (int j = 0; j < split.length; j++) {

                    AttendanceBar attendanceBar = new AttendanceBar();
                    attendanceBar.month = String.valueOf(time.get(Calendar.MONTH) + 1);
                    attendanceBar.day = String.valueOf(time.get(Calendar.DATE));
                    attendanceBar.courseName = "晚自习" + (j + 1) + "班";
                    attendanceBar.className = "计算机类" + (first + j) + "班";
                    attendanceBar.classroom = properties.getProperty(String.valueOf(first + j));
                    attendanceBar.other = "晚自习+" + split[j];
                    bars.add(attendanceBar);
                }
                time.add(Calendar.DATE, 1);//时间加一
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (AttendanceBar bar : bars) {
            System.out.println(bar);
        }
        return bars;
    }

}
