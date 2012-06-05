package org.jasig.ssp.model;

import java.util.Date;
import java.util.Set;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Where;
import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.model.reference.JournalSource;
import org.jasig.ssp.model.reference.JournalTrack;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class JournalEntry
		extends AbstractAuditable
		implements PersonAssocAuditable, Restricted {

	private static final long serialVersionUID = 1477217415946557983L;

	@Temporal(TemporalType.TIMESTAMP)
	private Date entryDate;

	private String comment;

	@Nullable()
	@ManyToOne()
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "confidentiality_level_id", nullable = false)
	private ConfidentialityLevel confidentialityLevel;

	@ManyToOne
	@JoinColumn(name = "journal_source_id", updatable = false, nullable = false)
	private JournalSource journalSource;

	@ManyToOne
	@JoinColumn(name = "journal_track_id", updatable = false, nullable = false)
	private JournalTrack journalTrack;

	@OneToMany(mappedBy = "journalEntry")
	@Where(clause = "object_status <> 3")
	private Set<JournalEntryDetail> journalEntryDetails;

	@ManyToOne
	@JoinColumn(name = "person_id", updatable = false, nullable = false)
	private Person person;

	@Override
	public ConfidentialityLevel getConfidentialityLevel() {
		return confidentialityLevel;
	}

	public void setConfidentialityLevel(
			final ConfidentialityLevel confidentialityLevel) {
		this.confidentialityLevel = confidentialityLevel;
	}

	public Date getEntryDate() {
		return entryDate == null ? null : new Date(entryDate.getTime());
	}

	public void setEntryDate(@NotNull final Date entryDate) {
		this.entryDate = entryDate == null ? null : new Date(
				entryDate.getTime());
	}

	public String getComment() {
		return comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	public JournalSource getJournalSource() {
		return journalSource;
	}

	public void setJournalSource(@NotNull final JournalSource journalSource) {
		this.journalSource = journalSource;
	}

	public JournalTrack getJournalTrack() {
		return journalTrack;
	}

	public void setJournalTrack(@NotNull final JournalTrack journalTrack) {
		this.journalTrack = journalTrack;
	}

	public Set<JournalEntryDetail> getJournalEntryDetails() {
		return journalEntryDetails;
	}

	public void setJournalEntryDetails(
			final Set<JournalEntryDetail> journalEntryDetails) {
		this.journalEntryDetails = journalEntryDetails;
	}

	@Override
	public Person getPerson() {
		return person;
	}

	@Override
	public void setPerson(@NotNull final Person person) {
		this.person = person;
	}

	@Override
	protected int hashPrime() {
		return 241;
	};

	@Override
	final public int hashCode() { // NOPMD by jon.adams on 5/14/12 1:49 PM
		int result = hashPrime();

		// AbstractAuditable properties
		result *= hashField("id", getId());
		result *= hashField("objectStatus", getObjectStatus());

		result *= hashField("entryDate", entryDate);
		result *= hashField("comment", comment);
		result *= hashField("confidentialityLevel", confidentialityLevel);
		result *= hashField("journalSource", journalSource);
		result *= hashField("journalTrack", journalTrack);
		result *= hashField("person", person);

		// collections are not included in these calculations

		return result;
	}
}