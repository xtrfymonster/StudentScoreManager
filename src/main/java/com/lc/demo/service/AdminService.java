package com.lc.demo.service;

import com.lc.demo.bean.Admin;

/**
 * @ClassName AdminService
 * @Deacription TODO
 **/
public interface AdminService  {

    Admin adminLogin(String AdminId,String AdminPass);
}
