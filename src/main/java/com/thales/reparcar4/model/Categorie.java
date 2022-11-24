package com.thales.reparcar4.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Categorie {

	@NonNull
	private String code;
	
	private String description;
	
	@NonNull
	private String nom;

	@NonNull
	private Date dateDebut;

	private Date dateFin;

}
