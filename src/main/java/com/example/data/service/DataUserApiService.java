package com.example.data.service;

import com.example.data.mapper.DataUserApiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataUserApiService {

    @Autowired
    private DataUserApiMapper dataUserApiMapper;

    public String showPwd(String username) {
        return dataUserApiMapper.showPwd(username);
    }

    public int updatePwd(String username, String password) {
        return dataUserApiMapper.updatePwd(username,password);
    }

    public List selectName(String username) {
        return dataUserApiMapper.selectName(username);
    }

    public List gridshow() {
        return dataUserApiMapper.gridshow();
    }

    public String selectProName(String project_name) {
        return dataUserApiMapper.selectProName(project_name);
    }

    public int importProject(String project_name,String city_name,String type,  String project_time, String username,String lt,  String area) {
        return dataUserApiMapper.importProject(project_name,city_name,type,project_time,username,lt,area);
    }


    public List exportProject(String project_name) {
        return dataUserApiMapper.exportProject(project_name);
    }

    public int pointed(String project_name, String point_lon, String point_lat, String point_describe) {
        return dataUserApiMapper.pointed(project_name,point_lon,point_lat,point_describe);
    }


    public String showName(String username) {
        return dataUserApiMapper.showName(username);
    }

    public String selectEmail(String username) {
        return dataUserApiMapper.selectEmail(username);
    }

    public List projectInfo() {
        return dataUserApiMapper.projectInfo();
    }

    public List projectInfoPoint(String project_name) {
        return dataUserApiMapper.projectInfoPoint(project_name);
    }
}
