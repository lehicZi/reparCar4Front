package com.thales.reparcar4.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thales.reparcar4.utils.DateDeserializer;
import com.thales.reparcar4.utils.DateSerializer;
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
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	private Date dateDebut;

	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	private Date dateFin;

}
