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
