package th.co.rocinante.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PARAM")
public class Param {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String group;
	private String code;
	private String value;
	public Long getId() {
		return id;
	}
	public String getGroup() {
		return group;
	}
	public String getCode() {
		return code;
	}
	public String getValue() {
		return value;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
