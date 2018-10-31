/*
 * Copyright (c) 2018 Nimbly Games, LLC all rights reserved
 */

package steam;

public class ControllerAnalogActionHandle_t {
	/** in C++ code this is a uint64_t */
	private long controllerAnalogActionHandle;

	public ControllerAnalogActionHandle_t(long controllerAnalogActionHandle) {
		super();
		this.controllerAnalogActionHandle = controllerAnalogActionHandle;
	}

	public long getControllerAnalogActionHandle() {
		return controllerAnalogActionHandle;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (controllerAnalogActionHandle ^ (controllerAnalogActionHandle >>> 32));
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
		ControllerAnalogActionHandle_t other = (ControllerAnalogActionHandle_t) obj;
      return controllerAnalogActionHandle == other.controllerAnalogActionHandle;
   }

	public String toString() {
		return "ControllerAnalogActionHandle_t [controllerAnalogActionHandle=" + controllerAnalogActionHandle + "]";
	}
}
