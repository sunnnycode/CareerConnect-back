plugins {
	java
	id("org.springframework.boot") version "3.3.3"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "com.restapi"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// Spring Boot starters
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-web-services")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-validation") {
		version {
			strictly("3.3.2")
		}
	}
	implementation("org.springframework.boot:spring-boot-starter-websocket")

	// Lombok
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	// Development tools
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	// Database connectors and tools
	runtimeOnly("com.mysql:mysql-connector-j")
	runtimeOnly("com.h2database:h2")
	runtimeOnly("org.mariadb.jdbc:mariadb-java-client:3.1.4")
	implementation("org.hibernate:hibernate-entitymanager:6.0.0.Alpha7")
	implementation("org.flywaydb:flyway-core:10.17.2")
	implementation("org.flywaydb:flyway-mysql")

	// Logging
	implementation("org.bgee.log4jdbc-log4j2:log4jdbc-log4j2-jdbc4.1:1.16")

	// API documentation
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")

	// Object mapping
	implementation("org.modelmapper:modelmapper:3.2.0")

	// JWT (JSON Web Token)
	implementation("io.jsonwebtoken:jjwt-api:0.12.6")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")

	// Kafka
	implementation("org.springframework.kafka:spring-kafka")
	implementation("org.springframework.kafka:spring-kafka-streams")
	testImplementation("org.springframework.kafka:spring-kafka-test")

	// JSON serialization/deserialization
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	// Testing
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
