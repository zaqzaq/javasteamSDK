/*
 * Copyright (c) 2018 Nimbly Games, LLC all rights reserved
 */

package steam;

public enum EControllerSourceMode {
	k_EControllerSourceMode_None, //
	k_EControllerSourceMode_Dpad, //
	k_EControllerSourceMode_Buttons, //
	k_EControllerSourceMode_FourButtons, //
	k_EControllerSourceMode_AbsoluteMouse, //
	k_EControllerSourceMode_RelativeMouse, //
	k_EControllerSourceMode_JoystickMove, //
	k_EControllerSourceMode_JoystickCamera, //
	k_EControllerSourceMode_ScrollWheel, //
	k_EControllerSourceMode_Trigger, //
	k_EControllerSourceMode_TouchMenu; //

	public static final EControllerSourceMode[] VALUES = values();
}
