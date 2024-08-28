### 1. API 명세서
<img width="858" alt="스크린샷 2024-08-16 01 17 42" src="https://github.com/user-attachments/assets/34aeb6eb-7e9e-4efd-8e25-a6abe56ccb9c">

## 유저(User) 관련 API
|기능|Method|URL|요청 Request|Response|
|:---:|:---|:---|:---|:---|
|유저 등록|POST|/api/users|요청 body|UserResponseDto|
|유저 단건 조회|GET|/api/users/{id}|요청 param|UserResponseDto|
|유저 전체 조회|GET|/api/users|-|List<UserResponseDto>|
|유저 삭제|DELETE|/api/users/{id}|요청 param|Long|
|일정 담당 유저 배치|POST|/api/users/{id}/schedules/{scheduleId}/assign|요청 body(담당 유저 고유 식별자-id를 담은 리스트), param|String|

### 2. ERD 
<img width="285" alt="스크린샷 2024-08-16 01 17 54" src="https://github.com/user-attachments/assets/c6778178-96d3-4b06-89d8-385aa8d9a53e">

