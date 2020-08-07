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

public class ControllerHandle_t {
   /**
    * in C++ code this is a uint64_t
    */
   private final long controllerHandle;

	public ControllerHandle_t(long controllerHandle) {
		super();
		this.controllerHandle = controllerHandle;
	}

	public long getControllerHandle() {
		return controllerHandle;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (controllerHandle ^ (controllerHandle >>> 32));
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
		ControllerHandle_t other = (ControllerHandle_t) obj;
      return controllerHandle == other.controllerHandle;
   }

	public String toString() {
		return "ControllerHandle_t [controllerHandle=" + controllerHandle + "]";
	}
}
