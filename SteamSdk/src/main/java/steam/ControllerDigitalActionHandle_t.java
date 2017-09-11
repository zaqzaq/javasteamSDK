package steam;

public class ControllerDigitalActionHandle_t {
	/** in C++ code this is a uint64_t */
	private long controllerDigitalActionHandle;

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
		if (controllerDigitalActionHandle != other.controllerDigitalActionHandle) {
			return false;
		}
		return true;
	}

	public String toString() {
		return "ControllerDigitalActionHandle_t [controllerDigitalActionHandle=" + controllerDigitalActionHandle + "]";
	}
}
