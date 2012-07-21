import sbt._
import Keys._

object PPowBuild extends Build {

	val main = Project(id = "p-pow", base = file(".")).settings(
		version := "0.1-SNAPSHOT",
		organization := "guillaume.bort",

		sbtPlugin := true,

		publishTo := Some(Resolver.file("file", file(Option(System.getProperty("repository.path")).getOrElse("/tmp"))))
	)

}