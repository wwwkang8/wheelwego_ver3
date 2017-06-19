-------------------------------------------------------------------------------

drop sequence survey_seq;
drop sequence customerpoint_seq;
drop sequence booking_seq;
drop sequence review_seq;
drop sequence menu_seq;

drop sequence information_seq;
drop sequence informationcomment_seq;
drop sequence qna_seq;
drop sequence qnacomment_seq;
drop sequence freeboard_seq;
drop sequence freeboardcomment_seq;

create sequence survey_seq;
create sequence customerpoint_seq;
create sequence booking_seq start with 150;
create sequence review_seq start with 201;
create sequence menu_seq start with 123;

create sequence information_seq start with 257;
create sequence informationcomment_seq;
create sequence qna_seq start with 257;
create sequence qnacomment_seq;
create sequence freeboard_seq start with 257;
create sequence freeboardcomment_seq;
-------------------------------------------------------------------------------
select * from member;
-------------------------------------------------------------------------------
create table member(
   id varchar2(100) primary key,
   password varchar2(100) not null,
   member_name varchar2(100) not null,
   address varchar2(100) not null,
   addressdetail varchar2(100) not null,
   phonenumber varchar2(100) not null,
   member_type varchar2(100) not null,
   postcode varchar2(100) not null
);    
-------------------------------------------------------------------------------
drop table member;
-------------------------------------------------------------------------------
create table customer(
   customer_id varchar2(100) primary key,
   constraint customer_id_fk foreign key(customer_id) references member(id) on delete cascade
);
-- ���� ���������� �̸��� ���Ҷ� [���̺��]_[Į����]_[������������] �̷��� �־����� �� ����.kos
-- ���� customer ���� ��쿡�� 
-- ���̺� �� : customer / �÷��� : customer_id / ������������ : fk
-- customer_customer_id_fk�� ������ ��� ȥ���� ���� ������ �� ���
-- ���̺� �� : customer / �÷Ÿ� : id / ������������ : fk
-- customer_id_fk�� �Ѵ�.
-------------------------------------------------------------------------------
drop table customer;
-------------------------------------------------------------------------------
create table seller(
   seller_id varchar2(100) primary key,
   businessnumber varchar2(100) not null,
   constraint seller_id_fk foreign key(seller_id) references member(id) on delete cascade
);
-------------------------------------------------------------------------------
drop table seller;
-------------------------------------------------------------------------------file삭제(0531)
create table foodtruck(
    foodtruck_number varchar2(100) primary key,
    seller_id varchar2(100) not null,
    foodtruck_name varchar2(100) not null,
    introduction clob not null,
    register_timeposted date not null,
    latitude BINARY_DOUBLE,
    longitude BINARY_DOUBLE,
    constraint foodtruck_id_fk foreign key(seller_id) references seller(seller_id) on delete cascade
);
-------------------------------------------------------------------------------
drop table foodtruck;
-------------------------------------------------------------------------------file table 추가(0531)
create table foodtruckfile(
   foodtruck_number varchar2(100) not null,
   foodtruck_filepath varchar2(100) not null,
   constraint foodtruckfile_trucknumber_fk foreign key(foodtruck_number) references foodtruck(foodtruck_number) on delete cascade ,
   constraint foodtruckfilePK primary key(foodtruck_number, foodtruck_filepath)
);
-------------------------------------------------------------------------------
drop table foodtruckfile;
-------------------------------------------------------------------------------
create table review(
    review_no varchar2(100) not null,
    foodtruck_number varchar2(100) not null,
    customer_id varchar2(100) not null,
    review_content clob not null,
    review_timeposted date not null,
    grade number not null,
    constraint review_trucknumber_fk foreign key(foodtruck_number) references foodtruck(foodtruck_number) on delete cascade ,
    constraint review_id_fk foreign key(customer_id) references customer(customer_id) on delete cascade ,
    CONSTRAINT review_PK PRIMARY KEY(review_no, foodtruck_number)
    )
-------------------------------------------------------------------------------
drop table review;
-------------------------------------------------------------------------------
create table menu(
    menu_id varchar2(100) primary key,
    foodtruck_number varchar2(100) not null,
    menu_name varchar2(100) not null,
    menu_price number not null,
    menu_filename varchar2(100) not null,
    constraint menu_trucknumber_fk foreign key(foodtruck_number) references foodtruck(foodtruck_number) on delete cascade
);
-------------------------------------------------------------------------------
drop table menu;
-------------------------------------------------------------------------------
create table booking(
    booking_number number not null,
    customer_id varchar2(100) not null,
    booking_date date not null,
    booking_state varchar2(100) default '결제완료',
    constraint booking_id_fk foreign key(customer_id) references customer(customer_id) on delete cascade ,
    CONSTRAINT booking_PK PRIMARY KEY(booking_number)
);
-------------------------------------------------------------------------------
drop table booking;
-------------------------------------------------------------------------------
create table booking_detail(
   booking_number number not null,
   menu_id varchar2(100) not null,
   menu_quantity number not null,
   constraint bookingdetail_bookingnumber_fk foreign key(booking_number) references booking(booking_number) on delete cascade,
   constraint bookingdetail_menuid_fk foreign key(menu_id) references menu(menu_id) on delete cascade,
   CONSTRAINT bookingdetail_PK PRIMARY KEY(booking_number, menu_id)
);
-------------------------------------------------------------------------------
drop table booking_detail;
-------------------------------------------------------------------------------
create table customerpoint(
    booking_number number not null,
    point_type varchar2(100) not null,
    point number not null,
    point_date date not null,
    constraint customerpoint_booking_fk foreign key (booking_number) references booking(booking_number) on delete cascade,
    CONSTRAINT customerpoint_booking_pk PRIMARY KEY(booking_number, point_type)
);
-------------------------------------------------------------------------------
drop table customerpoint;
-------------------------------------------------------------------------------
create table survey(
    survey_no varchar2(100) not null,
    customer_id varchar2(100) not null,
    suervey_date date not null,
    constraint survey_id_fk foreign key(customer_id) references customer(customer_id) on delete cascade,
    CONSTRAINT survey_PK PRIMARY KEY(survey_no, customer_id)
);
-------------------------------------------------------------------------------
drop table survey;
-------------------------------------------------------------------------------
create table wishlist(
    customer_id varchar2(100) not null,
    foodtruck_number varchar2(100) not null,
    constraint wishlist_id_fk foreign key(customer_id) references customer(customer_id) on delete cascade ,
    constraint wishlist_trucknumber_fk foreign key(foodtruck_number) references foodtruck(foodtruck_number) on delete cascade ,
    CONSTRAINT wishlist_PK PRIMARY KEY(customer_id, foodtruck_number)
);
-------------------------------------------------------------------------------
drop table wishlist;
-------------------------------------------------------------------------------
create table qna(
   qna_no number primary key,
   id varchar2(100) not null,
   qna_title varchar2(100) not null,
   qna_content clob not null,
   qna_timeposted date not null,
   qna_hits number default(0),
   constraint qna_id_fk foreign key(id) references member(id) on delete cascade
)
-------------------------------------------------------------------------------
drop table qna;
-------------------------------------------------------------------------------qnaFile(0531)
create table qnafile(
   qna_no number not null,
   qnafile_filepath varchar2(100) not null,
   constraint qnafile_qnano_fk foreign key(qna_no) references qna(qna_no) on delete cascade ,
   constraint qnafile_PK primary key(qna_no, qnafile_filepath)
);
-------------------------------------------------------------------------------
drop table qnafile;
-------------------------------------------------------------------------------
create table qnacomment(
   qnacomment_no number primary key,
   id varchar2(100) not null,
   qna_no number not null,
   qnacomment_content varchar2(300) not null,
   qnacomment_timeposted date not null,
   qnacomment_parent varchar2(100) not null,
   constraint qnacomment_id_fk foreign key(id) references member(id) on delete cascade ,
   constraint qnacomment_qnano_fk foreign key(qna_no) references qna(qna_no) on delete cascade
);
-------------------------------------------------------------------------------
drop table qnacomment;
-------------------------------------------------------------------------------
create table freeboard(
   freeboard_no number primary key,
   id varchar2(100) not null,
   freeboard_title varchar2(100) not null,
   freeboard_content clob not null,
   freeboard_timeposted date not null,
   freeboard_hits number default(0),
   constraint freeboard_id_fk foreign key(id) references member(id) on delete cascade
);
-------------------------------------------------------------------------------
drop table freeboard;
-------------------------------------------------------------------------------freeboardfile(0531)
create table freeboardfile(
   freeboard_no number not null,
   freeboardfile_filepath varchar2(100) not null,
   constraint freeboardfile_freeboardno_fk foreign key(freeboard_no) references freeboard(freeboard_no) on delete cascade , 
   constraint freeboardfile_PK primary key(freeboard_no, freeboardfile_filepath) 
);
-------------------------------------------------------------------------------
drop table freeboardfile;
-------------------------------------------------------------------------------
create table freeboardcomment(
   freeboardcomment_no number primary key,
   id varchar2(100) not null,
   freeboard_no number not null,
   freeboardcomment_content varchar2(300) not null,
   freeboardcomment_timeposted date not null,
   freeboardcomment_parent number not null,
   constraint freeboardcomment_id_fk foreign key(id) references member(id) on delete cascade,
   constraint freeboardcomment_boardno_fk foreign key(freeboard_no) references freeboard(freeboard_no) on delete cascade 
);
-------------------------------------------------------------------------------
drop table freeboardcomment;
-------------------------------------------------------------------------------
create table information(
   information_no number primary key,
   id varchar2(100) not null,
   information_title varchar2(100) not null,
   information_content clob not null,
   information_timeposted date not null,
   information_hits number default(0),
   constraint information_id_fk foreign key(id) references member(id) on delete cascade
)
-------------------------------------------------------------------------------
drop table information;
-------------------------------------------------------------------------------informationfile(0531)
create table informationfile(
   information_no number not null,
   informationfile_filepath varchar2(100) not null,
   constraint informationfile_infono_fk foreign key(information_no) references information(information_no) on delete cascade , 
   constraint informationfile_PK primary key(information_no, informationfile_filepath) 
);
-------------------------------------------------------------------------------
drop table informationfile;
-------------------------------------------------------------------------------
create table informationcomment(
   informationcomment_no number primary key,
   id varchar2(100) not null,
   information_no number not null,
   informationcomment_content varchar2(300) not null,
   informationcomment_timeposted date not null,
   informationcomment_parent varchar2(100) not null,
   constraint infocomm_id_fk foreign key(id) references member(id) on delete cascade,
   constraint infocomm_no_fk foreign key(information_no) references information(information_no) on delete cascade
);
-------------------------------------------------------------------------------
drop table informationcomment;
-------------------------------------------------------------------------------