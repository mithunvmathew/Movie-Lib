package tech.joes.models;

/**
 * The Class Error.
 */
public class Error {

	/** The code. */
	private int code;
	
	/** The message. */
	private String message;
	
	/** The classname. */
	private String classname;

	/**
	 * Instantiates a new error.
	 */
	public Error() {
	}

	/**
	 * Instantiates a new error.
	 *
	 * @param code the code
	 * @param message the message
	 * @param classname the classname
	 */
	public Error(int code, String message, String classname) {
		super();
		this.code = code;
		this.message = message;
		this.classname = classname;
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the classname.
	 *
	 * @return the classname
	 */
	public String getClassname() {
		return classname;
	}

	/**
	 * Sets the classname.
	 *
	 * @param classname the new classname
	 */
	public void setClassname(String classname) {
		this.classname = classname;
	}

	

}
