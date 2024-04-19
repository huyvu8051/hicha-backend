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
            FROM user 
            WHERE id = ${id}""")
    UserDTO findById(long id);

    @Insert("""
            INSERT INTO user(id, name)
            VALUES (#{id}, #{name})
            """)
    void save(UserDTO user);

    @Select("""
            SELECT *
            FROM user""")
    List<UserDTO> findAll();
}
