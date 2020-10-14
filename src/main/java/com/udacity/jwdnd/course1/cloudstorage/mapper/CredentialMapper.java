package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CredentialMapper {

  @Insert("INSERT INTO CREDENTIALS (url, username, password, credentialKey) VALUES(#{url}, #{username}, #{password}, #{credentialKey})")
  @Options(useGeneratedKeys = true, keyProperty = "credentialid")
  int addCredential(Credential credential);

  @Select("SELECT * FROM CREDENTIALS")
  List<Credential> getAllCredentials();

  @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid}")
  Credential getCredentialById(Integer credentialid);

  @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, password = #{password}, credentialKey = #{credentialKey} WHERE credentialid = #{credentialid}")
  int updateCredential(Credential credential);

  @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
  boolean deleteCredentialById(int id);

}
