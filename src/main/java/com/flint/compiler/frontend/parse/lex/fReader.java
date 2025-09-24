package com.flint.compiler.frontend.parse.lex;


import com.flint.compiler.frontend.parse.lex.util.ArrayUtils;
import java.util.Arrays;
import static com.flint.compiler.frontend.parse.lex.util.LayoutCharacters.EOI;


public class fReader {
	protected char[] buf;
	protected int bp;
	protected int unicodeConversionBp = -1;
	protected final int buflen;
	protected char ch;
	protected char[] sbuf = new char[128];
	protected int sp;

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
		buflen = inputLength;
		buf[buflen] = EOI;
		bp = -1;
		scanChar();
	}

	protected void scanChar() {
		if (bp < buflen) {
			ch = buf[++bp];
			if (ch == '\\') {
				convertUnicode();
			}
		}
	}

	protected void putChar(char ch, boolean scan)  {
		sbuf = ArrayUtils.ensureCapacity(sbuf, sp);
		sbuf[sp++] = ch;
		if (scan)
			scanChar();
	}

	protected void putChar(char ch)  {
		putChar(ch, false);
	}

	protected void putChar(boolean scan)  {
		putChar(ch, scan);
	}


	String tokStrValue() {
		return new String(sbuf, 0, sp);
	}

	protected int digit(int base) {
		char c = ch;
		if ('0' <= c && c <= '9')
			return Character.digit(ch, base); //a fast common case
		return -1;
	}

	protected char peekChar() {
		return buf[bp + 1];
	}

	protected boolean isUnicode() {
		return unicodeConversionBp == bp;
	}
	protected void skipChar() {
		bp++;
	}

	protected void convertUnicode()  {
		if (ch == '\\' && unicodeConversionBp != bp) {
			bp++; ch = buf[bp];
			if (ch == 'u') {
				do {
					bp++; ch = buf[bp];
				} while (ch == 'u');
				int limit = bp + 3;
				if (limit < buflen) {
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
}
