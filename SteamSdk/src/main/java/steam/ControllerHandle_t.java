/*
 * Copyright (c) 2018 Nimbly Games, LLC all rights reserved
 */

package steam;

public class ControllerHandle_t {
	/** in C++ code this is a uint64_t */
	private long controllerHandle;

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
