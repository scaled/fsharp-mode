//
// Scaled F# Mode - a Scaled major mode for editing F# code
// http://github.com/scaled/fsharp-mode/blob/master/LICENSE

package scaled.fsharp

import scaled._
import scaled.code.{Commenter, BlockIndenter}
import scaled.grammar.GrammarCodeMode

@Major(name="fsharp",
       tags=Array("code", "project", "fsharp"),
       pats=Array(".*\\.fs"),
       desc="A major editing mode for the F# language.")
class FSharpMode (env :Env) extends GrammarCodeMode(env) {

  override def langScope = "source.fsharp"

  override protected def createIndenter = new BlockIndenter(config, Std.seq(
    // bump extends/implements in two indentation levels
    BlockIndenter.adjustIndentWhenMatchStart(Matcher.regexp("(extends|implements)\\b"), 2),
    // align changed method calls under their dot
    new BlockIndenter.AlignUnderDotRule(),
    // handle indenting switch statements properly
    new BlockIndenter.SwitchRule(),
    // handle continued statements, with some special sauce for : after case
    new BlockIndenter.CLikeContStmtRule()
  ))

  override val commenter = new Commenter() {
    override def linePrefix  = "//"
    override def blockOpen   = "/*"
    override def blockPrefix = "*"
    override def blockClose  = "*/"
    override def docOpen     = "///"
    override def docPrefix   = "///"
  }

  // TODO: more things!
}
