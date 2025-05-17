

package com.flint.compiler.frontend.parse.lex.util;

public interface LayoutCharacters {

    /** Tabulator column increment.
     */
    final static int TabInc = 8;

    /** Standard indentation for subdiagnostics
     */
    final static int DiagInc = 4;

    /** Standard indentation for additional diagnostic lines
     */
    final static int DetailsInc = 2;

    char SP    = 0x20;
    /** Tabulator character.
     */
    char TAB   = 0x9;

    /** Line feed character.
     */
    char LF    = 0xA;

    /** Form feed character.
     */
    char FF    = 0xC;

    /** Carriage return character.
     */
    char CR    = 0xD;

    /** End of input character.  Used as a sentinel to denote the
     *  character one beyond the last defined character in a
     *  source file.
     */
    char EOI   = 0x1A;
}
