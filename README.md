## CryptoHows Backend API

### 도메인 구조

**[VentureCapital]**

- 벤쳐 캐피탈의 정보를 담은 엔티티
- ID | 회사명 | 소개 | 홈페이지 | 로고 사진 | List<Partnership\> partnerships | List<RoundParticipation\> roundParticipations

**[Partnership]**

- 벤쳐 캐피탈 - 프로젝트를 잇는 중개 엔티티
- 어떤 벤처 캐피탈과 프로젝트가 파트너쉽을 맺었는지 기록
- ID | VentureCapital | Project

**[Project]**

- 프로젝트의 정보를 담은 엔티티
- ID | 프로젝트명 | 소개 | 홈페이지 | 로고 사진 | 트위터 | 커뮤니티 | 카테고리 | 메인넷 | List<Partnership\> partnerships | List<Round\> rounds | List<Coin\> coins

**[Coin]**

- 코인의 정보를 담은 엔티티
- ID | Project | 코인 심볼 | 코인 정보 더보기 링크

**[RoundParticipation]**

- 벤처 캐피탈 - 라운드를 잇는 중개 엔티티
- 어떤 벤처캐피탈이 어떤 라운드에 참여했는지 기록
- ID | VentureCapital | Round

**[Round]**

- 투자 라운드의 정보를 담은 엔티티
- ID | Project | 투자 시기 | 투자 금액 | 투자 유치 뉴스 링크 | 총 투자자 | 투자 라운드 | List<RoundParticipation\> vcParticipants
