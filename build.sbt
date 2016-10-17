
enablePlugins(ScalaJSPlugin)

name := "PGM Tutorial"

scalaVersion := "2.11.7"

scalaJSUseRhino in Global := false

resolvers += "Sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

libraryDependencies ++= Seq(
  "com.github.yoeluk" %%% "paper-scala-js" % "0.5-SNAPSHOT",
  "com.lihaoyi" %%% "scalarx" % "0.2.8"
//  "org.webjars" % "paperjs" % "0.2"
)

jsDependencies ++= Seq(
  "org.webjars" % "paperjs" % "0.9.22" / "paper-full.min.js" commonJSName "paper"
)

persistLauncher in Compile := false

persistLauncher in Test := false

skip in packageJSDependencies := false

