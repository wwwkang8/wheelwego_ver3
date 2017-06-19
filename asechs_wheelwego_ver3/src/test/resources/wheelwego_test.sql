select * from foodtruck where seller_id='seller'
delete from BOOKING where customer_id='customer01'
select * from booking
select * from foodtruck where foodtruck_name='달리는타코'
delete from booking where CUSTOMER_ID='customer01'
select * from BOOKING_DETAIL
select * from CUSTOMER
select * from 
select * from SELLER
----------dbTEST---------------------
select bd.booking_number,bd.menu_id, bd.menu_quantity, m.menu_name, m.menu_price 
	   	from booking b, booking_detail bd, menu m
	   	where b.booking_number=bd.booking_number and 
	   	b.booking_number=17 and m.menu_id=bd.menu_id
select * from booking where customer_id='jhji21'
select * from foodtruck where foodtruck_name='현지트럭'
select * from foodtruck
select * from wishlist
select * from BOOKING where customer_id='customer01'
select * from BOOKING_DETAIL
select f.foodtruck_name
from (select * from menu)fn, foodtruck f
where fn.foodtruck_number = f.foodtruck_number and fn.menu_id='76'

select foodtruck_name from foodtruck where foodtruck_number='80나0035'
select t.*, f.foodtruck_filepath, m.menu_filename
		from(select * from foodtruck)t, foodtruckfile f, menu m
		where t.foodtruck_number=f.foodtruck_number and t.foodtruck_number=m.foodtruck_number and t.foodtruck_number='80나0001'

----------dbTEST---------------------
		select id from member where member_name='정현지' and
		phonenumber='01022552716'
select * from member
delete from review
delete from member where id='seller01'
delete from member where id='customer01'
delete from foodtruck where foodtruck_name='코알라트럭'
select * from seller
select * from menu
select * from FOODTRUCK where foodtruck_name='불빠닭'
select * from FOODTRUCKFILE where foodtruck_number='80나0019'
select * from FOODTRUCKFILE
select * from customer
select * from FOODTRUCK
select * from REVIEW
select * from FOODTRUCKFILE where foodtruck_number='80나0036'
select * from FOODTRUCKFILE
select * from seller

select * from menu where foodtruck_number='80나0001'
insert into MEMBER values('customer01','1234','정현지','위례','경기도 하남시 학암동','01022552716','customer','12345');
select t.*, f.foodtruck_filepath, m.menu_filename
		from(select * from foodtruck)t, foodtruckfile f, menu m
		where t.foodtruck_number=f.foodtruck_number and t.foodtruck_number=m.foodtruck_number and t.foodtruck_number='80나0001'
select * from menu where foodtruck_number='80나0001'
		--foodtruck detail test
select t.*, f.foodtruck_filepath, m.menu_filename
		from(select * from foodtruck)t, foodtruckfile f, menu m
		where t.foodtruck_number=f.foodtruck_number and t.foodtruck_number=m.foodtruck_number and t.foodtruck_number='80나0001'
select t.*, f.foodtruck_filepath
		from(select * from foodtruck)t, foodtruckfile f
		where t.foodtruck_number=f.foodtruck_number
-- pagingbean 적용 푸드트럭 리스트
select t.*, f.foodtruck_filepath
		from(select * from foodtruck)t, foodtruckfile f
		where t.foodtruck_number=f.foodtruck_number and t.foodtruck_name like '%' || '트럭' || '%'
--
	select f.foodtruck_filepath, t.foodtruck_name FROM foodtruck t, foodtruckfile f
	where t.foodtruck_number=f.foodtruck_number
-- 검색 결과 푸드트럭 리스트
SELECT f.foodtruck_filepath, t.foodtruck_name FROM(
		SELECT row_number() over(order by foodtruck_number desc) as rnum, foodtruck_filename1, foodtruck_name from foodtruck)
		f where rnum between #{startRowNumber} and #{endRowNumber}

----------dbTEST---------------------
insert into member values ('seller07', '1234', '김래발', '판교','코스타', '010-1111-1111', 'seller','1234');
insert into member values ('seller01', '1234', '김래발', '판교','코스타', '010-1111-1111', 'seller','1234');

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
	SELECT f.foodtruck_filename1, f.foodtruck_name FROM(
		SELECT row_number() over(order by foodtruck_number desc) as rnum, foodtruck_filename1, foodtruck_name from foodtruck
		)f where rnum between 1 and 5

insert into member values ('java01', 'java01', '김래발', '판교','코스타', '010-1111-1111', '일반회원','1234');
insert into member values ('seller02', '1234', '김래발', '판교','코스타', '010-1111-1111', 'seller','1234');
update member set member_type='0' where id='java01';
delete from member;
select * from foodtruck;
delete from member where id = 'seller02';
select * from member;
select * from seller;
delete member where id='java'
delete seller where seller_id='java'
insert into customer values ('java01');
insert into seller values ('seller07','1231');
delete from customer;
select c.customer_id, m.password, m.member_name, m.address, m.phonenumber, m.member_type
from customer c, member m
where c.customer_id = m.id;
--로그인
select id,password,member_name,address,phonenumber,member_type from member where id='java01' and password='java01'
select * from member;
select * from foodtruck;
select * from seller;

delete from member;
delete from customer;
delete from seller;
delete from foodtruck;
delete from wishlist;

select password from member where id ='java01';

select password from member where id = 'java01';

select * from member;

select * from seller;
select * from wishlist ;
select * from wishlist where customer_id = 'customer01';
-- 푸드트럭 위시리스트 sql문
select * from foodtruck where foodtruck_number in (select w.foodtruck_number
from customer c, wishlist w
where c.customer_id = w.customer_id and c.customer_id = 'customer01' );

select * from wishlist where customer_id = 'customer01';
insert into wishlist values('customer01','80나0001');
insert into wishlist values('customer01','80나0002');
insert into wishlist values('customer01','80나0003');
insert into wishlist values('customer01','80나0015');

select * from foodtruck;

delete from wishlist;

select * from seller;
update member set phonenumber='01012341234' where id='seller57'
update member set password='1234' where id='java01' and member_name='김래발' and phonenumber='01000000000';

select * from foodtruck where seller_id='seller60';

delete from wishlist;

delete member where id = 'java01';
update member set password=#{password}, address=#{address}, addressdetail = #{addressDetail}, phonenumber=#{phoneNumber}, postcode=#{postCode} where id=#{id}


insert into foodtruck (foodtruck_number, seller_id, foodtruck_name, introduction, foodtruck_filename1)
values ('80나0014', 'seller01', '스파르타쿡스', '전설의 불떡볶이와 바삭한 탕수육의 환상조화! 10년 조리경력의 주인장이 운영하는 스파르타쿡스입니다.', '스파르타쿡스_트럭1.PNG');

select * from customer;
select * from seller;
select * from foodtruck;

insert into foodtruck (foodtruck_number, seller_id, foodtruck_name, introduction, foodtruck_filename1)
values ('80나0014', 'seller02', '스파르타쿡스', '전설의 불떡볶이와 바삭한 탕수육의 환상조화! 10년 조리경력의 주인장이 운영하는 스파르타쿡스입니다.', '스파르타쿡스_트럭1.PNG');

select * from foodtruck where foodtruck_number in (select w.foodtruck_number
from customer c, wishlist w
where c.customer_id = w.customer_id and c.customer_id = 'customer01' );

-- 게시판 상세보기
select freeboard_no, id, freeboard_title, freeboard_content, to_char(freeboard_timeposted,'YYYY.MM.DD HH:mm:ss')
freeboard_timeposted,
freeboard_hits  from freeboard where freeboard_no=8
--게시물 삭제
delete freeboard where freeboard_no=1 
select foodtruck_name, foodtruck_filename1 from foodtruck where foodtruck_name like '%' || '트럭' || '%' 
select row_number() over(order by foodtruck_number desc)as rnum, foodtruck_name, foodtruck_filename1 from foodtruck where foodtruck_name like '%' || '트럭' || '%' 
SELECT f.foodtruck_filename1, f.foodtruck_name FROM(
		SELECT row_number() over(order by foodtruck_number desc) as rnum, foodtruck_filename1, foodtruck_name from foodtruck 
		where foodtruck_name like '%' || '트럭' || '%'
		)f  where rnum between 1 and 5
 

select * from 

select * from foodtruck where foodtruck_number in (select w.foodtruck_number
from customer c, wishlist w
where c.customer_id = w.customer_id and c.customer_id = 'customer01' );


select * from wishlist where customer_id = 'customer01';

select * from foodtruck where foodtruck_number in (select w.foodtruck_number
from customer c, wishlist w
where c.customer_id = w.customer_id and c.customer_id = 'customer01');

select * from wishList where customer_id = 'customer01';
select * from customer;
select * from foodtruck;

insert into wishList (customer_id, foodtruck_number) values ('customer01', '80나0011');

delete from wishlist where customer_id = 'java01' and foodtruck_number = '80나0011';
select * from 
create sequence freeboard_seq;


select m.member_name as membername
from freeboard f, member m
where m.id=f.id and f.id='java9'


select * from customer;

 select t.*, f.foodtruck_filepath   
 from(select * from foodtruck)t, foodtruckfile f    
 where t.foodtruck_number=f.foodtruck_number


select * from File
select * from freeboardfile
insert into freeboardfile(freeboard_no, freeboard_filepath) values ('93','한입만모모.jpg');


select f.freeboard_no, f.id, f.freeboard_title, f.freeboard_content,
		 to_char(freeboard_timeposted,'YYYY.MM.DD HH:mm:ss') as f.freeboard_timeposted,
		 f.freeboard_hits, file.freeboard_no, file.freeboardfile_filepath 
from freeboard f, freeboardfile file 
where f.freeboard_no=file.freeboard_no and f.freeboard_no='112'

select b.freeboard_no, b.id, b.freeboard_title, b.freeboard_content, to_char(b.freeboard_timeposted,'YYYY.MM.DD HH:mm:ss') as freeboard_timeposted,
b.freeboard_hits, f.freeboard_no, f.freeboardfile_filepath 
from freeboard b, freeboardfile f where b.freeboard_no=f.freeboard_no and f.freeboard_no='112'

select f.freeboard_no, f.id, f.freeboard_title, f.freeboard_content,
       to_char(f.freeboard_timeposted,'YYYY.MM.DD HH:mm:ss') as freeboard_timeposted, f.freeboard_hits, fi.freeboard_no, fi.freeboard_filepath
from freeboard f, freeboardfile fi 
where f.freeboard_no=fi.freeboard_no and f.freeboard_no='112'
from freeboard f, freeboardfile fi 
where f.freeboard_no=fi.freeboard_no and f.freeboard_no='112'

    
insert into review values (review_seq.nextval, '2523','customer1','맛있어요',sysdate, 4);
select review_no,foodtruck_number,customer_id,review_content, to_char(review_timeposted,'YYYY.MM.DD'), grade from review


select freeboard_no, id, freeboard_title, freeboard_content, to_char(freeboard_timeposted,'YYYY.MM.DD HH:mm:ss') as freeboard_timeposted,
		freeboard_hits from freeboard where freeboard_no='118'
		
select freeboard_no, id, freeboard_title, freeboard_content, to_char(freeboard_timeposted,'YYYY.MM.DD HH:mm:ss') as freeboard_timeposted,
		freeboard_hits from freeboard where freeboard_no='118'

select m.member_name as memberName from freeboard f, member m where m.id=f.id and f.id='java101' and f.freeboard_no='118'


	SELECT f.freeboard_no, f.id, f.freeboard_title, f.freeboard_content, f.freeboard_timePosted, f.freeboard_hits, m.id FROM(
		SELECT row_number() over(order by freeboard_no desc) as rnum, freeboard_no, id, freeboard_title, freeboard_content,
		to_char(freeboard_timePosted,'YYYY.MM.DD') as freeboard_timePosted , freeboard_hits from freeboard
		)f, member m where f.id=m.id and rnum between startRowNumber and endRowNumber
		order by freeboard_no desc


	SELECT f.freeboard_no, f.id, f.freeboard_title, f.freeboard_content, f.freeboard_timePosted, f.freeboard_hits, m.id FROM(
		SELECT row_number() over(order by freeboard_no desc) as rnum, freeboard_no, id, freeboard_title, freeboard_content,
		to_char(freeboard_timePosted,'YYYY.MM.DD') as freeboard_timePosted , freeboard_hits from freeboard
		)f, member m where f.id=m.id and rnum between 1 and 30
		order by freeboard_no desc


select * from foodtruck;

commit

insert into foodtruck ()

delete from seller;

delete from foodtruck;
delete from foodtruckfile;

insert into foodtruck (foodtruck_number, seller_id, foodtruck_name, introduction) values('80나0001', 'seller01', '봉군타코', '도시위에 멕시칸! 바로 봉군타코에서 느낄 수 있습니다.');
insert into foodtruck (foodtruck_number, seller_id, foodtruck_name, introduction) values('80나0002', 'seller02', '깡스푸드', '도시위에 멕시칸! 바로 봉군타코에서 느낄 수 있습니다.');
insert into foodtruck (foodtruck_number, seller_id, foodtruck_name, introduction) values('80나0003', 'seller03', '래발푸드', '도시위에 멕시칸! 바로 봉군타코에서 느낄 수 있습니다.');

insert into foodtruckfile(foodtruck_number,foodtruck_filepath) values ('80나0001', 'Friend_트럭1.jpg');
insert into foodtruckfile(foodtruck_number,foodtruck_filepath) values ('80나0002', '그남자의소시지_트럭1.PNG');
insert into foodtruckfile(foodtruck_number,foodtruck_filepath) values ('80나0003', '깡스푸드_트럭1.JPG');



select foodtruck_number from wishlist where customer_id = 'customer01';

select t.*, f.FOODTRUCK_FILEPATH 
from foodtruck t, foodtruckfile f
where t.foodtruck_number = f.foodtruck_number;

	select * from foodtruck where foodtruck_number in (select
	w.foodtruck_number
	from customer c, wishlist w
	where c.customer_id = w.customer_id and c.customer_id = 'cusomter01');
	
	select * from foodtruckfile;
	
select t.*, f.foodtruck_filepath
from(select * from foodtruck where foodtruck_number=#{value})t, foodtruckfile f
where t.foodtruck_number=f.foodtruck_number	

		
		
select * from foodtruck where foodtruck_number in 

select foodtruck_number from wishlist where customer_id=  'customer01';
		
select menu_id,foodtruck_number,menu_name,menu_price,menu_filename from menu where foodtruck_number='80나0001' order by menu_id asc

select t.*, f.foodtruck_filepath from foodtruck t, foodtruckfile f 
where f.foodtruck_number in 
(select foodtruck_number from wishlist where customer_id = 'customer01')
and t.foodtruck_number = f.foodtruck_number;

select * from foodtruck
where foodtruck_number in (
select w.foodtruck_number from customer c, wishlist w
where c.customer_id = 'cusomter01' and c.customer_id = w.customer_id);

SELECT A.MBR_ID, B.MBR_ID
FROM MEMBER A
INNER JOIN MEMBER B ON A.MBR_ID = B.MBR_ID


select * from wishlist where customer_id = 'customer01'

select * from foodtruckfile;
select * from wishlist;

insert into review(review_no,foodtruck_number,customer_id,review_content,review_timeposted,grade) 
values(review_seq.nextval,'1234','customer2','굿~~',sysdate,4);
insert into review(review_no,foodtruck_number,customer_id,review_content,review_timeposted,grade) 
values(review_seq.nextval,'1234','customer1','맛있음~~',sysdate,4);

SELECT * FROM FOODTRUCK
select *
from
(select row_number() over(order by review_no desc) as rnum ,review_no,foodtruck_number,customer_id,
review_content,to_char(review_timeposted,'YYYY.MM.DD') as review_timeposted, grade from review
where foodtruck_number='1234')
where rnum between 1 and 5;

select * from freeboardcomment

select freeboardcomment_no, id, freeboardcomment_content, to_char(freeboardcomment_timeposted,'YYYY.MM.DD hh:mm:ss') as freeboardcomment_timeposted 
		from freeboardcomment  where freeboard_no=154 order by freeboardcomment_no desc
select * from freeboardcomment


insert into freeboardcomment(freeboardcomment_no, id, freeboardcomment_content, freeboard_no,freeboardcomment_timeposted, freeboardcomment_parent)
values(freeboardcomment_seq.nextval,'java101','강정호 댓글시도',154,sysdate,0)

delete freeboardfile where freeboard_no=115 
delete freeboardfile where freeboard_no=93 and  freeboardfile_filepath='c.jpg'







37.660562, 126.769869



select id from member where seller_id='seller04';
delete from foodtruck;
select * from foodtruck;

update foodtruck set latitude=37.439211,longitude=127.127643 where foodtruck_number='80나0035';
update foodtruck set latitude=37.439232,longitude=127.128067 where foodtruck_number='80나0036';
update foodtruck set latitude=37.513423,longitude=127.099329 where foodtruck_number='80나0037';
update foodtruck set latitude=37.513797,longitude=127.100852 where foodtruck_number='80나0038';
update foodtruck set latitude=37.514052,longitude=127.100176 where foodtruck_number='80나0039';
update foodtruck set latitude=37.514477,longitude=127.099361 where foodtruck_number='80나0040';
update foodtruck set latitude=37.513958,longitude=127.098771 where foodtruck_number='80나0041';
update foodtruck set latitude=37.512775,longitude=127.099490 where foodtruck_number='80나0042';
update foodtruck set latitude=37.512511,longitude=127.100209 where foodtruck_number='80나0043';
update foodtruck set latitude=37.512698,longitude=127.101099 where foodtruck_number='80나0044';
update foodtruck set latitude=37.512238,longitude=127.100445 where foodtruck_number='80나0045';
update foodtruck set latitude=37.661029,longitude=126.769129 where foodtruck_number='80나0046';
update foodtruck set latitude=37.660774,longitude=126.768292 where foodtruck_number='80나0047';
update foodtruck set latitude=37.660562,longitude=126.769869 where foodtruck_number='80나0048';--
update foodtruck set latitude=37.660443,longitude=126.768453 where foodtruck_number='80나0048';
update foodtruck set latitude=37.660536,longitude=126.770020 where foodtruck_number='80나0049';
update foodtruck set latitude=37.662863,longitude=126.768303 where foodtruck_number='80나0050';
update foodtruck set latitude=37.662133,longitude=126.767123 where foodtruck_number='80나0051';
update foodtruck set latitude=37.661131,longitude=126.766672 where foodtruck_number='80나0052';
update foodtruck set latitude=37.660587,longitude=126.767573 where foodtruck_number='80나0053';
update foodtruck set latitude=37.659398,longitude=126.768700 where foodtruck_number='80나0054';
update foodtruck set latitude=37.659729,longitude=126.770449 where foodtruck_number='80나0056';


37.660443, 126.768453

insert into foodtruck values('80나0001','seller01','프랑크소시지1000',	'두툼한 닭꼬치의 두께와 선홍빛의 양고기 꼬치','	37.399606',	'127.10853580');
insert into foodtruck values('80나0002','seller02','그남자의소시지','크기도 엄청 크고 보기만 해도 군침이 도는 마약핫도그!','37.394921','127.111234');
insert into foodtruck values('80나0003','seller03','프랑크소시지1001','100% 국내산 생고기를 사용하는 수제버거! 깡스푸드 푸드트럭입니다!','37.400375','127.110104');
insert into foodtruck values('80나0004','seller04','프랑크소시지1002','고급 아이스크림을 먹고 싶은가? 이리로 오라 아이스크림으로 당신을 녹여주겠어',	'37.439793',	'127.127821');
insert into foodtruck values('80나0006','seller06',	'프랑크소시지1004','츄러스를 소스에도 찍어먹고 아이스크림에도 찍어먹을수 있는 츄러스의 달인 나이스츄미츄!','	37.511074','127.073489');
insert into foodtruck values('80나0007','seller07',	'프랑크소시지1005','이태리 거주 10년 본토의 맛을 푸드트럭에서 보여준다는 주인장! 날개없는 비행기 푸드트럭입니다!','	37.509873','	127.076533');
insert into foodtruck values('80나0005','seller05',	'프랑크소시지1003','규동 가츠동 에비후라이동 등 대표일본음식이 푸짐한 양으로 제공되는 끼니 푸드트럭입니다!',	'37.438595',	'127.124501');

select * from foodtruck where foodtruck_name like '%' || '푸드' || '%';
select * from foodtruck;
select * from foodtruckfile;

insert into foodtruckfile values ('80나0001', '그남자의소시지_트럭1.PNG');
insert into foodtruckfile values ('80나0002', '그남자의소시지_트럭1.PNG');
insert into foodtruckfile values ('80나0003', '그남자의소시지_트럭1.PNG');
insert into foodtruckfile values ('80나0004', '그남자의소시지_트럭1.PNG');
insert into foodtruckfile values ('80나0005', '그남자의소시지_트럭1.PNG');
insert into foodtruckfile values ('80나0006', '그남자의소시지_트럭1.PNG');
insert into foodtruckfile values ('80나0007', '그남자의소시지_트럭1.PNG');

select * from member;

 37.660536, 126.770020

37.662863, 126.768303

37.662133, 126.767123

37.661131, 126.766672

37.660587, 126.767573

37.659398, 126.768700

 37.659729, 126.770449

 SELECT f.foodtruck_filepath, t.* FROM
(SELECT fo.*, row_number() over(order by foodtruck_number desc) as rnum 
from foodtruck fo where foodtruck_name like '%' || '소시지' || '%' and latitude is not null and longitude is not null)t, foodtruckfile f  
where t.foodtruck_number=f.foodtruck_number and rnum between 1 and 8
order by t.foodtruck_number desc











select * from foodtruckfile;

		select t.*, f.foodtruck_filepath
		from(select * from foodtruck)t, foodtruckfile f
		where t.foodtruck_number=f.foodtruck_number

select * from member;


select t.*, f.foodtruck_filepath
from(select * from foodtruck)t, foodtruckfile f
where t.foodtruck_number=f.foodtruck_number and t.foodtruck_name like '%' || '소시지' || '%'

select * from foodtruck where foodtruck_name like '%' || '소시지' || '%'
select * from foodtruckfile;

insert into foodtruckfile values ('80나0002', 'Friend_트럭1.jpg');

select * from foodtruck;

	insert into freeboardfile(freeboard_no, freeboardfile_filepath)values(#{fileVO.no},#{fileVO.filepath})

	update freeboardfile set freeboardfile_filepath=#{value} where freeboard_no=#{fileVO.no} and freeboardfile_filepath=#{fileVO.filepath}
	update freeboardfile set freeboardfile_filepath='c.jpg' where freeboardfile_filepath='b.jpg' and freeboard_no=77


select * from freeboardfile

insert into wishlist();      


select * from wishlist;

select count(*) from wishlist where foodtruck_number = '80나0003' and customer_id = 'customer01';


select count(*) from wishlist where foodtruck_number = '80나0003' and customer_id = 'customer01';

select * from menu;
drop table bookingdetail
drop table booking_detail;
--해당 푸드트럭의 평점찾기
select nvl(trunc(avg(r.grade),1),0) as grade
from(select * from foodtruck)f, review r
where f.foodtruck_number=r.foodtruck_number(+) and f.foodtruck_number='80나0002' group by f.foodtruck_number 

--해당 푸드트럭의 위시리스트수 찾기

select * from wishlist where foodtruck_number='1234'
select f.foodtruck_number, count(customer_id) from foodtruck f, wishlist w
where f.foodtruck_number=w.foodtruck_number(+) and w.foodtruck_number='80나0056' group by f.foodtruck_number

--최신순 트럭 리스트
select * from(
select row_number() over(order by register_timeposted desc) as rnum, t.foodtruck_name, t.foodtruck_number, t.register_timeposted, f.foodtruck_filepath
from foodtruck t, foodtruckfile f where f.foodtruck_number=t.foodtruck_number and t.foodtruck_name like '%' || '트' || '%' order by register_timeposted desc
) where rnum between 1 and 9

--평점순 트럭 리스트
select * from(
select row_number() over(order by grade desc) as rnum,foodtruck_number, foodtruck_name, grade, foodtruck_filepath from(
select t.foodtruck_number, t.foodtruck_name, nvl(trunc(avg(r.grade),1),0) as grade, f.foodtruck_filepath from foodtruck t, review r, foodtruckfile f 
where t.foodtruck_number=r.foodtruck_number(+) and t.foodtruck_number=f.foodtruck_number and  t.foodtruck_name like '%' || '트' || '%'
group by t.foodtruck_number,t.foodtruck_name,f.foodtruck_filepath order by grade desc
)) where rnum between 1 and 9

--즐겨찾기순
select * from(
select row_number() over(order by wishlist_count desc) as rnum,foodtruck_number, foodtruck_name, wishlist_count, foodtruck_filepath from(
select t.foodtruck_number, t.foodtruck_name, count(customer_id) as wishlist_count, f.foodtruck_filepath from foodtruck t, wishlist w, foodtruckfile f 
where t.foodtruck_number=w.foodtruck_number(+) and t.foodtruck_number=f.foodtruck_number and  t.foodtruck_name like '%' || '트' || '%'
group by t.foodtruck_number,t.foodtruck_name,f.foodtruck_filepath order by wishlist_count desc
)) where rnum between 1 and 9

select freeboardcomment_no, id, freeboard_no, freeboardcomment_content, freeboardcomment_timeposted from freeboardcomment 
		where freeboardcomment_no=64 and freeboard_no=175
		
		delete from information where informationcomment_no=1 and information_no=91
		select * from informationcomment

	
		select * from foodtruckfile
		delete foodtrucckfile 

select review_no, foodtruck_number, customer_id, review_content, review_timeposted, grade from(
select row_number() over(order by review_timeposted desc) as rnum,review_no,foodtruck_number,customer_id,review_content, to_char(review_timeposted,'YYYY.MM.DD HH24:MI:SS') as review_timeposted, grade
	from review where customer_id='customer02' ) where rnum between 10 and 18

	select count(*) from review where customer_id='customer02'
	
	select * from menu
	select * from customer
	update booking set booking_state='결제완료' where booking_number=1
	select * from booking
	select * from menu
	select * from member
	select * from seller
	select * from foodtruck
	-- 강정호.작성
	insert into booking(booking_number, customer_id, menu_id, booking_quantity, booking_date, booking_state)
	values(1,'customer01','1',2,sysdate,'결제완료')
	insert into booking(booking_number, customer_id, menu_id, booking_quantity, booking_date, booking_state)
	values(1,'customer01','2',2,sysdate,'결제완료')
	insert into booking(booking_number, customer_id, menu_id, booking_quantity, booking_date, booking_state)
	values(1,'customer01','3',2,sysdate,'결제완료')
	insert into booking(booking_number, customer_id, menu_id, booking_quantity, booking_date, booking_state)
	values(1,'customer02','4',2,sysdate,'결제완료')
	insert into booking(booking_number, customer_id, menu_id, booking_quantity, booking_date, booking_state)
	values(1,'customer02','5',2,sysdate,'결제완료')
	insert into booking(booking_number, customer_id, menu_id, booking_quantity, booking_date, booking_state)
	values(1,'customer02','6',2,sysdate,'결제완료')
	select booking_number, customer_id, menu_id, booking_quantity, booking_date, booking_state from booking where booking_number=1
	select b.* from booking b, foodtruck f, menu m where f.foodtruck_number=m.foodtruck_number and f.foodtruck_number='80나0001' and m.menu_id=b.menu_id;
	select * from member
	
	--강정호 작성. 임시 예약
	insert into booking(booking_number, customer_id, booking_date, booking_state)
	values(1,'customer',sysdate, '결제완료');
	insert into booking_detail(booking_number,menu_id, menu_quantity)
	values(1,'123',5)
	insert into booking_detail(booking_number,menu_id, menu_quantity)
	values(1,'124',3)
	insert into booking_detail(booking_number,menu_id, menu_quantity)
	values(1,'125',2)
		insert into booking(booking_number, customer_id, booking_date, booking_state)
	values(2,'customer02',sysdate, '결제완료');
	insert into booking_detail(booking_number,menu_id, menu_quantity)
	values(2,'123',10)
	insert into booking_detail(booking_number,menu_id, menu_quantity)
	values(2,'124',20)
	insert into booking_detail(booking_number,menu_id, menu_quantity)
	values(2,'125',30)
			insert into booking(booking_number, customer_id, booking_date, booking_state)
	values(3,'customer03',sysdate, '결제완료');
		insert into booking_detail(booking_number,menu_id, menu_quantity)
	values(3,'123',8)
	insert into booking_detail(booking_number,menu_id, menu_quantity)
	values(3,'124',8)
	insert into booking_detail(booking_number,menu_id, menu_quantity)
	values(3,'125',8)
				insert into booking(booking_number, customer_id, booking_date, booking_state)
	values(4,'customer04',sysdate, '결제완료');
	insert into booking_detail(booking_number,menu_id, menu_quantity)
	values(4,'123',4)
	insert into booking_detail(booking_number,menu_id, menu_quantity)
	values(4,'124',4)
	insert into booking_detail(booking_number,menu_id, menu_quantity)
	values(4,'125',4)
					insert into booking(booking_number, customer_id, booking_date, booking_state)
	values(5,'customer05',sysdate, '결제완료');
		insert into booking_detail(booking_number,menu_id, menu_quantity)
	values(5,'123',5)
	insert into booking_detail(booking_number,menu_id, menu_quantity)
	values(5,'124',5)
	insert into booking_detail(booking_number,menu_id, menu_quantity)
	values(5,'125',5)
						insert into booking(booking_number, customer_id, booking_date, booking_state)
	values(22,'customer05',sysdate, '결제완료');
						insert into booking(booking_number, customer_id, booking_date, booking_state)
	values(23,'customer06',sysdate, '결제완료');
						insert into booking(booking_number, customer_id, booking_date, booking_state)
	values(24,'customer07',sysdate, '결제완료');
						insert into booking(booking_number, customer_id, booking_date, booking_state)
	values(25,'customer08',sysdate, '결제완료');
						insert into booking(booking_number, customer_id, booking_date, booking_state)
	values(26,'customer09',sysdate, '결제완료');
						insert into booking(booking_number, customer_id, booking_date, booking_state)
	values(27,'customer10',sysdate, '결제완료');
						insert into booking(booking_number, customer_id, booking_date, booking_state)
	values(28,'customer11',sysdate, '결제완료');
						insert into booking(booking_number, customer_id, booking_date, booking_state)
	values(29,'customer12',sysdate, '결제완료');
						insert into booking(booking_number, customer_id, booking_date, booking_state)
	values(30,'customer14',sysdate, '결제완료');
							insert into booking(booking_number, customer_id, booking_date, booking_state)
	values(31,'customer15',sysdate, '결제완료');
							insert into booking(booking_number, customer_id, booking_date, booking_state)
	values(32,'customer16',sysdate, '결제완료');
							insert into booking(booking_number, customer_id, booking_date, booking_state)
	values(33,'customer17',sysdate, '결제완료');
							insert into booking(booking_number, customer_id, booking_date, booking_state)
	values(34,'customer18',sysdate, '결제완료');
	
		insert into booking_detail(booking_number,menu_id, menu_quantity)
	values(22,'123',6);
			insert into booking_detail(booking_number,menu_id, menu_quantity)
	values(23,'123',6);
			insert into booking_detail(booking_number,menu_id, menu_quantity)
	values(24,'123',6);
			insert into booking_detail(booking_number,menu_id, menu_quantity)
	values(25,'123',6);
			insert into booking_detail(booking_number,menu_id, menu_quantity)
	values(26,'123',6);
			insert into booking_detail(booking_number,menu_id, menu_quantity)
	values(27,'123',6);
			insert into booking_detail(booking_number,menu_id, menu_quantity)
	values(28,'123',6);
			insert into booking_detail(booking_number,menu_id, menu_quantity)
	values(29,'123',6);
			insert into booking_detail(booking_number,menu_id, menu_quantity)
	values(30,'123',6);
		insert into booking_detail(booking_number,menu_id, menu_quantity)
	values(31,'123',6);
		insert into booking_detail(booking_number,menu_id, menu_quantity)
	values(32,'123',6);
		insert into booking_detail(booking_number,menu_id, menu_quantity)
	values(33,'123',6);
		insert into booking_detail(booking_number,menu_id, menu_quantity)
	values(34,'123',6);

	
	insert into booking_detail(booking_number,menu_id, menu_quantity)
	values(6,'124',6)
	insert into booking_detail(booking_number,menu_id, menu_quantity)
	values(6,'125',6)
	 select foodtruck_number from foodtruck where seller_id='seller'
	select menu_id from booking b, booking_detail bd where b.booking_number=bd.booking_number
		
	select b.booking_number, b.customer_id, bd.menu_id, bd.menu_quantity, b.booking_state,
	to_char(booking_date,'YYYY.MM.DD HH.MM.SS') as booking_date
	from booking b, booking_detail bd, foodtruck f, menu m 
	where b.booking_number=bd.booking_number and f.foodtruck_number=m.foodtruck_number and f.foodtruck_number='80나0061'
	and m.menu_id=bd.menu_id order by b.booking_number desc
	
	select DISTINCT b.booking_number, b.customer_id, b.booking_state,
	to_char(booking_date,'YYYY.MM.DD HH.MM.SS') as booking_date
	from booking b, foodtruck f, menu m 
	where  f.foodtruck_number=m.foodtruck_number and f.foodtruck_number='80나0061'
	order by b.booking_number desc
	
	select m.menu_id, bd.menu_quantity, m.menu_name, m.menu_price from booking b, booking_detail bd, menu m 
	where b.booking_number=bd.booking_number and bd.booking_number=1
	and bd.menu_id=m.menu_id
	
	select  b.booking_number, b.customer_id, b.booking_state,
	to_char(booking_date,'YYYY.MM.DD HH.MM.SS') as booking_date, bd.menu_id, bd.menu_quantity, m.menu_name 
	from booking b, booking_detail bd, foodtruck f, menu m 
	where  f.foodtruck_number=m.foodtruck_number and f.foodtruck_number='80나0061'
	and b.booking_number=bd.booking_number and bd.menu_id=m.menu_id
	order by b.booking_number desc
	
	select booking_number as bd.bookingNumber , menu_id as bd.menuId , menu_quantity as bd.bookingQuantity, m.menu_price 
   		from booking_detail bd, menu m where bd.booking_number=1 and bd.menu_id=m.menu_id
   	
   		-- bookingNumber 가져오는 sql 구문
   	select distinct b.booking_number
   	from booking b, foodtruck f, menu m, booking_detail bd 
   	where f.foodtruck_number=m.foodtruck_number and f.foodtruck_number='80나0037' and bd.menu_id=m.menu_id and b.booking_number=bd.booking_number
   	
   	--bookingNumber를 이용해서 메뉴 내역 가져오는 메서드
   	-- and bd.booking_number=2 이것이 없으면 다른 창업주에게 주문한 것도 내가 다 받아올 수있음. 실험용으로 좋음
   	select b.customer_id, b.booking_date, b.booking_state, bd.menu_id, bd.menu_quantity
   	, m.menu_name, m.menu_price 
   	from booking b, booking_detail bd, menu m
   	where b.booking_number=bd.booking_number and 
   	b.booking_number=2 and bd.booking_number=2
   	
   	-- booking_detail과 menu 테이블 이용해서 bookingDetailVO 가져오기
   	select bd.menu_id, m.menu_name, m.menu_price, bd.menu_quantity 
   	from booking_detail bd, menu m
   	where bd.menu_id=m.menu_id and bd.booking_number=2
   	
   	--customerBookingNumberList 가져오는 sql 구문
   	select distinct b.booking_number, b.customer_id, to_char(booking_date,'YYYY.MM.DD HH.MM.SS') as booking_date, b.booking_state 
   	from booking b, booking_detail bd
   	where b.booking_number=bd.booking_number
   	and b.customer_id='customer'
   	order by b.booking_number desc
   	
   	select bd.menu_id, m.menu_name, m.menu_price, bd.menu_quantity 
   	from booking_detail bd, menu m, booking b
   	where bd.menu_id=m.menu_id and b.customer_id='customer' and b.booking_number=bd.booking_number and b.booking_number=8
	
   	select count(*) from booking b, foodtruck f, menu m 
   	where f.foodtruck_number=m.foodtruck_number and f.foodtruck_number='80나0061'
   	
   	select distinct b.booking_number, b.customer_id, to_char(booking_date,'YYYY.MM.DD HH.MM.SS') as booking_date, b.booking_state 
	   	from booking b, foodtruck f, menu m , booking_detail bd
	   	where f.foodtruck_number=m.foodtruck_number and f.foodtruck_number='80나0051'
	   	
	   	select distinct b.booking_number, b.customer_id,  b.booking_date, b.booking_state 
	   	from(SELECT row_number() over(order by booking_number desc) as rnum, booking_number, customer_id, 
	   	to_char(booking_date,'YYYY.MM.DD HH.MM.SS') as booking_date, booking_state from booking)b, foodtruck f, menu m
	   	where f.foodtruck_number=m.foodtruck_number and f.foodtruck_number='80나0061' and rnum between 1 and 9
	   
   			select distinct b.booking_number, b.customer_id, b.booking_date, b.booking_state 
	   	from(SELECT row_number() over(order by booking_number desc) as rnum, booking_number, customer_id, 
	   	to_char(booking_date,'YYYY.MM.DD HH.MM.SS') as booking_date, booking_state from booking)b, foodtruck f, menu m
	   	where f.foodtruck_number=m.foodtruck_number and f.foodtruck_number='80나0061' and rnum between 1 and 9

	   	select distinct b.booking_number, b.customer_id, to_char(booking_date,'YYYY.MM.DD HH.MM.SS') as booking_date, b.booking_state  
	   	from booking b, foodtruck f, menu m, booking_detail bd 
	   	where f.foodtruck_number=m.foodtruck_number and f.foodtruck_number='80나0037'
	   	 and bd.menu_id=m.menu_id and b.booking_number=bd.booking_number
	   	
-------------------------------------------------------------------------------

update foodtruck set latitude=37.402403,longitude=127.106248 where foodtruck_number='80나0001';
update foodtruck set latitude=37.402238,longitude=127.106811 where foodtruck_number='80나0002';
update foodtruck set latitude=37.402074,longitude=127.107874 where foodtruck_number='80나0003';
update foodtruck set latitude=37.401800,longitude=127.106865 where foodtruck_number='80나0004';
update foodtruck set latitude=37.402311,longitude=127.106394 where foodtruck_number='80나0005';
update foodtruck set latitude=37.402359,longitude=127.106130 where foodtruck_number='80나0006';
update foodtruck set latitude=37.401832,longitude=127.106011 where foodtruck_number='80나0007';
update foodtruck set latitude=37.402365,longitude=127.105850 where foodtruck_number='80나0008';
update foodtruck set latitude=37.402452,longitude=127.106786 where foodtruck_number='80나0009';
---------------------------------------------------------------------------------------------판교--------------------------------------------------------------------------------------------------------------------------------
update foodtruck set latitude=37.411823,longitude=127.129187 where foodtruck_number='80나0010';
update foodtruck set latitude=37.411776,longitude=127.128522 where foodtruck_number='80나0011';
update foodtruck set latitude=37.411197,longitude=127.128487 where foodtruck_number='80나0012';
update foodtruck set latitude=37.411044,longitude=127.129037 where foodtruck_number='80나0013';
update foodtruck set latitude=37.410991,longitude=127.128501 where foodtruck_number='80나0014';
update foodtruck set latitude=37.411240,longitude=127.128579 where foodtruck_number='80나0015';
update foodtruck set latitude=37.411451,longitude=127.129223 where foodtruck_number='80나0016';
update foodtruck set latitude=37.410976,longitude=127.128296 where foodtruck_number='80나0018';
----------------------------------------------------------------------------야탑------------------------------------------------------------------------------------------------------------------------------------------------
update foodtruck set latitude=37.263289,longitude=127.032312 where foodtruck_number='80나0019';
update foodtruck set latitude=37.263500,longitude=127.031848 where foodtruck_number='80나0020';
update foodtruck set latitude=37.263641,longitude=127.031910 where foodtruck_number='80나0021';
update foodtruck set latitude=37.263619,longitude=127.032323 where foodtruck_number='80나0022';
update foodtruck set latitude=37.263391,longitude=127.031776 where foodtruck_number='80나0023';
update foodtruck set latitude=37.263643,longitude=127.032591 where foodtruck_number='80나0024';
update foodtruck set latitude=37.263430,longitude=127.031328 where foodtruck_number='80나0025';
update foodtruck set latitude=37.263759,longitude=127.031449 where foodtruck_number='80나0026';
update foodtruck set latitude=37.263774,longitude=127.031736 where foodtruck_number='80나0027';
----------------------------------------------------------------------------------------------------수원---------------------------------------------------------------
update foodtruck set latitude=37.440029,longitude=127.128465 where foodtruck_number='80나0028';
update foodtruck set latitude=37.439867,longitude=127.127231 where foodtruck_number='80나0029';
update foodtruck set latitude=37.439509,longitude=127.127413 where foodtruck_number='80나0030';
update foodtruck set latitude=37.439654,longitude=127.128105 where foodtruck_number='80나0031';
update foodtruck set latitude=37.439986,longitude=127.127059 where foodtruck_number='80나0032';
update foodtruck set latitude=37.439441,longitude=127.127354 where foodtruck_number='80나0033';
update foodtruck set latitude=37.439535,longitude=127.128019 where foodtruck_number='80나0034';

--------------------------------------------------------------------------------------------------------------태평-----------------------------------------------------------------------------------------------------------

---------------------------------------------------------------------------------------------------------------------------잠실------------------------------------------------------------------------------
update foodtruck set latitude=37.439211,longitude=127.127643 where foodtruck_number='80나0035';
update foodtruck set latitude=37.439232,longitude=127.128067 where foodtruck_number='80나0036';
update foodtruck set latitude=37.513423,longitude=127.099329 where foodtruck_number='80나0037';
update foodtruck set latitude=37.513797,longitude=127.100852 where foodtruck_number='80나0038';
update foodtruck set latitude=37.514052,longitude=127.100176 where foodtruck_number='80나0039';
update foodtruck set latitude=37.514477,longitude=127.099361 where foodtruck_number='80나0040';
update foodtruck set latitude=37.513958,longitude=127.098771 where foodtruck_number='80나0041';
update foodtruck set latitude=37.512775,longitude=127.099490 where foodtruck_number='80나0042';
update foodtruck set latitude=37.512511,longitude=127.100209 where foodtruck_number='80나0043';
update foodtruck set latitude=37.512698,longitude=127.101099 where foodtruck_number='80나0044';
update foodtruck set latitude=37.512238,longitude=127.100445 where foodtruck_number='80나0045';
update foodtruck set latitude=37.661029,longitude=126.769129 where foodtruck_number='80나0046';
update foodtruck set latitude=37.660774,longitude=126.768292 where foodtruck_number='80나0047';
update foodtruck set latitude=37.660562,longitude=126.769869 where foodtruck_number='80나0048';--
update foodtruck set latitude=37.660443,longitude=126.768453 where foodtruck_number='80나0048';
update foodtruck set latitude=37.660536,longitude=126.770020 where foodtruck_number='80나0049';
update foodtruck set latitude=37.662863,longitude=126.768303 where foodtruck_number='80나0050';
update foodtruck set latitude=37.662133,longitude=126.767123 where foodtruck_number='80나0051';
update foodtruck set latitude=37.661131,longitude=126.766672 where foodtruck_number='80나0052';
update foodtruck set latitude=37.660587,longitude=126.767573 where foodtruck_number='80나0053';
update foodtruck set latitude=37.659398,longitude=126.768700 where foodtruck_number='80나0054';
update foodtruck set latitude=37.659729,longitude=126.770449 where foodtruck_number='80나0056';

--------------------------------------------------------------------------------------------------------일산----------------------------------------

select count(*) from freeboard where id='customer1'

select freeboard_no,id,freeboard_title,freeboard_content,freeboard_timeposted,freeboard_hits
from freeboard where id='customer1'

select review_no, foodtruck_number,foodtruck_name, customer_id, review_content, review_timeposted, grade from(
select row_number() over(order by r.review_timeposted desc) as rnum,r.review_no,t.foodtruck_number,r.customer_id,r.review_content, to_char(r.review_timeposted,'YYYY.MM.DD HH24:MI:SS') as review_timeposted, r.grade, t.foodtruck_name
from review r, foodtruck t where r.customer_id=#{searchWord}  and r.foodtruck_number=t.foodtruck_number ) where rnum between #{startRowNumber} and #{endRowNumber}

select freeboard_no,id,freeboard_title,freeboard_content,freeboard_timeposted,freeboard_hits from(
select row_number() over(order by r.freeboard_timeposted desc) as rnum,r.review_no,t.foodtruck_number,r.customer_id,r.review_content, to_char(r.review_timeposted,'YYYY.MM.DD HH24:MI:SS') as review_timeposted, r.grade, t.foodtruck_name
from review r, foodtruck t where r.customer_id=#{searchWord}  and r.foodtruck_number=t.foodtruck_number ) where rnum between #{startRowNumber} and #{endRowNumber}
------------------------------------------------------------------------------


select review_no, foodtruck_number,foodtruck_name, customer_id, review_content, review_timeposted, grade from(
select row_number() over(order by r.review_timeposted desc) as rnum,r.review_no,t.foodtruck_number,r.customer_id,r.review_content, to_char(r.review_timeposted,'YYYY.MM.DD HH24:MI:SS') as review_timeposted, r.grade, t.foodtruck_name
from review r, foodtruck t where r.customer_id=#{searchWord}  and r.foodtruck_number=t.foodtruck_number ) where rnum between #{startRowNumber} and #{endRowNumber}

select freeboard_no, id, freeboard_title, freeboard_content, freeboard_timePosted, freeboard_hits from(
select row_number() over(order by f.freeboard_timePosted desc) as rnum,  f.freeboard_no, f.id, f.freeboard_title, f.freeboard_content, to_char(f.freeboard_timePosted,'YYYY.MM.DD.HH.MM HH:MM:SS') as freeboard_timePosted, f.freeboard_hits
from freeboard f, member m where f.id='customer1' and f.id=m.id ) where rnum between 1 and 4




SELECT f.freeboard_no, f.id, f.freeboard_title, f.freeboard_content, f.freeboard_timePosted, f.freeboard_hits, m.id FROM(
SELECT row_number() over(order by freeboard_timeposted desc) as rnum, freeboard_no, id, freeboard_title, freeboard_content,
to_char(freeboard_timePosted,'YYYY.MM.DD.HH.MM HH:MM:SS') as freeboard_timePosted, freeboard_hits from freeboard
)f, member m where f.id='customer1' and f.id=m.id and rnum between 1 and 4
order by freeboard_timePosted desc
		
		
SELECT f.freeboard_no, f.id, f.freeboard_title, f.freeboard_content, f.freeboard_timePosted, f.freeboard_hits, m.id FROM(
SELECT row_number() over(order by freeboard_timeposted desc) as rnum, freeboard_no, id, freeboard_title, freeboard_content,
to_char(freeboard_timePosted,'YYYY.MM.DD.HH.MM HH:MM:SS') as freeboard_timePosted, freeboard_hits from freeboard
)f, member m where f.id=#{searchWord} and f.id=m.id and rnum between #{startRowNumber} and #{endRowNumber}
order by freeboard_timePosted desc		
		
		
select qna_no, id, qna_title, qna_content, qna_timePosted, qna_hits from(
select row_number() over(order by f.qna_timePosted desc) as rnum,  f.qna_no, f.id, f.qna_title, f.qna_content, to_char(f.qna_timePosted,'YYYY.MM.DD.HH.MM HH:MM:SS') as qna_timePosted, f.qna_hits
from qna f, member m where f.id='customer1' and f.id=m.id ) where rnum between 1 and 5 		
		
		
		
		