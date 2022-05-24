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
// https://mvnrepository.com/artifact/com.google.guava/guava
  implementation("com.google.guava:guava:31.1-jre")


  implementation("info.picocli:picocli:4.6.3")
  annotationProcessor("info.picocli:picocli-codegen:4.6.3")

  testImplementation(platform("org.junit:junit-bom:5.8.2"))
  testImplementation("org.junit.jupiter:junit-jupiter-api")
  testImplementation("org.assertj:assertj-core:3.22.0")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.test {
  useJUnitPlatform()
  testLogging {
    showStandardStreams=true
  }
}

val dataDir = project.projectDir.resolve("data").toString()

val downloadTask by tasks.registering(JavaExec::class) {
  classpath = sourceSets.main.get().runtimeClasspath

  mainClass.set("csc22100.assignment6.AssignmentMain")

  args = listOf("download", "--output-dir", dataDir)
}

tasks.register<Zip>("packageAssignment") {
  archiveFileName.set("${project.name}-$YOUR_NAME.zip")
  from(layout.projectDirectory) {
    include("*.gradle.kts")
    include("src/**")
    include("gradle/**")
    include("gradlew")
  }

  outputs.upToDateWhen { false }

  finalizedBy(tasks.getByPath("copyZip"))
}

tasks.register<Copy>("copyZip") {
  from(tasks.getByName<Zip>("packageAssignment").outputs)

  destinationDir = File(rootProject.projectDir.toString())
}

tasks.register<JavaExec>("source2pdf") {
  classpath = files("${rootProject.projectDir}/source2pdf-all.jar")
  args = listOf("src", "-o", "${rootProject.projectDir}/$YOUR_NAME.pdf")
}
