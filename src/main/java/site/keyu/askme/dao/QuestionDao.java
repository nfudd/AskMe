package site.keyu.askme.dao;

import org.apache.ibatis.annotations.*;
import site.keyu.askme.pojo.Question;

import java.util.List;

/**
 * @Author:Keyu
 */
@Mapper
public interface QuestionDao {
    String TABLE_NAME = " question ";
    String INSERT_FIELDS = " title, content, created_date, user_id, comment_count, status ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;


    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{title},#{content},#{createdDate},#{userId},#{commentCount},#{status})"})
    int insertQuestion(Question question);

    @Select({"select",SELECT_FIELDS, " from ", TABLE_NAME, " where user_id=#{userId} order by id desc limit #{offset},#{limit}"})
    @Results({
            @Result(property = "userId",column = "user_id"),
            @Result(property = "createdDate",column = "created_date")
    })
    List<Question> selectLatestQuestions(@Param("userId") int userId,
                                         @Param("offset") int offset,
                                         @Param("limit") int limit);


    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    @Results({
            @Result(property = "userId",column = "user_id"),
            @Result(property = "createdDate",column = "created_date")
    })
    Question getById(int id);


    @Update({"update ", TABLE_NAME, " set comment_count = #{commentCount} where id=#{id}"})
    int updateCommentCount(int id,int commentCount);

}