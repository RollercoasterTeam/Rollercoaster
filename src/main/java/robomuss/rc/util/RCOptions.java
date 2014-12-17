package robomuss.rc.util;

<<<<<<< HEAD
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import robomuss.rc.client.gui.keybinding.RCKeyBinding;
import java.io.*;
=======
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

>>>>>>> origin/One8PortTake2
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import robomuss.rc.client.gui.keybinding.RCKeyBinding;

@SideOnly(Side.CLIENT)
public class RCOptions {
	private static final Logger logger = LogManager.getLogger();
	private File optionsFile;
	private Minecraft mc;
	private RCKeyBinding[] rcKeyBindings = null;
	public boolean invertMouse;
	public boolean sensitivity;
	public boolean touchscreen;

	public RCOptions(Minecraft mc, File optionsFile) {
		this.optionsFile = optionsFile;
		this.mc = mc;
	}

	public void registerRCKeyBindings(RCKeyBinding[] rcKeyBindings) {
		this.rcKeyBindings = rcKeyBindings;
	}

	public static String getRCKeyDisplayString(int code) {
		return code < 0 ? I18n.format("key.mouseButton", new Object[] {Integer.valueOf(code + 101)}) : Keyboard.getKeyName(code);
	}

	public static boolean isRCKeyDown(RCKeyBinding rcKeyBinding) {
		return rcKeyBinding.getRCKeyCode() == 0 ? false : (rcKeyBinding.getRCKeyCode() < 0 ? Mouse.isButtonDown(rcKeyBinding.getRCKeyCode() + 100) : Keyboard.isKeyDown(rcKeyBinding.getRCKeyCode()));
	}

	public void setRCOptionKeyBinding(RCKeyBinding rcKeyBinding, int code, boolean setEntered) {
		if (setEntered) {
			rcKeyBinding.setRCEnteredCode(code);
		}
		rcKeyBinding.setRCKeyCode(code);
		this.saveRCOptions();
	}

//	public void setOptionValue(RCOptionsEnum rcOptionsEnum, int value) {
////		if (rcOptionsEnum == RCOptionsEnum.INVERT_MOUSE) {
////			this.invertMouse = !this.invertMouse;
////		}
//	}

	public void saveRCOptions() {
		if (FMLClientHandler.instance().isLoading() || rcKeyBindings == null) return;
		try {
			PrintWriter printWriter = new PrintWriter(new FileWriter(this.optionsFile));
			for (RCKeyBinding rcKeyBinding : rcKeyBindings) {
				printWriter.printf("%s:%d:%d", rcKeyBinding.getRCKeyDescription(), rcKeyBinding.getRCKeyCode(), rcKeyBinding.getRCEnteredCode());
			}
			System.out.println(this.optionsFile.getAbsolutePath());
			printWriter.close();
		} catch (Exception exception) {
			logger.error("Failed to save roller coaster Options", exception);
		}
	}

	public void loadRCOptions() {
		try {
			if (!this.optionsFile.exists()) return;
			BufferedReader bufferedReader = new BufferedReader(new FileReader(this.optionsFile));
			String s = "";
			while ((s = bufferedReader.readLine()) != null) {
				try {
					String[] splitOptions = s.split(":");

					RCKeyBinding[] rcKeyBindings1 = this.rcKeyBindings;
					int i = rcKeyBindings1.length;
					int j;

					for (j = 0; j < i; j++) {
						RCKeyBinding rcKeyBinding = rcKeyBindings1[j];

						if (splitOptions[0].equals(rcKeyBinding.getRCKeyDescription())) {
							rcKeyBinding.setRCKeyCode(Integer.parseInt(splitOptions[1]));
							rcKeyBinding.setRCEnteredCode(Integer.parseInt(splitOptions[2]));
						}
					}
				} catch (Exception exception) {
					logger.warn("Skipping bad roller coaster option: " + s);
				}
			}
			RCKeyBinding.resetRCKeyBindingArrayAndHash();
			bufferedReader.close();
		} catch (Exception exception1) {
			logger.error("Failed to load roller coaster options", exception1);
		}
	}

	public boolean getOptionOrdinalValue(RCOptionsEnum rcOptionsEnum) {
		switch (SwitchRCOptions.rcOptionIDs[rcOptionsEnum.ordinal()]) {
			case 1:  return this.invertMouse;
			case 2:  return this.sensitivity;
			case 3:  return this.touchscreen;
			default: return false;
		}
	}

	public String getRCKeyBinding(RCOptionsEnum rcOptionsEnum) {
		String s = I18n.format(rcOptionsEnum.getEnumString(), new Object[0]) + ":";

		if (rcOptionsEnum.getEnumBoolean()) {
			boolean flag = this.getOptionOrdinalValue(rcOptionsEnum);
			return flag ? s + I18n.format("options.on", new Object[0]) : s + I18n.format("options.off", new Object[0]);
		} else {
			return s;
		}
	}

	@SideOnly(Side.CLIENT)
	public static enum RCOptionsEnum {
		INVERT_MOUSE("options.invertMouse", false, true),
		SENSITIVITY("options.sensitivity", true, false),
		TOUCHSCREEN("options.touchscreen", false, true);
		private final boolean enumFloat;
		private final boolean enumBoolean;
		private final String enumString;
		private final float valueStep;
		private float valueMin;
		private float valueMax;

		public static RCOptionsEnum getEnumOptions(int ordinal) {
			RCOptions.RCOptionsEnum[] rcOptionsEnums = values();
			int j = rcOptionsEnums.length;

			for (int k = 0; k < j; k++) {
				RCOptions.RCOptionsEnum rcOptionsEnum = rcOptionsEnums[k];

				if (rcOptionsEnum.returnEnumOrdinal() == ordinal) {
					return rcOptionsEnum;
				}
			}

			return null;
		}

		private RCOptionsEnum(String description, boolean enumFloat, boolean enumBoolean) {
			this(description, enumFloat, enumBoolean, 0.0F, 1.0F, 0.0F);
		}

		private RCOptionsEnum(String description, boolean enumFloat, boolean enumBoolean, float valueMin, float valueMax, float valueStep) {
			this.enumString = description;
			this.enumFloat = enumFloat;
			this.enumBoolean = enumBoolean;
			this.valueMin = valueMin;
			this.valueMax = valueMax;
			this.valueStep = valueStep;
		}

		public boolean getEnumFloat() {
			return this.enumFloat;
		}

		public boolean getEnumBoolean() {
			return this.enumBoolean;
		}

		public int returnEnumOrdinal() {
			return this.ordinal();
		}

		public String getEnumString() {
			return this.enumString;
		}

		public float getValueMax() {
			return this.valueMax;
		}

		public void setValueMax(float valueMax) {
			this.valueMax = valueMax;
		}

		public float normalizeValue(float value) {
			return MathHelper.clamp_float((this.snapToStepClamp(value) - this.valueMin) / (this.valueMax - this.valueMin), 0.0F, 1.0F);
		}

		public float deNormalizeValue(float value) {
			return this.snapToStepClamp(this.valueMin + (this.valueMax - this.valueMin) * MathHelper.clamp_float(value, 0.0F, 1.0F));
		}

		public float snapToStepClamp(float value) {
			value = this.snapToStep(value);
			return MathHelper.clamp_float(value, this.valueMin, this.valueMax);
		}

		protected float snapToStep(float value) {
			if (this.valueStep > 0.0F) {
				value = this.valueStep * (float)Math.round(value / this.valueStep);
			}
			return value;
		}

		RCOptionsEnum(String description, boolean enumFloat, boolean enumBoolean, float valueMin, float valueMax, float valueStep, Object obj) {
			this(description, enumFloat, enumBoolean, valueMin, valueMax, valueStep);
		}
	}

	@SideOnly(Side.CLIENT)
	static final class SwitchRCOptions {
		static final int[] rcOptionIDs = new int[RCOptionsEnum.values().length];

		static {
			try {
				rcOptionIDs[RCOptionsEnum.INVERT_MOUSE.ordinal()] = 1;
			} catch (NoSuchFieldError nsfe) {
				;
			}

			try {
				rcOptionIDs[RCOptionsEnum.SENSITIVITY.ordinal()] = 2;
			} catch (NoSuchFieldError nsfe) {
				;
			}

			try {
				rcOptionIDs[RCOptionsEnum.TOUCHSCREEN.ordinal()] = 3;
			} catch (NoSuchFieldError nsfe) {
				;
			}
		}
	}
}
