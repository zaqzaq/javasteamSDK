package steam;

public class ControllerActionSetHandle_t {
	/** in C++ code this is a uint64_t */
	private long controllerActionSetHandle;

	public ControllerActionSetHandle_t(long controllerActionSetHandle) {
		super();
		this.controllerActionSetHandle = controllerActionSetHandle;
	}

	public long getControllerActionSetHandle() {
		return controllerActionSetHandle;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (controllerActionSetHandle ^ (controllerActionSetHandle >>> 32));
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
		ControllerActionSetHandle_t other = (ControllerActionSetHandle_t) obj;
		if (controllerActionSetHandle != other.controllerActionSetHandle) {
			return false;
		}
		return true;
	}

	public String toString() {
		return "ControllerActionSetHandle_t [controllerActionSetHandle=" + controllerActionSetHandle + "]";
	}
}
