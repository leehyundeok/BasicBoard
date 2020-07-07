회원 전용 게시판
================
## 회원들간에 소통 및 정보를 공유 할 수 있는 JSP 게시판
### 회원가입 화면
#### 회원정보를 입력후 가입버튼을 눌러 테이블에 아이디 중복값이 있는지 확인후 입력한 값을 저장
![캡처](https://user-images.githubusercontent.com/62640249/86761586-07a83600-c081-11ea-8b68-75a3aeb43aee.PNG)
### 로그인
#### 로그인 화면 페이지에 입력한 회원정보가 DB테이블에 있는 정보와 일치 할 경우 세션에 ID의 값을 저장
![image](https://user-images.githubusercontent.com/62640249/86762745-d0865480-c081-11ea-9fde-57e3c68a9a45.png)
### 게시판 글쓰기
#### 로그인 세션값을 그대로 불러와서 회원전용 게시글을 작성 할 수 있음, 파일 업로드도 가능 
![image](https://user-images.githubusercontent.com/62640249/86764090-c2850380-c082-11ea-924f-90005e60506e.png)
### 글 목록
![image](https://user-images.githubusercontent.com/62640249/86764567-198ad880-c083-11ea-96d1-edd97659b0b2.png)
### 게시글 조회
#### 작성된 게시물을 클릭 후 게시글 수정, 삭제, 댓글, 업로드된 파일 다운로드 기능 구현 가능
1. 
* 글 수정
![image](https://user-images.githubusercontent.com/62640249/86765031-9322c680-c083-11ea-9d71-45f2391600f9.png)
* 수정완료
![image](https://user-images.githubusercontent.com/62640249/86765223-d715cb80-c083-11ea-9b57-c35296ba4b8c.png)
2. 
댓글 기능
![image](https://user-images.githubusercontent.com/62640249/86765452-25c36580-c084-11ea-9c1b-55465c7940a2.png)


3. 글 삭제
* 비밀번호 확인 작업필요
![image](https://user-images.githubusercontent.com/62640249/86765585-4db2c900-c084-11ea-9e08-754c468ddda0.png)
* 게시글이 목록에서 삭제된 모습
![image](https://user-images.githubusercontent.com/62640249/86765680-73d86900-c084-11ea-8992-18424b75eff0.png)
4. 
* 파일 다운로드
![image](https://user-images.githubusercontent.com/62640249/86765315-f876b780-c083-11ea-8bb5-e7342332d338.png)
### 회원 탈퇴
#### 회원탈퇴를 하면 게시판 테이블과 회원 테이블의 외래 참조관계 때문에 기존에 회원이 쓴 게시글이 남거나 회원탈퇴가 되지않는 문제가 있었는데 그부분을 해결하기 위해 참조관계를 일시적으로 끊을수있는 제어문 cascade문을 사용해서 해결하였음

#### 개선해야 할점
* 회원이 휴먼, 불발행동 등의 이유로 탈퇴를 강제로 시켜야할때 관리자의 권한을 부여받아 탈퇴, 게시글 삭제 등을 행할 수 있도록 해야함.
