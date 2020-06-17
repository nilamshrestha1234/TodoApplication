package android.com.todoapp.todolist.mvvm_model;


import androidx.room.TypeConverter;

import java.util.Date;

public class DateConversion {
    @TypeConverter
    public static Date toDate(Long timeStamp){
        return timeStamp == null ? null : new Date(timeStamp);
    }

    @TypeConverter
    public static Long toTimeStamp(Date date){return  date == null ? null : date.getTime();}
}
