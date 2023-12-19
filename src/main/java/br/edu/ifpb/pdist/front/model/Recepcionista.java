package br.edu.ifpb.pdist.front.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Recepcionista implements Serializable{

    // Para garantir que a assinatura de um número seja única , para o uso do @Id
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Este campo é obrigatório!")
    private String nome;

    @Size(min = 5, max = 5, message = "Informe um Matrícula com 5 dígitos!")
    @NotBlank(message = "Este campo é obrigatório!")
    private String matricula;

    @NotBlank(message = "Este campo é obrigatório!")
    private String sexo;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent
    @Past(message = "Data deve ser no passado")
    private Date dataNascimento;

    private String telefone;
}
