# Distributed Lottery System for High-Concurrency Marketing Activities

## Project Highlights
- Handles **8k TPS** and **80k QPS** across distributed systems.
- Supports **real-time draws, prize distributions, and activity configurations**.
- Seamlessly integrates with marketing platforms like WeChat Official Accounts.

## System Overview
![image](https://github.com/user-attachments/assets/b3c79b85-3d60-45bd-a160-8768ca09b482)


The distributed lottery system is part of a larger marketing framework, including rebate platforms, coupon systems, and more.
![about-220207-01 drawio](https://github.com/user-attachments/assets/18c47516-d525-4e0a-92a8-7ba5e360aaaa)


### Key Features
- **High Performance**: Handles up to 8k TPS and 80k QPS.
- **Scalable Design**: Distributed deployment ensures high availability.
- **Flexible Algorithms**: Supports both random and probability-weighted mechanisms.

## Technology Stack
- **Core Technologies**: JDK 1.8, Spring Boot, MyBatis, Dubbo, Redis, MySQL
- **Tools**: ELK, Docker, Otter
- **Architecture**:
  - Domain-Driven Design (DDD)
  - Rich Domain Model
  - Database Sharding

## Distributed System Design
![image](https://github.com/user-attachments/assets/8143a099-45a6-46b4-85ad-167e7e3549b7)

The system comprises six major components:
1. **Lottery**: Core distributed service.
2. **Lottery-API**: Gateway API for H5 and WeChat platforms.
3. **Lottery-Front**: Vue.js-based front-end with a lucky-canvas lottery wheel.
4. **Lottery-ERP**: Back-end operations system for activity management.
5. **DB-Router**: Database sharding and routing component.
6. **Lottery-Test**: Validation and testing system.
![image](https://github.com/user-attachments/assets/de21d45f-a239-4065-9916-826e6c6560d9)


## Getting Started
1. Clone the repository:
   ```bash
   git clone https://github.com/eMatthiola/Lottery.git
