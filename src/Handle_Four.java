import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Handle_Four {

    static String Early = "早自习";
    static String Day = "正课";
    static String Night = "晚自习";

    public static String run() {

        getDayAttendance();
        getClass(Early);
        getClass(Day);
        getClass(Night);

       return getAbsencePeople();
    }
    //时间	班级	教室	应到	实到	任课老师	任课课程	出勤率	排名	请假	旷课
    //2023.10.9 周一5、6节	计算机类2313	N4-108	62	61	王晓艳	思想道德与法治	100%	1	1	0


    public static void getDayAttendance() {
        File file = new File("Data\\OutFourCsv\\" + "日常考勤通报" + ".csv");
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file));
            bw.write("时间,班级,教室,应到,实到,任课老师,任课课程,出勤率,排名,请假,旷课,\n");
            bw.write("时间,班级,教室,应到,实到,任课老师,任课课程,出勤率,排名,请假,旷课,\n");
            ArrayList<AttendanceBar> DayAttendance = getClass(Day);

            for (AttendanceBar attendanceBar : DayAttendance) {

                StringBuilder stringBuilder = new StringBuilder();
                //05 06+程哲|曾庆婷1
                String[] split = attendanceBar.other.split("\\+")[0].split(" ");

                String time = getWeek(Calendar.getInstance().get(Calendar.YEAR), Integer.parseInt(attendanceBar.month), Integer.parseInt(attendanceBar.day))+split[0]+"、"+split[1]+"节";
                String className = attendanceBar.className;
                String classroom = attendanceBar.classroom;

                String dueNumber = attendanceBar.dueNumber;
                String attendance = attendanceBar.attendance;

                String teacher=attendanceBar.other.split("\\|")[1];
                String courseName = attendanceBar.courseName;

                String num=String.format("%.2f",Integer.parseInt(attendance)*100.0/Integer.parseInt(dueNumber))+"%";
                String count="0";
                String ask = attendanceBar.ask;
                String absence = attendanceBar.absence;
                stringBuilder.append(time+","+className + ","+ classroom + "," + dueNumber + "," + attendance + ","  + teacher + ","  + courseName + ","  + num + "," + count + ","  + ask +  "," +absence+"\n");
                bw.write(String.valueOf(stringBuilder));

            }
            bw.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getAbsencePeople() {
        StringBuilder stringBuilder = new StringBuilder();

        ArrayList<AttendanceBar> attendanceBars = getAttendanceBars();


        TreeMap<String, ArrayList<String>> treeMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o1.equals(o2)) {
                    return 0;
                } else {
                    String a = o1.substring(0, o1.length() - 5);
                    String b = o2.substring(0, o2.length() - 5);
                    String substring1 = o1.substring(o1.length() - 5, o1.length() - 1);
                    String substring2 = o2.substring(o2.length() - 5, o2.length() - 1);
                    return o1.compareTo(o2) + (Integer.parseInt(substring1) - Integer.parseInt(substring2)) * 10000;
                }
            }
        });

        for (AttendanceBar attendanceBar : attendanceBars) {
            treeMap.put(attendanceBar.className, new ArrayList<>());
        }

        Set<String> keySet = treeMap.keySet();

        for (String s : keySet) {
            ArrayList<String> arrayList = new ArrayList<>();
            for (AttendanceBar attendanceBar : attendanceBars) {
                if (attendanceBar.className.equals(s)) {
                    if (!Objects.equals(attendanceBar.absencePeople[0], "无")) {
                        arrayList.addAll(List.of(attendanceBar.absencePeople));
                    }
                }
            }
            treeMap.put(s, arrayList);
        }

        String firstKey = treeMap.firstKey();
        String substring = firstKey.substring(firstKey.length() - 5, firstKey.length() - 1);
        int grade = Integer.parseInt(substring);


        for (int x = 0; x < 3; x++) {

            stringBuilder.append(String.valueOf(grade / 100) + "级：\n");
            for (String s : keySet) {

                String string = s.substring(s.length() - 5, s.length() - 3);
                int parseInt = Integer.parseInt(string);

                if (parseInt == grade / 100) {
                    stringBuilder.append(s + "：");
                    ArrayList<String> arrayList = treeMap.get(s);
                    if (arrayList.size() == 0) {
                        stringBuilder.append("\n");
                    }
                    arrayList.sort(new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            return o1.compareTo(o2);
                        }
                    });

                    for (int g = 0; g < arrayList.size(); g++) {
                        arrayList.set(g, arrayList.get(g) + "|1");
                    }
                    HashMap<String, Integer> counterMap = new HashMap<>();
                    // 统计计数器
                    for (String item : arrayList) {
                        String name = item.split("\\|")[0];
                        int count = Integer.parseInt(item.split("\\|")[1]);
                        counterMap.put(name, counterMap.getOrDefault(name, 0) + count);
                    }
                    // 移除重复项
                    ArrayList<String> result = new ArrayList<>();
                    for (String name : counterMap.keySet()) {
                        int count = counterMap.get(name);
                        if (count > 1) {

                            result.add(name + "（" + count + "）");
                        } else if (count == 1) {
                            result.add(name);
                        }
                    }

                    for (int i = 0; i < result.size(); i++) {
                        if (i == result.size() - 1) {
                            stringBuilder.append(result.get(i) + "\n");
                        } else {
                            stringBuilder.append(result.get(i) + "、");
                        }
                    }
                }
            }
            grade += 100;
        }
        return stringBuilder.toString();
    }


    public static ArrayList<AttendanceBar> getClass(String DayTime) {

        ArrayList<AttendanceBar> DayTimeBar = new ArrayList<>();
        try {

            File file = new File("Data\\OutFourCsv\\" + DayTime + ".csv");

            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            if (DayTime.equals(Day)) {
                bw.write("班级,课程,教室,应到,请假,实到,时间,缺勤人员\n");
                bw.write("班级,课程,教室,应到,请假,实到,时间,缺勤人员,\n");
            } else {
                bw.write("班级,教室,应到,实到,请假,时间,缺勤人员\n");
                bw.write("班级,教室,应到,实到,请假,时间,缺勤人员,\n");
            }

            ArrayList<AttendanceBar> attendanceBars = getAttendanceBars();
            for (AttendanceBar attendanceBar : attendanceBars) {

                StringBuilder stringBuilder = new StringBuilder();
                String className = attendanceBar.className;
                String courseName = attendanceBar.courseName;
                String classroom = attendanceBar.classroom;
                String dueNumber = attendanceBar.dueNumber;
                String ask = attendanceBar.ask;
                String attendance = attendanceBar.attendance;
                String time = attendanceBar.month + "月" + attendanceBar.day + "日";
                StringBuilder peoples = new StringBuilder();
                for (int i = 0; i < attendanceBar.absencePeople.length; i++) {
                    if (i == 0) {
                        peoples.append(attendanceBar.absencePeople[i]);
                    } else {
                        peoples.append("、" + attendanceBar.absencePeople[i]);
                    }
                }

                if (DayTime.equals(Day)) {
                    if (!attendanceBar.other.split("\\+")[0].equals(Early) && !attendanceBar.other.split("\\+")[0].equals(Night)) {
                        stringBuilder.append(className + "," + courseName + "," + classroom + "," + dueNumber + "," + ask + "," + attendance + "," + time + "," + peoples + "\n");
                        bw.write(String.valueOf(stringBuilder));

                        DayTimeBar.add(attendanceBar);
                    }
                } else {
                    if (attendanceBar.other.split("\\+")[0].equals(DayTime)) {

                        stringBuilder.append(className + "," + classroom + "," + dueNumber + "," + attendance + "," + ask + "," + time + "," + peoples + "\n");
                        bw.write(String.valueOf(stringBuilder));

                        DayTimeBar.add(attendanceBar);
                    }
                }
            }

            bw.close();
            return DayTimeBar;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    //获取考勤条
    public static ArrayList<AttendanceBar> getAttendanceBars() {
        File file = new File("Data\\data.txt");

        ArrayList<AttendanceBar> attendanceBars = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            String s = null;
            bufferedReader.readLine();

            while ((s = bufferedReader.readLine()) != null) {
                if (!s.equals("\t\t\t\t\t\t\t\t\t\t\t")) {
                    String[] split = s.split("\t");

                    AttendanceBar attendanceBar = new AttendanceBar();
                    attendanceBar.month = split[0].split("月")[0];
                    attendanceBar.day = split[0].split("月")[1].split("日")[0];
                    attendanceBar.className = split[1];
                    attendanceBar.courseName = split[2];
                    attendanceBar.other = split[3] + "+" + split[5] + "|" + split[6];
                    attendanceBar.classroom = split[4];
                    attendanceBar.dueNumber = split[7];
                    attendanceBar.attendance = split[8];
                    attendanceBar.ask = split[9];
                    attendanceBar.absence = split[10];
                    if (!attendanceBar.absence.equals("0")) {
                        attendanceBar.absencePeople = split[11].split(" ");
                    } else {
                        attendanceBar.absencePeople = new String[]{"无"};
                    }
                    attendanceBars.add(attendanceBar);
                }
            }


//            for (AttendanceBar attendanceBar : attendanceBars) {
//                if (Integer.valueOf(attendanceBar.dueNumber) != Integer.valueOf(attendanceBar.attendance) + Integer.valueOf(attendanceBar.ask) + Integer.valueOf(attendanceBar.absence)) {
//                    System.out.print("数据不对----");
//                }
//                System.out.println(attendanceBar);
//            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return attendanceBars;
    }


    public static String getWeek(int year, int month, int day) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = dateFormat.parse(year + "-" + month + "-" + day);
            calendar.setTime(date);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            String[] weekdays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
            return year + "." + month + "." + day + " " + weekdays[dayOfWeek - 1];

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
