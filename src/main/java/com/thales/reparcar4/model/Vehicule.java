package com.thales.reparcar4.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Vehicule {

	@NonNull
	private String code;
	
	private String description;
	
	@NonNull
	private String marque;
	
	@NonNull
	private String modele;
	
	@NonNull
	private int moi;
	
	@NonNull
	private int annee;
	
	@NonNull
	private int euroCritere;

	private List<Piece> pieces;

}
