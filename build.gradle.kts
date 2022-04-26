plugins {
  id("java")
}

val YOUR_NAME = "Your_Name"

repositories {
  mavenCentral()
}

dependencies {
  // https://mvnrepository.com/artifact/org.apache.commons/commons-csv
  implementation("org.apache.commons:commons-csv:1.9.0")
  // https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
  implementation("org.apache.commons:commons-lang3:3.12.0")

  // https://mvnrepository.com/artifact/com.google.auto.value/auto-value
  annotationProcessor("com.google.auto.value:auto-value:1.9")
  // https://mvnrepository.com/artifact/com.google.auto.value/auto-value-annotations
  implementation("com.google.auto.value:auto-value-annotations:1.9")


  implementation("info.picocli:picocli:4.6.3")
  implementation("org.junit.jupiter:junit-jupiter:5.8.2")
  annotationProcessor("info.picocli:picocli-codegen:4.6.3")
}

val dataDir = project.projectDir.resolve("data").toString()

val downloadTask by tasks.registering(JavaExec::class) {
  classpath = sourceSets.main.get().runtimeClasspath

  mainClass.set("csc22100.assignment6.AssignmentMain")

  args = listOf("download", "--output-dir", dataDir)
}