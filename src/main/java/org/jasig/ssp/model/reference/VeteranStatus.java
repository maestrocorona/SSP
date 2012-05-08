package org.jasig.ssp.model.reference;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;

/**
 * VeteranStatus reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class VeteranStatus extends AbstractReference implements Serializable {

	private static final long serialVersionUID = -8572858642333315262L;

	@Column(nullable = false)
	@NotNull
	private short sortOrder = 0; // NOPMD by jon.adams on 5/4/12 1:41 PM

	/**
	 * Constructor
	 */
	public VeteranStatus() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public VeteranStatus(@NotNull final UUID id) {
		super(id);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 * @param name
	 *            Name; required; max 80 characters
	 * @param description
	 *            Description; max 64000 characters
	 * @param sortOrder
	 *            Default sort order when displaying objects to the user
	 */
	public VeteranStatus(@NotNull final UUID id, @NotNull final String name,
			final String description, final short sortOrder) { // NOPMD by jon
		super(id, name, description);
		this.sortOrder = sortOrder;
	}

	/**
	 * Gets the default sort order when displaying an item list to the user
	 * 
	 * @return the sortOrder
	 */
	public short getSortOrder() { // NOPMD by jon.adams on 5/4/12 1:41 PM
		return sortOrder;
	}

	/**
	 * Sets the default sort order when displaying an item list to the user
	 * 
	 * @param sortOrder
	 *            the sortOrder to set
	 */
	public void setSortOrder(short sortOrder) { // NOPMD by jon on 5/4/12 11:16
		this.sortOrder = sortOrder;
	}

	/**
	 * Unique (amongst all Models in the system) prime for use by
	 * {@link #hashCode()}
	 */
	@Override
	protected int hashPrime() {
		return 149;
	};

	@Override
	public int hashCode() { // NOPMD by jon.adams on 5/3/12 11:48 AM
		return hashPrime() * super.hashCode()
				* (sortOrder > 0 ? sortOrder : hashPrime());
	}
}
