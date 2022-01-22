package com.mooop.board.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "MSB_AUTHORITY")
@DynamicInsert
public class MSBAuthority extends BaseEntity{
	
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
	
}
