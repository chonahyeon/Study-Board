## 게시판 서비스

---

### 개발 환경
* 개발 도구 : Intellij IDEA Ultimate
* 개발 언어 : Java
* 프레임워크 : Spring Boot, MySQL
* 소스코드 관리 : Git


### 구현할 기능 목록

- [X] 게시물 작성 기능 (C)
- [ ] 게시물 리스트 페이지 구현 (R)
    - 게시물이 일정 개수 이상 넘어가면 일부분만 보이도록 하기
- [X] 게시물 수정 (U)
- [X] 게시물 삭제 (D)


### Board 테이블
* **id** (INT,PK,NN,AI) : 게시물의 고유 번호
* **title** (VARCHAR(45),NN) : 게시물의 제목
* **content** (TEXT,NN) : 게시물의 내용
