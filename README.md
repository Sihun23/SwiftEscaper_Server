# 🌐 SwiftEscaper Backend Web Server
다양한 client(센서, GPS, IPS) 측의 요청을 처리하고 저장하기 위해 **Spring Boot 기반 Web Server**를 구축.  
다양한 기능을 갖춘 서버가 아닌, **여러 데이터를 수신하고 데이터를 처리하는 것**에 중점을 둔 Web Server.

---

## 🏆 성과 및 개선 결과
| 문제점 | 해결법 | 성과 |
|:---:|:---:|:---:|
| DB에 저장된 위치 정보를 기반으로 그룹화하고 알람을 전송하는 시간이 너무 오래 소요됨 | 위치 테이블의 **위도-경도, 터널-절대위치**에 Indexing 설정 및 **Batch 전송** 적용 | 기존 300명 대상 **8초 → 평균 0.7초**로 획기적 성능 개선 |

---

## 💻 **역할**
### **Infra 구성**
- 🔧 **KOREN Cloud 환경 구축**
- 🚀 **Docker 기반 배포 및 CI/CD 적용**
- 🌐 **Apache2 Proxy Server 세팅**

### **Spring Boot 기반 애플리케이션 개발**
- 🏗 **기초 프로젝트 구성**
- 🔄 **WebSocket 기반 실시간 데이터 송수신**
- 📍 **위치 기반 사용자 그룹화 기능**
- 🔔 **FCM(Firebase Cloud Messaging) 알람 전송**
- ⚡ **고성능 데이터 처리 및 최적화**

---

## 🏗 **전체 시스템 아키텍처**
![Edge](https://github.com/user-attachments/assets/f3fc1dcc-aa87-4e3e-92ec-34730128a1e6)

---

## 🔩 **기술 스택**
### **Backend**
![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![REST API](https://img.shields.io/badge/REST_API-000000?style=for-the-badge&logo=rest&logoColor=white)

### **DevOps**
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-2088FF?style=for-the-badge&logo=github-actions&logoColor=white)
![KOREN Cloud](https://img.shields.io/badge/KOREN_Cloud-0052CC?style=for-the-badge&logo=cloud&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=white)

### **Tools & Others**
![IntelliJ](https://img.shields.io/badge/IntelliJ-000000?style=for-the-badge&logo=intellij-idea&logoColor=white)
![DataGrip](https://img.shields.io/badge/DataGrip-000000?style=for-the-badge&logo=jetbrains&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
![FCM](https://img.shields.io/badge/FCM-FFCA28?style=for-the-badge&logo=firebase&logoColor=white)

---

## 📆 **업데이트 계획**
✔ **WebSocket 성능 최적화**  
✔ **대규모 사용자 대상 부하 테스트 진행**  
✔ **모니터링 시스템 추가 (Observability 지원)**  
✔ **로그 수집 및 분석 시스템 구축**  
✔ **데이터베이스 인덱싱 최적화 및 응답 속도 개선**
