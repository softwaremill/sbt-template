// Nexus configuration
publishTo := {
  val nexus = "https://nexus3.softwaremill.com/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "repository/scala-academy-snapshots/")
  else
    Some("releases"  at nexus + "repository/scala-academy-release/")
}

resolvers ++= Seq(
  "SoftwareMill Snapshots" at "https://nexus3.softwaremill.com/repository/scala-academy-snapshots/",
  "SoftwareMill Releases" at "https://nexus3.softwaremill.com/repository/scala-academy-release/"
)
