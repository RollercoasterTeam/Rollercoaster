package robomuss.rc.util;

public enum ColourUtil {
	WHITE(0xFFFFFF, "white", "White"),
	ORANGE(0xF4B33F, "orange", "Orange"),
	MAGENTA(0xDB7AD5, "magenta", "Magenta"),
	LIGHT_BLUE(0x82ACE7, "light_blue", "Light Blue"),
	YELLOW(0xCEC045, "yellow", "Yellow"),
	LIME(0x83D41C, "lime", "Lime"),
	PINK(0xF7B4D6, "pink", "Pink"),
	DARK_GRAY(0x646464, "dark_gray", "Dark Gray"),
	LIGHT_GRAY(0xD5D5D5, "light_gray", "Light Gray"),
	CYAN(0x3C8EB0, "cyan", "Cyan"),
	PURPLE(0xB064D8, "purple", "Purple"),
	BLUE(0x4D82E4, "blue", "Blue"),
	BROWN(0x976746, "brown", "Brown"),
	GREEN(0x63862E, "green", "Green"),
	RED(0xEB535E, "red", "Red"),
	BLACK(0x323232, "black", "Black");

	private final int color;
	public final String unlocalized_name;
	public final String display_name;
	public static final int numColours = 16;
	public static final ColourUtil[] COLORS = {WHITE, ORANGE, MAGENTA, LIGHT_BLUE, YELLOW, LIME, PINK, DARK_GRAY, LIGHT_GRAY, CYAN, PURPLE, BLUE, BROWN, GREEN, RED, BLACK};
	public static final ColourUtil[] COLORS_INVERSE = {BLACK, RED, GREEN, BROWN, BLUE, PURPLE, CYAN, LIGHT_GRAY, DARK_GRAY, PINK, LIME, YELLOW, LIGHT_BLUE, MAGENTA, ORANGE, WHITE};
	public static final boolean USE_ALT_CODES = false;
	private static final int[] ALTERNATE_CODES = {0xDDDDDD, 0xDB7D3E, 0xB350BC, 0x6B8AC9, 0xB1A627, 0x41AE38, 0xD08499, 0x404040, 0x9AA1A1, 0x2E6E89, 0x7E3DB5, 0x2E388D, 0x4F321F, 0x35461B, 0x963430, 0x191616};

	private ColourUtil(int color, String unlocalized_name, String display_name) {
		this.color = color;
		this.unlocalized_name = unlocalized_name;
		this.display_name = display_name;
	}
}
