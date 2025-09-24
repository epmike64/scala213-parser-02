package com.flint.compiler.frontend.parse.lex;



import com.flint.compiler.frontend.parse.lex.token.fTokenKind;
import com.flint.compiler.frontend.parse.lex.token.fTokenMap;
import com.flint.compiler.frontend.parse.lex.token.type.fNameValToken;
import com.flint.compiler.frontend.parse.lex.token.type.fNumericToken;
import com.flint.compiler.frontend.parse.lex.token.type.fStringToken;
import com.flint.compiler.frontend.parse.lex.token.type.fToken;
import static com.flint.compiler.frontend.parse.lex.util.LayoutCharacters.*;

public class fTokenizer {

	private final fReader reader;
	private int radix;

	/**
	 * The token's name, set by nextToken().
	 */
	private String tokStrVal;
	private fTokenKind tKnd;

	/**
	 * The position where a lexical error occurred;
	 */
	private int errPos = -1;

	public fTokenizer(fReader reader) {
		this.reader = reader;
	}

	fToken readToken() {
		reader.sp = 0;
		tokStrVal = null;
		radix = 0;

		int pos = 0;
		int endPos = 0;

		wlp:
		while (true) {

			assert reader.sp == 0 && tokStrVal == null && radix == 0;
			pos = reader.bp;

			switch (reader.ch) {
				case EOI: {
					tKnd = fTokenKind.T_EOF;
					break wlp;
				}
				case SP: case TAB: case FF: {
					do {
						reader.scanChar();
					} while (reader.ch == SP || reader.ch == TAB || reader.ch == FF);
					continue;
				}
				case CR: {
					reader.scanChar();
					continue;
				}
				case LF: {
					reader.scanChar();
					tKnd = fTokenKind.T_NL;
					break wlp;
				}
				case 's': {
					if (reader.peekChar() == '"') {
						reader.scanChar();
						scanLiteralString();
						break wlp;
					}
					// fall through
				}
				case 'A': case 'B': case 'C': case 'D': case 'E':
				case 'F': case 'G': case 'H': case 'I': case 'J':
				case 'K': case 'L': case 'M': case 'N': case 'O':
				case 'P': case 'Q': case 'R': case 'S': case 'T':
				case 'U': case 'V': case 'W': case 'X': case 'Y':
				case 'Z':
				case 'a': case 'b': case 'c': case 'd': case 'e':
				case 'f': case 'g': case 'h': case 'i': case 'j':
				case 'k': case 'l': case 'm': case 'n': case 'o':
				case 'p': case 'q': case 'r': case 't':
				case 'u': case 'v': case 'w': case 'x': case 'y':
				case 'z':
				case '$': case '_': {
					scanIdent(pos);
					break wlp;
				}
				case '0': {
					reader.scanChar();
					if (reader.ch == 'x' || reader.ch == 'X') {
						reader.scanChar();
						scanNumber(pos, 16);

					} else if (reader.ch == 'b' || reader.ch == 'B') {
						reader.scanChar();
						scanNumber(pos, 2);

					} else {

						reader.putChar('0');
						scanNumber(pos, 8);
					}
					break wlp;
				}

				case '1': case '2': case '3': case '4':
				case '5': case '6': case '7': case '8': case '9': {
					scanNumber(pos, 10);
					break wlp;
				}

				case '.': {
					reader.scanChar();
					if (reader.digit(10) >= 0) {
						reader.putChar('.');
						scanFractionAndSuffix(pos);
					} else {
						tKnd = fTokenKind.T_DOT;
					}
					break wlp;
				}
				case ',': {
					reader.scanChar();
					tKnd = fTokenKind.T_COMMA;
					break wlp;
				}
				case ';': {
					reader.scanChar();
					tKnd = fTokenKind.T_SEMICOLON;
					break wlp;
				}
				case '(': {
					reader.scanChar();
					tKnd = fTokenKind.T_LPAREN;
					break wlp;
				}
				case ')': {
					reader.scanChar();
					tKnd = fTokenKind.T_RPAREN;
					break wlp;
				}
				case '[': {
					reader.scanChar();
					tKnd = fTokenKind.T_LBRACKET;
					break wlp;
				}
				case ']': {
					reader.scanChar();
					tKnd = fTokenKind.T_RBRACKET;
					break wlp;
				}
				case '{': {
					reader.scanChar();
					tKnd = fTokenKind.T_LCURL;
					break wlp;
				}
				case '}': {
					reader.scanChar();
					tKnd = fTokenKind.T_RCURL;
					break wlp;
				}

				case '/': {
					if (reader.peekChar() == '/') {
						do {
							reader.scanChar();
						} while (reader.ch != CR && reader.ch != LF && reader.bp < reader.buflen);
						continue wlp;
					}

					if (reader.peekChar() == '*') {
						reader.skipChar();
						reader.scanChar();
						while (reader.bp < reader.buflen) {
							if (reader.ch == '*') {
								reader.scanChar();
								if (reader.ch == '/') break;
							} else {
								reader.scanChar();
							}
						}
						if (reader.ch == '/') {
							reader.scanChar();
							continue wlp;
						}

						lexError(pos, "unclosed.comment");
					}

					scanIdent(pos);
					break wlp;
				}
				case '\'': {
					reader.scanChar();
					if (reader.ch == '\'') {
						lexError(pos, "empty.char.lit");
						reader.scanChar();
					} else {
						if (reader.ch == CR || reader.ch == LF)
							lexError(pos, "illegal.line.end.in.char.lit");
						scanLitChar();
						if (reader.ch == '\'') {
							reader.scanChar();
							tKnd = fTokenKind.T_CHAR_LIT;
						} else {
							lexError(pos, "unclosed.char.lit");
						}
					}
					break wlp;
				}
				case '\"': {
					scanLiteralString();
					break wlp;
				}
				default: {

					if (isOpChar(reader.ch)) {
						scanIdent(pos);
						break wlp;
					}

					throw new RuntimeException("illegal.char");
				}
			}
		}
		endPos = reader.bp;
		switch (tKnd.tokTag) {
			case OPERATOR: case KWRD: case INTERN: return new fToken(tKnd, pos, endPos, fToken._undef);
			case NAME_VAL: return new fNameValToken(tKnd, pos, endPos, tokStrVal);
			case STRING: return new fStringToken(tKnd, pos, endPos, tokStrVal);
			case NUMERIC: return new fNumericToken(tKnd, pos, endPos, tokStrVal, radix);
			default: throw new AssertionError();
		}
	}

	private void scanLiteralString() {
		assert reader.ch == '\"';
		reader.scanChar();
		while (reader.ch != '\"' && reader.ch != CR && reader.ch != LF && reader.bp < reader.buflen)
			scanLitChar();
		if (reader.ch == '\"') {
			tKnd = fTokenKind.T_STRING_LIT;
			reader.scanChar();
			tokStrVal = reader.tokStrValue();
		} else {
			lexError(-1, "unclosed.str.lit");
		}
	}

	private void scanLitChar() {
		if (reader.ch == '\\') {
			if (reader.peekChar() == '\\' && !reader.isUnicode()) {
				reader.skipChar();
				reader.putChar('\\', true);
			} else {
				reader.scanChar();
				switch (reader.ch) {
					case '0': case '1': case '2': case '3':
					case '4': case '5': case '6': case '7':
						char leadch = reader.ch;
						int oct = reader.digit(8);
						reader.scanChar();
						if ('0' <= reader.ch && reader.ch <= '7') {
							oct = oct * 8 + reader.digit( 8);
							reader.scanChar();
							if (leadch <= '3' && '0' <= reader.ch && reader.ch <= '7') {
								oct = oct * 8 + reader.digit( 8);
								reader.scanChar();
							}
						}
						reader.putChar((char)oct);
						break;
					case 'b':
						reader.putChar('\b', true); break;
					case 't':
						reader.putChar('\t', true); break;
					case 'n':
						reader.putChar('\n', true); break;
					case 'f':
						reader.putChar('\f', true); break;
					case 'r':
						reader.putChar('\r', true); break;
					case '\'':
						reader.putChar('\'', true); break;
					case '\"':
						reader.putChar('\"', true); break;
					case '\\':
						reader.putChar('\\', true); break;
					default:
						lexError(reader.bp, "illegal.esc.char");
				}
			}
		} else if (reader.bp != reader.buflen) {
			reader.putChar(true);
		}
	}

	private boolean isOpChar(char ch) {
		switch (ch) {
			case '!': case '#': case '%': case '&': case '*': case '+': case '-': case '/': case ':':
			case '<': case '=': case '>': case '?': case '@': case '\\': case '^': case '|': case '~':
				return true;
			default:
				return false;
		}
	}

	private void scanIdent(int pos) {

		boolean seenOpChar = false;
		if (isOpChar(reader.ch)) {
			seenOpChar = true;
		}

		loop:
		while (true) {
			char prev = reader.ch;
			reader.putChar(true);

			switch (reader.ch) {
				case 'A': case 'B': case 'C': case 'D': case 'E':
				case 'F': case 'G': case 'H': case 'I': case 'J':
				case 'K': case 'L': case 'M': case 'N': case 'O':
				case 'P': case 'Q': case 'R': case 'S': case 'T':
				case 'U': case 'V': case 'W': case 'X': case 'Y':
				case 'Z': case '$':
				case 'a': case 'b': case 'c': case 'd': case 'e':
				case 'f': case 'g': case 'h': case 'i': case 'j':
				case 'k': case 'l': case 'm': case 'n': case 'o':
				case 'p': case 'q': case 'r': case 's': case 't':
				case 'u': case 'v': case 'w': case 'x': case 'y':
				case 'z': case '_':
				case '0': case '1': case '2': case '3': case '4':
				case '5': case '6': case '7': case '8': case '9':
					if (seenOpChar) {
						break loop;
					}
					continue;

				default:
					if (isOpChar(reader.ch)) {
						if (prev == '_') {
							seenOpChar = true;
							continue;
						} else if (seenOpChar) {
							continue;
						}
					}
					break loop;
			}
		}

		tokStrVal = reader.tokStrValue();
		tKnd = fTokenMap.lookupKind(tokStrVal);
	}

	private void scanDigits(int pos, int digitRadix) {
		do {
			reader.putChar(true);
		} while (reader.digit(digitRadix) >= 0);
	}

	/**
	 * Read fractional part of hexadecimal floating point number.
	 */
	private void scanHexExponentAndSuffix(int pos) {
		if (reader.ch == 'p' || reader.ch == 'P') {
			reader.putChar(true);

			if (reader.ch == '+' || reader.ch == '-') {
				reader.putChar(true);
			}

			if (reader.digit(10) >= 0) {
				scanDigits(pos, 10);
//				if (!hexFloatsWork)
				lexError(pos, "unsupported.cross.fp.lit");
			} else
				lexError(pos, "malformed.fp.lit");
		} else {
			lexError(pos, "malformed.fp.lit");
		}
		if (reader.ch == 'f' || reader.ch == 'F') {
			reader.putChar(true);
			tKnd = fTokenKind.T_FLOAT_LIT;
			radix = 16;
		} else {
			if (reader.ch == 'd' || reader.ch == 'D') {
				reader.putChar(true);
			}
			tKnd = fTokenKind.T_FLOAT_LIT;
			radix = 16;
		}
	}

	/**
	 * Read fractional part of floating point number.
	 */
	private void scanFraction(int pos) {

		if (reader.digit(10) >= 0) {
			scanDigits(pos, 10);
		}
		int sp1 = reader.sp;
		if (reader.ch == 'e' || reader.ch == 'E') {
			reader.putChar(true);

			if (reader.ch == '+' || reader.ch == '-') {
				reader.putChar(true);
			}

			if (reader.digit(10) >= 0) {
				scanDigits(pos, 10);
				return;
			}
			lexError(pos, "malformed.fp.lit");
			reader.sp = sp1;
		}
	}

	/**
	 * Read fractional part and 'd' or 'f' suffix of floating point number.
	 */
	private void scanFractionAndSuffix(int pos) {
		radix = 10;
		scanFraction(pos);
		if (reader.ch == 'f' || reader.ch == 'F') {
			reader.putChar(true);
			tKnd = fTokenKind.T_FLOAT_LIT;
		} else {
			if (reader.ch == 'd' || reader.ch == 'D') {
				reader.putChar(true);
			}
			tKnd = fTokenKind.T_FLOAT_LIT;
		}
	}

	/**
	 * Read fractional part and 'd' or 'f' suffix of floating point number.
	 */
	private void scanHexFractionAndSuffix(int pos, boolean seendigit) {
		radix = 16;
		assert (reader.ch == '.');
		reader.putChar(true);

		if (reader.digit(16) >= 0) {
			seendigit = true;
			scanDigits(pos, 16);
		}
		if (!seendigit)
			lexError(pos, "invalid.hex.number");
		else
			scanHexExponentAndSuffix(pos);
	}


	/**
	 * Read a number.
	 *
	 * @param radix The radix of the number; one of 2, 8, 10, 16.
	 */
	private void scanNumber(int pos, int radix) {
		// for octal, allow base-10 digit in case it's a float literal
		this.radix = radix;
		int digitRadix = (radix == 8 ? 10 : radix); //2, 10, 16
		int firstDigit = reader.digit(Math.max(10, digitRadix)/*10, 16*/);
		boolean seendigit = firstDigit >= 0;
		boolean seenValidDigit = firstDigit >= 0 && firstDigit < digitRadix;
		if (seendigit) {
			scanDigits(pos, digitRadix);
		}
		if (seendigit && radix == 16 && (reader.ch == 'p' || reader.ch == 'P')) {
			scanHexExponentAndSuffix(pos);
		} else if (digitRadix == 10 && reader.ch == '.') {
			char p = reader.peekChar();
			if ('0' <= p && p <= '9') {
				reader.putChar(true);
				scanFractionAndSuffix(pos);
			} else {
				tokStrVal = reader.tokStrValue();
				tKnd = fTokenKind.T_INT_LIT;
			}
		} else if (digitRadix == 10 &&
				(reader.ch == 'e' || reader.ch == 'E' ||
						reader.ch == 'f' || reader.ch == 'F' ||
						reader.ch == 'd' || reader.ch == 'D')) {
			scanFractionAndSuffix(pos);
		} else {
			if (!seenValidDigit) {
				switch (radix) {
					case 2:
						lexError(pos, "invalid.binary.number");

					case 16:
						lexError(pos, "invalid.hex.number");

				}
			}
			tokStrVal = reader.tokStrValue();
			if (reader.ch == 'l' || reader.ch == 'L') {
				reader.scanChar();
				tKnd = fTokenKind.T_INT_LIT;
			} else {
				tKnd = fTokenKind.T_INT_LIT;
			}
		}
	}

	/**
	 * Report an error at the given position using the provided arguments.
	 */
	protected void lexError(int pos, String key, Object... args) throws RuntimeException {
		tKnd = fTokenKind.T_ERROR;
		errPos = pos;
		throw new RuntimeException("pos: " + pos + ",m key=" + key);
	}
}
