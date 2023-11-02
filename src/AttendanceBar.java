import java.util.Arrays;

public class AttendanceBar {

    //计算机工程学院 第__周   星期__考勤
    //
    // 时间：__年__月__日__时__分                          大学英语I    98%
    //
    //班级     教室    应到   实到   请假人数  缺勤人数       职务         班委签名    任课教师    抽查人
    //计科2311 N6-408  58   56    1          1      临时负责人                 王嘉豪                                赵奕哲
    //
    //缺勤人员名单：...

    String month;//月
    String day;//日
    String courseName;//正课名称 早自习 晚自习

    String className;//班级名称
    String other;//其他////负责人//课程节数//星期几
    String classroom;//教室
    String dueNumber;//应到
    String attendance;//实到

    String ask;//请假

    String absence;//缺勤

    String[] absencePeople;//缺勤名单

    public AttendanceBar(String month, String day,String className,String courseName, String other, String classroom) {

        this.month = month;
        this.day = day;
        this.className = className;
        this.courseName = courseName;
        this.other = other;
        this.classroom = classroom;

        this.dueNumber = null;
        this.attendance = null;
        this.ask = null;
        this.absence = null;
        this.absencePeople = null;
    }

    public AttendanceBar(String month, String day, String courseName, String className, String other, String classroom, String dueNumber, String attendance, String ask, String absence, String[] absencePeople) {
        this.month = month;
        this.day = day;
        this.courseName = courseName;
        this.className = className;
        this.other = other;
        this.classroom = classroom;
        this.dueNumber = dueNumber;
        this.attendance = attendance;
        this.ask = ask;
        this.absence = absence;
        this.absencePeople = absencePeople;
    }

    public AttendanceBar() {
    }

    @Override
    public String toString() {
        return "考勤条信息: {" +
                "月='" + month + '\'' +
                ", 日='" + day + '\'' +
                ", 课程名称='" + courseName + '\'' +
                ", 班级名称='" + className + '\'' +
                ", 教室名称='" + classroom + '\'' +
                ", 应到人数='" + dueNumber + '\'' +
                ", 实到人数='" + attendance + '\'' +
                ", 请假人数='" + ask + '\'' +
                ", 缺勤人数='" + absence + '\'' +
                ", 其他信息='" + other + '\'' +
                ", 缺勤名单=" + Arrays.toString(absencePeople) +
                '}';
    }
}
