package com.feinno.pay.entity;

// Generated 2013-5-15 15:58:25

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.feinno.security.entity.IdEntity;
import com.google.common.collect.Lists;

/**
 * Dictionary
 */
@Entity
@Table(name = "pay_dictionary")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "com.feinno.pay.entity")
public class Dictionary extends IdEntity {

	private static final long serialVersionUID = 132655464053472477L;

	@ManyToOne
	@JoinColumn(name = "parentId")
	private Dictionary parent;

	@Column(name = "name", length = 20)
	private String name;

	@Column(name = "code", length = 20)
	private String code;

	@Column(name = "description", length = 100)
	private String description;

	@OneToMany(cascade=CascadeType.PERSIST, mappedBy="parent")
	private List<Dictionary> children = Lists.newArrayList();

	public Dictionary() {
	}

	public Dictionary(Dictionary parent, String name, String code,
			String description, List<Dictionary> children) {
		this.parent = parent;
		this.name = name;
		this.code = code;
		this.description = description;
		this.children = children;
	}

	public Dictionary getParent() {
		return this.parent;
	}

	public void setParent(Dictionary parent) {
		this.parent = parent;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Dictionary> getChildren() {
		return this.children;
	}

	public void setChildren(List<Dictionary> children) {
		this.children = children;
	}

}
