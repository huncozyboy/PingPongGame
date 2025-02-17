# PingPongGame
단식, 복식 탁구 게임 CRUD
- 단식 2명, 복식 4명
- RED, BLUE 2개의 팀이 존재하고 서로 반/반 정확하게 인원이 나뉘어진 상태에서만 게임을 진행할 수 있습니다.
- 방을 생성한 사람을 host 라고 명칭하고, host 가 방을 나가면 해당 방은 'FINISH(완료)' 상태로 변경됩니다.
- 게임은 방에 인원이 모두 찬 상태에서만 시작 가능합니다.
- 방에 모든 인원이 가득찬 상태라면 참가 할 수 없습니다.
- 방에 참가한 인원에 대한 팀 배정 로직은 다음과 같습니다.
    - 한 쪽 팀에 인원이 모두 찬 경우, 반대팀으로 배정합니다.
    - 양쪽 팀에 모두 자리가 있는 경우, RED팀에 먼저 배정됩니다.
