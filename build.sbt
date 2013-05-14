import AssemblyKeys._

seq(assemblySettings: _*)

name := "sudoku"

version := "0.1"

scalaVersion := "2.10.1"

scalacOptions ++= Seq("-unchecked", "-deprecation" )

mainClass in assembly := Some("com.sudoku.TODO")

jarName in assembly := "sudoku.jar"

libraryDependencies ++= Seq(
   "org.piccolo2d" % "piccolo2d-core" % "1.3.1"
  ,"org.piccolo2d" % "piccolo2d-extras" % "1.3.1"
  ,"org.piccolo2d" % "piccolo2d-parent" % "1.3.1"
  ,"org.scalatest" %% "scalatest" % "1.9.1" % "test"
  ,"com.novocode" % "junit-interface" % "0.10-M4" % "test"
  ,"junit" % "junit" % "3.8.2" % "test"
)


initialCommands in console := """import com.exproxy._"""

sourceGenerators in Compile <+= 
 (sourceManaged in Compile, version, name, jarName in assembly) map {
  (dir, version, projectname, jarexe) =>
  val file = dir / "com" / "sudoku" / "MetaInfo.scala"
  IO.write(file,
  """package com.sudoku
    |object MetaInfo { 
    |  val version="%s"
    |  val project="%s"
    |  val jarbasename="%s"
    |}
    |""".stripMargin.format(version, projectname, jarexe.split("[.]").head) )
  Seq(file)
}

