## ✨ Live Voting Service ✨
> 실시간 투표 시스템 / 현재 버전 v0.0

**라이브 방송, 실시간 강의, 회의, 이벤트 중 즉석 투표**를 진행할 수 있는 실시간 투표 시스템입니다.

- STOMP 기반 실시간 양방향 통신
- WebSocket을 통한 낮은 지연시간의 메시징
- JSON 기반 메시지 처리
- 서버 재기동 없이 실시간 갱신
- (예정) Redis Pub/Sub 구조 확장 예정

---

## 기술 스택

| 분류 | 기술 |
|------|------|
| Back-end | Spring Boot, STOMP, WebSocket, Java |
| Front-end | Thymeleaf, JS, SockJS, STOMP.js |
| Build Tool | Gradle |
| 테스트 도구 | Chrome, F12 콘솔 |

---

## 구조 설명

##### 사용자 → /app/vote 로 메시지 전송 (투표 or 초기화) 
##### ↓ VoteController 에서 메시지 처리 ↓
##### /topic/result 구독 중인 모든 사용자에게 실시간 전송
##### 실시간 반응은 `/topic/result`를 통해 브라우저에 전달됨
##### 모든 유저가 **자동으로 최신 결과를 수신**함

---

## 주요 기능

- 이름 입력 후 실시간 투표 가능
- 중복 투표 방지 (user 기반 체크)
- 실시간 결과 브로드캐스트
- 투표 초기화 버튼
- 다중 투표 세션(Room 기반 구조) 확장 예정
- Redis 메시지 브로커 연동 (확장형 Pub/Sub 구조)

---

## 실행 방법

```bash
# Spring Boot 실행
./gradlew bootRun

# 브라우저에서 접속
http://localhost:8080/vote
