package org.asechs.wheelwego.model.vo;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class FileManager {
   private MultipartFile imgFile;
   //private String newFilePath="C:\\Users\\User\\AppData\\Roaming\\SPB_16.6\\git\\wheelwego\\asechs_wheelwego\\src\\main\\webapp\\resources\\upload\\";
   private String newFilePath="C:\\Users\\KOSTA\\git\\wheelwego\\asechs_wheelwego\\src\\main\\webapp\\resources\\upload\\";
   private String newFileFullName;
   private  String newFileName;

   
   public MultipartFile getImgFile() {
      return imgFile;
   }

   public void setImgFile(MultipartFile imgFile) {
      this.imgFile = imgFile;
   }

   public String getNewFilePath() {
      return newFilePath;
   }

   public void setNewFilePath(String newFilePath) {
      this.newFilePath = newFilePath;
   }

   public String getNewFileFullName() {
      return newFileFullName;
   }

   public void setNewFileFullName(String newFileFullName) {
      this.newFileFullName = newFileFullName;
   }

   public String getNewFileName() {
      return newFileName;
   }

   public void setNewFileName(String newFileName) {
      this.newFileName = newFileName;
   }

   public void uploadFile(MultipartFile imgFile,String renamedFile) throws IOException {
      imgFile.transferTo(new File(/*newFilePath+*/renamedFile)); //서버에 전송하여 저장      
      System.out.println("업로드 완료");
   }
   
   // file upload method needed inner class
   // 파일의 저장 경로 및 파일이름 새로 생성
   
   public String rename(MultipartFile file,String key) { //파일이 들어오면 
         String fileName = file.getOriginalFilename();
           String fileExt = "";
           int i = -1;
           if(( i = fileName.indexOf(".")) != -1){
               fileExt = fileName.substring(i);
               fileName = fileName.substring(0,i);
           }
          newFileName =/*( new Date( ).getTime( ) / 1000)*/key +/*"_"+ fileName+*/fileExt;
          System.out.println("rename : "+newFileName);
         return newFileName; //rename된 파일을 반환
   }
}



