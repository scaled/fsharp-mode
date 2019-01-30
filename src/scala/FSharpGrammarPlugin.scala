//
// Scaled F# Mode - a Scaled major mode for editing F# code
// http://github.com/scaled/fsharp-mode/blob/master/LICENSE

package scaled.fsharp

import scaled._
import scaled.code.CodeConfig
import scaled.grammar._

@Plugin(tag="textmate-grammar")
class FSharpGrammarPlugin extends GrammarPlugin {
  import CodeConfig._

  override def grammars = Map("source.fsharp" -> "FSharp.ndf")

  override def effacers = List(
    effacer("comment.line", commentStyle),
    effacer("comment.block", docStyle),
    effacer("constant", constantStyle),
    effacer("invalid", invalidStyle),
    effacer("string", stringStyle),
    effacer("keyword", keywordStyle),

    effacer("entity.name.type", typeStyle),
    effacer("entity.name.function", functionStyle),
    effacer("entity.name.section", moduleStyle),
    effacer("entity.name.val-declaration", variableStyle),

    effacer("meta.method.annotation", preprocessorStyle),
    effacer("meta.method-call", functionStyle),

    effacer("storage.modifier", keywordStyle),
    effacer("storage.type", typeStyle),

    effacer("support.other.module", moduleStyle),
    effacer("variable.import", typeStyle),
    effacer("variable.language", constantStyle),
    effacer("variable.parameter", variableStyle)
  )

  override def syntaxers = List(
    syntaxer("comment.line", Syntax.LineComment),
    syntaxer("comment.block", Syntax.DocComment),
    syntaxer("constant", Syntax.OtherLiteral),
    syntaxer("string.quoted.double", Syntax.StringLiteral)
  )
}
