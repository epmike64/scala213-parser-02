package com.flint.compiler.frontend.parse.lex;


import com.flint.compiler.frontend.parse.lex.util.ArrayUtils;
import java.util.Arrays;
import static com.flint.compiler.frontend.parse.lex.util.LayoutCharacters.EOI;


public class fReader {
	private final char[] buf;
	private int bp;
	private int unicodeConversionBp = -1;
	private final int bufLen;
	private char ch;
	private char[] sbuf = new char[128];
	private int sp;

	public fReader(char[] input, int inputLength)  {
		assert input != null && inputLength <= input.length;
		if (inputLength == input.length) {
			if (input.length > 0 && Character.isWhitespace(input[input.length - 1])) {
				inputLength--;
			} else {
				input = Arrays.copyOf(input, inputLength + 1);
			}
		}
		buf = input;
		bufLen = inputLength;
		buf[bufLen] = EOI;
		bp = -1;
		scanChar();
	}

	 void scanChar() {
		if (bp < bufLen) {
			ch = buf[++bp];
			if (ch == '\\') {
				convertUnicode();
			}
		}
	}

	 void putChar(char ch, boolean scan)  {
		sbuf = ArrayUtils.ensureCapacity(sbuf, sp);
		sbuf[sp++] = ch;
		if (scan)
			scanChar();
	}

	 void putChar(char ch)  {
		putChar(ch, false);
	}

	 void putChar(boolean scan)  {
		putChar(ch, scan);
	}


	String tokStrValue() {
		return new String(sbuf, 0, sp);
	}

	 int digit(int base) {
		char c = ch;
		if ('0' <= c && c <= '9')
			return Character.digit(ch, base); //a fast common case
		return -1;
	}

	 char peekChar() {
		return buf[bp + 1];
	}

	 boolean isUnicode() {
		return unicodeConversionBp == bp;
	}
	 void skipChar() {
		bp++;
	}

	 void convertUnicode()  {
		if (ch == '\\' && unicodeConversionBp != bp) {
			bp++; ch = buf[bp];
			if (ch == 'u') {
				do {
					bp++; ch = buf[bp];
				} while (ch == 'u');
				int limit = bp + 3;
				if (limit < bufLen) {
					int d = digit(16);
					int code = d;
					while (bp < limit && d >= 0) {
						bp++; ch = buf[bp];
						d = digit(16);
						code = (code << 4) + d;
					}
					if (d >= 0) {
						ch = (char)code;
						unicodeConversionBp = bp;
						return;
					}
				}
				throw new RuntimeException("illegal.unicode.esc");
			} else {
				bp--;
				ch = '\\';
			}
		}
	}

	int bp() {
		return bp;
	}

	int sp() {
		return sp;
	}

	void setSp(int v) {
		sp = v;
	}

	char ch() {
		return ch;
	}

	int bufLen() {
		return bufLen;
	}
}
