package com.thales.reparcar4.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Individu {

	private int id;
	
	@NonNull
	private String email;
	
	@NonNull
	private String telephone;
	
	@NonNull
	private String nom;
	
	@NonNull
	private String prenom;

	@NonNull
	private String motDePasse;

	@NonNull
	private String role;
}
