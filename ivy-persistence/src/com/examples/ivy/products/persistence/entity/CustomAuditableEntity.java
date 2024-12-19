package com.examples.ivy.products.persistence.entity;

import static com.examples.ivy.products.persistence.entity.TableAndColumnNameDirectory.CREATED_BY;
import static com.examples.ivy.products.persistence.entity.TableAndColumnNameDirectory.CREATED_DATE;
import static com.examples.ivy.products.persistence.entity.TableAndColumnNameDirectory.DELETED_BY;
import static com.examples.ivy.products.persistence.entity.TableAndColumnNameDirectory.DELETED_DATE;
import static com.examples.ivy.products.persistence.entity.TableAndColumnNameDirectory.ID;
import static com.examples.ivy.products.persistence.entity.TableAndColumnNameDirectory.MODIFIED_BY;
import static com.examples.ivy.products.persistence.entity.TableAndColumnNameDirectory.MODIFIED_DATE;
import static com.examples.ivy.products.persistence.entity.TableAndColumnNameDirectory.VERSION;

import java.util.Objects;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.axonivy.utils.persistence.beans.AuditableIdEntity;

import ch.ivyteam.log.Logger;


@MappedSuperclass
@AttributeOverrides(value = { @AttributeOverride(name = "id", column = @Column(name = ID, length = 32)),
		@AttributeOverride(name = "version", column = @Column(name = VERSION)),
		@AttributeOverride(name = "header.createdByUserName", column = @Column(name = CREATED_BY)),
		@AttributeOverride(name = "header.createdDate", column = @Column(name = CREATED_DATE)),
		@AttributeOverride(name = "header.modifiedByUserName", column = @Column(name = MODIFIED_BY)),
		@AttributeOverride(name = "header.modifiedDate", column = @Column(name = MODIFIED_DATE)),
		@AttributeOverride(name = "header.flaggedDeletedByUserName", column = @Column(name = DELETED_BY)),
		@AttributeOverride(name = "header.flaggedDeletedDate", column = @Column(name = DELETED_DATE)) })
public class CustomAuditableEntity extends AuditableIdEntity {	
	private static final Logger LOG = Logger.getLogger(CustomAuditableEntity.class);
	private static final long serialVersionUID = 1158750877742312571L;

	@Override
	public int hashCode() {
		if (this.getId() == null) {// if id is zero super.hashcode returns wrong hashcode 0
			return System.identityHashCode(this);
		} else {
			return super.hashCode();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getId() == null) {// if id is null super.equals is not consistent with hascode
			return obj != null && Objects.equals(hashCode(), obj.hashCode());
		} else {
			return super.equals(obj);
		}

	}

	@Override
	public boolean hasValidId() {

		if (super.hasValidId()) {
			if (!getId().isBlank()) {
				return true;
			} else {
				LOG.warn("Id is blank for {0}", this);
			}

		}
		return false;
	}

}