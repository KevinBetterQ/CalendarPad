package com.qwk.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

public class Dao extends BaseDao {

    private static final Dao dao;
    

    static {
        dao = new Dao();
    }

    public static Dao getInstance() {
        return dao;
    }

    public Vector<Vector> sNoteByType(String type) {
        return this.selectSomeNote("select * from tb_note where type='" + type + "'");
    }

public Vector<Vector> sNoteByKeywords(String type, String keywords) {
    keywords = "%" + keywords.replace(' ', '%') + "%";
    return this.selectSomeNote("select * from tb_note where type='" + type + "' and (date like '" + keywords + "' or title like '" + keywords + "' or content like '" + keywords + "')");
}

    public Vector<Vector> sAwake() {
        return this.selectSomeNote("select * from tb_note where awake=1");
    }

public Vector<Vector> sAwakeByKeywords(String keywords) {
    keywords = "%" + keywords.replace(' ', '%') + "%";
    return this.selectSomeNote("select * from tb_note where awake=1 and (title like '" + keywords + "' or content like '" + keywords + "')");
}

    public Vector<Vector> sAwakeOfLatest() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String[] datetime = simpleDateFormat.format(Calendar.getInstance().getTime()).split(" ");
        Object minTime = dao.selectOnlyValue("select min(time) from tb_note where awake=1 and date>='" + datetime[0] + "' and time>='" + datetime[1] + "'");
        if (minTime == null) {
            return null;
        } else {
            return dao.selectSomeNote("select *  from tb_note where time='" + minTime + "'");
        }
    }

    public void iNote(String[] values) {
        int id = 1;
        Object selectOnlyValue = this.selectOnlyValue("select max(id) from tb_note");
        if (selectOnlyValue != null) {
            id = Integer.valueOf(selectOnlyValue.toString()) + 1;
        }
        this.longHaul("insert into tb_note values(" + id + ",'" + values[0] + "','" + values[1] + "','" + values[2] + "','" + values[3] + "'," + values[4] + ",'" + values[5] + "')");
    }

    public void uNote(String[] values) {
        this.longHaul("update tb_note set date='" + values[1] + "',time='" + values[2] + "', title='" + values[3] + "', awake=" + values[4] + ", content='" + values[5] + "' where id=" + values[0]);
    }

    public void dNote(String id) {
        this.longHaul("delete from tb_note where id=" + id);
    }

    // main
    public static void main(String[] args) {
        Dao dao = Dao.getInstance();

        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        int second = now.get(Calendar.SECOND);

        int s = second + 10;
        if (s > 59) {
            minute += 1;
            second = s - 59;
        } else {
            second = s;
        }

        StringBuffer time = new StringBuffer();
        time.append(hour < 10 ? "0" + hour : hour);
        time.append(":");
        time.append(minute < 10 ? "0" + minute : minute);
        time.append(":");
        time.append(second < 10 ? "0" + second : second);
        dao.longHaul("update tb_note set time='" + time.toString() + "' where id in(6,7)");



//        Vector<Vector> selectSomeNote = dao.selectSomeNote("select * from tb_note");
//        System.out.println("====================");
//        for (Vector note : selectSomeNote) {
//            for (int i = 0; i < note.size(); i++) {
//                System.out.println(note.get(i));
//            }
//            System.out.println("----------------");
//        }
//        System.out.println("====================");
    }
}
