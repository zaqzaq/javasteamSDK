/*
 * Copyright 2020 Nimbly Games, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package steam;

public class ISteamController {
	public static final int STEAM_CONTROLLER_MAX_COUNT = 16;

	public static final int STEAM_CONTROLLER_MAX_ANALOG_ACTIONS = 16;

	public static final int STEAM_CONTROLLER_MAX_DIGITAL_ACTIONS = 32;

	public static final int STEAM_CONTROLLER_MAX_ORIGINS = 8;

	/** When sending an option to a specific controller handle, you can send to all controllers via this command UINT64_MAX */
	public static final ControllerHandle_t STEAM_CONTROLLER_HANDLE_ALL_CONTROLLERS = new ControllerHandle_t(0xffffffffffffffffL);

	public static final float STEAM_CONTROLLER_MIN_ANALOG_ACTION_DATA = -1.0f;
	public static final float STEAM_CONTROLLER_MAX_ANALOG_ACTION_DATA = 1.0f;

	/** Init and Shutdown must be called when starting/ending use of this interface */
	public static boolean init() {
		return nInit();
	}

	private static native boolean nInit();

	public static boolean shutdown() {
		return nShutdown();
	}

	private static native boolean nShutdown();

	/**
	 * Pump callback/callresult events Note: SteamAPI_RunCallbacks will do this for you, so you should never need to call this directly.
	 * 
	 */
	public static void runFrame() {
		nRunFrame();
	}

	private static native void nRunFrame();

	/**
	 * Enumerate currently connected controllers handlesOut should point to a STEAM_CONTROLLER_MAX_COUNT sized array of ControllerHandle_t handles Returns the
	 * number of handles written to handlesOut
	 */
	public static ControllerHandle_t[] getConnectedControllers() {
		return nGetConnectedControllers();
	}

	private static native ControllerHandle_t[] nGetConnectedControllers();

	/**
	 * Invokes the Steam overlay and brings up the binding screen Returns false is overlay is disabled / unavailable, or the user is not in Big Picture mode
	 */
	public static boolean showBindingPanel(ControllerHandle_t controllerHandle) {
		return nShowBindingPanel(controllerHandle);
	}

	private static native boolean nShowBindingPanel(ControllerHandle_t controllerHandle);

	/**
	 * ACTION SETS Lookup the handle for an Action Set. Best to do this once on startup, and store the handles for all future API calls.
	 */
	public static ControllerActionSetHandle_t getActionSetHandle(String pszActionSetName) {
		return nGetActionSetHandle(pszActionSetName);
	}

	private static native ControllerActionSetHandle_t nGetActionSetHandle(String pszActionSetName);

	/**
	 * Reconfigure the controller to use the specified action set (ie 'Menu', 'Walk' or 'Drive') // This is cheap, and can be safely called repeatedly. It's
	 * often easier to repeatedly call it in // your state loops, instead of trying to place it in all of your state transitions.
	 */
	public static void activateActionSet(ControllerHandle_t controllerHandle, ControllerActionSetHandle_t actionSetHandle) {
		nActivateActionSet(controllerHandle, actionSetHandle);
	}

	private static native void nActivateActionSet(ControllerHandle_t controllerHandle, ControllerActionSetHandle_t actionSetHandle);

	public static ControllerActionSetHandle_t getCurrentActionSet(ControllerHandle_t controllerHandle) {
		return nGetCurrentActionSet(controllerHandle);
	}

	public static native ControllerActionSetHandle_t nGetCurrentActionSet(ControllerHandle_t controllerHandle);

	/**
	 * ACTIONS // Lookup the handle for a digital action. Best to do this once on startup, and store the handles for all future API calls.
	 */
	public static ControllerDigitalActionHandle_t getDigitalActionHandle(String pszActionName) {
		return nGetDigitalActionHandle(pszActionName);
	}

	private static native ControllerDigitalActionHandle_t nGetDigitalActionHandle(String pszActionName);

	/** Returns the current state of the supplied digital game action */
	public static ControllerDigitalActionData_t getDigitalActionData(ControllerHandle_t controllerHandle, ControllerDigitalActionHandle_t digitalActionHandle) {
		return nGetDigitalActionData(controllerHandle, digitalActionHandle);
	}

	public static native ControllerDigitalActionData_t nGetDigitalActionData(ControllerHandle_t controllerHandle, ControllerDigitalActionHandle_t digitalActionHandle);

	/**
	 * Get the origin(s) for a digital action within an action set. Returns the number of origins supplied in originsOut. Use this to display the appropriate
	 * on-screen prompt for the action. originsOut should point to a STEAM_CONTROLLER_MAX_ORIGINS sized array of EControllerActionOrigin handles
	 */
	public static EControllerActionOrigin[] getDigitalActionOrigins(ControllerHandle_t controllerHandle, ControllerActionSetHandle_t actionSetHandle,
			ControllerDigitalActionHandle_t digitalActionHandle) {
		return nGetDigitalActionOrigins(controllerHandle, actionSetHandle, digitalActionHandle);
	}

	private static native EControllerActionOrigin[] nGetDigitalActionOrigins(ControllerHandle_t controllerHandle, ControllerActionSetHandle_t actionSetHandle,
			ControllerDigitalActionHandle_t digitalActionHandle);

	/** Lookup the handle for an analog action. Best to do this once on startup, and store the handles for all future API calls. */
	public static ControllerAnalogActionHandle_t getAnalogActionHandle(String pszActionName) {
		return nGetAnalogActionHandle(pszActionName);
	}

	public static native ControllerAnalogActionHandle_t nGetAnalogActionHandle(String pszActionName);

	/** Returns the current state of these supplied analog game action */
	public static ControllerAnalogActionData_t getAnalogActionData(ControllerHandle_t controllerHandle, ControllerAnalogActionHandle_t analogActionHandle) {
		return nGetAnalogActionData(controllerHandle, analogActionHandle);
	}

	private static native ControllerAnalogActionData_t nGetAnalogActionData(ControllerHandle_t controllerHandle, ControllerAnalogActionHandle_t analogActionHandle);

	/**
	 * Get the origin(s) for an analog action within an action set. Returns the number of origins supplied in originsOut. Use this to display the appropriate
	 * on-screen prompt for the action. originsOut should point to a STEAM_CONTROLLER_MAX_ORIGINS sized array of EControllerActionOrigin handles
	 */
	public static EControllerActionOrigin[] getAnalogActionOrigins(ControllerHandle_t controllerHandle, ControllerActionSetHandle_t actionSetHandle,
			ControllerAnalogActionHandle_t analogActionHandle) {
		return nGetAnalogActionOrigins(controllerHandle, actionSetHandle, analogActionHandle);
	}

	private static native EControllerActionOrigin[] nGetAnalogActionOrigins(ControllerHandle_t controllerHandle, ControllerActionSetHandle_t actionSetHandle,
			ControllerAnalogActionHandle_t analogActionHandle);

	public static void stopAnalogActionMomentum(ControllerHandle_t controllerHandle, ControllerAnalogActionHandle_t eAction) {
		nStopAnalogActionMomentum(controllerHandle, eAction);
	}

	private static native void nStopAnalogActionMomentum(ControllerHandle_t controllerHandle, ControllerAnalogActionHandle_t eAction);

	/** Trigger a haptic pulse on a controller */
	public static void triggerHapticPulse(ControllerHandle_t controllerHandle, ESteamControllerPad eTargetPad, int usDurationMicroSec) {
		nTriggerHapticPulse(controllerHandle, eTargetPad, usDurationMicroSec);
	}

	private static native void nTriggerHapticPulse(ControllerHandle_t controllerHandle, ESteamControllerPad eTargetPad, int usDurationMicroSec);
}
