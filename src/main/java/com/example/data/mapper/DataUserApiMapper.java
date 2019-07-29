package com.example.data.mapper;

import java.util.List;

public interface DataUserApiMapper {


    String showPwd(String username);

    int updatePwd(String username, String password);

    List selectName(String username);

    List gridshow();

    String selectProName(String project_name);

    int importProject(String project_name,String city_name, String type, String project_time, String username, String lt,String area);

    List exportProject(String project_name);

    int pointed(String project_name, String point_lon, String point_lat, String point_describe);


    String selectEmail(Object username);

    String showName(String username);

    List projectInfo();

    List projectInfoPoint(String project_name);
}
