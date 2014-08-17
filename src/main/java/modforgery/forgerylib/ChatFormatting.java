/**
* @author roboyobo
*/
package modforgery.forgerylib;

public class ChatFormatting {
	
	/**
	 * @param number The number you wish to superscript
	 * @return The superscripted equivalent of the parameter
	 */
	public static String getSuperscriptedNumber(int number) {
		return String.valueOf(Character.toChars(Integer.parseInt("207" + number, 16)));
	}
	
	/**
	 * @param number The number you wish to subscript
	 * @return The subscripted equivalent of the parameter
	 */
	public static String getSubscriptedNumber(int number) {
		return String.valueOf(Character.toChars(Integer.parseInt("208" + number, 16)));
	}
}
