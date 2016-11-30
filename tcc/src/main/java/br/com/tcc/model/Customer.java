package br.com.tcc.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by hernaneb on 18/10/16.
 */
@Entity
@Table( name="TCC_SO_CUSTOMER" )
public class Customer implements Serializable{

	private static final long serialVersionUID = 1l;

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	private String name;

	private String sexo;

	private String email;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Customer cliente = (Customer) o;

		return id.equals(cliente.id);

	}

	@Override public int hashCode() {
		return id.hashCode();
	}
}
