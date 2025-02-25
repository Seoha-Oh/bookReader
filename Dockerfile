# Build Stage
FROM gradle:7.6-jdk17 AS build
WORKDIR /app

# Gradle Wrapper 파일 복사 및 실행 권한 부여
COPY gradlew /app/gradlew
RUN chmod +x /app/gradlew

# Gradle 관련 설정 파일 복사
COPY gradle /app/gradle
COPY build.gradle /app/build.gradle
COPY settings.gradle /app/settings.gradle

# 의존성 캐시 다운로드
RUN ./gradlew dependencies --no-daemon

# 소스 파일 복사 및 빌드
COPY . /app
RUN ./gradlew clean build -x test --no-daemon

# Run Stage
FROM openjdk:17-jdk-slim
WORKDIR /app

# 빌드된 JAR 파일 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
