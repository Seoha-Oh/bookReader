# Build Stage
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copy pom.xml 먼저 복사
COPY pom.xml /app/

# 의존성 캐싱을 위한 단계
RUN mvn dependency:go-offline -B

# 이후 소스 파일 복사
COPY . /app/

# 빌드 실행
RUN mvn clean package -DskipTests

# Run Stage
FROM openjdk:17-jdk-slim
WORKDIR /app

# Build 단계에서 생성된 JAR 파일 복사
COPY --from=build /app/target/*.jar app.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
