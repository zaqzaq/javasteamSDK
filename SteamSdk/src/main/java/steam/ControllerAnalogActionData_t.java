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
