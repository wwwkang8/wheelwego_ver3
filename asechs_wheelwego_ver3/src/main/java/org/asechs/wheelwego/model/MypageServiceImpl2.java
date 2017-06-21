package org.asechs.wheelwego.model;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class MypageServiceImpl2 /*implements MypageService*/ {
   @Resource
   private MypageDAO mypageDAO;
   @Resource
   private FoodTruckDAO foodtruckDAO;
  
}