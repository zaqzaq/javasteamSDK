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

public class ControllerDigitalActionHandle_t {
   /**
    * in C++ code this is a uint64_t
    */
   private final long controllerDigitalActionHandle;

	public ControllerDigitalActionHandle_t(long controllerDigitalActionHandle) {
		super();
		this.controllerDigitalActionHandle = controllerDigitalActionHandle;
	}

	public long getControllerDigitalActionHandle() {
		return controllerDigitalActionHandle;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (controllerDigitalActionHandle ^ (controllerDigitalActionHandle >>> 32));
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ControllerDigitalActionHandle_t other = (ControllerDigitalActionHandle_t) obj;
      return controllerDigitalActionHandle == other.controllerDigitalActionHandle;
   }

	public String toString() {
		return "ControllerDigitalActionHandle_t [controllerDigitalActionHandle=" + controllerDigitalActionHandle + "]";
	}
}
