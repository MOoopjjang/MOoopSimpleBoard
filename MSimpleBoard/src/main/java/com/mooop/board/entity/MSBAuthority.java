package com.mooop.board.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "MSB_AUTHORITY")
@DynamicInsert
public class MSBAuthority {
	
	public MSBAuthority(String authorityName , String authorityDesc) {
		this.authorityName = authorityName;
		this.authorityDesc = authorityDesc;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "AUTHORITY_ID")
	private Long idx;
	
	@Column(name= "AUTHORITY_NAME")
	private String authorityName;
	
	@Column(name = "AUTHORITY_DESC")
	private String authorityDesc;
	
	@Column(name = "DT_CREATE")
	@Temporal(TemporalType.TIMESTAMP)
	@ColumnDefault("CURRENT_TIMESTAMP")
	private Date dtCreate;

}
