//
// Scaled F# Mode - a Scaled major mode for editing F# code
// http://github.com/scaled/fsharp-mode/blob/master/LICENSE

package scaled.fsharp

import scaled._
import scaled.code.{Commenter, Indenter}
import scaled.grammar.GrammarCodeMode

@Major(name="fsharp",
       tags=Array("code", "project", "fsharp"),
       pats=Array(".*\\.fs"),
       desc="A major editing mode for the F# language.")
class FSharpMode (env :Env) extends GrammarCodeMode(env) {

  override def langScope = "source.fsharp"

  override protected def createIndenter = new Indenter(config) {
    import Indenter._
    override def apply (info :Info) = if (info.row == 0) 0 else {
      val prev = info.buffer.line(info.row-1)
      val prevFirst = prev.firstNonWS
      // if prev line ends with '=' or '->', indent one step
      if (endsWith(prev, equalsM) || endsWith(prev, arrowM)) prevFirst + indentWidth
      // indent match cases under 'function' by one step
      else if (endsWith(prev, functionM)) prevFirst + indentWidth
      // indent 'match x when' cases under 'match'
      else if (endsWith(prev, withM) && prev.indexOf(matchM) >= 0) prev.indexOf(matchM)
      // if previous line is blank, leave indentation alone
      else if (prevFirst == prev.length) info.first
      // otherwise indent to same level as previous line
      else prevFirst
    }

    private val equalsM = Matcher.exact("=")
    private val arrowM = Matcher.exact("->")
    private val matchM = Matcher.regexp("\\bmatch\\b")
    private val withM = Matcher.regexp("\\bwith\\b")
    private val functionM = Matcher.regexp("\\bfunction\\b")
  }

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
