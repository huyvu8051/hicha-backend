package io.huyvu.hicha.hichabusiness.repository;

import io.huyvu.hicha.hichabusiness.model.UserDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserRepository {
    @Select("""
            SELECT *
            FROM hicha.users
            WHERE user_id = #{id}""")
    UserDTO findById(long id);

    @Insert("""
            INSERT INTO hicha.users(user_id, username)
            VALUES (#{userId}, #{username})
            """)
    void save(UserDTO user);

    @Select("""
            SELECT *
            FROM hicha.users""")
    List<UserDTO> findAll();
}
