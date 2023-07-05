# Routine Timer
---
`MadCamp Week1`
- 연락처, 갤러리, 루틴 타이머 세 탭으로 이루어진 Android 기반 어플리케이션입니다.
- 연락처를 추가, 편집 그리고 삭제할 수 있습니다.
- 저장되어 있는 이미지를 확인할 수 있습니다.
- 자신만의 루틴을 만들어 효과적으로 운동할 수 있습니다.
---
## 팀원
- [김성록](https://github.com/SeongrokKim)
- [권혁원](https://github.com/Gerbera3090)
---
## 개발 환경
- OS: Android (minSdk: 24, targetSdk: 33)
- Language: Java
- IDE: Android Studio
- Target Device: Galaxy S7
---
## 프로젝트 설명
### 연락처 탭
- **앱 실행**
![image](https://github.com/SeongrokKim/2023S_MadCampWeek1/assets/110150859/49c5b3c5-bb20-4f8c-bdcd-67a8a2ff4261)


![image](https://github.com/SeongrokKim/2023S_MadCampWeek1/assets/110150859/45d79508-3d11-40d8-b3ee-e2a613a7a12e)


![image](https://github.com/SeongrokKim/2023S_MadCampWeek1/assets/110150859/dbf7197a-aeec-41a2-a362-4ebc5fff903e)

![image](https://github.com/SeongrokKim/2023S_MadCampWeek1/assets/110150859/cc29fc0d-def2-4b90-96da-519cc1f956aa)

- **핵심 기능**
  - 저장되어 있는 연락처를 확인할 수 있습니다.
  - `새 연락처 추가` 버튼을 통해 새로운 연락처 정보를 저장할 수 있습니다.
  - `검색` 버튼을 통해 필터링된 연락처 정보를 확인할 수 있습니다.
  - 저장된 연락처를 길게 눌러 편집/삭제할 수 있습니다.

- **기술 설명**
  - Recycler View를 통해 저장되어 있는 연락처 정보를 보여줍니다.
  - 연락처 정보는 JSON 형식으로 저장되어 있으며, 사용자 입력 데이터를 JSON 형식으로 변환하여 저장합니다.
  - Adapter를 이용하여 JSON 형식의 데이터를 Recycler View의 아이템으로 연결합니다.
  - View Holder 생성 후 View의 data에 바인딩하여 화면에 보여줍니다.
  - View Holder와 관련된 메서드로 onCreateViewHolder()(View Holder와 연결된 view 생성 및 초기화), onBindViewHolder()(View Holder와 data가 연결)를 구현합니다.

### 갤러리 탭
- **앱 실행**

- **핵심 기능**
  - 

- **기술 설명**
  - 

### 루틴 타이머 탭
- **앱 실행**
  

- **핵심 기능**
  - 

- **기술 설명**
  - 
