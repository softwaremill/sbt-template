val sbtSoftwareMillVersion = "2.0.12"
val sbtFs2GrpcVersion = "2.5.5"

addSbtPlugin("com.softwaremill.sbt-softwaremill" % "sbt-softwaremill-common" % sbtSoftwareMillVersion)
addSbtPlugin("org.typelevel" % "sbt-fs2-grpc" % sbtFs2GrpcVersion)
