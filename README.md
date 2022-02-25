## CryptoHows Backend API

### 도메인 설계

**[VentureCapital]**

- 벤쳐 캐피탈의 정보를 담은 엔티티
- 이름 / 소개 / 홈페이지 / 로고 사진 / List<Partnership\> partnerships

**[Partnership]**

- 벤쳐 캐피탈 - 프로젝트를 잇는 중개 엔티티
- 어떤 벤처 캐피탈과 프로젝트가 파트너쉽을 맺었는지 기록
- VentureCapital / Project

**[Project]**

- 프로젝트의 정보를 담은 엔티티
- 이름 / 소개 / 홈페이지 / 로고 사진 / 카테고리 / 메인넷 / List<Partnership\> partnerships / List<Round\> rounds

**[RoundParticipation]**

- 벤처 캐피탈 - 라운드를 잇는 중개 엔티티
- 어떤 벤처캐피탈이 어떤 라운드에 참여했는지 기록
- VentureCapital / Round

**[Round]**

- 투자 라운드의 정보를 담은 엔티티
- Project 프로젝트 / 투자 시기 / 투자 금액 / 투자 라운드 / List<RoundParticipation/> participants

### 구현할 기능 목록

- [x] TDD를 통한 도메인 설계
- [x] 도메인 별 패키지 분리
- [x] Repository Layer 테스트 작성
- [x] Service Layer 구현
- [x] Web Layer 구현
- [x] API 문서화
- [ ] 데이터 Excel 파일 업로드 지원
  - [ ] VC 업로드 지원
  - [ ] Project 업로드 지원
  - [ ] Round 업로드 지원
