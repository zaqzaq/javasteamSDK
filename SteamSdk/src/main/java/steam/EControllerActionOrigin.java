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

public enum EControllerActionOrigin {
	k_EControllerActionOrigin_None, //
	k_EControllerActionOrigin_A, //
	k_EControllerActionOrigin_B, //
	k_EControllerActionOrigin_X, //
	k_EControllerActionOrigin_Y, //
	k_EControllerActionOrigin_LeftBumper, //
	k_EControllerActionOrigin_RightBumper, //
	k_EControllerActionOrigin_LeftGrip, //
	k_EControllerActionOrigin_RightGrip, //
	k_EControllerActionOrigin_Start, //
	k_EControllerActionOrigin_Back, //
	k_EControllerActionOrigin_LeftPad_Touch, //
	k_EControllerActionOrigin_LeftPad_Swipe, //
	k_EControllerActionOrigin_LeftPad_Click, //
	k_EControllerActionOrigin_LeftPad_DPadNorth, //
	k_EControllerActionOrigin_LeftPad_DPadSouth, //
	k_EControllerActionOrigin_LeftPad_DPadWest, //
	k_EControllerActionOrigin_LeftPad_DPadEast, //
	k_EControllerActionOrigin_RightPad_Touch, //
	k_EControllerActionOrigin_RightPad_Swipe, //
	k_EControllerActionOrigin_RightPad_Click, //
	k_EControllerActionOrigin_RightPad_DPadNorth, //
	k_EControllerActionOrigin_RightPad_DPadSouth, //
	k_EControllerActionOrigin_RightPad_DPadWest, //
	k_EControllerActionOrigin_RightPad_DPadEast, //
	k_EControllerActionOrigin_LeftTrigger_Pull, //
	k_EControllerActionOrigin_LeftTrigger_Click, //
	k_EControllerActionOrigin_RightTrigger_Pull, //
	k_EControllerActionOrigin_RightTrigger_Click, //
	k_EControllerActionOrigin_LeftStick_Move, //
	k_EControllerActionOrigin_LeftStick_Click, //
	k_EControllerActionOrigin_LeftStick_DPadNorth, //
	k_EControllerActionOrigin_LeftStick_DPadSouth, //
	k_EControllerActionOrigin_LeftStick_DPadWest, //
	k_EControllerActionOrigin_LeftStick_DPadEast, //
	k_EControllerActionOrigin_Gyro_Move, //
	k_EControllerActionOrigin_Gyro_Pitch, //
	k_EControllerActionOrigin_Gyro_Yaw, //
	k_EControllerActionOrigin_Gyro_Roll, //
	k_EControllerActionOrigin_Count; //
	
	public static final EControllerActionOrigin[] VALUES = values();
}
