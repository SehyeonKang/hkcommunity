# 💻 Vill-V Atelier
> SpringBoot를 기반으로 개발한 게시판 프로젝트입니다.
<br/>
<br/>

![메인 화면](https://github.com/SehyeonKang/hkcommunity/assets/80624927/1cadd2d4-4af8-49c1-86d9-6b48c8ccb010)

---

## 목차
- [프로젝트 소개](#프로젝트-소개)
  - [소개](#1-소개)    
  - [프로젝트 기능](#2-프로젝트-기능)    
  - [기술 스택](#3-기술-스택)   
     - [백엔드](#3-1-백엔드)
     - [프론트엔드](#3-2-프론트엔드)
     - [프로젝트 배포](#3-3-프로젝트-배포)
  - [실행 화면](#4-실행-화면)

- [프로젝트 설계](#프로젝트-설계)
  - [DB 설계](#1-db-설계)
  - [API 설계](#2-api-설계)

---

## 프로젝트 소개
### 1. 소개

웹사이트를 개발할 때는 전체적인 큰 틀과 구성, 동작 원리 및 흐름등을 익히는 것이 중요하다고 생각합니다. 

또한 강의와 책으로 배운 지식들을 보고 따라하며 코딩하는 것으로 끝나지 않고, 이를 바탕으로 배운 것외에 기능을 추가하며 직접 웹사이트를 개발해보아야 좀 더 확실하게 배운 것들을 정리하며 이해할 수 있을 것이라고 생각합니다. 

그래서 회원, 게시글, 댓글, 알림 기능이 있는 기본적인 게시판 프로젝트를 개발했습니다.
<br/>
<br/>

그리고 웹사이트 개발에 그치지않고 로컬 환경에서만 작동하는 것이 아닌 실제로 다른 사람들도 쉽게 볼 수 있도록 배포하는 경험도 필요하다고 생각합니다. 

따라서 커밋을 하면 배포가 되도록 자동화하고, 도메인 추가와 https 설정을 추가하는 것까지 진행했습니다.

🔖 웹사이트 링크: https://villv-atelier.com

<br/>

### 2. 프로젝트 기능

게시판 프로젝트의 주요 기능은 다음과 같습니다.

- **회원**
  - 회원가입 및 로그인
  - 회원가입시 유효성, 중복 검사, 비밀번호 암호화하여 DB 저장
  - 회원가입시 이메일 인증 메일 전송
  - 비밀번호를 잊었을 경우 인증 메일 전송
  - 자기소개, 프로필 이미지등의 프로필 조회
  - 작성 글, 작성 댓글 목록 조회
  - 작성 글, 작성 댓글 페이징 기능
  - 프로필 세팅, 비밀번호, 닉네임 변경

- **게시판**
  - CRUD 기능
  - CRUD: 회원, RUD: 이메일 인증한 회원, UD: 작성자만 가능하도록 유효성 검사
  - 작성자 클릭시 작성자 프로필로 이동
  - 조회수 기능
  - 게시판, 게시글 카테고리별 분류 기능
  - 추천 기능
  - 검색 조건별 검색 목록 조회
  - 게시글 목록 페이징 기능

- **댓글**
  - CRUD 기능
  - CRUD: 회원, RUD: 이메일 인증한 회원, UD: 작성자만 가능하도록 유효성 검사
  - 계층형 답글 기능
  - 댓글 삭제시 답글 보유 여부에 따라 DB 삭제 보류
  - 댓글 작성자 클릭시 작성자 프로필로 이동
  
- **알림**
  - 댓글, 답글 알림 목록 조회
  - 읽은 댓글 알림 삭제 기능

### 3. 기술 스택

#### 3-1 백엔드

##### 주요 프레임워크 / 라이브러리 / IDE
- Java 11
- SpringBoot
- JPA(Spring Data JPA)
- QueryDSL
- Spring Security
- Junit
- Lombok
- IntelliJ

##### Build Tool
- Gradle

##### DataBase
- MariaDB

#### 3-2 프론트엔드
- Html/CSS
- JavaScript
- Bootstrap
- Summernote

#### 3-3 프로젝트 배포
- Github
- Travis CI
- AWS EC2, RDS, S3, CodeDeploy, Route 53
- NGINX

### 4. 실행 화면

  <details>
    <summary>회원 관련 실행 화면</summary>
    <br/>
    
  **1. 회원 가입 실행 후 메일 인증**
  
  ![회원-1](https://github.com/SehyeonKang/hkcommunity/assets/80624927/6de435e7-1bd7-46ba-89c9-0ee69bdeb36f)
    
  - 회원 가입 폼에서 유효성, 중복 검사 후 이메일 확인용 인증 메일을 보냅니다.
  - 비밀번호는 Bcrypt를 사용해서 암호화되어 저장됩니다.
  - 인증 메일을 확인하고나서 확인 링크를 클릭하면 이메일 인증이 완료되며 게시글, 댓글 작성이 가능해집니다.
  <br/>
  
  **2. 비밀번호를 잊었을 경우 메일 전송 후 인증**

  ![회원-2](https://github.com/SehyeonKang/hkcommunity/assets/80624927/6ed13c9e-fcc4-4df4-99bd-7e525bf7c1ef)

  - 로그인 시, 비밀번호를 잊었다면 메일 전송을 통해 로그인을 할 수 있습니다.
  <br/>
  
  **3. 프로필 화면**

  ![회원-3](https://github.com/SehyeonKang/hkcommunity/assets/80624927/ac674fa5-ce15-4633-879c-71aa2fabf550)

  - 프로필 이미지와 자기 소개, 작성 글, 작성 댓글을 확인 할 수 있으며, 본인 프로필이라면 프로필 수정 페이지로 이동 가능합니다.
  <br/>

  **4. 프로필 작성글, 작성댓글 화면**

  ![회원-5](https://github.com/SehyeonKang/hkcommunity/assets/80624927/f0f40687-d2b0-45ff-9893-8e5b2136e3dd)

  - 작성글, 작성댓글은 페이징되며 클릭시 해당 게시글 페이지로 이동합니다.
  <br/>
  
  **5. 계정 세팅 화면**

  ![회원-4](https://github.com/SehyeonKang/hkcommunity/assets/80624927/00f49084-ad44-41ff-8ffa-d48d6e28824c)

  = 프로필 이미지, 자기 소개, 비밀번호, 닉네임을 수정할 수 있습니다.
  
  </details>
  <br/>

  <details>
    <summary>게시글 관련 실행 화면</summary>
    <br/>

  **1. 게시글 작성 화면**

  ![게시글-1](https://github.com/SehyeonKang/hkcommunity/assets/80624927/76cfc046-9412-43a2-8ec0-c81ce1380d03)
  
  - 게시글 작성이 가능하며, 게시글 조회 페이지에서 작성자 닉네임을 클릭하면 프로필로 이동할 수 있습니다.
  <br/>
  
  **2. 게시글 수정 화면**

  ![게시글-2](https://github.com/SehyeonKang/hkcommunity/assets/80624927/a5b884f0-94cd-420d-9fb2-1b28b0b7756b)
  
  - 작성자는 게시글 수정이 가능합니다.
  <br/>
  
  **3. 게시글 삭제 화면**

  ![게시글-3](https://github.com/SehyeonKang/hkcommunity/assets/80624927/c9a2e54f-60db-4805-b41a-970b825acf70)
  
  - 작성자는 게시글 삭제가 가능합니다.
  <br/>
  
  **4. 작성자 외의 회원이 게시글을 볼 경우**

  ![게시글-4](https://github.com/SehyeonKang/hkcommunity/assets/80624927/aafa97ae-a0c6-4475-94db-e069cf1aa07e)
  
  - 수정, 삭제 버튼이 없으며, 백엔드 부분에서도 수정, 삭제시 작성자인지 확인합니다.
  <br/>
  
  **5. 게시글 조회수 기능**

  ![게시글-5](https://github.com/SehyeonKang/hkcommunity/assets/80624927/a4eb568e-2d89-4ab1-baa0-1909f0944fd9)
  
  - 게시글을 조회할 때마다 조회수가 증가합니다.
  <br/>
  
  **6. 게시글 카테고리별 분류 기능**

  ![게시글-6](https://github.com/SehyeonKang/hkcommunity/assets/80624927/f4fe538d-5f53-4277-9d96-bd8e03f0ae27)
  
  - 게시판별, 카테고리별로 게시글이 분류됩니다.
  <br/>
  
  **7. 게시글 추천 기능**

  ![게시글-7](https://github.com/SehyeonKang/hkcommunity/assets/80624927/d8474ff3-d22a-4f2f-a579-5c34e12067e0)

  - 한 회원당 한 게시글에 한 번만 추천이 가능하며, 추천을 취소할 수 있습니다.
  <br/>
  
  **8. 게시글 검색 기능**

  ![게시글-8](https://github.com/SehyeonKang/hkcommunity/assets/80624927/542ba1cb-d3f4-44ba-bbde-b6b35c505f89)
  
  - 전체, 제목, 글쓴이등으로 검색 조건을 걸고 검색이 가능하며, 카테고리와 동시에 검색할 수 있습니다.
  <br/>
  
  **9. 게시글 목록 페이징 기능**

  ![게시글-9](https://github.com/SehyeonKang/hkcommunity/assets/80624927/b37736f9-a4bf-4b4e-8a20-74c3b12b34fd)
  
  - 게시글 페이징을 확인하기 편하게 하기 위해서 실행 화면 테스트에서는 페이지당 게시글 2개, 최대 페이지 개수 5개로 설정했습니다.
  - 게시글 목록 페이지에서는 페이징을 통해 많은 게시글을 더 쉽게 볼 수 있습니다.
    
  </details>
  <br/>

  <details>
    <summary>댓글 관련 실행 화면</summary>
    <br/>

  **1. 댓글, 답글 작성 화면**

  ![댓글-1](https://github.com/SehyeonKang/hkcommunity/assets/80624927/33268e1f-92a3-4682-8408-4318a74f7703)
  
  - 댓글, 답글이 작성 가능하며 답글은 계층형 구조로써 계속해서 작성 가능합니다.
  - 답글을 작성한 댓글의 작성자를 알 수 있도록 표시했습니다.
  <br/>
  
  **2. 댓글 수정, 삭제 화면**

  ![댓글-2](https://github.com/SehyeonKang/hkcommunity/assets/80624927/b6786383-f5b8-4ffd-b481-bb9b0451ade2)
  
  - 작성자만 댓글 수정, 삭제가 가능합니다.
  - 답글이 있는 댓글은 삭제해도 DB에서 삭제를 하지않고 삭제 여부 상태만 변경한 뒤, 자신 아래의 답글들이 모두 삭제되면 DB에서 삭제됩니다.
  <br/>
  
  **3. 댓글 작성자 닉네임 클릭시 프로필 페이지로 이동**

  ![댓글-3](https://github.com/SehyeonKang/hkcommunity/assets/80624927/badad41c-5857-4f81-9114-bc4ed79b0fdd)
  
  - 댓글 작성자 닉네임을 클릭하면 해당 작성자의 프로필 페이지로 이동합니다.

  </details>
  <br/>

  <details>
    <summary>알림 관련 실행 화면</summary>
    <br/>

  **1. 알림 목록 화면**

  ![알림](https://github.com/SehyeonKang/hkcommunity/assets/80624927/c4caab11-b8c6-4f7a-b931-3e076158cc60)
  
  - 자신의 게시글이나 댓글에 댓글, 답글이 생긴다면 실시간으로 알림이 생깁니다.
  - 알림 목록에서 알림이 생긴 게시글, 댓글 작성자, 내용등을 확인 할 수 있습니다.
  - 읽은 후에는 알림 일괄 삭제가 가능합니다.
  <br/>

  **2. 알림 목록에서 게시글 이동**

  ![알림-2](https://github.com/SehyeonKang/hkcommunity/assets/80624927/d8a92de0-4172-48af-a537-0fd544d11ea6)
  
  - 알림 목록에서 알림을 클릭하면 해당 게시글로 이동합니다.
    
  </details>
  <br/>

---

## 프로젝트 설계

### 1. DB 설계

<br/>

<img src="https://github.com/SehyeonKang/hkcommunity/assets/80624927/8cc5edfb-41e1-4cab-901f-380040ed369f" width=700 />
<img src="https://github.com/SehyeonKang/hkcommunity/assets/80624927/e7da2bf8-099a-4274-9e46-305760aa2d6a" width=600 />
<img src="https://github.com/SehyeonKang/hkcommunity/assets/80624927/1a4f220d-89e3-44a7-be85-6fae1411450d" width=505 />
<img src="https://github.com/SehyeonKang/hkcommunity/assets/80624927/725d7bc0-3df8-4f2e-8f75-c1581521484d" width=505 />
<img src="https://github.com/SehyeonKang/hkcommunity/assets/80624927/3d3fffe8-d77a-496b-aa6c-8594f4db5300" width=600 />
<img src="https://github.com/SehyeonKang/hkcommunity/assets/80624927/15f20487-1c1f-49ee-a275-4e9bad8658dc" width=600 />
<img src="https://github.com/SehyeonKang/hkcommunity/assets/80624927/1a4f471b-04d2-4630-9d34-6a07e4601622" width=600 />

<br/>

### 2. API 설계

<br/>

<img src="https://github.com/SehyeonKang/hkcommunity/assets/80624927/2674d172-f267-4a7d-9c0e-eb4947d863d6" width=600 />
<img src="https://github.com/SehyeonKang/hkcommunity/assets/80624927/0cb4922e-e4b9-4639-948a-cc5318805f97" width=600 />
<img src="https://github.com/SehyeonKang/hkcommunity/assets/80624927/c6dd0124-5fce-49cb-afe0-90ffd28d57b5" width=505 />
<img src="https://github.com/SehyeonKang/hkcommunity/assets/80624927/fde8d4ca-2a72-4182-b1c1-d87f645553b1" width=505 />
<img src="https://github.com/SehyeonKang/hkcommunity/assets/80624927/c7c05fb4-e7a9-41de-a441-0b176ce394ad" width=600 />


<br/>

