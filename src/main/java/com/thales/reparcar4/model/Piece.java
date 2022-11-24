package com.thales.reparcar4.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Piece {

	@NonNull
	private String code;
	
	private String description;

	private int stock;

	private int disponible;
	
	@NonNull
	private String couleur;
	
	@NonNull
	private float prixAchat;
	
	@NonNull
	private float prix;
	
	private float tva = 20f;
	
	@NonNull
	private Categorie categorie;

	private List<Vehicule> vehicules;
}
