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
 ![image](https://github.com/SeongrokKim/2023S_MadCampWeek1/assets/110150859/828fd8fb-b5c9-41b6-9ca9-2da9f9b21eab)

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
![image](https://github.com/SeongrokKim/2023S_MadCampWeek1/assets/110150859/7c319789-e3b9-4b6e-bda3-728cc6477bba)


- **핵심 기능**
  - 어플리케이션 내장 이미지를 이름 순서대로 불러올 수 있습니다.
  - Add Photo 버튼을 눌러 내장 이미지를 추가 로드할 수 있습니다.
  - 이미지를 눌러 더 큰 이미지 dialog를 띄울 수 있습니다.
  - 이미지 확대 dialog에서 스와이프 제스쳐 및 버튼을 통해서 이미지를 넘길 수 있습니다.

- **기술 설명**
  - Gridmanager를 이용한 Recycler View를 통해서 저장되어 있는 image 목록을 보여줍니다.
  - image 목록은 R.drawable.class.getDeclaredFields를 통해서 얻습니다.
  - DialogFragment를 통해서 확대된 이미지를 보여 주는 창을 띄웁니다.
  - Viewpager2를 통해서 확대된 이미지를 스와이프를 통해서 이동시킬 수 있도록 합니다.


### 루틴 타이머 탭
- **앱 실행**
![image](https://github.com/SeongrokKim/2023S_MadCampWeek1/assets/110150859/69b3ed26-2941-4ac8-8c17-0bb561717282)


- **핵심 기능**
  - 운동 추가 버튼을 통해 한 루틴에 여러 운동을 추가할 수 있습니다.
  - editText를 수정하거나 +- 버튼을 눌러 시간 및 반복횟수를 설정할 수 있습니다.
  - 루틴 추가 버튼을 통해 여러 루틴을 생성할 수 있습니다.
  - 확인 버튼을 눌러 Routine Timer를 실행할 수 있습니다.
  - 설정된 시간이 지나면 각 루틴이 넘어가고, 루틴의 종료는 진동과 소리로 안내됩니다.

- **기술 설명**
  - 이중 Adapter를 사용하여 Recycler View의 아이템 루틴 Card View에 운동 목록을 저장하는 Recycler View를 포함하였습니다.
  - Dialog Fragment를 이용하여 CardView에 있는 내용을 바탕으로 Timer Fragment를 실행합니다.
  - CountDownTimer를 이용하여 만든 MyTimer class 를 이용하여 timer 기능을 구현합니다.
  - 타이머 종료 시마다 MediaPlayer를 이용하여 청각적 피드백을, Vibrator를 이용한 촉각적 피드백을 제공합니다.
  - MyTimer에서 onFinish를 Override하여 끝났을 때 남은 루틴을 위한 타이머가 자동 시작되도록 합니다.
  - progressbar를 이용하여 남은 시간 및 운동에 대한 시각적 피드백을 제공합니다.
