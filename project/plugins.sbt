val sbtSoftwareMillVersion = "2.0.12"
val sbtFs2GrpcVersion      = "2.5.10"

addSbtPlugin("com.softwaremill.sbt-softwaremill" % "sbt-softwaremill-common" % sbtSoftwareMillVersion)
addSbtPlugin("org.typelevel"                     % "sbt-fs2-grpc"            % sbtFs2GrpcVersion)
addSbtPlugin("com.github.sbt"                    % "sbt-native-packager"     % "1.9.13")
addSbtPlugin("io.github.davidgregory084"         % "sbt-tpolecat"            % "0.4.2")
addSbtPlugin("com.github.sbt"                    % "sbt-release"             % "1.1.0")
