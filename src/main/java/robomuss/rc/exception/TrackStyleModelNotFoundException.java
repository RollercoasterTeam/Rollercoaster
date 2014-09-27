package robomuss.rc.exception;

public class TrackStyleModelNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4796791161139031846L;

	private String modelType;
	private String modelLocation;
	
	public TrackStyleModelNotFoundException(String modelType, String modelLocation) {
		this.modelType = modelType;
		this.modelLocation = modelLocation;
	}
	
	@Override
	public String getMessage() {
		return "The provided location for the '" + modelType + "' model was invalid (" + modelLocation + ")";
	}
}
