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

public class ControllerAnalogActionData_t {
	/** Type of data coming from this action, this will match what got specified in the action set */
	public EControllerSourceMode eMode;

	/** The current state of this action; will be delta updates for mouse actions */
	public float x;

	/** The current state of this action; will be delta updates for mouse actions */
	public float y;

	/** Whether or not this action is currently available to be bound in the active action set */
	public boolean bActive;
}
