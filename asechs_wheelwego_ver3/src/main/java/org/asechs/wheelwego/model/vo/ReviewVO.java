package org.asechs.wheelwego.model.vo;

public class ReviewVO {
   private String reviewNo;
   private String foodtruckNumber;
   private String foodtruckName;
   private String customerId;
   private String reviewContent;
   private String reviewTimeposted;
   private int grade;
   public ReviewVO() {
      super();
      // TODO Auto-generated constructor stub
   }

   public ReviewVO(String reviewNo, String foodtruckNumber, String foodtruckName, String customerId,
         String reviewContent, String reviewTimeposted, int grade) {
      super();
      this.reviewNo = reviewNo;
      this.foodtruckNumber = foodtruckNumber;
      this.foodtruckName = foodtruckName;
      this.customerId = customerId;
      this.reviewContent = reviewContent;
      this.reviewTimeposted = reviewTimeposted;
      this.grade = grade;
   }

   public String getReviewNo() {
      return reviewNo;
   }
   public void setReviewNo(String reviewNo) {
      this.reviewNo = reviewNo;
   }
   public String getFoodtruckNumber() {
      return foodtruckNumber;
   }
   public void setFoodtruckNumber(String foodtruckNumber) {
      this.foodtruckNumber = foodtruckNumber;
   }
   public String getCustomerId() {
      return customerId;
   }
   public void setCustomerId(String customerId) {
      this.customerId = customerId;
   }
   public String getReviewContent() {
      return reviewContent;
   }
   public void setReviewContent(String reviewContent) {
      this.reviewContent = reviewContent;
   }
   public String getReviewTimeposted() {
      return reviewTimeposted;
   }
   public void setReviewTimeposted(String reviewTimeposted) {
      this.reviewTimeposted = reviewTimeposted;
   }
   public int getGrade() {
      return grade;
   }
   public void setGrade(int grade) {
      this.grade = grade;
   }
   
   public String getFoodtruckName() {
      return foodtruckName;
   }

   public void setFoodtruckName(String foodtruckName) {
      this.foodtruckName = foodtruckName;
   }

   @Override
   public String toString() {
      return "ReviewVO [reviewNo=" + reviewNo + ", foodtruckNumber=" + foodtruckNumber + ", foodtruckName="
            + foodtruckName + ", customerId=" + customerId + ", reviewContent=" + reviewContent
            + ", reviewTimeposted=" + reviewTimeposted + ", grade=" + grade + "]";
   }


   
}