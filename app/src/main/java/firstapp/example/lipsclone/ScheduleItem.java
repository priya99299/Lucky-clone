package firstapp.example.lipsclone;



public class ScheduleItem {
    private String subject;
    private String teacher;
    private String room;
    private String time;
    private int color;

    public ScheduleItem(String subject, String teacher, String room, String time, int color) {
        this.subject = subject;
        this.teacher = teacher;
        this.room = room;
        this.time = time;
        this.color = color;
    }

    public String getSubject() {
        return subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getRoom() {
        return room;
    }

    public String getTime() {
        return time;
    }

    public int getColor() {
        return color;
    }
}