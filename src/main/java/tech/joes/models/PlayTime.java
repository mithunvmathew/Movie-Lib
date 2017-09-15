package tech.joes.models;

// TODO: Auto-generated Javadoc
/**
 * The Class PlayTime.
 */
public class PlayTime {

	/** The gte. */
	String gte;
	
	/** The lte. */
	String lte;

	/**
	 * Instantiates a new play time.
	 *
	 * @param gte the gte
	 * @param lte the lte
	 */
	public PlayTime(String gte, String lte) {
		super();
		this.gte = gte;
		this.lte = lte;
	}

	public String getGte() {
		return gte;
	}

	public void setGte(String gte) {
		this.gte = gte;
	}

	public String getLte() {
		return lte;
	}

	public void setLte(String lte) {
		this.lte = lte;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PlayTime [gte=" + gte + ", lte=" + lte + "]";
	}

}
