/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SqlConncetion;

import Exception.DBException;
import static SqlConncetion.SqlCon.dbConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Danielle
 */
public class LessonGet extends SqlCon {
    private String videoID;
    private String pdfPlayer;
    private String lessonPhotos;
    public static LessonGet getLessonData(int id) throws SQLException{
        StringBuilder query = new StringBuilder();
        LessonGet lesson = null;
        query.append("SELECT videoId,pdfPlayer,lessonPhotos ");
        query.append("FROM lesson ");
        query.append(String.format("WHERE idLesson = '%d';", id));
        try (Statement statement = dbConnection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query.toString());

            if(resultSet.next()){      
                //If the credentials are correct, we'll return the id of the current user
                    lesson = new LessonGet();
                    lesson.videoID = resultSet.getString("videoId");
                    lesson.pdfPlayer = resultSet.getString("pdfPlayer");
                    lesson.lessonPhotos = resultSet.getString("lessonPhotos");
                 
               
            }


            statement.close();
        }
        return lesson;
        
    }
    private LessonGet(){};
    
    
}
