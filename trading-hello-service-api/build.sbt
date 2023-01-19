// Nexus configuration
credentials += Credentials(Path.userHome / ".sbt" / ".credentials_sml_nexus")

publishTo := {
  val nexus = "https://nexus3.softwaremill.com/repository/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "scala-academy-snapshots/")
  else
    Some("releases"  at nexus + "scala-academy-releases/")
}

resolvers ++= Seq(
  "SoftwareMill Snapshots" at "https://nexus3.softwaremill.com/repository/scala-academy-snapshots/",
  "SoftwareMill Releases" at "https://nexus3.softwaremill.com/repository/scala-academy-releases/"
)
