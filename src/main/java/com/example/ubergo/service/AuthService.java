package com.example.ubergo.service;

import com.example.ubergo.DTO.GeneralMessageDTO;
import com.example.ubergo.entity.User;
import com.example.ubergo.mapper.MyMapper;
import com.example.ubergo.mapper.UserMapper;
import com.example.ubergo.utils.RandomUsernameGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {
    private final UserMapper userMapper;

    @Autowired
    public AuthService(UserMapper userMapper) {

        this.userMapper = userMapper;
    }

    public GeneralMessageDTO createAUser(User user){
        if(user.getUserName()==null || user.getUserName().length()==0){
            user.setUserName(RandomUsernameGenerator.generate());
        }
        if (user.getIdentity()==null || user.getIdentity().length()==0){
            return  new GeneralMessageDTO(101,"role required");
        }
        else if(user.getPassword()==null || user.getPassword().length()==0 )
        {
            return new GeneralMessageDTO(102,"password required");
        }
        else if(  user.getMobileNumber()==null || user.getMobileNumber().length()==0)
        {
            return new GeneralMessageDTO(103,"mobile number required");
        }
        else{
            try{
                userMapper.createAUser(user);
                return  new GeneralMessageDTO(0,"Success");
            }catch (Exception e){
                return  new GeneralMessageDTO(599,e.getMessage());
            }
        }
        
    }
    public GeneralMessageDTO loginParams(String mobileNumber,String token){
        User user=userMapper.getUserByMobileNumber(mobileNumber);
        if(user==null){
            return new GeneralMessageDTO(111,"User does not exist ");
        }
        if(user.getPassword().equals(token)){

            return new GeneralMessageDTO(0,"Success",(Object) user);
        }
        else{
            return new GeneralMessageDTO(112,"password error");
        }

    }

    public GeneralMessageDTO uidSend(int uid,User user){
        try{

            user.setUid(uid);
            userMapper.updateUser(user);
            return new GeneralMessageDTO(0,"Success",userMapper.getById(uid));

        }
        catch (Exception e){
            e.printStackTrace();
            return new GeneralMessageDTO(129,"Error:"+e.getMessage());
        }

    }


}
