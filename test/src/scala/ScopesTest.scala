//
// Scaled FSharp Mode - support for editing FSharp code
// https://github.com/scaled/fsharp-mode/blob/master/LICENSE

package scaled.fsharp

import org.junit.Assert._
import org.junit._
import scaled._
import scaled.code._
import scaled.grammar._
import scaled.impl.BufferImpl

class FSharpScopesTest {

  // @Test def dumpGrammar () {
  //   val plugin = new FSharpGrammarPlugin()
  //   plugin.grammar.print(System.out)
  // }

  val testFSharpCode = Seq(
    //                1         2         3         4         5         6         7         8
    //      012345678901234567890123456789012345678901234567890123456789012345678901234567890123456
    /* 0*/ "namespace FSTest",
    /* 1*/ "",
    /* 2*/ "open System",
    /* 3*/ "open System.IO",
    /* 4*/ "",
    /* 5*/ "type private TestType = {",
    /* 6*/ "  sources :FileInfo list",
    /* 7*/ "  options :FSharpProjectionOptions",
    /* 8*/ "  target :FileInfo",
    /* 9*/ "  errors :Diagnostic list",
    /*10*/ "}",
    /*11*/ "",
    /*12*/ "/// Maintains caches of parsed versions of .fsprojOrFsx files",
    /*13*/ "type ProjectManager (checker :FSharpChecker) =",
    /*14*/ "  let knownSolutions = new Dictionary<String, list<FileInfo>>()",
    /*15*/ "  member this.AddWorkspaceRoot(root :DirectoryInfo) :Async<unit> = 0",
    /*--*/ "").mkString("\n")

  val plugin = new FSharpGrammarPlugin()

  @Test def testRecordScopes () {
    // val code = ("type ProjectManager (checker :FSharpChecker) =\n" +
    //             "  let knownSolutions = new Dictionary<String, list<FileInfo>>()")
    val code = ("let foo :List<number> = bar")
    val buffer = BufferImpl(new TextStore("Test.fs", "", code))
    val scoper = Grammar.testScoper(Seq(plugin.grammar("source.fsharp")), buffer,
                                    List(new Selector.Processor(plugin.effacers)))
    scoper.rethinkBuffer()

    val start = 0  ; val end = buffer.lines.length
    start until end foreach { ll =>
      println(buffer.line(ll))
      scoper.showScopes(ll) foreach { s => println(ll + ": " + s) }
    }
  }

  // @Test def testStylesLink () {
  //   val buffer = BufferImpl(new TextStore("Test.fs", "", testFSharpCode))
  //   val scoper = Grammar.testScoper(Seq(plugin.grammar("source.fsharp")), buffer,
  //                                   List(new Selector.Processor(plugin.effacers)))
  //   scoper.rethinkBuffer()

  //   // println(scoper.showMatchers(Set("#code", "#class")))
  //   val start = 0  ; val end = buffer.lines.length
  //   start until end foreach { ll =>
  //     println(buffer.line(ll))
  //     scoper.showScopes(ll) foreach { s => println(ll + ": " + s) }
  //   }

  //   // assertFalse("Whitespace before doc comment not scoped as comment",
  //   //            scoper.scopesAt(Loc(8, 0)).contains("comment.block.documentation.fsharp"))
  //   // assertTrue("Open doc comment is scoped as comment",
  //   //            scoper.scopesAt(Loc(8, 2)).contains("comment.block.documentation.fsharp"))
  //   // assertTrue("Doc comment content is scoped as comment",
  //   //            scoper.scopesAt(Loc(8, 6)).contains("comment.block.documentation.fsharp"))
  // }
}
