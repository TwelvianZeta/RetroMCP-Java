package org.mcphackers.mcp.tools.source;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class MathConstants extends Source {

	// Used to prevent strings from being captured, such as "2.0D"
	private static final Pattern CONSTANT_REGEX = Pattern.compile("(?<![a-zA-Z\"'])-?\\d+(?:\\.\\d+[fFdD]?|)(?![a-zA-Z\"'])");
	private static final Map<String, String> CONSTANTS = new HashMap<>();

	static {
		for (int i = 1; i <= 100; i++) {
			double d = i * 0.01D;
			if(d != (float)d) { // if imprecise
				floatCastedToDouble((float)d);
			}
		}
		floatCastedToDouble(0.0075F);
		floatCastedToDouble(0.999F);
		floatCastedToDouble(0.997F);
		floatCastedToDouble(1.62F);
		replaceValue(Math.PI, "Math.PI");
		replaceValue((float)Math.PI, "(float)Math.PI");
		replaceValue((float)Math.PI / 2F, "(float)Math.PI / 2F");
		replaceValue((float)Math.PI / 4.5F, "(float)Math.PI / 4.5F");
		replaceValue((double)(float)Math.PI, "(double)(float)Math.PI");
		replaceValue(Math.PI * 2D, "Math.PI * 2D");
		replaceValue(Math.PI / 2D, "Math.PI / 2D");
		replaceValue(0xFFFFFF, "0xFFFFFF");
		replaceValue(0x20200000, "0x20200000");
		replaceValue(0x20400000, "0x20400000");
		replaceValue(0xFF000000, "0xFF000000");
		replaceValue(1.0D / 256D, "1.0D / 256D");
		replaceValue(2.0D / 256D, "2.0D / 256D");
		replaceValue(6.0D / 256D, "6.0D / 256D");
		replaceValue(7.0D / 256D, "7.0D / 256D");
		replaceValue(8.0D / 256D, "8.0D / 256D");
		replaceValue(9.0D / 256D, "9.0D / 256D");
	}

	@Override
	public void apply(String className, StringBuilder source) {
		replaceTextOfMatchGroup(source, CONSTANT_REGEX, match1 -> {
			String constant = match1.group(0);
			return CONSTANTS.getOrDefault(constant, constant);
		});
	}

	private static String floatCastedToDouble(float value) {
		return CONSTANTS.put((double)value + "D", "(double)" + value + "F");
	}

	private static String replaceValue(double value, String replace) {
		return CONSTANTS.put(value + "D", replace);
	}

	private static String replaceValue(float value, String replace) {
		return CONSTANTS.put(value + "F", replace);
	}

	private static String replaceValue(int value, String replace) {
		return CONSTANTS.put(String.valueOf(value), replace);
	}

	@SuppressWarnings("unused")
	private static String replaceValue(long value, String replace) {
		return CONSTANTS.put(value + "L", replace);
	}
}
